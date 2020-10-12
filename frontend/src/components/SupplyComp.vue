<template>
    <div class = "supplyItemBoxContainer">
        <div class = "supplyItemBox" v-on:click="addSupplyToCraftbox(currentSupply)">
            <span class = "supplyName">{{currentSupply.supplyName}}</span> 
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
            allSupplies: [],
            addSupply : false,
            supplyName : null,
            altName : null,
            supplyId : null,
            isCurator : false,
        }
    },
    methods: {
        addSupplyToCraftbox(availableSupply){
            let craftboxSupply = {
                supplyId: availableSupply.supplyId,
                supplyName: availableSupply.supplyName,
                color: availableSupply.color,
                notes: availableSupply.notes
            }
            SupplyService.insertSupplyIntoUsersSuppliesTable(availableSupply).then(response =>{
                craftboxSupply.craftboxId = response.data;
                this.$store.commit("ADD_SUPPLY_TO_CRAFTBOX",craftboxSupply);
                SupplyService.fetchUserSupplies().then(response => this.$store.state.userSupplies = response.data);
            });

        }
    }
}
</script>

<style>
    .supplyItemBox{
        width:inherit;
        height:100%;
    }
    .supplyItemBoxContainer{
        width:inherit;
        display:flex;
        flex-direction: column;
    }
    .supplyItemBox .supplyName{
        padding-left:2px;
        padding-right:2px;
    }
</style>