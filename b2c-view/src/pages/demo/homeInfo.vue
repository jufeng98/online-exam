<template>
  <div>
    <div class="radio-wrap">
      <div class="radio-group" v-model="tabView">
        <span :class="{cur:iscur===index}" @click="iscur=index,tabChange(tab.name)" style="cursor: pointer;"
              v-for="(tab ,index) in tabs">
             {{tab.label}}
        </span>
      </div>
      <div style="margin:10px 0;"></div>
      <!--include:需要缓存的组件,exclude:不需要缓存的组件-->
      <keep-alive exclude="productConsumeRecord" include="testComponent1,testComponent2">
        <component :username="username" v-bind:is="tabView"/>
      </keep-alive>
    </div>
  </div>
</template>

<script>
  import testComponent1 from "./testComponent1"
  import testComponent2 from "./testComponent2"
  import productConsumeRecord from "./productConsumeRecord"

  export default {
    props: {
      username: String,
    },
    data() {
      return {
        tabView: 'testComponent1',
        iscur: 0,
        tabs: [
          {label: "选项一", name: "testComponent1"},
          {label: "选项二", name: "testComponent2"},
          {
            label: "选项三",
            name: "productConsumeRecord"
          }
        ]
      }
    },
    components: {
      testComponent1,
      testComponent2,
      productConsumeRecord,
    },
    methods: {
      tabChange(tab) {
        this.tabView = tab;
      },
    }
  }
</script>

<style scoped>
  .cur {
    background-color: dodgerblue;
  }
</style>
