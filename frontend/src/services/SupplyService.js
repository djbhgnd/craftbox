import axios from 'axios';

export default {
    fetchSupplies(){
        return axios.get('/supply/allSupplies')
    },
    fetchUserSupplies(){
        return axios.get('/supply/userSupplies')
    },
    insertSupplyIntoUsersSuppliesTable(supply){
        return axios.post('/supply/supplyToUsersSupplies',supply)
    },
    deleteSupplyFromUsersSuppliesTable(supply){
        return axios.post('/supply/supplyDelete',supply)
    },
    updateSupply(supply){
        return axios.post('/supply/supplyToUsersSupplies/Update',supply)
    },
    addSupplyToSupplyTable(supply){
        return axios.post('/supply/addSupplyToSupplyBank',supply)
    },
    addAltNameToAltTable(supply){
        return axios.put('/supply/addSupplyToSupplyBank/AltName',supply)
    },
    addSupplyToProject(supply, projectId){
        return axios.post(`/supply/addSupplyToProject/${projectId}`,supply)
    },
    editProjectSupplies(listOfSupplies,id){
        return axios.put(`/supply/projectSupply/${id}`,listOfSupplies,id)
    }
    
}