<template>
  <div v-loading="loading" :style="'height:'+ height">
    <iframe :src="src" title="druid" name="druid" frameborder="no" class="page-content" scrolling="auto" />
  </div>
</template>
<script>
export default {
  name: 'Druid',
  data() {
    return {
      src: process.env.VUE_APP_BASE_URL + '/druid/index.html',
      height: document.documentElement.clientHeight - 84 + 'px;',
      loading: true
    }
  },
  mounted: function() {
    setTimeout(() => {
      this.loading = false
    }, 230)
    const that = this
    window.onresize = function temp() {
      that.height = document.documentElement.clientHeight - 84 + 'px;'
    }
  }
}
</script>
