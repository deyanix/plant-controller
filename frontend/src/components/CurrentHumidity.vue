<template>
  <div class="q-mb-sm">Current humidity</div>
  <div class="text-subtitle2"> {{ currentHumidity }} % </div>
</template>

<script setup>
const props = defineProps({
  currentHumidity: String
})
</script>
