<template>
  <div class="container">

    <router-link to="/" class="link">Revenir à la page d'accueil</router-link>
    <div class="wrapper">
      <div v-if="loading" class="loader">
        <div class="lds-ring"><div></div><div></div><div></div><div></div></div>
      </div>
      <div v-else>
        <div class="header">
          <h2>{{ this.poll.name }}</h2>
        </div>
        <div class="body">
          <div v-if="!hasVoted">
            <h4 class="body-title">Liste des choix</h4>
            <ul>
              <li class="body-choice" v-for="choice in this.poll.choices" :key="choice.id"
              @click="vote(choice.id)">
                {{ choice.name }} - {{ choice.users.length }} votes
              </li>
            </ul>
          </div>
          <div v-else>
            <h3>Merci pour votre vote !</h3>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
// @ is an alias to /src
import axios from 'axios'

export default {
  name: 'poll',
  data: function() {
    return {
      poll: undefined,
      loading: true,
      hasVoted: false,
    }
  },
  mounted: function() {
    const { id } = this.$route.params
    
    axios.get(`http://localhost:7777/polls/${id}`)
      .then((res) => {
        this.poll = res.data
        this.loading = false
      })
    
  },
  methods: {
    vote: function(id) {
      let name = prompt("Veuillez préciser un pseudo")
      let user = {username: name}
      axios.post(`http://localhost:7777/users`, user)
        .then(res => {
          let { id: userId } = res.data
          let choices = [id]
          axios.post(`http://localhost:7777/polls/${this.poll.id}/vote/${userId}`, {
            choices
          })
          .then(() => {
                /*
                //TODO: Ne pas refaire un requête, ça implique de refactoriser la manière dont on sauvegarde les choices et de sauvegarder la liste des choices en dehors du dict poll
                axios.get(`http://localhost:7777/polls/${this.poll.id}`)
                .then((res) => {
                  this.poll = res.data
                })
                */
                this.hasVoted = true
          })
          .catch(err => {
            console.error(err)
          })
        })
        .catch(err => {
          console.error(err)
        })

    }
  }
}
</script>

<style scoped>

.lds-ring {
  display: inline-block;
  position: relative;
  width: 64px;
  height: 64px;
}
.lds-ring div {
  box-sizing: border-box;
  display: block;
  position: absolute;
  width: 51px;
  height: 51px;
  margin: 6px;
  border: 6px solid #fff;
  border-radius: 50%;
  animation: lds-ring 1.2s cubic-bezier(0.5, 0, 0.5, 1) infinite;
  border-color: #fff transparent transparent transparent;
}
.lds-ring div:nth-child(1) {
  animation-delay: -0.45s;
}
.lds-ring div:nth-child(2) {
  animation-delay: -0.3s;
}
.lds-ring div:nth-child(3) {
  animation-delay: -0.15s;
}
@keyframes lds-ring {
  0% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(360deg);
  }
}


.container {
  width: 800px;
}

.link {
  text-decoration: none;
  color: #5D55FA;
}

.body-title {
  margin: 0;
  padding: 0;
}

.wrapper {
  background-color: #98AEEB;
  box-shadow: 0 1px 3px 0 rgba(0, 0, 0, 0.1), 0 1px 2px 0 rgba(0, 0, 0, 0.06);
  border-radius: 0.25rem;
}

.header {
  padding: 1rem;
  display: flex;
  justify-content: space-between;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1.5rem;
}

.body {
  padding: 1rem;
  font-size: 1.2rem;
}

.loader {
  display: flex;
  justify-content: center;
  padding: 2rem;
}

.body-choice {
  background-color: white;
  padding: 1rem;
  cursor: pointer;
  color: #242424;
  margin-bottom: 1rem;
}

.body-choice:last-child {
  margin-bottom: 0;
}

li {
  list-style-type: none;
}
ul {
  padding: 0;
}
</style>
