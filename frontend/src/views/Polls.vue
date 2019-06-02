<template>
  <div class="container">
    <div class="header">
      <h2>Liste des sondages</h2>
      <router-link to="/create" class="link">Créer un sondage!</router-link>
    </div>
    <ul class="polls" >
      <li class="poll" v-for="poll in polls" v-bind:key="poll.id" @click="goTo(poll.id)">
        <div class="poll-info">
          <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-eye"><path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"></path><circle cx="12" cy="12" r="3"></circle></svg>
          <span>{{ poll.name }}</span>
        </div>
        <ul class="choices">
          <li v-for="choice in poll.choices" v-bind:key="choice.id" class="choice">
            {{ choice.name }} - {{ choice.users.length }} votes
          </li>
        </ul>
      </li>
    </ul>
  </div>
</template>

<script>
// @ is an alias to /src
import axios from 'axios'
import router from '../router'

export default {
  name: 'polls',
  data: function() {
    return {
      polls: []
    }
  },
  mounted: function() {
    axios
      .get('http://localhost:7777/polls')
      .then(response => (this.polls = response.data))
    
  },
  methods: {
    goTo: function(id) {
      router.push({name: 'show', params: {id}})
    }
  }
}
</script>

<style scoped>
.polls {
  padding: 0;
  margin-top: 0;
}

.feather {
  margin-right: 1rem;
}

.choice:first-child {
  margin-top: 1rem;
}

.poll {
  background-color: white;
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  color: #242424;
  font-size: 1.3rem;
  padding: 1rem;
  cursor: pointer;
  margin-bottom: 1rem;
  list-style: none;
}

.poll-info {
  display: flex;
  align-items: center;
}

.container {
  width: 800px;
  background-color: #98AEEB;
  box-shadow: 0 1px 3px 0 rgba(0, 0, 0, 0.1), 0 1px 2px 0 rgba(0, 0, 0, 0.06);
  border-radius: 0.25rem;
  margin: 2rem;
}

.header {
  padding: 1rem;
  display: flex;
  justify-content: space-between;
  display: flex;
  align-items: center;
}

.link {
  text-decoration: none;
  color: inherit;
  font-size: 1rem;
  padding: 1rem;
  background-color: #5D55FA;
  border-radius: 5px;
  color: white;
  font-weight: 600;
}
</style>
