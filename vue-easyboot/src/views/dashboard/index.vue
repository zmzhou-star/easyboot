<template>
  <div class="dashboard-container">
    <github-corner class="github-corner" />

    <div v-if="roles[0] !== 'admin'" class="dashboard-text">name: {{ name }} </div>
    <!-- cardList -->
    <div v-hasRole="['admin']">
      <el-row class="infoCards">
        <el-col :span="6">
          <div class="cardItem">
            <div class="cardItem_txt">
              <count-to
                class="cardItem_p0 color-green1"
                :start-val="startVal"
                :end-val="cardsData.totalVisitors"
                :duration="2000"
              />
              <p class="cardItem_p1">Total Visitors</p>
            </div>
            <router-link to="/monitor/online">
              <div class="cardItem_txt">
                <count-to
                  class="cardItem_p0 color-green1"
                  :start-val="startVal"
                  :end-val="cardsData.onlineVisitors"
                  :duration="2000"
                />
                <p class="cardItem_p1">Online Visitors</p>
              </div>
            </router-link>
            <div class="cardItem_icon">
              <i class="el-icon-user color-green1" />
            </div>
          </div>
        </el-col>
        <el-col :span="6">
          <router-link to="/system/notice">
            <div class="cardItem">
              <div class="cardItem_txt">
                <count-to
                  class="cardItem_p0 color-blue"
                  :start-val="startVal"
                  :end-val="cardsData.messages"
                  :duration="2000"
                />
                <p class="cardItem_p1">Messages</p>
              </div>
              <div class="cardItem_icon">
                <i class="el-icon-s-comment color-blue" />
              </div>
            </div>
          </router-link>
        </el-col>
        <el-col :span="6">
          <div class="cardItem">
            <div class="cardItem_txt">
              <count-to
                class="cardItem_p0 color-red"
                :start-val="startVal"
                :end-val="cardsData.orderForm"
                :duration="2000"
              />
              <p class="cardItem_p1">Total Order Placeed</p>
            </div>
            <div class="cardItem_icon">
              <i class="el-icon-shopping-cart-2 color-red" />
            </div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="cardItem">
            <div class="cardItem_txt">
              <count-to
                class="cardItem_p0 color-green2"
                :start-val="startVal"
                :end-val="cardsData.profit"
                :duration="2000"
              />
              <p class="cardItem_p1">Total Profit</p>
            </div>
            <div class="cardItem_icon">
              <i class="el-icon-wallet color-green2" />
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
      <mapChart class="map-charts" :map-data="mapData" />
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import CountTo from 'vue-count-to'
import mapChart from '@/views/echarts/map-chart'
import { cardStat, userLoginStat } from '@/api/dashboard'
import GithubCorner from '@/components/GithubCorner'

export default {
  name: 'Dashboard',
  computed: {
    ...mapGetters([
      'name', 'roles'
    ])
  },
  components: {
    GithubCorner,
    CountTo,
    mapChart
  },
  data() {
    return {
      startVal: 0,
      cardsData: {
        totalVisitors: 0,
        onlineVisitors: 0,
        messages: 0,
        orderForm: 0,
        profit: 0
      },
      mapData: [],
      // 日期范围
      dateRange: [this.parseTime(new Date(), '{y}-{m}-{d}'), this.parseTime(new Date(), '{y}-{m}-{d}')],
      queryParams: {}
    }
  },
  created() {
    this.getConfigKey('sys.index.skin').then(res => {
      this.$store.dispatch('settings/changeSetting', {
        key: 'theme',
        value: res
      })
    })
    if (this.roles.includes('admin')) {
      this.getMapData()
      this.getCardsData()
      const that = this
      setTimeout(function() {
        that.getMapData()
        that.getCardsData()
      }, 60 * 1000)
    }
  },
  methods: {
    /**
     * 获取卡片统计数据
     */
    getCardsData() {
      cardStat().then(res => {
        this.cardsData = res
      })
    },
    /**
     * 获取用户登录日志统计
     */
    getMapData() {
      userLoginStat(this.addDateRange(this.queryParams, this.dateRange)).then(res => {
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
    padding: 30px;
    position: relative;
    background-color: #f0f2f5;
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
