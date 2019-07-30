<template>
  <div>
    <el-row>
      <el-col>
        <el-form :inline="true" style="text-align: left;margin: 0;" :model="mappingForm" ref="mappingCondiRef">
          <el-form-item label="路径:" prop="url">
            <el-input v-model="mappingForm.url" placeholder="支持模糊搜索"></el-input>
          </el-form-item>
          <el-form-item label="处理器:" prop="handler">
            <el-input v-model="mappingForm.handler" placeholder="支持模糊搜索"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="searchMapping">搜索</el-button>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="resetForm('mappingCondiRef')">重置</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
    <el-row style="margin-bottom: 20px;text-align: left">
      <el-col>
        <el-button type="primary" icon="search" @click="showServletFilters">查看Servlet Filters</el-button>
        <el-button type="primary" icon="search" @click="showServlets">查看Servlets</el-button>
      </el-col>
    </el-row>
    <el-row>
      <el-col>
        <el-table :border="true" :data="dispatcherServletCondition" highlight-current-row v-loading="listLoading" fit
                  style="margin-right:50px;">
          <el-table-column type="index" width="50"></el-table-column>
          <el-table-column prop="details.requestMappingConditions.methods" label="HTTP方法"
                           width="100px"></el-table-column>
          <el-table-column prop="details.requestMappingConditions.patterns" label="路径" width="300px"></el-table-column>
          <el-table-column prop="handler" label="处理器"></el-table-column>
          <el-table-column prop="details.handlerMethod.className" label="类名"></el-table-column>
        </el-table>
      </el-col>
    </el-row>

    <el-dialog title="Servlet Filters" :visible.sync="dialogServletFiltersTableVisible" width="75%">
      <el-table :data="mappings.servletFilters">
        <el-table-column property="urlPatternMappings" label="匹配路径" width="100px"></el-table-column>
        <el-table-column property="servletNameMappings" label="Servlet名字映射" width="200px"></el-table-column>
        <el-table-column property="name" label="filter名称" width="300px"></el-table-column>
        <el-table-column property="className" label="filter类名" width="600px"></el-table-column>
      </el-table>
    </el-dialog>

    <el-dialog title="Servlets" :visible.sync="dialogServletsTableVisible" width="70%">
      <el-table :data="mappings.servlets">
        <el-table-column property="mappings" label="匹配路径" width="100px"></el-table-column>
        <el-table-column property="name" label="servlet名称" width="300px"></el-table-column>
        <el-table-column property="className" label="servlet类名" width="600px"></el-table-column>
      </el-table>
    </el-dialog>

  </div>
</template>
<script>

  import baseAxios from '../../common/baseAxios'
  import config from '../../config'

  export default {
    data() {
      return {
        mappingForm: {url: '', handler: ''},
        mappings: {},
        dispatcherServletCondition: [],
        dispatcherServlet: [],
        listLoading: false,
        dialogServletFiltersTableVisible: false,
        dialogServletsTableVisible: false,
      }
    },
    methods: {
      getMappings() {
        baseAxios.get(config.GET_MAPPINGS).then((resJson) => {
          this.mappings = resJson.contexts['b2c-core'].mappings
          this.dispatcherServlet = this.mappings.dispatcherServlets.dispatcherServlet
          this.dispatcherServletCondition = this.dispatcherServlet
        })
      },
      searchMapping() {
        this.dispatcherServletCondition = []
        this.dispatcherServletCondition = this.dispatcherServlet.filter((item) => {
          let urlCondi = true
          if (this.mappingForm.url !== '') {
            if (item.details === null) {
              urlCondi = false
            } else {
              urlCondi = item.details.requestMappingConditions.patterns.join(' ').indexOf(this.mappingForm.url) !== -1
            }
          }
          let handlerCondi = true
          if (this.mappingForm.handler !== '') {
            handlerCondi = item.handler.indexOf(this.mappingForm.handler) !== -1
          }
          return urlCondi && handlerCondi
        })
      },
      showServletFilters() {
        this.dialogServletFiltersTableVisible = true
      },
      showServlets() {
        this.dialogServletsTableVisible = true
      },
      resetForm(formName) {
        this.$refs[formName].resetFields()
      },
    },
    mounted() {
      this.getMappings()
      window.vue = this
    },
  }
</script>
