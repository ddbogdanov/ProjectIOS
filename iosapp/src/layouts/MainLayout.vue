<template>
  <q-layout view="hHh Lpr fFf"> <!-- Be sure to play with the Layout demo on docs -->

    <!-- (Optional) The Header -->
    <q-header>
      <q-toolbar>
        

        <q-toolbar-title>
          Innovative Stencils IOS
        </q-toolbar-title>
       
      </q-toolbar>

      <q-tabs>
        <q-route-tab
          icon="assignment"
          to="/"
          replace
          label="Dashboard"
        />
        <q-route-tab
          icon="assignment"
          to="/Products"
          replace
          label="Products"
        />
        <q-route-tab
          icon="assignment"
          to="/Reports"
          replace
          label="Reports"
        />
        <q-route-tab
          icon="assignment"
          to="/ProductOrders"
          replace
          label="Product Orders"
        />
        <q-route-tab
          icon="assignment"
          to="/Materials"
          replace
          label="Materials"
        />
      </q-tabs>
    </q-header>

    

    

    <q-page-container>
      <!-- This is where pages get injected -->
      <router-view />
    </q-page-container>

  </q-layout>
</template>

<script>
import { ref } from 'vue'

export default {
  // name: 'LayoutName',

  setup () {
    const leftDrawerOpen = ref(false)

    return {
      leftDrawerOpen,
      toggleLeftDrawer () {
        leftDrawerOpen.value = !leftDrawerOpen.value
      }
    }
  }
}
</script>
