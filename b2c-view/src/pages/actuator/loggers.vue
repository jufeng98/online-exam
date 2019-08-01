<template>
  <div>
    <el-row>
      <el-col>
        <el-form :inline="true" :model="loggersForm" style="text-align: left;margin: 0;" ref="loggersFormRef">
          <el-form-item label="生效级别" prop="level">
            <el-select v-model="loggersForm.level" clearable>
              <el-option
                v-for="item in levels"
                :key="item"
                :label="item"
                :value="item">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="路径:" prop="name">
            <el-input v-model="loggersForm.name" placeholder="支持模糊搜索"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" @click="searchLoggers">搜索</el-button>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-warning" @click="resetForm('loggersFormRef')">重置</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
    <el-row>
      <el-col>
        <el-table :border="true" :data="loggersCondition" highlight-current-row v-loading="listLoading" fit
                  style="margin-right:50px;" element-loading-text="拼命加载中">
          <el-table-column type="index" width="50"></el-table-column>
          <el-table-column prop="name" label="路径" width="600px"></el-table-column>
          <el-table-column prop="configuredLevel" label="配置级别"></el-table-column>
          <el-table-column prop="effectiveLevel" label="生效级别"></el-table-column>
          <el-table-column label="操作" min-width="180">
            <template slot-scope="scope">
              <el-button type="text" icon="edit" @click="editLevelOpenDialog(scope.$index, scope.row)">
                修改级别
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>

    <el-dialog title="修改级别" :visible.sync="dialogEditTableVisible" width="25%">
      <el-select v-model="editForm.level">
        <el-option
          v-for="item in levels"
          :key="item"
          :label="item"
          :value="item">
        </el-option>
      </el-select>
      <el-button type="primary" icon="search" @click="dialogEditTableVisible = false">取消</el-button>
      <el-button type="primary" icon="search" @click="changeLevel">确定</el-button>
    </el-dialog>
  </div>
</template>
<script>

  import baseAxios from '../../common/baseAxios'
  import config from '../../config'

  export default {
    data() {
      return {
        loggersForm: {name: '', level: ''},
        editForm: {name: '', level: ''},
        loggersCondition: [],
        loggers: [],
        levels: [],
        listLoading: false,
        dialogEditTableVisible: false,
      }
    },
    methods: {
      getLoggers() {
        baseAxios.get(config.GET_LOGGERS).then((resJson) => {
          this.levels = resJson.levels
          let tmp = resJson.loggers
          for (let key in tmp) {
            let obj = tmp[key]
            let configuredLevel = obj.configuredLevel ? obj.configuredLevel : ''
            this.loggers.push({name: key, configuredLevel, 'effectiveLevel': obj.effectiveLevel})
          }
          this.loggersCondition = this.loggers
        })
      },
      searchLoggers() {
        this.loggersCondition = []
        this.loggersCondition = this.loggers.filter((item) => {
          let condi1 = true
          if (this.loggersForm.level !== '') {
            condi1 = item.effectiveLevel === this.loggersForm.level
          }
          let condi2 = true
          if (this.loggersForm.name !== '') {
            condi2 = item.name.indexOf(this.loggersForm.name) !== -1
          }
          return condi1 && condi2
        })
      },
      editLevelOpenDialog(index, row) {
        this.editForm.name = row.name
        this.editForm.level = this.levels[3]
        this.dialogEditTableVisible = true
      },
      changeLevel() {
        baseAxios.post(config.GET_LOGGERS + '/' + this.editForm.name, {
          configuredLevel: this.editForm.level
        }).then(() => {
          this.loggers = []
          this.getLoggers()
          this.$message({message: '修改成功', type: 'success'})
        }).finally(() => {
          this.dialogEditTableVisible = false
        })
      },
      resetForm(formName) {
        this.$refs[formName].resetFields()
      },
    },
    mounted() {
      this.getLoggers()
      window.vue = this
    },
  }
</script>
