<template>
    <div>
        <div v-show="this.$store.state.addSupply === false && this.$store.state.isCurator === true" class = "spaceFiller">
        </div>
        <div class = "craftBoxDisplay">
            <div class = "craftBoxBorderDiv">
                <div class = "craftBox scrollbar scrollbar-rare-wind">
                    <div class = "craftboxSupplyItem"
                    v-for="currentSupply in $store.state.userSupplies" 
                    v-bind:key="currentSupply.craftboxId" 
                    >
                    <CraftboxSupplyComp v-bind:currentSupply="currentSupply"> </CraftboxSupplyComp>
                    </div>
                </div>
            </div>
            <div class = "findProjectsButtonContainer">
                <router-link v-bind:to="{ name: 'find-projects' }">
                    <button type="submit" class="btn btn-lg btn-primary btn-block"> Find Projects </button>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script>
import SupplyService from '../services/SupplyService';
import CraftboxSupplyComp from '../components/CraftboxSupplyComp';
export default {
    components: {
        CraftboxSupplyComp
    },
    props: {
        supply : Object
    },created() {
        SupplyService.fetchUserSupplies().then(response => this.$store.state.userSupplies = response.data);
    },
    data() {
        return {
            supplyBeingEdited : {
                supplyName: ""
            },
            color : null,
            notes : null,
            isActive: false
        }
    },
    methods: {
        removeSupplyFromCraftbox(currentSupply){
            var result = confirm("Are you sure you want to delete this supply?");
            if (result) {
            SupplyService.deleteSupplyFromUsersSuppliesTable(currentSupply).then(
                this.$store.commit("REMOVE_SUPPLY_FROM_CRAFTBOX",currentSupply)
            );
            }
            
        },
        editSupply(currentSupply){
            this.supplyBeingEdited = currentSupply;
        },
        clearForm(){
            this.supplyBeingEdited = null;
            this.color = null;
            this.notes = null;
        },
        updateSupply(currentSupply){
            currentSupply.color = this.color;
            currentSupply.notes = this.notes;
            SupplyService.updateSupply(currentSupply).then(
                this.$store.commit("UPDATE_SUPPLY_FROM_CRAFTBOX",currentSupply)
            )
            this.color = null;
            this.notes = null;
            this.supplyBeingEdited = null;
        }
    }
}
</script>

<style scoped>
.transparent {
    width: 100%;
    height: 49px;
    margin-top: 0px;
    margin-left: 0px;
}

.supplyBox {
  width: 40%;
  height: 70vh;  
  margin: auto;
  overflow: scroll;
  background-color: rgb(138,43,226, .50);
}

.craftBoxBorderDiv{
  background-color:rgba(138, 43, 226, 0.2);
 /*  245, 97, 195 */
 /* 147, 112, 219 */
 /* rgb(138,43,226) */
  border: white solid;
  border-radius: 20px; 
  margin-right: 15px; 
  margin-left: 15px;
  margin-top: 91px;
}

.supplyBoxBorderDiv{
  background-color:rgb(131, 226, 43, 0.2);
  border: white solid;
  border-radius: 20px;
  margin-right: 15px; 
  margin-left: 15px;
}
.findProjectsButtonContainer{
    margin-top: 40px;
}
</style>

/* .craftBoxBorderDiv{
  background-color:rgba(138, 43, 226, 0.2);
 /*  245, 97, 195 */
 /* 147, 112, 219 */
 /* rgb(138,43,226) */
  border: white solid;
  border-radius: 20px; 
  margin-right: 15px; 
  margin-left: 15px;
  margin-top: 49px;
}

.supplyBoxBorderDiv{
  background-color:rgb(131, 226, 43, 0.2);
  border: white solid;
  border-radius: 20px;
  margin-right: 15px; 
  margin-left: 15px; */

/* .supplyBox {
  width: 40%;
  height: 70vh;  
  margin: auto;
  overflow: scroll;
  background-color: rgb(131,226,43, .50
  ); */
