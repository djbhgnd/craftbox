<template>
    <div class = "formPopup supplyFormDisplay" id = "addSupplyForm" v-show="this.$store.state.addSupply === true && this.$store.state.isCurator === true">
        <div class = "innerAddSupplyForm">
            <form class="add-supply-form-container">
                <h4 id="addSupplyText">Add Supply</h4>
                <br><label for="supplyName" >Name:</label><br>
                <input v-model="supplyName" type="text" id="name" name="name"><br>
                <br><label for="altName" >Alternate Name (Optional):</label><br>
                <input v-model="altName" type="text" id="altName" name="altName"><br>
                <br>
                <div class = "add-supply-btn-group" role="group">
                    <input id="submit-btn" class="submit btn btn-success" type="button" value="Submit" v-on:click="addSupplyToSupplyTable()"/>
                    <input id="cancel-btn" class="cancel btn btn-danger" type="button" value="Cancel" v-on:click="clearSupplyForm()"/>
                </div>
            </form>
        </div>
    </div>
</template>

<script>
import SupplyService from '../services/SupplyService';
export default {
    props: {
        supply : Object
    },
    data() {
        return{
            supplyName : null,
            altName : null
        }
    },
    created() {
        if(this.$store.state.user.authorities[0].name === "ROLE_ADMIN"){
            this.$store.state.isCurator = true;
        }
    },
    methods: {
        addSupplyToSupplyTable(){
            this.$store.state.addSupply = false;
            let newSupply = {};
            newSupply.supplyName = this.supplyName;
            SupplyService.addSupplyToSupplyTable(newSupply).then(response =>{
                newSupply = response.data;
                if(this.altName !== null){
                    newSupply.altName = this.altName;
                    SupplyService.addAltNameToAltTable(newSupply);
                }
                SupplyService.fetchSupplies().then(response => this.$store.state.allSupplies = response.data);
            })
            this.clearSupplyForm();
        },
        clearSupplyForm(){
            this.$store.state.addSupply = false;
            this.supplyName = null;
            this.altName = null;
        }
    }

}
</script>

<style>
#addSupplyForm{
    margin-top:91px;
}
</style>