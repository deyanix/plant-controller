<template>

  <q-layout view="hHh Lpr lFf">

    <q-header elevated>
      <q-toolbar>
        <q-btn
          flat
          dense
          round
          icon="menu"
          aria-label="Menu"
          @click="toggleLeftDrawer"
        />

        <q-toolbar-title>
          Plant Controller
        </q-toolbar-title>

      </q-toolbar>
    </q-header>

    <q-drawer
      class="bg-light-blue-2"
      v-model="leftDrawerOpen"
      show-if-above
      bordered
    >
      <q-list>
        <EssentialLink
          v-for="link in essentialLinks"
          :key="link.title"
          v-bind="link"
          disable
        />
      </q-list>
    </q-drawer>

    <q-page-container class="bg-light-blue-8">
      <router-view />
    </q-page-container>
  </q-layout>
</template>

<script>
import { defineComponent, ref } from 'vue'
import EssentialLink from 'components/EssentialLink.vue'

import { Loading, useQuasar } from 'quasar'

const linksList = [
  {
    title: 'Account',
    caption: 'Show accounts information',
    icon: 'person',
    link: '#'
  },
  {
    title: 'Plants',
    caption: 'Show your plants',
    icon: 'emoji_nature',
    link: '#'
  },
  {
    title: 'Settings',
    caption: 'Change your account settings',
    icon: 'settings',
    link: '#'
  },
]

export default defineComponent({

  onBeforeMount(){
    Loading.show({
      message: "Creating a new booking..."
    });

  },

  name: 'MainLayout',

  components: {
    EssentialLink
  },

  setup () {
    const leftDrawerOpen = ref(false)

    const $q = useQuasar()

   $q.loading.show({
      delay: 400 // ms
    })

  $q.loading.hide()

    return {
      essentialLinks: linksList,
      leftDrawerOpen,
      toggleLeftDrawer () {
        leftDrawerOpen.value = !leftDrawerOpen.value
      }
    }
  },

})
</script>
