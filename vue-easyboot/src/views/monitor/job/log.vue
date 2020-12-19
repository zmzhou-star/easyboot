<template>
  <div class="app-container">
    <el-form v-show="showSearch" ref="queryForm" :model="queryParams" :inline="true" label-width="68px">
      <el-form-item label="任务名称" prop="jobName">
        <el-input
          v-model="queryParams.jobName"
          placeholder="请输入任务名称"
          clearable
          size="small"
          style="width: 150px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="任务分组" prop="jobGroup">
        <el-select v-model="queryParams.jobGroup" placeholder="请选择任务分组" clearable size="small" style="width: 150px">
          <el-option
            v-for="dict in jobGroupOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="执行状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择执行状态" clearable size="small" style="width: 150px">
          <el-option
            v-for="dict in statusOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="执行时间">
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
        <el-button v-hasPermi="['monitor:jobLog:list']" type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['monitor:jobLog:remove']"
          type="danger"
          icon="el-icon-delete"
          size="mini"
          @click="handleClean"
        >清空</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['monitor:jobLog:remove']"
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['monitor:jobLog:export']"
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
        >导出</el-button>
      </el-col>
      <right-toolbar :show-search.sync="showSearch" @queryTable="getList" />
    </el-row>

    <el-table
      v-loading="loading"
      :data="jobLogList"
      element-loading-text="Loading"
      stripe
      height="465"
      fit
      highlight-current-row
      @sort-change="sortChange"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column align="center" label="序号" width="60">
        <template slot-scope="scope">
          {{ scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column label="任务名称" align="center" prop="jobName" :show-overflow-tooltip="true" />
      <el-table-column label="任务分组" align="center" prop="jobGroup" sortable="custom" :formatter="jobGroupFormat" />
      <el-table-column label="bean名字" align="center" prop="beanName" sortable="custom" />
      <el-table-column label="类的方法名" align="center" prop="methodName" sortable="custom" />
      <el-table-column label="类的方法参数" align="center" prop="methodParams" :show-overflow-tooltip="true" />
      <el-table-column label="cron表达式" align="center" prop="cronExpression" />
      <el-table-column label="耗时(毫秒)" align="center" prop="timeConsuming" sortable="custom" />
      <el-table-column class-name="status-col" label="状态" width="110" prop="status" align="center" sortable="custom">
        <template slot-scope="scope">
          <el-tag :type="scope.row.status | statusFilter">{{ statusFormat(scope.row) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="执行时间" align="center" prop="createTime" min-width="90" :show-overflow-tooltip="true" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            v-hasPermi="['monitor:jobLog:query']"
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleView(scope.row)"
          >详细</el-button>
          <el-button
            v-hasPermi="['monitor:jobLog:remove']"
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
          >删除</el-button>
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

    <!-- 定时任务日志详细 -->
    <el-dialog title="定时任务日志详细" :visible.sync="open" width="780px" append-to-body>
      <el-form ref="form" :model="form" label-width="100px" size="mini">
        <el-row>
          <el-col :span="12">
            <el-form-item label="日志序号：">{{ form.id }}</el-form-item>
            <el-form-item label="任务名称：">{{ form.jobName }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="任务分组：">{{ jobGroupFormat(form, form.jobGroup) }}</el-form-item>
            <el-form-item label="执行时间：">{{ form.createTime }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="bean名字：">{{ form.beanName }}</el-form-item>
            <el-form-item label="类的方法名：">{{ form.methodName }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="方法参数：">{{ form.methodParams }}</el-form-item>
            <el-form-item label="cron表达式：">{{ form.cronExpression }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="耗时(毫秒)：">{{ form.timeConsuming }}</el-form-item>
            <el-form-item label="执行状态：">
              <template>
                <el-tag :type="form.status | statusFilter">
                  <div v-if="form.status === '1'">正常</div>
                  <div v-else-if="form.status === '0'">失败</div>
                </el-tag>
              </template>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item v-if="form.status === '0'" label="异常信息：">{{ form.exceptionInfo }}</el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="open = false">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listJobLog, delJobLog, cleanJobLog, exportJobLog } from '@/api/monitor/jobLog'

export default {
  name: 'JobLog',
  filters: {
    statusFilter(status) {
      const statusMap = {
        1: 'success',
        0: 'danger'
      }
      return statusMap[status]
    }
  },
  components: {

  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 定时任务日志表格数据
      jobLogList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 任务分组字典
      jobGroupOptions: [],
      // 执行状态字典
      statusOptions: [],
      // 日期范围
      dateRange: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        jobName: null,
        jobGroup: null,
        status: null,
        createTime: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        jobName: [
          { required: true, message: '任务名称不能为空', trigger: 'blur' }
        ],
        jobGroup: [
          { required: true, message: '任务分组不能为空', trigger: 'change' }
        ],
        beanName: [
          { required: true, message: 'bean名字不能为空', trigger: 'blur' }
        ],
        methodName: [
          { required: true, message: '类的方法名不能为空', trigger: 'blur' }
        ],
        cronExpression: [
          { required: true, message: 'cron表达式不能为空', trigger: 'blur' }
        ],
        createTime: [
          { required: true, message: '创建时间不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.getList()
    this.getDicts('sys_job_group').then(response => {
      this.jobGroupOptions = response
    })
    this.getDicts('sys_common_status').then(response => {
      this.statusOptions = response
    })
  },
  methods: {
    /** 查询定时任务日志列表 */
    getList() {
      this.loading = true
      listJobLog(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
        this.jobLogList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 任务分组字典翻译
    jobGroupFormat(row, column) {
      return this.selectDictLabel(this.jobGroupOptions, row.jobGroup)
    },
    // 执行状态字典翻译
    statusFormat(row, column) {
      return this.selectDictLabel(this.statusOptions, row.status)
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        jobName: null,
        jobGroup: null,
        beanName: null,
        methodName: null,
        methodParams: null,
        cronExpression: null,
        timeConsuming: null,
        status: null,
        exceptionInfo: null,
        createTime: null
      }
      this.resetForm('form')
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
    /** 详细按钮操作 */
    handleView(row) {
      this.open = true
      this.form = row
    },
    /** 多选框选中数据 */
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    sortChange(sort) {
      this.queryParams.prop = sort.prop
      this.queryParams.order = sort.order
      this.handleQuery()
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$confirm('是否确认删除定时任务日志编号为"' + ids + '"的数据项?', '操作警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function() {
        return delJobLog(ids)
      }).then(() => {
        this.getList()
        this.msgSuccess('删除成功')
      })
    },
    /** 清空按钮操作 */
    handleClean() {
      this.$confirm('是否确认清空所有定时任务日志数据?', '操作警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function() {
        return cleanJobLog()
      }).then(() => {
        this.getList()
        this.msgSuccess('清空成功')
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams
      queryParams.excelName = '定时任务日志'
      this.$confirm('是否确认导出所有定时任务日志数据项?', '操作警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function() {
        return exportJobLog(queryParams)
      }).then(response => {
        this.download(response)
      })
    }
  }
}
</script>
