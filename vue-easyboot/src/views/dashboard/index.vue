<template>
  <div class="dashboard-container">
    <div v-if="roles[0] !== 'admin'" class="dashboard-text">name: {{ name }} </div>
    <!-- cardList -->
    <div v-hasRole="['admin']">
      <el-row class="infoCards">
        <el-col :span="6">
          <div class="cardItem">
            <div class="cardItem_txt">
              <count-to
                class="cardItem_p0 color-green1"
                :startVal="startVal"
                :endVal="vistors"
                :duration="2000"
              ></count-to>
              <p class="cardItem_p1">Total Visitors</p>
            </div>
            <div class="cardItem_icon">
              <i class="el-icon-user color-green1"></i>
            </div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="cardItem">
            <div class="cardItem_txt">
              <count-to
                class="cardItem_p0 color-blue"
                :startVal="startVal"
                :endVal="message"
                :duration="2000"
              ></count-to>
              <p class="cardItem_p1">Messages</p>
            </div>
            <div class="cardItem_icon">
              <i class="el-icon-s-comment color-blue"></i>
            </div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="cardItem">
            <div class="cardItem_txt">
              <count-to
                class="cardItem_p0 color-red"
                :startVal="startVal"
                :endVal="order"
                :duration="2000"
              ></count-to>
              <p class="cardItem_p1">Total Order Placeed</p>
            </div>
            <div class="cardItem_icon">
              <i class="el-icon-shopping-cart-2 color-red"></i>
            </div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="cardItem">
            <div class="cardItem_txt">
              <count-to
                class="cardItem_p0 color-green2"
                :startVal="startVal"
                :endVal="profit"
                :duration="2000"
              ></count-to>
              <p class="cardItem_p1">Total Profit</p>
            </div>
            <div class="cardItem_icon">
              <i class="el-icon-wallet color-green2"></i>
            </div>
          </div>
        </el-col>
      </el-row>
      <!-- end -->
      <div class="map-query">
        <el-form ref="queryForm" :model="queryParams" :inline="true" label-width="68px">
          <el-form-item label="登录时间">
            <el-date-picker
              v-model="dateRange"
              size="small"
              style="width: 240px"
              value-format="yyyy-MM-dd"
              type="daterange"
              range-separator="-"
              :clearable="false"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              @change="getMapData"
            />
          </el-form-item>
        </el-form>
      </div>
      <mapChart class="map-charts" :mapData="mapData"></mapChart>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import CountTo from 'vue-count-to'
import mapChart from '@/views/echarts/map-chart'
import request from '@/utils/request'

export default {
  name: 'Dashboard',
  computed: {
    ...mapGetters([
      'name', 'roles'
    ])
  },
  data() {
    return {
      startVal: 0,
      vistors: 0,
      message: 0,
      order: 0,
      profit: 0,
      mapData: [],
      // 日期范围
      dateRange: [this.parseTime(new Date(), '{y}-{m}-{d}'), this.parseTime(new Date(), '{y}-{m}-{d}')],
      queryParams: {}
    }
  },
  components: {
    CountTo,
    mapChart
  },
  created() {
    this.getConfigKey('sys.index.skin').then(res => {
      this.$store.dispatch('settings/changeSetting', {
        key: 'theme',
        value: res
      })
    })
    this.getMapData();
    let that = this
    setTimeout(function() {
      that.getMapData();
    }, 60 * 1000)
  },
  methods: {
    getMapData() {
      let params = this.addDateRange(this.queryParams, this.dateRange)
      request({
        url: '/monitor/loginLog/stat',
        method: 'post',
        data: params
      }).then(res => {
        if (res) {
          for (let i = 0; i < res.length; i++) {
            res[i].name = res[i].userName
            res[i].value = res[i].coordinate.split(',')
          }
        }
        this.mapData = res
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.dashboard {
  &-container {
    height: 100%;
    margin: 30px;
  }
  &-text {
    font-size: 30px;
    line-height: 46px;
  }
}
$mgTop: 30px;
@mixin shadow {
  box-shadow: 0 0 10px #e2e2e2;
}
.color-green1 {
  color: #40c9c6 !important;
}
.color-blue {
  color: #36a3f7 !important;
}
.color-red {
  color: #f4516c !important;
}
.color-green2 {
  color: #34bfa3 !important;
}

.infoCards {
  margin: 0 -20px 0 -20px;
  .el-col {
    padding: 0 20px;
    .cardItem {
      height: 108px;
      background: #fff;
    }
  }
}
.cardItem {
  color: #666;
  @include shadow();
  .cardItem_txt {
    float: left;
    margin: 26px 0 0 20px;
    .cardItem_p0 {
      font-size: 20px;
      font-weight: bold;
    }
    .cardItem_p1 {
      font-size: 16px;
    }
  }
  .cardItem_icon {
    float: right;
    margin: 24px 20px 0 0;
    i {
      font-size: 55px;
    }
  }
}
.map-charts {
  height: calc(100vh - 290px);
  background: #fff;
  margin-top: $mgTop;
  padding: 10px;
  @include shadow();
}
.map-query .el-form-item {
  margin: 10px;
}
.map-query {
  width: 330px;
  position: absolute;
  margin-top: 30px;
  z-index: 999;
  background-color: #FFF;
}
</style>
