<template>
    <div class = "EditProjectSupplyBox" v-on:click="toggleSupplyButtons()">
        <span v-show="beingEdited === false">{{currentSupply.supplyName}}</span>
        <br>
        <span v-show="currentSupply.color !== null && beingEdited === false && currentSupply.color !== ''">({{currentSupply.color}})</span>
        <div class = "formPopup" id = "editForm" v-show="beingEdited === true">
            <form class="edit-supply-form-container">
                <h4>Edit Supply</h4>
                    Color
                    <select name="color" id="color" v-model="color">
                        <option value="green">green</option>
                        <option value="blue">blue</option>
                        <option value="red">red</option>
                        <option value="yellow">yellow</option>
                    </select>        
                <br><label for="notes" >Notes:</label><br>
                <input class = "notesInput" v-model="notes" type="text" id="notes" name="notes"><br>
                <div class="btn-group" role = "group">
                    <input id="submit-btn" class="submit btn btn-success" type="button" value="Submit" v-on:click="updateSupply(currentSupply)"/>
                    <input id="cancel-btn" class="cancel btn btn-danger" type="button" value="Cancel" v-on:click="clearForm()"/>
                </div>
            </form>
        </div>
        <div class = "supplyInfo" v-show="viewingInfo === true" v-on:click="stopViewingInfo()">
            {{currentSupply.notes}}
        </div>
        <div id="editAndInfoAndDeleteButtons" v-show="beingEdited === false && viewingInfo === false && areSupplyButtonsVisible === true">
            <br><img src="../assets/editIcon.png" alt = "pencil" width="25" height="25"
            id="edit-btn" class="edit" type="button" value="Edit" v-on:click="editSupply(currentSupply)"/>
            <img src="../assets/infoIcon.png" alt = "infoIcon" width="25" height="25"
            id="info-btn" class="info" type="button" value="Info" v-on:click="viewSupplyInfo()"/> 
            <img src="../assets/trashIcon.png" alt = "trashcan" width="25" height="25"
            id="delete-btn" class="delete" type="button" value="Delete" v-on:click="removeSupplyFromNewSupplyList(currentSupply)"/>
        </div>
    </div>
</template>

<script>
export default {
props: {
    currentSupply: Object,
    newSuppliesList: Array
  },
    data() {
        return {
            beingEdited : false,
            viewingInfo : false,
            isClicked: false,
            color : null,
            notes : null,
            isActive: false,
            areSupplyButtonsVisible: false
        }
    },
    methods: {
        // removeSupplyFromCraftbox(currentSupply){
        //     let result = confirm("Are you sure you want to delete this supply?");
        //     if (result) {
        //     SupplyService.deleteSupplyFromUsersSuppliesTable(currentSupply).then(
        //         this.$store.commit("REMOVE_SUPPLY_FROM_CRAFTBOX",currentSupply)
        //     );
        //     }
            
        // },
        removeSupplyFromNewSupplyList(supplyToRemove){
            this.$emit('delete', supplyToRemove);
    //        let newArray = [];

    //   for (let i = 0; i < this.newSuppliesList.length; i++) {
    //     if (this.newSuppliesList[i] != supplyToRemove) {
    //       newArray.push(this.newSuppliesList[i]);
    //     }
    //   }
    //   this.newSuppliesList = newArray; 
        },
        viewSupplyInfo(){
            this.viewingInfo = true;
        },
        stopViewingInfo(){
            this.viewingInfo = false
            this.toggleSupplyButtons();
        },
        toggleSupplyButtons(){
            if(this.areSupplyButtonsVisible === false && this.beingEdited === false && this.viewingInfo === false){
                this.areSupplyButtonsVisible = true;
            }else if(this.areSupplyButtonsVisible === true && this.beingEdited === false && this.viewingInfo === false){
                this.areSupplyButtonsVisible = false;
            }
        },
        editSupply(supplyToUpdate){
            
            this.beingEdited = true;
           supplyToUpdate.color = this.color;
           supplyToUpdate.notes = this.notes;
            // this.$emit('edit', supplyToUpdate);
        },
        clearForm(){
            this.beingEdited = false;
            this.color = null;
            this.notes = null;
            this.toggleSupplyButtons();
        },
        updateSupply(currentSupply){
            currentSupply.color = this.color;
            currentSupply.notes = this.notes;

            //edit the parents array of supplies
            
            this.color = null;
            this.notes = null;
            this.beingEdited = false;
            this.toggleSupplyButtons();
        }
    }
}
</script>

<style>
    .edit-supply-form-container .btn-group .cancel{
        margin-top: 15px;
        width: 87px;
    }
    .edit-supply-form-container .btn-group .submit{
        margin-top: 15px;
        width: 87px;
    }
    .EditProjectSupplyBox .formPopup{
        background: none;
        border: none;
    }
    .edit{
        display: inline;

    }
    .info{
        display: inline;
        
    }
    .delete{
        display: inline;
        
    }
</style>