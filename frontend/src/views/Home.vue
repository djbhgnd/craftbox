<head>

</head>
<template>
  <div class="home">
    <div class="transparent">
      <img class="logo" src="..\assets\Craft Box Logo Final Final.png">
    <h1 class="header">Welcome to Craft Box, {{$store.state.user.username}}!</h1>
    <!--p> Your role is {{$store.state.user.authorities[0].name}}</p>-->
    <h3 class="subheader">Browse projects and get some good ideas for your great crafts.</h3>
    </div>
    <div id="link-container">
      <div id="craftBox" class="containerElement">
        <router-link v-bind:to="{ name: 'craftbox' }">
        <button type="submit" class="btn btn-lg btn-primary">
      My Craft Box</button></router-link>
      </div>
      <div id="browseProjects" class="containerElement">
        <router-link v-bind:to="{ name: 'projects' }"><button type="submit" class="btn btn-lg btn-primary">
      Browse All Projects</button></router-link>
      </div>
    </div>

    <div class="carousel-div">
    <b-carousel
      id="carousel-1"
      v-model="slide"
      :interval="4000"
      controls
      background="#ababab"
      style="text-shadow: 1px 1px 2px #333;"
    >
      <!-- Text slides with image -->
      <b-carousel-slide
        class="carousel-image"
        caption="Paper Bag Monster Puppets"
        img-src="..\assets\paper-bag-monsters.png"
      ></b-carousel-slide>

      <!-- Slides with custom text -->
      <b-carousel-slide
      class="carousel-image"
      caption="Paper Plate Dinosaur"
      img-src="..\assets\How-to-make-a-paper-dinosaur.png">
      </b-carousel-slide>

      <b-carousel-slide
      class="carousel-image"
      caption="Fire Truck Handprint"
      img-src="..\assets\fire-truck-crafts-.png"
      ></b-carousel-slide>

      <b-carousel-slide
      class="carousel-image"
      caption="Clothespin Snowman"
      img-src="..\assets\Clothespin-Snowman-Craft-for-Kids.png"
      ></b-carousel-slide>

      <b-carousel-slide
      class="carousel-image"
      caption="Craft Stained Glass Window"
      img-src="..\assets\stained_glass window_diy_make.png"
      ></b-carousel-slide>
    </b-carousel>
  </div>

  </div>
  
</template>

<script>
import ProjectService from '@/services/ProjectService.js';

export default {
  name: 'home',
  props:{currentUser:Object,

  project: Object},
data() {
      return {
        slide: 0,
        sliding: null
      }
    },
  methods:{
    getRandomProject(){
      let randomProject = ProjectService.getProjectByID(Math.floor((1+Math.random()) * 100)).then(response => this.project = response.data);
       return randomProject;
    }
  },

  created() { 
     ProjectService.getProjectByID(Math.floor((1+Math.random()) * 100)).then(response => this.project = response.data);
      if(this.$store.state.user.authorities[0].name === "ROLE_ADMIN"){
        this.$store.state.isCurator = true;
      }
    }

};
</script>

<style>

.carousel-div{
  display: flex;
  padding-top: 30px;
  max-width: 600px;
  max-height: 300px;
  margin-left: auto;
  margin-right: auto;
}


</style>