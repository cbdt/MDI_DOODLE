<template>
  <div class="container">
    <router-link to="/">Revenir à la page d'accueil</router-link>
    <div class="form-container">
      <div class="form-header">
        <h1 class="title">Créer un doodle!</h1>
      </div>
      <div v-if="step === 1">
        <form v-on:submit.prevent="goToNext" class="create-form">
          <span class="label-error" v-show="this.errors['name'] !== undefined">{{this.errors['name']}}</span>
          <input type="text" id="name" class="create-form-input input" placeholder="Soirée SI" v-model.trim="name" :class="{ 'error': this.errors['name'] !== undefined }" />
          <span class="label-error" v-show="this.errors['location'] !== undefined">{{this.errors['location']}}</span>
          <input type="text" id="location" class="create-form-input  input" placeholder="Chez Kévin" v-model.trim="location" :class="{ 'error': this.errors['location'] !== undefined }"/>
          <textarea id="description" class="create-form-input input create-form-input__textarea" placeholder="Description" v-model.trim="description">
          </textarea>
          <button class="create-form-button primary-button">
            Suivant
          </button>
        </form>
      </div>
      <div v-if="step === 2" class="create-choices-form">
        <div class="create-form">
          <div class="create-choices-field">
            <input type="text" id="choice" class="input create-choices-input" placeholder="Barbecue..." v-model.trim="newChoice" v-on:keyup.enter="addChoice"/>
            <button class="add-button" @click="addChoice">Ajouter</button>
          </div>
          <ul class="create-choices-choices">
          <li v-for="(choice, index) in choices" :key="index"  class="create-choices-choice" @click="deleteChoice(index)">
              {{ choice }}
          </li>
          </ul>
          <div class="inputs">
          <button class="create-form-button primary-button" @click="goToPrevious">
            Modifier
          </button>
          <button class="create-form-button success-button" @click="onSubmit">
            Créer!
          </button>
          </div>
        </div>
      </div>
      <!--<div v-if="false">
        <form v-on:submit.prevent="onSubmit" class="create-form">
          <div v-for="choice in choices" :key="choice.id"  class="choice-form-field" :class="{ 'choice-selected': isSelected(choice.id) }" @click="select(choice.id)">
            <div :id="choice.id" :name="choice.name"  >
              {{ choice.name }} - {{ choice.count }}
            </div>
          </div>
          <button class="create-form-button">
            Créer!
          </button>
        </form>
      </div>-->
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'CreatePoll',
  data: () => {
    return {
      step: 1,
      name: "",
      location: "",
      description: "",
      choices: [],
      //selected: [],
      errors: {},
      newChoice: "",
    }
  },
  methods: {
    deleteChoice: function(index) {
      this.choices.splice(index, 1)
    },
    goToNext: function() {
      this.errors = []
      if(this.name === "") {
         this.$set(this.errors, 'name', "Veuillez indiquer un nom")
         return
      }
      if(this.location === "") {
         this.$set(this.errors, 'location', "Veuillez indiquer une localisation")
         return
      }
      this.step = 2
    },
    goToPrevious: function() {
      this.step = 1
    },
    onSubmit: function() {
      const data = {
        "name": this.name,
        "location": this.location,
        "description": this.description,
      }
      console.log(data)

      const data_choices = this.choices.map((e) => {
        return {"name": e}
      })
      console.log(data_choices)
      axios.post('http://localhost:7777/polls', data)
        .then(res => {
          const { id } = res.data.id
          axios.post(`http://localhost:7777/polls/${id}/choices`, data_choices)
            .then(res => {
              console.log(res)
            })
            .catch((err) => {
              console.log(err)
            })
        })
    },
    select: function(id) {
      if(this.selected.indexOf(id) === -1) {
        this.selected.push(id)
      } else {
        this.selected.splice(this.selected.indexOf(id), 1)
      }
    },
    isSelected: function(id) {
      return this.selected.indexOf(id) !== -1
    },
    addChoice: function() {
      if(this.newChoice === "") { return }
      this.choices.push(this.newChoice)
      this.newChoice = ""
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

.form-header {
  border-bottom: 8px solid #FFE0F0;
  padding: 1rem;
}

.title {
  margin: 0;
}

.success-button {
  background-color: #EF4E4E;
}

.success-button:hover {
  background-color: #E12D39;
}

.primary-button {
  background-color: #5D55FA;
}

.primary-button:hover {
  background-color: #4D3DF7;
}

.inputs {
  display: flex;
}
.add-button {
  border: none;
  font-size: 1.2rem;
  padding: 1.2rem;
  background-color: #5D55FA;
  border: 4px solid white;
  border-radius: 5px;
  color: white;
  font-weight: 600;
  font-size: 1.2rem;
}

.add-button:hover {
  background-color: #4D3DF7;
}

.title {
  text-align: center;
}

.create-form {
  padding: 1rem;
}

.choice-selected {
  background-color: #F7C948!important;
}

.label-error {
  color: #D64545;
}

.error {
  border: 2px solid #D64545!important;
}

li {
  list-style: none;
}

.create-choices-choices {
  max-height: 500px;
  overflow-y: scroll;
  padding: 0;
  margin: 0;
}

.create-choices-field {
  margin-bottom: 1rem;
  display: flex;
}

.create-choices-input {
  flex: 1;
  margin-right: 1rem;
}

.create-form-input {
  width: 100%;
  margin-bottom: 1rem;
}

.input {
  display: block;
  /*height: 40px;*/
  border-radius: 5px;
  border: none;
  color: #242424;
  font-size: 1.2rem;
  padding: 1.2rem;
  box-shadow: 0 1px 3px 0 rgba(0, 0, 0, 0.1), 0 1px 2px 0 rgba(0, 0, 0, 0.06);
}

.create-choices-choice {
  color: #242424;
  font-size: 1.2rem;
  padding: 1rem;
  background-color: white;
  margin-bottom: 1rem;
  font-weight: 500;
  border: 4px solid white;
  cursor: pointer;
}

.create-choices-choice:hover {
  border: 4px solid #5D55FA;
}

.create-form-input__textarea {
  resize: vertical;
  min-width: 100%;
  max-width: 100%;
}

.create-form-field label {
  display: block;
}

.create-form-button:last-child {
  margin-right: 0;
}

.create-form-button {
  margin-right: 1rem;
  width: 100%;
  border: none;
  color: white;
  font-weight: 600;
  font-size: 1.5rem;
  box-shadow: 0 1px 3px 0 rgba(0, 0, 0, 0.1), 0 1px 2px 0 rgba(0, 0, 0, 0.06);
  border-radius: 5px;
  padding: 0.8rem;
}

.container {
  width: 800px;
}

.form-container {
  background-color: #98AEEB;
  box-shadow: 0 1px 3px 0 rgba(0, 0, 0, 0.1), 0 1px 2px 0 rgba(0, 0, 0, 0.06);
  border-radius: 0.25rem;
}
</style>
