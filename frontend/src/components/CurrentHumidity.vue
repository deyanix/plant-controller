<template>
  <div class="q-mb-sm">Latest humidity</div>
  <div class="text-subtitle2"> {{ value.toFixed(2) }} % </div>
</template>

<script setup>
const props = defineProps({
  value: {
    type: Number,
    default: 0
  },
})
</script>
