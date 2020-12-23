<template>
  <div class="app-container driver">
    <p class="driver_p0">
      <i class="el-icon-s-opportunity"></i>点击按钮可查看本系统的基本操作
    </p>
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
.driver_p0 {
  font-size: 14px;
  margin-bottom: 20px;
  i {
    margin-right: 5px;
    color: #ffc107;
    font-size: 18px;
  }
}
</style>
