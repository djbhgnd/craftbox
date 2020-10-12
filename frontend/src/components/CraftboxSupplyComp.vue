<template>
    <div class = "craftboxSupplyBox" v-on:click="toggleSupplyButtons()">
        <span class = "supplyName" v-show="beingEdited === false">{{currentSupply.supplyName}}</span>
        <span  id="craftboxSupplyBoxSupplyColor" v-show="currentSupply.color !== null && beingEdited === false"><br>({{currentSupply.color}})</span>
        <div id="craftboxSupplyBoxSupplyNotes" class = "supplyNotes" v-show="currentSupply.notes !== null && beingEdited === false">{{currentSupply.notes}}</div>
        <div class = "formPopup" id = "editForm" v-show="beingEdited === true">
            <form class="edit-supply-form-container">
                <h4>Edit Supply</h4>
                    Color
                    <select name="color" id="color" v-model="color">
                        <option value="aquamarine">aquamarine</option>

                        <option value="black">black</option>
                        <option value="blue">blue</option>
                        <option value="brown">brown</option>

                        <option value="cyan">cyan</option>

                        <option value="gold">gold</option>
                        <option value="gray">gray</option>
                        <option value="green">green</option>
                        
                        <option value="indigo">indigo</option>

                        <option value="lime">lime</option>

                        <option value="magenta">magenta</option>
                        <option value="maroon">maroon</option>
                        
                        <option value="navy">navy</option>

                        <option value="olive">olive</option>
                        <option value="orange">orange</option>

                        <option value="pink">pink</option>
                        <option value="purple">purple</option>

                        <option value="red">red</option>

                        <option value="silver">silver</option>

                        <option value="tan">tan</option>
                        <option value="teal">teal</option>
                        <option value="transparent">transparent</option>
                        <option value="turquoise">turquoise</option>

                        <option value="white">white</option>
                        
                        <option value="yellow">yellow</option>
                        
                        <option value="violet">violet</option>
                    </select>        
                <br><label for="notes" >Notes:</label><br>
                <input class = "notesInput" v-model="notes" type="text" id="notes" name="notes"><br>
                <div class="btn-group add-supply-btn-group" role = "group">
                    <input id="submit-btn" class="submit btn btn-success" type="button" value="Submit" v-on:click="updateSupply(currentSupply)"/>
                    <input id="cancel-btn" class="cancel btn btn-danger" type="button" value="Cancel" v-on:click="clearForm()"/>
                </div>
            </form>
        </div>
        <div id="editAndInfoAndDeleteButtons" v-show="beingEdited === false && areSupplyButtonsVisible === true">
            <br><img src="../assets/editIcon.png" alt = "pencil" width="25" height="25"
            id="edit-btn" class="edit" type="button" value="Edit" v-on:click="editSupply(currentSupply)"/>
            <img src="../assets/trashIcon.png" alt = "trashcan" width="25" height="25"
            id="delete-btn" class="delete" type="button" value="Delete" v-on:click="removeSupplyFromCraftbox(currentSupply)"/>
        </div>
    </div>
</template>

<script>
import SupplyService from '../services/SupplyService';
export default {
    props: {
        currentSupply: Object
    },
    data() {
        return {
            beingEdited : false,
            isClicked: false,
            color : null,
            notes : null,
            isActive: false,
            areSupplyButtonsVisible: false
        }
    },
    methods: {
        removeSupplyFromCraftbox(currentSupply){
            let result = confirm("Are you sure you want to delete this supply?");
            if (result) {
            SupplyService.deleteSupplyFromUsersSuppliesTable(currentSupply).then(
                this.$store.commit("REMOVE_SUPPLY_FROM_CRAFTBOX",currentSupply)
                );
            }       
        },
        viewSupplyInfo(){
            this.viewingInfo = true;
        },
        toggleSupplyButtons(){
            if(this.areSupplyButtonsVisible === false && this.beingEdited === false){
                this.areSupplyButtonsVisible = true;
            }else if(this.areSupplyButtonsVisible === true && this.beingEdited === false){
                this.areSupplyButtonsVisible = false;
            }
        },
        editSupply(currentSupply){
            this.beingEdited = true;
            this.color = currentSupply.color;
            this.notes = currentSupply.notes;
        },
        clearForm(){
            this.beingEdited = false;
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
            this.beingEdited = false;
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
    .edit-supply-form-container{
        overflow: hidden;
    }
    .edit-supply-form-container .btn-group{
        display:flex;
        justify-content: center;
    }
    .craftboxSupplyBox .formPopup{
        background: none;
        border: none;
    }
    .craftboxSupplyBox .supplyName{
        padding-left:2px;
        padding-right:2px;
    }
    
    .craftboxSupplyBox .supplyNotes{
        border:black solid;
        border-width: 1px;
        margin: 3px;
        border-radius: 5px;
        padding-left: 5px;
        padding-right: 5px;
    }
    #craftboxSupplyBoxSupplyColor{
        font-size: small;
    }
    #craftboxSupplyBoxSupplyNotes{
        font-style: italic;
        font-size: smaller;
    }
</style>