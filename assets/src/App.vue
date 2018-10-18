<template>
  <div id="app">
    <router-view/>
    <alert-view></alert-view>
  </div>
</template>

<script>
import Alert from '@/components/common/Alert.vue'
export default {
  name: 'App',
  components:{
  	'alert-view': Alert,
  }
}
</script>

<style>
</style>
