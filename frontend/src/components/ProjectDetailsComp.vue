<template>
  <div class="additionsContainer">
    <div class="editAndDeleteButtons">
      <button
        class="editProjectButton btn btn-secondary"
        v-show="isCurator === true && editingProject === false"
        v-on:click="updateProject()"
      >Edit Project</button>
      <div>
      <button
        class="deleteProjectButton btn btn-danger"
        v-show="isCurator === true && editingProject === false"
        v-on:click.prevent="deleteProjectFromDatabase(project.projectId)"
      >Delete Project</button>
    </div>
    </div>
    <div class="holdsThreeComponentsAndButtons" v-show="editingProject === true">
      <div class="holdsThreeComponents">
        <div class="formPopup" id="editProjectForm" v-show="editingProject === true">
          <form class="edit-project-form-container">
            <h4>Edit Project</h4>
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
            <input
              v-model="projectInstructions"
              type="text"
              id="projectInstructions"
              name="projectInstructions"
            />
            <br />
            <label for="ageRange">Project Age Range (Optional):</label>
            <br />
            <input v-model="ageRange" type="text" id="ageRange" name="ageRange" />
            <br />
            <label for="season">Project Season (Optional):</label>
            <br />
            <input v-model="season" type="text" id="season" name="season" />
            <br />
          </form>
        </div>
          <div class="supplyBox boxFlex" v-show="editingProject === true">
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
        <div class="supplyBox boxFlex" v-show="editingProject === true">
          <div
            id="projectCurrentSupplies"
            class="supplyItem"
            v-for="currentSupply in this.newSuppliesList"
            v-bind:key="currentSupply.supplyId"
          >
            <EditProjectSupplyComp
              v-bind:currentSupply="currentSupply"
              v-bind:newSuppliesList="newSuppliesList"
              @delete="removeSupplyFromProject"
            ></EditProjectSupplyComp>
          </div>
        </div>
        <br />
      </div>
      <div class="buttonHolder">
        <input
          id="submit-btn"
          class="submit btn btn-success"
          type="button"
          value="Submit"
          v-on:click.prevent="editProjectToDatabase()"
        />
        <input
          id="cancel-btn"
          class="cancel btn btn-danger"
          type="button"
          value="Cancel"
          v-on:click="clearProjectForm()"
        />
      </div>
    </div>
    <br />
    <br />

    <div class="cardContainer" v-show="editingProject === false">
      <div class="projectCard">
        <div class="cardImgContainer">
          <img v-bind:src="project.projectImg" class="projectImg cardImg" />
        </div>
        <div class="supplyContainerCard ">
          <div class="displayProjectNameContainer">
            <h4>
              <b>{{project.projectName}}</b>
            </h4>
          </div>
          <div class="normalAndMissingSupplies scrollbar scrollbar-rare-wind">
            <div>
              <p class="bold">Supplies Needed</p>
              <p
                id="detailSupplies"
                class="supplies text-left"
                v-for="currentSupply in project.listOfSupplies"
                v-bind:key="currentSupply.supplyId+currentSupply.color"
                
              >
                • {{currentSupply.supplyName}}
                <span
                  v-if="currentSupply.color"
                >({{currentSupply.color}})</span>
                &nbsp;
                <br />
                <span v-if="currentSupply.notes">Notes: {{currentSupply.notes}}</span>
              </p>
            </div>
            <div>
              <p class="bold" v-if="missingSupplies.length">Missing Supplies</p>
              <p
                id="missingSupplies"
                class="supplies text-left"
                v-for="currentSupply in missingSupplies"
                v-bind:key="currentSupply.supplyName"
              >• {{currentSupply.supplyName}}</p>
            </div>
          </div>
          <div class="detailsBottomConatiner">
            <div class="seasonAndAge">
              <p v-if="project.ageRange">Great for ages: {{project.ageRange}}</p><br>
              <p v-if="project.season">Great for {{project.season}}</p>
            </div>
            <a
              v-bind:href="project.projectInstructions"
              target="_blank"
              class="text-dark bold"
            >Instructions: {{project.projectName}}</a>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import ProjectService from "../services/ProjectService";
import SupplyService from "@/services/SupplyService.js";
import EditProjectSupplyComp from "../components/EditProjectSupplyComp.vue";
export default {
  name: "project-details-comp",
  data() {
    return {
      allSupplies: [],
      newSuppliesList: [],
      isCurator: false,
      editingProject: false,
      projectId: null,
      projectName: null,
      projectImg: null,
      projectInstructions: null,
      ageRange: null,
      season: null,

      //edit project sup comp
      beingEdited: false,
      viewingInfo: false,
      isClicked: false,
      color: null,
      notes: null,
      isActive: false,
      areSupplyButtonsVisible: false,
    };
  },
  components: {
    EditProjectSupplyComp,
  },
  props: {
    project: Object,
    missingSupplies: Array,
  },
  created() {
    if (this.$store.state.user.authorities[0].name === "ROLE_ADMIN") {
      this.isCurator = true;
    }

    SupplyService.fetchSupplies().then(
      (response) => (this.allSupplies = response.data)
    );
  },

  methods: {
    updateProject() {
      this.editingProject = true;
      this.projectId = this.project.projectId;
      this.projectName = this.project.projectName;
      this.projectImg = this.project.projectImg;
      this.projectInstructions = this.project.projectInstructions;
      this.ageRange = this.project.ageRange;
      this.season = this.project.season;
      this.newSuppliesList = this.project.listOfSupplies;
    },
    editProjectToDatabase() {
      this.editingProject = false;
      let updatedProject = {};
      updatedProject.projectId = this.projectId;
      updatedProject.projectName = this.projectName;
      updatedProject.projectImg = this.projectImg;
      updatedProject.projectInstructions = this.projectInstructions;
      updatedProject.ageRange = this.ageRange;
      updatedProject.season = this.season;
      ProjectService.editProject(updatedProject, updatedProject.projectId);
      SupplyService.editProjectSupplies(
        this.newSuppliesList,
        updatedProject.projectId
      );

      //reload the page
      this.$router.go();
    },

    clearProjectForm() {
      this.editingProject = false;
    },

    removeSupplyFromProject(supplyToRemove) {
      let newArray = [];

      for (let i = 0; i < this.newSuppliesList.length; i++) {
        if (this.newSuppliesList[i] != supplyToRemove) {
          newArray.push(this.newSuppliesList[i]);
        }
      }
      this.newSuppliesList = newArray;
    },

    addSupplyToNewProject(currentSupply) {
      this.newSuppliesList.push(currentSupply);
    },

    deleteProjectFromDatabase(projectId) {
      ProjectService.deleteProjectFromDatabase(projectId).then(
        this.$router.push("/projects")
      );
    },
  },
};
</script>

<style>
.editAndDeleteButtons{
  display: flex;
  justify-content: center;

}

.text-left{
  text-align: left;
}
.bold{
  font-weight: bold;
}
.boxFlex{
  align-content: flex-start;
}

.allSupplies{
  width: 40%;
}
.additionsContainer {
  display: flex;
  flex-direction: column;
  justify-content: center;
  flex-basis: auto;
  padding: 10px;
  align-items: center;
}

.buttonHolder {
  display: inline-block;
  margin-left: auto;
  margin-right: auto;
}

.cardImg {
  width: 390px;
  height: 444px;
  border-radius: 20px;
}

.cardImgContainer {
  margin: 15px;
  width: 390px;
  height: 444px;
}
.detailsBottomConatiner {
  justify-content: space-between;
  text-align: center;
}

#editProjectForm {
  width: 20%;
  text-align: center;
}

.holdsThreeComponents {
  display: flex;
  align-content: flex-start;
}

.holdsThreeComponents .supplyBox{
  width: 40%;
}

.holdsThreeComponentsAndButtons {
  display: flex;
  flex-direction: column;
}

.seasonAndAge {
  display: flex;
  flex-direction: row;
  column-gap: 10px;
  justify-content: center;
}
.normalAndMissingSupplies {
  display: flex;
  max-width: 350px;
  max-height: 400px;
  justify-content: space-between;
  margin-left: 20px;
  overflow-y: auto;
}

.displayProjectNameContainer {
  display: flex;
  max-height: 100px;
  justify-content: center;
}
.projectCard {
  /* Add shadows to create the "card" effect */
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);
  transition: 0.3s;
  background-color: rgba(138, 43, 226, 0.75);
  display: flex;
  justify-content: space-around;
  align-items: space-around;
  margin-top: 50px;
  width: 840px;
  height: 480px;
  border-radius: 20px;
  
}

/* On mouse-over, add a deeper shadow */
.projectCard:hover {
  box-shadow: 0 8px 16px 0 rgba(0, 0, 0, 0.2);
}


.supplyList{
  width: 100%;
}

/* Add some padding inside the card container */
.supplyContainerCard {
  margin: 15px;
  display: flex;
  flex-direction: column;
  background-color: rgba(207, 185, 228, 0.6);
  width: 390px;
  height: 444px;
  border-radius: 20px;
  
}

.projectCard .scrollbar-rare-wind::-webkit-scrollbar-track {
  -webkit-box-shadow: inset 0 0 6px rgba(0, 0, 0, 0.1);
  box-shadow: inset 0 0 6px rgba(0, 0, 0, 0.1);
  background-color: #F5F5F5;
  border-radius: 10px;
}
.projectCard .scrollbar-rare-wind::-webkit-scrollbar {
  width: 12px;
  background-color: #F5F5F5;
  border-radius: 10px;
}

.projectCard .scrollbar-rare-wind::-webkit-scrollbar-thumb {
  border-radius: 10px;
  background-image: linear-gradient(to top, #E9D3FE 0%, #cfb9e4 100%);
}
</style>