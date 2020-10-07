<template>
  <q-page class="flex flex-center">
    <div
      class=""
    >
      <img
        alt="Quasar logo"
        src="~assets/quasar-logo-full.svg"
      >
    
    <div class="ac mt-5" align="center">
      <q-btn to="./dashboard" color="primary">Dashboard</q-btn>
    </div>
    <div class="ac mt-1" align="center">
      <q-btn to="./signup" color="primary">Cadastrar usuário</q-btn>
    </div>
    <div class="ac mt-1" align="center">
      <q-btn color="black" @click="goToGithub">Codigo no github</q-btn>
    </div>
  
    </div>

  </q-page>
</template>

<script>
export default {
  name: 'PageIndex',

  methods:{
    goToGithub(){
      window.location.href = "https://github.com/pedrolopeshlsSENAI/senai-Test/tree/master";
    }
  }
}
</script>
