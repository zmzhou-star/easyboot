<template>
  <div>
    <svg-icon icon-class="question" @click="goto" />
  </div>
</template>

<script>
export default {
  name: 'Doc',
  data() {
    return {
      url: process.env.VUE_APP_BASE_URL + '/doc.html'
    }
  },
  methods: {
    goto() {
      window.open(this.url)
    }
  }
}
</script>
