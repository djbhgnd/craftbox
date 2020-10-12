<template>
  <div>
    
    <project-details-comp v-bind:project="project" v-bind:missingSupplies="missingSupplies" >
    </project-details-comp>
    
  </div>
</template>

<script>

import ProjectDetailsComp from '@/components/ProjectDetailsComp.vue'
import ProjectService from '../services/ProjectService.js'

export default {
    name: 'project-details',

      data() {
        return {
            projectId: 0,
            project: {},
            missingSupplies: []
        }
    
    },
    
   components:{
      ProjectDetailsComp

    },
     props:[
         'id'         
     ],

     created() { 
     ProjectService.getProjectByID(this.id).then(response => {
       this.project = response.data;
     });
     ProjectService.getMissingSuppliesByProjectId(this.id).then(response => this.missingSupplies = response.data);
    }
  }



</script>

<style>



</style>