<script setup>
  import { ref, reactive, computed, onMounted } from 'vue'
  //import { mainDashboardTable } from './data/mainDashboardTable.js'
  import TrafficLight from 'src/components/TrafficLight.vue'

  const rows = ref([])
  const columns = reactive([
    {
      name: 'Id',
      field: 'id',
      align: 'left',
      sortable: true
    },
    {
      name: 'Typ',
      field: 'type',
      align: 'left',
      sortable: true
    },
    {
      name: 'Timestamp',
      field: 'timestamp',
      align: 'left',
      sortable: true
    },
    {
      name: 'Value',
      field: 'value',
      align: 'left',
      sortable: true
    }
    
  ])
  const loading = ref(false)
  const hideHeader = ref(false)
  const hideBottom = ref(false)

  const deviceStatus = computed(() => {
    return false
  })

  onMounted(() => {
    //rows.value = mainDashboardTable
    rows.value = [
    { id: 1, typ: 'typ 1', timestamp: '2023-03-22T10:30:00', value: 2137 },
    { id: 2, typ: 'typ 2', timestamp: '2023-03-22T11:30:00', value: 2138 },
    { id: 3, typ: 'typ 3', timestamp: '2023-03-22T12:30:00', value: 2139 },
    { id: 4, typ: 'typ 4', timestamp: '2023-03-22T13:30:00', value: 2140 },
    { id: 5, typ: 'typ 5', timestamp: '2023-03-22T14:30:00', value: 2141 },
    { id: 6, typ: 'typ 1', timestamp: '2023-03-22T15:30:00', value: 2142 },
    { id: 7, typ: 'typ 2', timestamp: '2023-03-22T16:30:00', value: 2143 },
    { id: 8, typ: 'typ 3', timestamp: '2023-03-22T17:30:00', value: 2144 },
    { id: 9, typ: 'typ 4', timestamp: '2023-03-22T18:30:00', value: 2145 },
    { id: 10, typ: 'typ 5', timestamp: '2023-03-22T19:30:00', value: 2146 },
    { id: 11, typ: 'typ 1', timestamp: '2023-03-22T20:30:00', value: 2147 },
    { id: 12, typ: 'typ 2', timestamp: '2023-03-22T21:30:00', value: 2148 },
    { id: 13, typ: 'typ 3', timestamp: '2023-03-22T22:30:00', value: 2149 },
    { id: 14, typ: 'typ 4', timestamp: '2023-03-22T23:30:00', value: 2150 },
    { id: 15, typ: 'typ 5', timestamp: '2023-03-23T00:30:00', value: 2151 },
    { id: 16, typ: 'typ 1', timestamp: '2023-03-23T01:30:00', value: 2152 }
    ]
  })
</script>

<template>
  <q-page padding class="">
    <q-card>
      <q-card-section>
        <div class="row">
          <div class="q-pa-md col-6">
            <h1 class="text-h4">Dashboard</h1>
            <p class="text-subtitle2">
              Device Status: {{ deviceStatus }}
            </p>
          </div>
          <div class="col-6 bg-dark">
            <h4 class="text-accent q-pa-md">
            Tutaj zrobię sygnalizator świetlny, który pokaże status urządzenia.
            </h4>
            <TrafficLight>
            </TrafficLight>
          </div>
        </div>
      </q-card-section>
      <q-card-section>
        <q-table
          row-key="id"
          v-bind:pagination="{ rowsPerPage: 10 }"
          v-bind:rows="rows"
          v-bind:columns="columns"
          v-bind:loading="loading"
          v-bind:hide-header="hideHeader"
          v-bind:hide-bottom="hideBottom"
        />
      </q-card-section>
    </q-card>
  </q-page>
</template>
