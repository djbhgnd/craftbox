import axios from 'axios';

export default {
    getProjects(){
        return axios.get('/projects')
    },
    getProjectByID(id){
        return axios.get(`/projects/${id}`)
    },
    getProjectsWithAllSupplies(){
        return axios.get('/projects?percentOwned=100')
    },
    getProjectsWithMostSupplies(){
        return axios.get('/projects?percentOwned=80')
    },
    addNewProject(project){
        return axios.post('/projects/add',project)
    },
    editProject(project,id){
        return axios.put(`/projects/${id}/update`,project)
    },
    getMissingSuppliesByProjectId(id){
        return axios.get(`/projects/${id}/missingSupplies`)
    },
    deleteProjectFromDatabase(id){
        return axios.delete(`/projects/${id}`)
    }
    
}