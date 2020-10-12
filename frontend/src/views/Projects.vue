<template>
  <div>
    <h1 class="header transparent">Browse All Projects</h1>
    <button
      class="addProject btn btn-secondary"
      v-on:click="addingProject = true"
      v-show="isCurator === true"
    >Add Project</button>
    <div class="search-wrapper">
                <label>Search Projects</label>
    <input type="text" v-model="search" placeholder="Project Name"/>
        
  </div>

    <div class="additionsContainer">

      <div class="addProjectsContainer">
      <div class="formPopup" id="addProjectForm" v-show="addingProject === true">
        <form class="add-project-form-container">
        <br />
          <h4 style="font-weight: bold">Add Project</h4>
          <br />
          <label for="projectName">Project Name:</label>
          <br />
          <input v-model="projectName" type="text" id="projectName" name="projectName" />
          <br />
          <label for="projectImg">Project Image Link:</label>
          <br />
          <input v-model="projectImg" type="text" id="projectImg" name="projectImg" />
          <br />
          <label for="projectInstructions">Project Instructions Link:</label>
          <br />
          <input v-model="projectInstructions" type="text" id="projectInstructions" name="projectInstructions"/>
          <br />
          <label for="ageRange">Project Age Range (Optional):</label>
          <br />
          <input v-model="ageRange" type="text" id="ageRange" name="ageRange" />
          <br />
          <label for="season">Project Season (Optional):</label>
          <br />
          <input v-model="season" type="text" id="season" name="season" />
          <br />
          <br />
        </form>
      </div>
    </div>
      <div class="supplyBoxBorderDiv" id="allSupplies" v-show="addingProject === true" >
          <div class="supplyBox">
            <div
              class="supplyItem"
              v-for="currentSupply in this.allSupplies"
              v-bind:key="currentSupply.supplyId"
              v-bind:supply="currentSupply" 
              v-on:click="addSupplyToNewProject(currentSupply)"
            >
              <span class="supplyName">{{currentSupply.supplyName}}</span>
              <img
                src="../assets/addIcon.png"
                alt="plus symbol"
                width="25"
                height="25"
                input
                class="add"
                type="button"
                value="Add"
              />
            </div>
          </div>
      </div>

      <div class="supplyBoxBorderDiv" id="allSupplies" v-show="addingProject === true" >

      <div class="supplyBox" >
        <div id="addedProjectSupplies" 
              class="supplyItem"
              v-for="currentSupply in this.addedProjectSupplies"
              v-bind:key="currentSupply.supplyId"
              v-bind:supply="currentSupply" 
              v-on:click="removeSupplyFromNewProject(currentSupply)"> 
            <span class="supplyName">{{currentSupply.supplyName}}</span>
          </div>
      </div>
</div>
  </div>


    <div class="buttons">
      <input
            id="submit-btn"
            class="submit btn btn-success"
            type="button"
            value="Submit"
            v-on:click="addProjectToDatabase()"
            v-show="addingProject === true"
          />
          <input
            id="cancel-btn"
            class="cancel btn btn-danger"
            type="button"
            value="Cancel"
            v-on:click="clearProjectForm()"
            v-show="addingProject === true"
          />
    </div>
    
    <div class="container">
      <project-display
        v-show="addingProject === false"
        v-for="currentProject in filteredList"
        v-bind:key="currentProject.projectId"
        v-bind:project="currentProject"
      ></project-display>
    </div>
  </div>
</template>

<script>
import ProjectService from "@/services/ProjectService.js";
import ProjectDisplay from "@/components/ProjectDisplay.vue";
import SupplyService from "@/services/SupplyService.js";
export default {
  name: "projects",
  data() {
    return {
      allProjects: [],
      addingProject: false,
      projectId: null,
      projectName: null,
      projectImg: null,
      projectInstructions: null,
      ageRange: null,
      season: null,
      isCurator: false,
      addedProjectSupplies: [],
      allSupplies: [],
      search: null
    };
  },
  components: {
    ProjectDisplay,
  },
  computed: {
    filteredList() {
    if(this.search){
        return this.allProjects.filter(project => {
        return project.projectName.toLowerCase().includes(this.search.toLowerCase())
      })
    }
    else {
        return this.allProjects;
    }
      
    }
  },
  created() {
    SupplyService.fetchSupplies().then(
      (response) => (this.allSupplies = response.data)
    );
    ProjectService.getProjects().then(
      (response) => (this.allProjects = response.data)
    );
    if (this.$store.state.user.authorities[0].name === "ROLE_ADMIN") {
      this.isCurator = true;
    }
  },
  methods: {
    addProjectToDatabase() {
        //add new project to database
      let newProject = {};
      newProject.projectName = this.projectName;
      newProject.projectImg = this.projectImg;
      newProject.projectInstructions = this.projectInstructions;
      newProject.ageRange = this.ageRange;
      newProject.season = this.season;
      ProjectService.addNewProject(newProject).then(
          (response) => {
              (this.projectId = response.data);
                 //add supplies to new project
             for(let i = 0; i<this.addedProjectSupplies.length; i++){
          SupplyService.addSupplyToProject(this.addedProjectSupplies[i], this.projectId);
             }
      }
          
      );
     

        //update the projects displayed
      ProjectService.getProjects().then(
        (response) => (this.allProjects = response.data)
      );
      


      


      this.clearProjectForm();
      this.projectSubmitted = true;
      ProjectService.getProjects().then(
        (response) => (this.allProjects = response.data)
      );
    },
    clearProjectForm() {
      this.addingProject = false;
      this.projectName = null;
    },
    addSupplyToNewProject(currentSupply){
       this.addedProjectSupplies.push(currentSupply); 
    },
    removeSupplyFromNewProject(supplyToDelete){
        let newArray = [];
        for(let i=0 ; i<this.addedProjectSupplies.length ; i++){
            if(this.addedProjectSupplies[i] != supplyToDelete){
                newArray.push(this.addedProjectSupplies[i]);
            }
            
        }
        this.addedProjectSupplies = newArray;
        
    }
  },
};
</script>

<style scoped>


.additionsContainer {
  display: flex;
  flex-direction: row;
  flex-basis: auto;
  padding: 10px;
  align-items: flex-start;
}

#allSupplies {
  width: 40%;
  height: 70vh;
  margin-top: 0;
  overflow-y: hidden;
  flex-basis: auto;
}

.supplyBox {
  width: 40%;
  height: 70vh;  
  margin: auto;
  overflow: scroll;
  background-color: rgb(131,226,43, .50 );
  border-color: white 20px;
  margin-top: 0;
  border-radius: 20px;
}

#allSupplies .supplyBox {
  width: 100%;
}

.buttons {
  margin-left: auto;
  margin-right: auto;
  width: 200px;
}

.addProject.btn.btn-secondary {
  width: 20%;
  margin-left: 10px;
}

.supplyItem {
  max-height: 20%;
}

/* .addProjectsContainer{
    text-align: center;
    margin: auto;
    max-width: 300px;
    max-height: 600px;
    
} */

</style>