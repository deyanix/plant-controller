<template>
  <q-layout view="hHh Lpr lFf">
    <q-header elevated>
      <q-toolbar>
        <q-toolbar-title class="row q-gutter-x-sm items-center">
          <img :src="logo" class="q-my-xs" style="height: 26px" alt="Logo"/>
          <div>Plant Controller</div>
        </q-toolbar-title>

      </q-toolbar>
    </q-header>

    <q-page-container class="bg-light-blue-1">
      <router-view />
    </q-page-container>
  </q-layout>
</template>

<script setup>
import logo from 'src/assets/logo.png';
</script>
