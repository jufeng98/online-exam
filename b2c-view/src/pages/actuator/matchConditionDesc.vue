<template>
  <div style="margin:0">
    <el-table :data="conditionExplain" height="485">
      <el-table-column property="conditionName" label="条件" width="500px"></el-table-column>
      <el-table-column property="explain" label="说明"></el-table-column>
    </el-table>
    <div style="text-align: center">
      <el-button type="primary" @click="close">确定</el-button>
    </div>
  </div>
</template>

<script>
  export default {
    name: "matchConditionDesc",
    props: {
      name: String,
    },
    data() {
      return {
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
      close() {
        this.$emit('close', {action: 'confirm'})
      },
    },
    mounted() {
      console.log(this.name)
    }
  }
</script>

<style scoped>

</style>
