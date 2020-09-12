<template>
  <div v-loading="loading" :style="'height:'+ height">
    <iframe :src="src" title="swagger" name="swagger" frameborder="no" class="page-content" scrolling="auto" />
  </div>
</template>
<script>
export default {
  name: 'Swagger',
  data() {
    return {
      src: process.env.VUE_APP_BASE_URL + '/doc.html',
      height: document.documentElement.clientHeight - 94.5 + 'px;',
      loading: true
    }
  },
  mounted: function() {
    setTimeout(() => {
      this.loading = false
    }, 230)
    const that = this
    window.onresize = function temp() {
      that.height = document.documentElement.clientHeight - 94.5 + 'px;'
    }
  }
}
</script>
