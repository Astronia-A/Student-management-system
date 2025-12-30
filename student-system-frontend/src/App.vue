<template>
  <!-- router-view 是一个占位符，它会根据当前 URL 自动显示对应的组件（Login 或 StudentList） -->
  <router-view />
</template>

<script setup>
// 这里暂时不需要写逻辑
</script>

<style>
/* 全局样式：去除浏览器默认边距 */
body {
  margin: 0;
  padding: 0;
  font-family: 'Helvetica Neue', Helvetica, 'PingFang SC', 'Hiragino Sans GB', 'Microsoft YaHei', Arial, sans-serif;
}
</style>