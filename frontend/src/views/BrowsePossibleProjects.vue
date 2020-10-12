<template>
<div>
  <h1 class="header transparent">These are the projects you can do!</h1>
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
name: 'possible-projects',
components: {
    ProjectDisplay
},
data() {
        return {
            allProjects: []
        }
    },
    created() {
        ProjectService.getProjectsWithAllSupplies().then(response => this.allProjects = response.data);
    }
}
</script>

<style>

</style>