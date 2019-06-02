<template>
  <div class="app">
    <router-view></router-view>
  </div>
</template>

<script>

export default {
  name: 'app',
}
</script>

<style>

body {
  margin: 0;
  padding: 0;
}

* {
  box-sizing: border-box;
}

.app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  background-color: #FFE0F0;
  width: 100vw;
  height: 100%;
  min-height: 100vh;
  color: white;
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
