<script setup>
  import { ref, reactive, computed, onMounted } from 'vue'
  //import { mainDashboardTable } from './data/mainDashboardTable.js'


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
    { id: 1, typ: 'typ 1', timestamp: '2023-03-22T10:30:00', value: 'Value 1' },
    { id: 2, typ: 'typ 2', timestamp: '2023-03-22T11:30:00', value: 'Value 2' },
    { id: 3, typ: 'typ 3', timestamp: '2023-03-22T12:30:00', value: 'Value 3' },
    { id: 4, typ: 'typ 4', timestamp: '2023-03-22T13:30:00', value: 'Value 4' },
    { id: 5, typ: 'typ 5', timestamp: '2023-03-22T14:30:00', value: 'Value 5' },
    { id: 6, typ: 'typ 1', timestamp: '2023-03-22T15:30:00', value: 'Value 6' },
    { id: 7, typ: 'typ 2', timestamp: '2023-03-22T16:30:00', value: 'Value 7' },
    { id: 8, typ: 'typ 3', timestamp: '2023-03-22T17:30:00', value: 'Value 8' },
    { id: 9, typ: 'typ 4', timestamp: '2023-03-22T18:30:00', value: 'Value 9' },
    { id: 10, typ: 'typ 5', timestamp: '2023-03-22T19:30:00', value: 'Value 10' },
    { id: 11, typ: 'typ 1', timestamp: '2023-03-22T20:30:00', value: 'Value 11' },
    { id: 12, typ: 'typ 2', timestamp: '2023-03-22T21:30:00', value: 'Value 12' },
    { id: 13, typ: 'typ 3', timestamp: '2023-03-22T22:30:00', value: 'Value 13' },
    { id: 14, typ: 'typ 4', timestamp: '2023-03-22T23:30:00', value: 'Value 14' },
    { id: 15, typ: 'typ 5', timestamp: '2023-03-23T00:30:00', value: 'Value 15' },
    { id: 16, typ: 'typ 1', timestamp: '2023-03-23T01:30:00', value: 'Value 16' }
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
