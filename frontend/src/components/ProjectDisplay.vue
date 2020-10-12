<template>

  <div class="project">
    <p class="projectName">{{project.projectName}}</p>
    <a v-bind:href="project.projectInstructions"
          target="_blank" >
    <img v-bind:src="project.projectImg" class="projectImg  gridImg" width="200" height="200"/>
    </a>
   <router-link class="text-dark" v-bind:to="{ name: 'project-details', params: {id:project.projectId}}">Show Project Details</router-link>
    <br>
  </div>
  
</template>

<script>

export default {
    name: 'project-display',
  
    props: {
        project: Object,
        
    },
    


  
}
</script>

<style>





</style>