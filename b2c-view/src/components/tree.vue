<template>
  <el-submenu v-if="subMenu.hasSubMenu" :index="subMenu.name+'-'+subMenu.path" :id="subMenu.name+'-'+subMenu.path">
    <template slot="title"><i :class="subMenu.icon"></i>{{subMenu.name}}</template>
    <tree-menu v-for="(subSubMenu, subSubIndex) in subMenu.subMenus" :sub-menu="subSubMenu" :parent-index="subIndex+1"
               :sub-index="subSubIndex+1" :key="Math.random()">
    </tree-menu>
  </el-submenu>
  <el-menu-item v-else :index="subMenu.name+'-'+subMenu.path" :id="subMenu.name+'-'+subMenu.path">
    <template slot="title"><i :class="subMenu.icon"></i>{{subMenu.name}}</template>
  </el-menu-item>
</template>
<script>
  export default {
    name: 'treeMenu',
    props: {
      subMenu: {
        type: Object,
        required: true
      },
      parentIndex: {
        type: Number,
        required: true
      },
      subIndex: {
        type: Number,
        required: true
      },
    },
  }
</script>
