<script setup>
  import { ref, reactive, computed, onMounted } from 'vue'
  import SignallingDiode from 'src/components/SignallingDiode.vue';
  import CurrentHumidity from 'src/components/CurrentHumidity.vue'

  import axios from 'axios';
  import {format} from "date-fns";

  const props = defineProps({
  currentHumidity: String,
  changeCurrentHumidity: Function
  })

  const currentHumidityValue = ref(props.currentHumidity)

  function updateCurrentHumidity() {
    props.changeCurrentHumidity(currentHumidityValue.value)
  }

  onMounted(() => {
    currentHumidityValue.value = props.currentHumidity
  })

  const rows = ref([])
  const columns = reactive([
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
      format: (val) => `${val} %`
    }

  ])
  const loading = ref(false);
  const pagination = ref({rowsPerPage: 10});
  const isActive = ref(false)

  const toggleActive = () => {
    isActive.value = !isActive.value
  }

  onMounted(async () => {
    const [statusData, historyData] =
      await Promise.all([
        axios.get('http://localhost:8080/sensors/1/status'),
        axios.get('http://localhost:8080/sensors/1/measurements')
      ]);
    currentHumidityValue.value = statusData.data.humidity;
    isActive.value = statusData.data.active;
    rows.value = historyData.data;
  })


</script>

<template>
  <q-page padding>
    <q-card class="bg-light-blue-1">
      <q-card-section>
        <div class="row">
          <div class="col-12">
            <h1 class="text-h4">Dashboard</h1>
          </div>
          <div class="q-py-md col-4 d-inline-flex">
            <div class="text-subtitle2 text-left q-mb-sm">Device status</div>
            <div class="d-inline-block text-right">
              <SignallingDiode v-bind:isActive="isActive" v-bind:toggleActive="toggleActive" />
            </div>
          </div>
          <div class="col-4 q-pa-md">
            <span class="text-subtitle2">
              <CurrentHumidity :current-humidity="currentHumidityValue" />
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
          :loading="loading"
        />
      </q-card-section>
    </q-card>
  </q-page>
</template>
