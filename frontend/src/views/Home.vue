<template>
  <div class="home">
    <router-link to="/create">Créer un doodle!</router-link>
    <ul>
      <li v-for="poll in polls" v-bind:key="poll.id">
        {{ poll.name }}
        <ul>
          <li v-for="choice in poll.choices" v-bind:key="choice.id">{{ choice }}</li>
        </ul>
      </li>
    </ul>
  </div>
</template>

<script>
// @ is an alias to /src
import axios from 'axios'

export default {
  name: 'home',
  data: function() {
    return {
      polls: []
    }
  },
  mounted: function() {
    axios
      .get('http://localhost:7777/polls')
      .then(response => (this.polls = response.data))
    
  }
}
</script>
