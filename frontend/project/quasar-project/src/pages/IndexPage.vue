<template>
  <q-page class="flex flex-center bg-light-blue-9">
    <img
      alt="Quasar logo"
      src="~assets/quasar-logo-vertical.svg"
      style="width: 200px; height: 200px"
    >
    <div class="q-pa-md">
    <q-btn color="purple" @click="Loading.show()" label="Show Loading" />
  </div>
  </q-page>
</template>

<script>
import { defineComponent } from 'vue'

export default defineComponent({
  name: 'IndexPage'
})
</script>
