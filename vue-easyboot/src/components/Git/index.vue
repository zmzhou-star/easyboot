<template>
  <div>
    <svg-icon icon-class="github" @click="goto" />
  </div>
</template>

<script>
export default {
  name: 'Git',
  data() {
    return {
      url: 'https://github.com/zmzhou-star/easyboot'
    }
  },
  methods: {
    goto() {
      window.open(this.url)
    }
  }
}
</script>
