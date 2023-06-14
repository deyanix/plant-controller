<script setup>
import {ref, onMounted, shallowRef, onBeforeMount, computed, onUnmounted} from 'vue'
import SignallingDiode from 'src/components/SignallingDiode.vue';
import CurrentHumidity from 'src/components/CurrentHumidity.vue'
import {format} from "date-fns";
import {getCssVar, Loading} from "quasar";
import {api, wsClient} from "boot/api";
import {Line} from "vue-chartjs";
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend, TimeScale
} from 'chart.js';
import 'chartjs-adapter-luxon';
import logo from 'src/assets/logo.png';

const data = computed(() => ({
  datasets: [{
    label: 'Humidity',
    data: rows.value.map(r => ({
      x: new Date(r.date),
      y: r.value
    })),
    borderColor: getCssVar('primary'),
    backgroundColor: '#00892b80'
  }],
}))

const options = {
  responsive: true,
  maintainAspectRatio: false,
  plugins: {
    title: {
      display: true,
      text: 'Last week measurements'
    }
  },
  scales: {
    x: {
      type: 'time',
      time: {
        unit: 'day'
      },
      title: {
        display: true,
        text: 'Date'
      }
    },
    y: {
      min: 0,
      max: 100,
      ticks: {
        callback: function(value) {
          return value + '%';
        }
      }
    }
  }
}

const hourData = computed(() => ({
  datasets: [{
    label: 'Humidity',
    data: hourRows.value.map(r => ({
      x: new Date(r.date),
      y: r.value
    })),
    borderColor: getCssVar('primary'),
    backgroundColor: '#00892b80'
  }],
}))

const hourOptions = {
  responsive: true,
  maintainAspectRatio: false,
  plugins: {
    title: {
      display: true,
      text: 'Last hour measurements'
    }
  },
  scales: {
    x: {
      type: 'time',
      time: {
        unit: 'minute'
      },
      title: {
        display: true,
        text: 'Date'
      }
    },
    y: {
      min: 0,
      max: 100,
      ticks: {
        callback: function(value) {
          return value + '%';
        }
      }
    }
  }
}

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
const rows = ref([]);
const hourRows = ref([]);
const pagination = ref({rowsPerPage: 10});
const wsUnsubscribe = shallowRef();
const wsTimeout = ref();

async function fetchSensors() {
  const sensorsRes = await api.get('/sensors');
  sensors.value = sensorsRes.data;
  sensor.value = sensors.value[0];
}

async function updateHourHistory(id) {
  hourRows.value = (await api.get(`/sensors/${id}/measurements/last-hour`)).data;
}

async function fetchSensor(id) {
  const [statusRes, historyRes] =
    await Promise.all([
      api.get(`/sensors/${id}/status`),
      api.get(`/sensors/${id}/measurements/last-week`),
      updateHourHistory(id)
    ]);
  rows.value = historyRes.data;
  updateStatus(id, statusRes.data);
}

function updateStatus(id, data) {
  if (id === status.value?.id && status.value?.preferred === true && data?.preferred === false) {
    if (("Notification" in window) && Notification.permission === "granted") {
      new Notification("Your plant is drying up!", {
        icon: logo
      });
    }
  }
  data.id = id;
  status.value = data;
  clearTimeout(wsTimeout.value);
  if (data.active) {
    wsTimeout.value = setTimeout(() => {
      wsClient.publish({destination: `/app/sensors/${id}/status`})
    }, sensor.value?.duration * 1000 * 3)
  }
}

function subscribeStatus(id) {
  wsUnsubscribe.value?.();
  const {unsubscribe} = wsClient.subscribe(`/topic/sensors/${id}/status`, message => {
    if (sensor.value?.id === id) {
      updateStatus(id, JSON.parse(message.body));
    }
  });
  wsClient.subscribe(`/topic/sensors/${id}/history`, message => {
    if (sensor.value?.id === id) {
      hourRows.value = JSON.parse(message.body);
    }
  });
  wsUnsubscribe.value = unsubscribe;
}

async function onChangeSensor() {
  Loading.show({message: 'Loading sensor data...'});
  try {
    const id = sensor.value?.id;
    if (id !== undefined) {
      await fetchSensor(id);
      subscribeStatus(id);
    }
  } finally {
    Loading.hide();
  }
}

onBeforeMount(() => {
  if ("Notification" in window) {
    Notification.requestPermission()
  }

  ChartJS.register(
    CategoryScale,
    LinearScale,
    PointElement,
    LineElement,
    Title,
    Tooltip,
    Legend,
    TimeScale
  )
})

onMounted(async () => {
  Loading.show({message: 'Loading sensors...'});
  try {
    await fetchSensors();
    await onChangeSensor();
  } finally {
    Loading.hide();
  }
});

onUnmounted(() => {
  wsUnsubscribe.value?.();
})
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
      <q-separator/>
      <q-card-section style="height: 400px">
        <q-scroll-area class="fit">
            <Line :data="hourData" :options="hourOptions" style="min-width: 520px; max-width: 100%"/>
        </q-scroll-area>
      </q-card-section>
      <q-separator/>
      <q-card-section style="height: 400px">
        <q-scroll-area class="fit">
          <Line :data="data" :options="options" style="min-width: 520px; max-width: 100%"/>
        </q-scroll-area>
      </q-card-section>
      <q-separator/>
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
