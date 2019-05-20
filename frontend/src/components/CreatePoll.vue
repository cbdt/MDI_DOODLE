<template>
  <div class="form-container">

    <h1 class="title">Créer un doodle!</h1>
    <div v-if="step == 1">
      <form v-on:submit.prevent="goToNext" class="create-form">
        <span class="label-error" v-show="this.errors['name'] !== undefined">{{this.errors['name']}}</span>
        <input type="text" id="name" class="create-form-input" placeholder="Nom" v-model.trim="name" :class="{ 'error': this.errors['name'] !== undefined }" />
        <span class="label-error" v-show="this.errors['location'] !== undefined">{{this.errors['location']}}</span>
        <input type="text" id="location" class="create-form-input" placeholder="Localisation" v-model.trim="location" :class="{ 'error': this.errors['location'] !== undefined }"/>
        <textarea id="description" class="create-form-input create-form-input__textarea" placeholder="Description" v-model.trim="description">
        </textarea>
        <button class="create-form-button">
          Suivant
        </button>
      </form>
    </div>
    <div v-if="step === 2" class="create-choices-form">
      <form v-on:submit.prevent="onSubmit" class="create-form">
        <input type="text" id="choice" class="create-form-input" placeholder="Choix" v-model.trim="newChoice" v-on:keyup.enter="addChoice"/>
        <div v-for="(choice, index) in choices" :key="index"  class="choice-form-field">
          <div :id="index" :name="choice">
            {{ choice }}
          </div>
        </div>
        <button class="create-form-button" >
          Créer!
        </button>
      </form>
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
</template>

<script>
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
    onSubmit: function() {
      console.log("Submitted!")
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

.create-choices-form {
  max-height: 700px;
  overflow-y: scroll;
}

.create-form-input {
  display: block;
  /*height: 40px;*/
  border-radius: 5px;
  border: none;
  color: #242424;
  font-size: 1.2rem;
  padding: 1.2rem;
  width: 100%;
  margin-bottom: 1rem;
  box-shadow: 0 1px 3px 0 rgba(0, 0, 0, 0.1), 0 1px 2px 0 rgba(0, 0, 0, 0.06);
}

.choice-form-field {
  color: #242424;
  font-size: 1.2rem;
  padding: 1rem;
  background-color: white;
  margin-bottom: 1rem;
  font-weight: 500;
  cursor: pointer;
}

.create-form-input__textarea {
  resize: vertical;
  min-width: 100%;
  max-width: 100%;
}

.create-form-field label {
  display: block;
}

.create-form-button {
  width: 100%;
  height: 40px;
  border: none;
  background-color: #4055A8;
  color: white;
  font-weight: 600;
  font-size: 1.5rem;
  box-shadow: 0 1px 3px 0 rgba(0, 0, 0, 0.1), 0 1px 2px 0 rgba(0, 0, 0, 0.06);
  border-radius: 5px;

}
</style>
