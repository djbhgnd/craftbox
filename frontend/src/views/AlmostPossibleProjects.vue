<template>
<div>
  <h1 class="header transparent">These are the projects you can almost do!</h1>
  <p style="text-align:center">Check each project! If you're missing one or two materials, substitute other items in your Craftbox!</p>
  <div class="container">
      <project-display v-for="currentProject in allProjects" v-bind:key="currentProject.projectId" v-bind:project="currentProject">
      </project-display>
  </div>
</div>
</template>

<script>

import ProjectService from '@/services/ProjectService.js';
import ProjectDisplay from '@/components/ProjectDisplay.vue';

export default {
name: 'almost-possible-projects',
components: {
    ProjectDisplay
},
data() {
        return {
            allProjects: []
        }
    },
    created() {
        ProjectService.getProjectsWithMostSupplies().then(response => this.allProjects = response.data);
    }
}
</script>

<style>

</style>