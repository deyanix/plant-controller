<template>
  <div class="traffic-light">
    <div class="light red" :class="{ active: isRedOn.value }"></div>
    <div class="light yellow" :class="{ active: isYellowOn.value }"></div>
    <div class="light green" :class="{ active: isGreenOn.value }"></div>
  </div>
</template>

<script>
import { ref, watch } from 'vue'

export default {
  name: 'TrafficLight',
  props: {
    initialColor: {
      type: String,
      default: 'red'
    }
  },
  setup(props) {
    const isRedOn = ref(props.initialColor === 'red')
    const isYellowOn = ref(props.initialColor === 'yellow')
    const isGreenOn = ref(props.initialColor === 'green')

    watch(isRedOn, (value) => {
      if (value) {
        isYellowOn.value = false
        isGreenOn.value = false
      }
    })

    watch(isYellowOn, (value) => {
      if (value) {
        isRedOn.value = false
        isGreenOn.value = false
      }
    })

    watch(isGreenOn, (value) => {
      if (value) {
        isRedOn.value = false
        isYellowOn.value = false
      }
    })

    return {
      isRedOn,
      isYellowOn,
      isGreenOn
    }
  }
}
</script>

<style scoped>
.light {
  width: 60px;
  height: 60px;
  border-radius: 50%;
  margin: 10px;
  transition: background-color 0.5s ease;
}

.red {
  background-color: red;
}

.yellow {
  background-color: yellow;
}

.green {
  background-color: green;
}

.active {
  box-shadow: 0px 0px 20px #fff, 0px 0px 50px #fff;
}
</style>
