<template>
  <div>
    <el-row>
      <el-col>
        <el-form :inline="true" :model="conditionsForm" style="text-align: left;margin: 0;" ref="conditionsFormRef">
          <el-form-item label="自动配置类名:" prop="name">
            <el-input v-model="conditionsForm.name" placeholder="支持模糊搜索"></el-input>
          </el-form-item>
          <el-form-item label="条件" prop="condition">
            <el-select v-model="conditionsForm.condition" clearable>
              <el-option
                v-for="item in twoConditions"
                :key="item.value"
                :label="item.name"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="search" @click="searchConditions">搜索</el-button>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="resetForm('conditionsFormRef')">重置</el-button>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="dialogTableVisible = true">匹配条件说明</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
    <el-row>
      <el-col>
        <el-table :border="true" :data="conditionsCondition" highlight-current-row v-loading="listLoading" fit
                  style="margin-right:50px;">
          <el-table-column type="index" width="50"></el-table-column>
          <el-table-column prop="name" label="自动配置类名" width="300px"></el-table-column>
          <el-table-column prop="condition" label="匹配条件"></el-table-column>
        </el-table>
      </el-col>
    </el-row>

    <el-dialog title="匹配条件说明" :visible.sync="dialogTableVisible" width="70%">
      <el-table :data="conditionExplain">
        <el-table-column property="conditionName" label="条件" width="500px"></el-table-column>
        <el-table-column property="explain" label="说明"></el-table-column>
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
        conditionsForm: {name: '', condition: ''},
        conditionsCondition: [],
        conditions: [],
        positiveMatches: [],
        negativeMatches: [],
        listLoading: false,
        twoConditions: [
          {name: '成功条件', value: 'positiveMatches'},
          {name: '失败条件', value: 'negativeMatches'},
        ],
        dialogTableVisible: false,
        conditionExplain: [
          {conditionName: '@ConditionalOnBean', explain: '配置了某个特定Bean'},
          {conditionName: '@ConditionalOnMissingBean', explain: '没有配置特定的Bean'},
          {conditionName: '@ConditionalOnClass', explain: 'Classpath里有指定的类'},
          {conditionName: '@ConditionalOnMissingClass', explain: 'Classpath里缺少指定的类'},
          {conditionName: '@ConditionalOnExpression', explain: '给定的Spring Expression Language（SpEL）表达式计算结果为true'},
          {conditionName: '@ConditionalOnJava', explain: 'Java的版本匹配特定值或者一个范围值'},
          {conditionName: '@ConditionalOnJndi', explain: '参数中给定的JNDI位置必须存在一个，如果没有给参数，则要有JNDI InitialContext'},
          {conditionName: '@ConditionalOnProperty', explain: '指定的配置属性要有一个明确的值'},
          {conditionName: '@ConditionalOnResource', explain: 'Classpath里有指定的资源'},
          {conditionName: '@ConditionalOnWebApplication', explain: '这是一个Web应用程序'},
          {conditionName: '@ConditionalOnNotWebApplication', explain: '这不是一个Web应用程序'},
        ],
      }
    },
    methods: {
      getConditions() {
        baseAxios.get(config.GET_CONDITIONS).then((resJson) => {
          let tmp = resJson.contexts['b2c-core'].positiveMatches
          for (let key in tmp) {
            let obj = tmp[key]
            this.positiveMatches.push({name: key, condition: JSON.stringify(obj)})
          }
          tmp = resJson.contexts['b2c-core'].negativeMatches
          for (let key in tmp) {
            let obj = tmp[key]
            this.negativeMatches.push({name: key, condition: JSON.stringify(obj)})
          }
          this.conditions = [].concat(this.positiveMatches, this.negativeMatches)
          this.conditionsCondition = this.conditions
        })
      },
      searchConditions() {
        let tmp
        if (this.conditionsForm.condition === this.twoConditions[0].value) {
          tmp = this.positiveMatches
        } else if (this.conditionsForm.condition === this.twoConditions[1].value) {
          tmp = this.negativeMatches
        } else {
          tmp = this.conditions
        }
        this.conditionsCondition = []
        this.conditionsCondition = tmp.filter((item) => {
          let condi1 = true
          if (this.conditionsForm.name !== '') {
            condi1 = item.name.indexOf(this.conditionsForm.name) !== -1
          }
          return condi1
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
