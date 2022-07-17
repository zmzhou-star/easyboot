<template>
  <div class="app-container">
    <el-form v-show="showSearch" ref="queryForm" :model="queryParams" :inline="true" label-width="68px">
      <el-form-item label="期号" prop="lotteryId">
        <el-input
          v-model="queryParams.lotteryId"
          placeholder="请输入期号"
          clearable
          size="small"
          style="width: 150px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="彩票类型" prop="lotteryType">
        <el-select v-model="queryParams.lotteryType" placeholder="请选择彩票类型" clearable size="small" style="width: 150px">
          <el-option
            v-for="dict in lotteryTypeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="开奖日期">
        <el-date-picker
          v-model="dateRange"
          size="small"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['miniapp.lottery:history:export']"
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :show-search.sync="showSearch" @queryTable="getList" />
    </el-row>

    <el-table
      v-loading="loading"
      :data="historyList"
      element-loading-text="Loading"
      stripe
      height="565"
      fit
      highlight-current-row
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="期号" align="center" prop="lotteryId" />
      <el-table-column label="彩票类型" align="center" prop="lotteryType" :formatter="lotteryTypeFormat" />
      <el-table-column label="开奖日期" align="center" prop="lotteryDate" width="120" />
      <el-table-column label="开奖结果" align="center" prop="blue1" width="235">
        <template slot-scope="scope">
          <div class="ball">
            <div class="red">{{ scope.row.red1 }}</div>
            <div class="red">{{ scope.row.red2 }}</div>
            <div class="red">{{ scope.row.red3 }}</div>
            <div class="red">{{ scope.row.red4 }}</div>
            <div class="red">{{ scope.row.red5 }}</div>
            <div class="blue">{{ scope.row.blue1 }}</div>
            <div class="blue">{{ scope.row.blue2 }}</div>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="一等奖数量" align="center" prop="prizeNum1" />
      <el-table-column label="一等奖奖金" align="center" prop="prizeBonus1" />
      <el-table-column label="二等奖数量" align="center" prop="prizeNum2" />
      <el-table-column label="二等奖奖金" align="center" prop="prizeBonus2" />
      <el-table-column label="三等奖数量" align="center" prop="prizeNum3" />
      <el-table-column label="三等奖奖金" align="center" prop="prizeBonus3" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            v-hasPermi="['miniapp.lottery:history:edit']"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="getDetail(scope.row)"
          >查看详情</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改彩票历史数据对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="780px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <div>
          第{{ form.lotteryId }}期   {{ form.lotteryDate }}
        </div>
        <div class="ball">
          <div class="red">{{ form.red1 }}</div>
          <div class="red">{{ form.red2 }}</div>
          <div class="red">{{ form.red3 }}</div>
          <div class="red">{{ form.red4 }}</div>
          <div class="red">{{ form.red5 }}</div>
          <div class="blue">{{ form.blue1 }}</div>
          <div class="blue">{{ form.blue2 }}</div>
        </div>

        <div>
          一等奖{{ form.prizeNum1 }}注 奖金：{{ form.prizeBonus1 }}
        </div>
        <div>
          二等奖{{ form.prizeNum2 }}注 奖金：{{ form.prizeBonus2 }}
        </div>
        <div>
          三等奖{{ form.prizeNum3 }}注 奖金：{{ form.prizeBonus3 }}
        </div>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listHistory,
  getHistory,
  addHistory,
  updateHistory,
  exportHistory
} from '@/api/miniapp/lottery/history'

export default {
  name: 'History',
  filters: {
    statusFilter(status) {
      const statusMap = {
        1: 'success',
        0: 'danger'
      }
      return statusMap[status]
    }
  },
  components: {},
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 日期范围
      dateRange: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 彩票历史数据表格数据
      historyList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 彩票类型字典
      lotteryTypeOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        lotteryId: null,
        lotteryType: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {}
    }
  },
  created() {
    this.getList()
    this.getDicts('lotteryType').then(response => {
      this.lotteryTypeOptions = response
    })
  },
  methods: {
    /** 查询彩票历史数据列表 */
    getList() {
      this.loading = true
      listHistory(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
        this.historyList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 彩票类型字典翻译
    lotteryTypeFormat(row) {
      return this.selectDictLabel(this.lotteryTypeOptions, row.lotteryType)
    },
    // 取消按钮
    cancel() {
      this.open = false
    },

    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    /** 多选框选中数据 */
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },

    /** 查看详情按钮操作 */
    getDetail(row) {
      const id = row.id || this.ids
      getHistory(id).then(response => {
        this.form = response
        this.open = true
        this.title = '查看彩票历史详情'
      })
    },

    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams
      queryParams.excelName = '彩票历史数据'
      this.$confirm('是否确认导出所有彩票历史数据数据项?', '操作警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function() {
        return exportHistory(queryParams)
      }).then(response => {
        this.download(response)
      })
    }
  }
}
</script>
<style>
.el-form > div {
  padding: 15px;
}
.ball {
  display: table-cell;
}
.ball > div {
  border-radius: 50%;
  width: 30px;
  height: 30px;
  text-align: center;
  vertical-align: middle;
  display: table-cell;
  color: white;
}
.red {
  background: #ff4949;
}
.blue {
  background: #1890ff;
}
</style>
