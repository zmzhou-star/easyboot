<template>
  <div class="chartBox">
    <div class="chartBox_d" id="myCharts" ref="myCharts"></div>
  </div>
</template>

<script>
import echarts from 'echarts'
import 'echarts/map/js/china.js' // 引入中国地图数据
require('echarts/theme/vintage')
export default {
  props: {
    width: {
      type: String,
      default: '100%'
    },
    height: {
      type: String,
      default: '450px'
    },
    mapData: {
      type: Array,
      required: true
    }
  },
  data() {
    return {
      myCharts: null
    }
  },
  watch: {
    mapData: {
      deep: true,
      handler(val) {
        this._setOption(val)
      }
    }
  },
  mounted() {
    this.$nextTick().then(() => {
      this.initEcharts()
    })
  },
  methods: {
    initEcharts() {
      this.myCharts = echarts.init(this.$refs.myCharts, 'vintage')
      // 监听屏幕变化自动缩放图表
      let that = this
      window.addEventListener('resize', function () {
        that.myCharts.resize()
      })
      this._setOption(this.mapData)
    },
    _setOption(mapData) {
      this.myCharts.setOption({
        backgroundColor: '#404a59',
        title: {
          text: '用户分布图',
          left: 'center',
          top: 30,
          textStyle: {
            color: '#fff'
          }
        },
        tooltip: {
          trigger: 'item'
        },
        geo: {
          // 这个是重点配置区
          map: 'china', // 表示中国地图
          roam: true,
          layoutCenter: ['50%', '50%'],
          layoutSize: '120%',
          label: {
            emphasis: {
              show: true,
              color: '#adadad'
            }
          },
          itemStyle: {
            normal: {
              areaColor: '#323c48',
              borderColor: '#404a59'
            },
            emphasis: {
              areaColor: '#2a333d',
              shadowOffsetX: 0,
              shadowOffsetY: 0,
              shadowBlur: 20,
              borderWidth: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
          }
        },
        series: [
          {
            name: '用户登录信息',
            type: 'effectScatter',
            coordinateSystem: 'geo',
            zlevel: 2,
            rippleEffect: {
              brushType: 'stroke'
            },
            tooltip: {
              trigger: 'item',
              padding: 10,
              formatter: function (param) {
                let data = param.data;
                return param.seriesName + '<br>ip地址：' + data.ipAddr + '<br>登录地点：' + data.loginLocation
                  + '<br>浏览器类型：' + data.browser + '<br>操作系统：' + data.os + '<br>登录时间：' + data.loginTime;
              }
            },
            label: {
              normal: {
                show: true,
                position: 'left',
                offset: [-5, 5],
                formatter: '{b}'
              },
              emphasis: {
                show: true
              }
            },
            hoverAnimation: true,
            symbol: 'circle',
            symbolSize: 10,
            itemStyle: {
              normal: {
                show: true,
                color: '#a6c84c'
              }
            },
            data: mapData
          }
        ]
      })
    }
  }
}
</script>
<style lang="scss" scoped>
.chartBox {
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  background: #fff;
  height: 100%;
  .chartBox_d {
    height: 100%;
    box-sizing: border-box;
  }
}
</style>
