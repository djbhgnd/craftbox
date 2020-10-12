<template>
  <div>
      <div class="landing" v-on:click="toggleLanding()"
      v-show="!this.$store.state.clickedLanding">
      <br>
      <br>
      <br>
      <img src="..\assets\Craft Box Logo Final Final.png" class="landingImg">
      <br>
      <br>
      <h1> Get Started!</h1>
      </div>
  <div id="login" class="text-center" v-show="this.$store.state.clickedLanding">
    <form class="form-signin" @submit.prevent="login">
      <h1 class="h3 mb-3 font-weight-normal">Welcome to Craft Box</h1>
      <div
        class="alert alert-danger"
        role="alert"
        v-if="invalidCredentials"
      >Invalid username and password!</div>
      <div
        class="alert alert-success"
        role="alert"
        v-if="this.$route.query.registration"
      >Thank you for registering, please sign in.</div>
      <label for="username" class="sr-only">Username</label>
      <input
        type="text"
        id="username"
        class="form-control"
        placeholder="Username"
        v-model="user.username"
        required
        autofocus
      /><br/>
      <label for="password" class="sr-only">Password</label>
      <input
        type="password"
        id="password"
        class="form-control"
        placeholder="Password"
        v-model="user.password"
        required
      /><br/>
      <button type="submit" class="btn btn-lg btn-primary btn-block">Sign in</button>
      <br/>
      <router-link :to="{ name: 'register' }">Need an account?</router-link>
      
    </form>
  </div>
  </div>
</template>

<script>
import authService from '../services/AuthService';

export default {
  name: 'login',
  components: {},
  data() {
    return {
      user: {
        username: '',
        password: ''
      },
      invalidCredentials: false,
      
    };
  },
  created() {
    this.$store.state.clickedLanding = false;
  },
  methods: {
    login() {
      authService
        .login(this.user)
        .then(response => {
          if (response.status == 200) {
            this.$store.commit('SET_AUTH_TOKEN', response.data.token);
            this.$store.commit('SET_USER', response.data.user);
            this.$router.push('/');
          }
        })
        .catch(error => {
          const response = error.response;

          if (response.status === 401) {
            this.invalidCredentials = true;
          }
        });
    },
    toggleLanding(){
      this.$store.state.clickedLanding = true;
    
    }
  }
};
</script>

<style>
.landing{
  text-align: center;
}



</style>