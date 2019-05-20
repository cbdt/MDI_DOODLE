<template>
  <div class="app">
    <div class="container">
      <create-poll></create-poll>
    </div>
  </div>
</template>

<script>
import CreatePoll from './components/CreatePoll.vue'

export default {
  name: 'app',
  components: {
    CreatePoll
  }
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
  height: 100vh;;
  color: white;
  display: flex;
  justify-content: center;
  align-items: center;
}

.container {
  width: 800px;
  background-color: #98AEEB;
  box-shadow: 0 1px 3px 0 rgba(0, 0, 0, 0.1), 0 1px 2px 0 rgba(0, 0, 0, 0.06);
  border-radius: 0.25rem;
}
</style>
