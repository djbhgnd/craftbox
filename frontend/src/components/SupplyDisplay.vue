<template>   
    <div class = "supplyDisplay">
        <div>
            <div class = "spaceFiller"></div>
        </div>
        <div class = "supplyBoxBorderDivBorderDiv">
            <button class = "addSupply btn btn-secondary" v-on:click="addSuppliesToDatabase()" v-show="isCurator === true && this.$store.state.addSupply === false">Add Supply</button>
            <div class="search-wrapper">
                <label>Search </label>
                <input type="text" v-model="search" placeholder="Supply Name"/>
            </div>
            <div class = "supplyBoxBorderDiv flex">
                <div class = "supplyBox scrollbar scrollbar-rare-wind" id = "supplyBox">
                    <div class="supplyItem" 
                    v-for="currentSupply in this.filteredList" 
                    v-bind:key="currentSupply.supplyId"
                    >
                        <SupplyComp v-bind:currentSupply="currentSupply"> </SupplyComp> 
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import SupplyService from '../services/SupplyService';
import SupplyComp from '../components/SupplyComp';
export default {
    props: {
        supply : Object
    },
    components: {
        SupplyComp
    },
    data() {
        return {
            supplyId : null,
            isCurator : false,
            search: null
        }
    },
    created() {
        SupplyService.fetchSupplies().then(response => this.$store.state.allSupplies = response.data);
        if(this.$store.state.user.authorities[0].name === "ROLE_ADMIN"){
            this.isCurator = true;
        }
    },
    computed: {
    filteredList() {
    if(this.search){
        return this.$store.state.allSupplies.filter(supply => {
        return supply.supplyName.toLowerCase().includes(this.search.toLowerCase())
      })
    }
    else {
        return this.$store.state.allSupplies;
    }
      
    }
  },
    methods: {
        addSuppliesToDatabase(){
            this.$store.state.addSupply = true;
        }
    }
}
</script>

<style>
    .supplyDisplay{
        display:flex;
        flex-direction: row;
    }
    #addSupplyForm{
        background-color:rgba(136, 136, 136, 0.95);
        margin-left: 20px;
        margin-right: 10px;
    }
    #addSupplyText{
        text-align: center;
        margin-top: 10px;
    }
    .supplyDisplay .addSupply{
        margin-bottom: 20px;
    }
    .innerAddSupplyForm{
        background-color:rgba(255, 255, 255, 0.5);
        margin: 20px;
        border-radius: 20px;
    }
    .add-supply-form-container{
        border-bottom-left-radius: 20px;
        border-bottom-right-radius: 20px;
        overflow:hidden;
    }
    .search-wrapper{
        display: flex;
        flex-direction: column;
        align-items: center;
        margin-bottom: 10px;
    }
    .supplyBoxBorderDivBorderDiv{
        width: 100%;
    }
    .supplyBoxBorderDiv .supplyBox{
        width: 100%;
    }
</style>