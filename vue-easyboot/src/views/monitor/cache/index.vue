<template>
  <div class="app-container">
    <el-row>
      <el-col :span="24" class="card-box">
        <el-card>
          <div slot="header"><span>基本信息</span></div>
          <div class="el-table el-table--enable-row-hover el-table--medium">
            <table cellspacing="0" style="width: 100%">
              <tbody>
                <tr>
                  <td class="el-table__cell is-leaf"><div class="cell">Redis版本</div></td>
                  <td class="el-table__cell is-leaf"><div v-if="cache.info" class="cell">{{ cache.info.redis_version }}</div></td>
                  <td class="el-table__cell is-leaf"><div class="cell">运行模式</div></td>
                  <td class="el-table__cell is-leaf"><div v-if="cache.info" class="cell">{{ cache.info.redis_mode === "standalone" ? "单机" : "集群" }}</div></td>
                  <td class="el-table__cell is-leaf"><div class="cell">端口</div></td>
                  <td class="el-table__cell is-leaf"><div v-if="cache.info" class="cell">{{ cache.info.tcp_port }}</div></td>
                  <td class="el-table__cell is-leaf"><div class="cell">客户端数</div></td>
                  <td class="el-table__cell is-leaf"><div v-if="cache.info" class="cell">{{ cache.info.connected_clients }}</div></td>
                </tr>
                <tr>
                  <td class="el-table__cell is-leaf"><div class="cell">运行时间(天)</div></td>
                  <td class="el-table__cell is-leaf"><div v-if="cache.info" class="cell">{{ cache.info.uptime_in_days }}</div></td>
                  <td class="el-table__cell is-leaf"><div class="cell">使用内存</div></td>
                  <td class="el-table__cell is-leaf"><div v-if="cache.info" class="cell">{{ cache.info.used_memory_human }}</div></td>
                  <td class="el-table__cell is-leaf"><div class="cell">使用CPU</div></td>
                  <td class="el-table__cell is-leaf"><div v-if="cache.info" class="cell">{{ parseFloat(cache.info.used_cpu_user_children).toFixed(2) }}</div></td>
                  <td class="el-table__cell is-leaf"><div class="cell">内存配置</div></td>
                  <td class="el-table__cell is-leaf"><div v-if="cache.info" class="cell">{{ cache.info.maxmemory_human }}</div></td>
                </tr>
                <tr>
                  <td class="el-table__cell is-leaf"><div class="cell">AOF是否开启</div></td>
                  <td class="el-table__cell is-leaf"><div v-if="cache.info" class="cell">{{ cache.info.aof_enabled === "0" ? "否" : "是" }}</div></td>
                  <td class="el-table__cell is-leaf"><div class="cell">RDB是否成功</div></td>
                  <td class="el-table__cell is-leaf"><div v-if="cache.info" class="cell">{{ cache.info.rdb_last_bgsave_status }}</div></td>
                  <td class="el-table__cell is-leaf"><div class="cell">Key数量</div></td>
                  <td class="el-table__cell is-leaf"><div v-if="cache.dbSize" class="cell">{{ cache.dbSize }} </div></td>
                  <td class="el-table__cell is-leaf"><div class="cell">网络入口/出口</div></td>
                  <td class="el-table__cell is-leaf"><div v-if="cache.info" class="cell">{{ cache.info.instantaneous_input_kbps }}kps/{{ cache.info.instantaneous_output_kbps }}kps</div></td>
                </tr>
              </tbody>
            </table>
          </div>
        </el-card>
      </el-col>

      <el-col :span="12" class="card-box">
        <el-card>
          <div slot="header"><span>命令统计</span></div>
          <div class="el-table el-table--enable-row-hover el-table--medium">
            <div ref="commandstats" style="height: 420px" />
          </div>
        </el-card>
      </el-col>

      <el-col :span="12" class="card-box">
        <el-card>
          <div slot="header">
            <span>内存信息</span>
          </div>
          <div class="el-table el-table--enable-row-hover el-table--medium">
            <div ref="usedmemory" style="height: 420px" />
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-form ref="queryForm" :model="queryParams" :inline="true">
      <el-form-item label="缓存键" prop="cacheKey">
        <el-input
          v-model="queryParams.cacheKey"
          placeholder="请输入缓存键"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button
          v-hasPermi="['monitor:cache:list']"
          type="primary"
          icon="el-icon-search"
          size="mini"
          @click="handleQuery"
        >搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-table
      v-loading="tableLoading"
      stripe
      height="485"
      fit
      highlight-current-row
      :data="cacheList.slice((pageNum-1)*pageSize, pageNum*pageSize)"
      style="width: 100%;"
    >
      <el-table-column label="序号" type="index" align="center">
        <template slot-scope="scope">
          <span>{{ (pageNum - 1) * pageSize + scope.$index + 1 }}</span>
        </template>
      </el-table-column>
      <el-table-column label="缓存名" align="center" prop="key" :show-overflow-tooltip="true" width="300" />
      <el-table-column label="缓存值" align="center" prop="value" :show-overflow-tooltip="true" />
    </el-table>
    <pagination v-show="total>0" :total="total" :page.sync="pageNum" :limit.sync="pageSize" />
  </div>
</template>

<script>
import { getCacheInfo, getCacheData } from '@/api/monitor/cache'
import echarts from 'echarts'

export default {
  name: 'Server',
  data() {
    return {
      // 加载层信息
      loading: [],
      // 统计命令信息
      commandstats: null,
      // 使用内存
      usedmemory: null,
      // cache信息
      cache: [],
      tableLoading: true,
      // 获取redis缓存信息列表
      cacheList: [],
      // 总条数
      total: 0,
      pageNum: 1,
      pageSize: 10,
      // 查询参数
      queryParams: {
        cacheKey: undefined
      }
    }
  },
  created() {
    this.getCacheInfo()
    this.getCacheData()
    this.openLoading()
  },
  methods: {
    /** 查缓存询信息 */
    getCacheInfo() {
      getCacheInfo().then((response) => {
        this.cache = response
        this.loading.close()

        this.commandstats = echarts.init(this.$refs.commandstats, 'macarons')
        this.commandstats.setOption({
          tooltip: {
            trigger: 'item',
            formatter: '{a} <br/>{b} : {c} ({d}%)'
          },
          series: [
            {
              name: '命令',
              type: 'pie',
              roseType: 'radius',
              radius: [15, 95],
              center: ['50%', '38%'],
              data: this.cache.commandStats,
              animationEasing: 'cubicInOut',
              animationDuration: 1000
            }
          ]
        })
        this.usedmemory = echarts.init(this.$refs.usedmemory, 'macarons')
        this.usedmemory.setOption({
          tooltip: {
            formatter: '{b} <br/>{a} : ' + this.cache.info.used_memory_human
          },
          series: [
            {
              name: '峰值',
              type: 'gauge',
              min: 0,
              max: 1000,
              detail: {
                formatter: this.cache.info.used_memory_human
              },
              data: [
                {
                  value: parseFloat(this.cache.info.used_memory_human),
                  name: '内存消耗'
                }
              ]
            }
          ]
        })
      })
    },
    /** 获取redis缓存信息列表 */
    getCacheData() {
      this.tableLoading = true
      getCacheData(this.queryParams).then((response) => {
        const cacheArr = []
        for (const item in response) {
          let value = response[item];
          if (value instanceof Array) {
            cacheArr.push({ 'key': item, 'value': value.join(', ') })
          } else {
            cacheArr.push({ 'key': item, 'value': value })
          }
        }
        this.cacheList = cacheArr
        this.total = cacheArr.length
        this.tableLoading = false
      })
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.pageNum = 1
      this.getCacheData()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 打开加载层
    openLoading() {
      this.loading = this.$loading({
        lock: true,
        text: '正在加载缓存监控数据',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      })
    }
  }
}
</script>
