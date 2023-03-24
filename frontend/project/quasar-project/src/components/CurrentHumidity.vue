<template>
  <div>Current humidity:</div>
  <div class="text-subtitle2"> {{ currentHumidity }}</div>
</template>

<script setup>
const props = defineProps({
  currentHumidity: String,
  changeCurrentHumidity: Function
})
</script>
