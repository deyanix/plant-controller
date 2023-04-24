<template>
  <div
    :class="{ 'led-on': active, 'led-off': !active }"
    class="shadow-5 d-inline-block"
  />
</template>

<script setup>
defineProps({
  active: Boolean
})
</script>

<style lang="scss">
.led-on {
  background-color: green;
  width: 20px;
  height: 20px;
  border-radius: 50%;
}

.led-off {
  background-color: red;
  width: 20px;
  height: 20px;
  border-radius: 50%;
}
</style>
