<template>
<div class = "AllPossibleAndAlmostPossibleDiv">
  <div class = "projectDisplayAllPossibleProjects">
      <h1 class="header transparent">Projects You Can Do!</h1>
      <p>(Projects you can do now with you Craftbox!)</p>
      <div class="container">
  <project-display v-for="currentProject in allPossibleProjects" v-bind:key="currentProject.projectId" v-bind:project="currentProject" > </project-display>
      </div>
  </div>
  <div class = "projectDisplayAlmostPossibleProjects">
      <h1 class="header transparent">Projects You Can Almost Do!</h1>
      <p>(Projects you almost have everything for!)</p>
      <div class="container">
      <project-display v-for="currentProject in almostPossibleProjects" v-bind:key="currentProject.projectId" v-bind:project="currentProject"></project-display>
        </div>
  </div>
</div>

</template>

<script>
// import FindProjects from '../components/FindProjects.vue';
// import PossibleProjects from '../views/BrowsePossibleProjects.vue';
import ProjectDisplay from '../components/ProjectDisplay.vue';
import ProjectService from '@/services/ProjectService.js';

export default {
    name: 'find-projects',
components: {
    ProjectDisplay
},
data() {
        return {
            allPossibleProjects: [],
            almostPossibleProjects: [],
            
        }
    },
    created() {
        ProjectService.getProjectsWithAllSupplies().then(response => this.allPossibleProjects = response.data);
        ProjectService.getProjectsWithMostSupplies().then(response => this.almostPossibleProjects = response.data);
    }
}
</script>

<style>

.projectDisplayAllPossibleProjects{
    float: left;
    align-content: center;
    width: 50%;
    
}
.projectDisplayAlmostPossibleProjects{
    float: right;
    width: 50%;
    height: 100%;
    border-left: 2px solid black;
}
@media (max-width:1000px){
    .AllPossibleAndAlmostPossibleDiv{
        display:flex;
        flex-direction: column;
        align-items: center;
    }
     .AllPossibleAndAlmostPossibleDiv .container{
        display:flex;
        justify-content: center;
    }
    .AllPossibleAndAlmostPossibleDiv .header{
        display:flex;
        align-items: stretch;
        justify-content: stretch;
    }
     .projectDisplayAlmostPossibleProjects{
        margin-top: 20px;
        border-left:none;
        width: 100%;
    }
    .projectDisplayAllPossibleProjects{
        width: 100%;
    }
}
p{
    text-align: center;
}
</style>