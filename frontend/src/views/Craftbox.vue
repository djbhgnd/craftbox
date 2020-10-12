<template>
    <div class = "craftbox-view">
        <div class = "add-supply-display" v-show="this.$store.state.addSupply === true">
            <h1 class = "header transparent"> Add Supply </h1>
            <div id = "add-supply-display">
                <add-supply-display/>
            </div>
        </div>
        <div class = "supply-display">
            <h1 class = "header transparent"> List of All Supplies </h1>
            <supply-display/>
        </div>
        <div class = "craftbox-display">
            <h1 class = "header transparent"> My Craft Box </h1>
            <craftbox-display/>
        </div>
    </div>
</template>

<script>
import SupplyDisplay from '../components/SupplyDisplay.vue';
import AddSupplyDisplay from '../components/AddSupplyDisplay.vue';
import CraftboxDisplay from '../components/CraftboxDisplay.vue';
export default {
    components: {
        AddSupplyDisplay,
        SupplyDisplay,
        CraftboxDisplay
    }
}

</script>

<style>
@media (max-width:1000px){
    .craftbox-view{
        display:flex;
        flex-wrap:wrap;
        justify-content: center;
        margin-top:30px;
        flex-direction: column;
    }
    #nav{
        margin-top:10px;
    }
    .add-supply-display{
        display:flex;
        justify-content: center;
        flex-direction: column;
    }
    #add-supply-display{
        display:flex;
        align-items: center;
        justify-content: center;
    }
    .navbar{
    height: 70px;
    }
}
</style>