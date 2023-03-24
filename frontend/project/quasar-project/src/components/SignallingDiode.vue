<template>
  <div :class="{ 'led-on': isActive, 'led-off': !isActive }" class="shadow-5"></div>
</template>

<script setup>
const props = defineProps({
  isActive: Boolean,
  toggleActive: Function
})
</script>

<style lang="scss">
  @import "src/css/signallingDiode.scss";
</style>
