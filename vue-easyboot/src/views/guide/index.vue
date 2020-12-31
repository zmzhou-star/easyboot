<template>
  <div class="app-container">
    <aside>
      <i class="el-icon-s-opportunity" />
      引导页面对于某些首次进入该项目的人很有用，点击按钮可查看本系统的基本操作。演示基于
      <a href="https://github.com/kamranahmedse/driver.js" target="_blank">driver.js.</a>
    </aside>
    <el-button icon="el-icon-question" type="primary" @click.prevent.stop="guide">
      打开引导
    </el-button>
  </div>
</template>

<script>
import Driver from 'driver.js' // import driver.js
import 'driver.js/dist/driver.min.css' // import driver.js css
import steps from './steps'

export default {
  name: 'Guide',
  data() {
    return {
      driver: null
    }
  },
  mounted() {
    this.driver = new Driver({
      opacity: 0.5,
      stageBackground: 'rgba(255, 255, 255, 0.5)'
    })
  },
  methods: {
    guide() {
      this.driver.defineSteps(steps)
      this.driver.start()
    }
  }
}
</script>
<style lang="scss" scoped>
aside {
  i {
    margin-right: 5px;
    color: #ffc107;
    font-size: 20px;
  }
}
</style>
