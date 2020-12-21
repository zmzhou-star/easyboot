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
        <el-select v-model="queryParams.jobGroup" placeholder="请选择任务组名" clearable size="small" style="width: 150px">
          <el-option
            v-for="dict in jobGroupOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="任务状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择任务状态" clearable size="small" style="width: 150px">
          <el-option
            v-for="dict in statusOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['monitor:job:add']"
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['monitor:job:edit']"
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['monitor:job:remove']"
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['monitor:job:log']"
          type="info"
          icon="el-icon-s-operation"
          size="mini"
          @click="handleJobLog"
        >日志</el-button>
      </el-col>
      <right-toolbar :show-search.sync="showSearch" @queryTable="getList" />
    </el-row>

    <el-table
      v-loading="loading"
      :data="jobList"
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
      <el-table-column label="bean名字" align="center" prop="beanName" sortable="custom" :show-overflow-tooltip="true" />
      <el-table-column label="类的方法名" align="center" prop="methodName" sortable="custom" :show-overflow-tooltip="true" />
      <el-table-column label="类的方法参数" align="center" prop="methodParams" :show-overflow-tooltip="true" />
      <el-table-column label="cron表达式" align="center" prop="cronExpression" />
      <el-table-column label="状态" align="center" prop="status" sortable="custom" width="110">
        <template slot-scope="scope">
          <el-switch v-model="scope.row.status" inactive-value="0" active-value="1" @change="handleStatusChange(scope.row)" />
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" :show-overflow-tooltip="true" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" min-width="120">
        <template slot-scope="scope">
          <el-button
            v-hasPermi="['monitor:job:run']"
            size="mini"
            type="text"
            icon="el-icon-caret-right"
            @click="handleRun(scope.row)"
          >执行一次</el-button>
          <el-button
            v-hasPermi="['monitor:job:edit']"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
          >修改</el-button>
          <el-button
            v-hasPermi="['monitor:job:remove']"
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

    <!-- 添加或修改定时任务对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="780px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="任务名称" prop="jobName">
              <el-input v-model="form.jobName" placeholder="请输入任务名称" />
            </el-form-item>
            <el-form-item label="任务分组" prop="jobGroup">
              <el-select v-model="form.jobGroup" placeholder="请选择任务分组">
                <el-option
                  v-for="dict in jobGroupOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="bean名字" prop="beanName">
              <el-input v-model="form.beanName" placeholder="请输入任务执行时调用哪个类" />
            </el-form-item>
            <el-form-item label="类的方法名" prop="methodName">
              <el-input v-model="form.methodName" placeholder="请输入类的方法名" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="任务状态" prop="status">
              <el-radio-group v-model="form.status">
                <el-radio
                  v-for="dict in statusOptions"
                  :key="dict.dictValue"
                  :label="dict.dictValue"
                >{{ dict.dictLabel }}</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="错误策略" prop="misfirePolicy">
              <el-radio-group v-model="form.misfirePolicy" size="small">
                <el-radio-button label="1">立即执行</el-radio-button>
                <el-radio-button label="2">执行一次</el-radio-button>
                <el-radio-button label="3">放弃执行</el-radio-button>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="类的方法参数" prop="methodParams">
              <el-input v-model="form.methodParams" placeholder="请输入类的方法参数" />
            </el-form-item>
            <el-form-item label="cron表达式" prop="cronExpression">
              <el-input v-model="form.cronExpression" placeholder="请输入cron表达式" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否并发执行" prop="concurrent">
              <el-radio-group v-model="form.concurrent" size="small">
                <el-radio-button label="1">允许</el-radio-button>
                <el-radio-button label="0">禁止</el-radio-button>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="备注" prop="remark">
              <el-input v-model="form.remark" placeholder="备注" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listJob, getJob, delJob, addJob, updateJob, changeJobStatus, runJob } from '@/api/monitor/job'

export default {
  name: 'Job',
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
      // 定时任务表格数据
      jobList: [],
      // 任务分组字典
      jobGroupOptions: [],
      // 任务状态字典
      statusOptions: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        jobName: null,
        jobGroup: null,
        status: null
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
        status: [
          { required: true, message: '任务状态不能为空', trigger: 'change' }
        ]
      }
    }
  },
  created() {
    this.getList()
    this.getDicts('sys_job_group').then(response => {
      this.jobGroupOptions = response
    })
    this.getDicts('sys_job_status').then(response => {
      this.statusOptions = response
    })
  },
  methods: {
    /** 查询定时任务列表 */
    getList() {
      this.loading = true
      listJob(this.queryParams).then(response => {
        this.jobList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 任务分组字典翻译
    jobGroupFormat(row, column) {
      return this.selectDictLabel(this.jobGroupOptions, row.jobGroup)
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
        jobGroup: 'DEFAULT',
        beanName: 'easyScheduler',
        methodName: null,
        methodParams: null,
        cronExpression: '0 0/2 * * * ?',
        misfirePolicy: 3,
        concurrent: 0,
        status: '1',
        remark: null
      }
      this.resetForm('form')
    },
    /** 跳转到定时任务日志列表 */
    handleJobLog() {
      this.$router.push('/monitor/job/log')
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
    sortChange(sort) {
      this.queryParams.prop = sort.prop
      this.queryParams.order = sort.order
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    handleStatusChange(row) {
      const text = row.status === '1' ? '启用' : '停用'
      this.$confirm('确认要"' + text + '""' + row.jobName + '"任务吗?', '操作警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function() {
        return changeJobStatus(row.id, row.status)
      }).then(() => {
        this.msgSuccess(text + '成功')
        this.getList()
      }).catch(function() {
        row.status = row.status === '0' ? '1' : '0'
      })
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = '添加定时任务'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getJob(id).then(response => {
        this.form = response

        this.open = true
        this.title = '修改定时任务'
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateJob(this.form).then(response => {
              this.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addJob(this.form).then(response => {
              this.msgSuccess('新增成功')
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 立即执行一次 */
    handleRun(row) {
      this.$confirm('确定要立即执行一次"' + row.jobName + '"任务吗?', '操作警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function() {
        return runJob(row.id)
      }).then(() => {
        this.msgSuccess('执行成功')
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$confirm('是否确认删除定时任务编号为"' + ids + '"的数据项?', '操作警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function() {
        return delJob(ids)
      }).then(() => {
        this.getList()
        this.msgSuccess('删除成功')
      })
    }
  }
}
</script>
