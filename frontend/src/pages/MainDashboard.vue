<script setup>
  import { ref, reactive, computed, onMounted } from 'vue'
  import SignallingDiode from 'src/components/SignallingDiode.vue';
  import CurrentHumidity from 'src/components/CurrentHumidity.vue'

  import axios from 'axios';
  import {format} from "date-fns";
  import {Loading} from "quasar";

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
  const pagination = ref({rowsPerPage: 10});
  const isActive = ref(false)

  const toggleActive = () => {
    isActive.value = !isActive.value
  }

  onMounted(async () => {
    Loading.show({message: 'Loading data...'});

    try {
      const [statusData, historyData] =
        await Promise.all([
          axios.get('https://plant-controller.deyanix.eu/sensors/4/status'),
          axios.get('https://plant-controller.deyanix.eu/sensors/4/measurements')
        ]);
      currentHumidityValue.value = statusData.data.humidity;
      isActive.value = statusData.data.active;
      rows.value = historyData.data;
    } finally {
      Loading.hide();
    }
  })


</script>

<template>
  <q-page padding>
    <q-card>
      <q-card-section>
        <div class="row">
          <div class="col-4">
            <div class="text-subtitle2 q-mb-sm">Device status</div>
            <SignallingDiode v-bind:isActive="isActive" v-bind:toggleActive="toggleActive" />
          </div>
          <div class="col-4">
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
        />
      </q-card-section>
    </q-card>
  </q-page>
</template>
