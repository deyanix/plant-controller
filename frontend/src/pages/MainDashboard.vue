<script setup>
import {ref, onMounted} from 'vue'
import SignallingDiode from 'src/components/SignallingDiode.vue';
import CurrentHumidity from 'src/components/CurrentHumidity.vue'
import axios from 'axios';
import {format} from "date-fns";
import {Loading} from "quasar";

const columns = [
  {
    label: 'Date',
    field: 'date',
    align: 'left',
    sortable: true,
    format: (val) => format(new Date(val), 'dd MMMM yyyy HH:mm')
  },
  {
    label: 'Value',
    field: 'value',
    align: 'left',
    sortable: true,
    format: (val) => `${val.toFixed(2)} %`
  }
];

const sensors = ref([]);
const sensor = ref();
const status = ref();
const rows = ref([])
const pagination = ref({rowsPerPage: 10});

async function fetchSensors() {
  const sensorsRes = await axios.get('https://plant-controller.deyanix.eu/sensors');
  sensors.value = sensorsRes.data;
  sensor.value = sensors.value[0]?.id;
}

async function fetchSensor(id) {
  const [statusRes, historyRes] =
    await Promise.all([
      axios.get(`https://plant-controller.deyanix.eu/sensors/${id}/status`),
      axios.get(`https://plant-controller.deyanix.eu/sensors/${id}/measurements`)
    ]);
  status.value = statusRes.data;
  rows.value = historyRes.data;
}

async function onChangeSensor() {
  Loading.show({message: 'Loading sensor data...'});
  try {
    if (sensor.value !== undefined) {
      await fetchSensor(sensor.value);
    }
  } finally {
    Loading.hide();
  }
}

onMounted(async () => {
  Loading.show({message: 'Loading sensors...'});
  try {
    await fetchSensors();
    await onChangeSensor();
  } finally {
    Loading.hide();
  }
});
</script>

<template>
  <q-page padding>
    <q-card>
      <q-card-section>
        <div class="row q-mb-md">
          <div class="col-12 col-md-4">
            <div class="row q-gutter-md items-center">
              <q-select
                v-model="sensor"
                @update:model-value="onChangeSensor"
                :options="sensors"
                option-label="name"
                option-value="id"
                emit-value
                map-options
                filled
                dense
                label="Sensor"
                class="col"
              />
              <q-btn
                icon="refresh"
                dense
                round
                flat
                @click="onChangeSensor"
              />
            </div>
          </div>
        </div>
        <div class="row q-col-gutter-md">
          <div class="col-12 col-md-4">
            <div class="text-subtitle2 q-mb-sm">Device status</div>
            <SignallingDiode
              :active="status?.active"
            />
          </div>
          <div class="col-12 col-md-4">
            <span class="text-subtitle2">
              <CurrentHumidity
                :value="status?.humidity"
              />
            </span>
          </div>
        </div>
      </q-card-section>
      <q-card-section>
        <q-table
          row-key="id"
          :pagination="pagination"
          :rows="rows"
          :columns="columns"
        />
      </q-card-section>
    </q-card>
  </q-page>
</template>
