<template>
  <div>
    <el-row>
      <el-col>
        <el-form :inline="true" :model="beanForm" style="text-align: left;margin: 0;" ref="beanFormRef">
          <el-form-item label="Bean名称:" prop="name">
            <el-input v-model="beanForm.name" placeholder="支持模糊搜索"></el-input>
          </el-form-item>
          <el-form-item label="前缀:" prop="prefix">
            <el-input v-model="beanForm.prefix" placeholder="支持模糊搜索"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" @click="searchConditions">搜索</el-button>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-warning" @click="resetForm('beanFormRef')">重置</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
    <el-row>
      <el-col>
        <el-table :border="true" :data="beansCondition" highlight-current-row v-loading="listLoading" fit
                  style="margin-right:50px;">
          <el-table-column type="index" width="50"></el-table-column>
          <el-table-column prop="name" label="Bean名称" width="700px"></el-table-column>
          <el-table-column prop="prefix" label="前缀" width="200px"></el-table-column>
          <el-table-column prop="properties" label="属性"></el-table-column>
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
        beanForm: {name: '', prefix: ''},
        beans: [],
        beansCondition: [],
        listLoading: false,
      }
    },
    methods: {
      getConditions() {
        baseAxios.get(config.GET_CONFIGPROPS).then((resJson) => {
          let tmp = resJson.contexts['b2c-core'].beans
          for (let key in tmp) {
            let obj = tmp[key]
            let prefix = obj.prefix ? obj.prefix : ''
            let properties = obj.properties
            this.beans.push({name: key, prefix, properties: JSON.stringify(properties)})
          }
          this.beansCondition = this.beans
        })
      },
      searchConditions() {
        this.beansCondition = []
        this.beansCondition = this.beans.filter((item) => {
          let condi1 = true
          if (this.beanForm.name !== '') {
            condi1 = item.name.indexOf(this.beanForm.name) !== -1
          }
          let condi2 = true
          if (this.beanForm.prefix !== '') {
            condi2 = item.prefix.indexOf(this.beanForm.prefix) !== -1
          }
          return condi1 && condi2
        })
      },
      resetForm(formName) {
        this.$refs[formName].resetFields()
      },
    },
    mounted() {
      this.getConditions()
      window.vue = this
    },
  }
</script>
