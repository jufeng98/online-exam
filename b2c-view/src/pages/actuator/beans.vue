<template>
  <div>
    <el-row>
      <el-col>
        <el-form :inline="true" :model="beansForm" style="text-align: left;margin: 0;" ref="beansFormRef">
          <el-form-item label="Bean ID:" prop="beanId">
            <el-input v-model="beansForm.beanId" placeholder="支持模糊搜索"></el-input>
          </el-form-item>
          <el-form-item label="所在包名:" prop="package">
            <el-input v-model="beansForm.package" placeholder="支持模糊搜索"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="search" @click="searchBeans">搜索</el-button>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="resetForm('beansFormRef')">重置</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
    <el-row>
      <el-col>
        <el-table :border="true" :data="beansCondition" highlight-current-row v-loading="listLoading" fit
                  style="margin-right:50px;">
          <el-table-column type="index" width="50"></el-table-column>
          <el-table-column prop="beanId" label="Bean ID" width="200px"></el-table-column>
          <el-table-column prop="aliases" label="别名" width="150px"></el-table-column>
          <el-table-column prop="scope" label="scope" width="100px"></el-table-column>
          <el-table-column prop="type" label="类型"></el-table-column>
          <el-table-column prop="resource" label="资源文件"></el-table-column>
          <el-table-column prop="dependencies" label="依赖"></el-table-column>
        </el-table>
      </el-col>
    </el-row>
  </div>
</template>
<script>

  import baseAxios from '../../common/baseAxios'
  import config from '../../config'

  export default {
    data() {
      return {
        beansForm: {beanId: '', package: ''},
        beansCondition: [],
        beans: [],
        listLoading: false,
      }
    },
    methods: {
      getBeans() {
        baseAxios.get(config.GET_BEANS).then((resJson) => {
          let tmp = resJson.contexts['b2c-core'].beans
          for (let key in tmp) {
            let obj = tmp[key]
            let aliases = obj.aliases.join(' ')
            let scope = obj.scope
            let type = obj.type
            let resource = obj.resource ? obj.resource : ''
            let dependencies = obj.dependencies.join(' ')
            this.beans.push({beanId: key, aliases, scope, type, resource, dependencies})
          }
          this.beansCondition = this.beans
        })
      },
      searchBeans() {
        this.beansCondition = []
        this.beansCondition = this.beans.filter((item) => {
          let condi1 = true
          if (this.beansForm.beanId !== '') {
            condi1 = item.beanId.indexOf(this.beansForm.beanId) !== -1
          }
          let condi2 = true
          if (this.beansForm.package !== '') {
            condi2 = item.type.indexOf(this.beansForm.package) !== -1
          }
          return condi1 && condi2
        })
      },
      resetForm(formName) {
        this.$refs[formName].resetFields()
      },
    },
    mounted() {
      this.getBeans()
      window.vue = this
    },
  }
</script>
