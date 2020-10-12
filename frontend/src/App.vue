<template>
  <div id="app">
    <nav class="navbar navbar-expand-lg navbar-light transparent" v-show="this.$store.state.clickedLanding">
    <div id="nav">
      <router-link v-bind:to="{ name: 'home' }">Home | </router-link>&nbsp;&nbsp;
      <router-link v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''">Logout | </router-link>&nbsp;
      <router-link v-bind:to="{ name: 'find-projects' }"  v-if="$store.state.token != ''">Find Projects | </router-link>&nbsp;
      <router-link v-bind:to="{ name: 'projects' }">Browse Projects </router-link>&nbsp;
      <router-link v-bind:to="{ name: 'craftbox' }"  v-if="$store.state.token != ''">| My Craftbox</router-link>
    </div>
    </nav>
    <router-view />
  </div>
  
</template>

<style>
@import url('https://fonts.googleapis.com/css2?family=Marcellus&family=Roboto&display=swap');

.add{
  display: none;
  background-color: transparent;
  margin-left: 15px;
}

.addProjectsContainer{
    text-align: center;
    margin: auto;
    max-width: 300px;
    max-height: 600px;
    
}

#addProjectForm{
    margin:auto;
    justify-content: center;
    background-color: rgba(136, 136, 136, 0.95);
    border-radius: 20px;
}
.addSupply{
  display:flex;
  justify-content: center;
  margin-bottom: 5px;
}
.ageRange{
  grid-area: ageRange;
}

#allSupplies{
    grid-area: allSupplies;
    background-color:rgba(53, 211, 250, 0.2);
    height:500px;
    overflow-y: scroll;
    height:70vh;
    border-radius: 20px;
    margin-left: 10px;
    margin-right: 10px;
}


#almostPossibleProjects{
    grid-area: almostPossibleProjects;
    opacity: 0.7;
  }

#browseProjects{
    grid-area: browseProjects;
    opacity: .7;
    font-family: 'Roboto', sans-serif;
  }

body {

  background-image: url("https://images.unsplash.com/photo-1527402858-36f052d83df4?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&w=1000&q=80");
/*   background-color: 255, 255, 255, 0.10;
 */  background-position: center;
  width: 100%;
  height: auto;
  background-repeat: no-repeat;
  background-attachment: fixed;
  background-size: cover;

}

/* body::after {
  content: "";
  background: url("https://images.unsplash.com/photo-1527402858-36f052d83df4?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&w=1000&q=80");
  opacity: 0.10;
  top: 0;
  left: 0;
  bottom: 0;
  right: 0;
  position: absolute;
  z-index: -1;   
} */
.add-supply-btn-group #submit-btn{
  width: 108px;
  border-radius: 0px;
}
.add-supply-btn-group #cancel-btn{
  width: 108px;
  border-radius: 0px;
}

.btn{
  text-align: center;
  width: 300px;
  margin: 0 auto;
}

.cardContainer{
  display: flex;
  justify-content: center;
  align-items: center;
  margin: auto;
}

.cancel{
  width: 100px;
}

#color{
  border-radius: 15px;
}
#craftBox{
  grid-area: craftBox;
  opacity: .7;
  
}
.craftBox{
  display: flex;
  margin: 20px;
  flex-wrap: wrap;
  justify-content: space-evenly;
  flex-direction: row;
  padding: 10px;
  align-items: flex-start;
  align-content: flex-start;
  background-color:rgba(255, 255, 255, 0.5);
  border-radius: 20px;
  overflow-y: auto;
  height:45vh;
}
.craftBoxDisplay{
  margin-top: 77px;
}

.craftbox-display{
  flex-basis: 35%;
  margin-bottom: 20px;
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
  margin-top: 49px;
}

.craftBoxDisplay .scrollbar-rare-wind::-webkit-scrollbar-track {
  -webkit-box-shadow: inset 0 0 6px rgba(0, 0, 0, 0.1);
  box-shadow: inset 0 0 6px rgba(0, 0, 0, 0.1);
  background-color: #F5F5F5;
  border-radius: 10px
}

.craftBoxDisplay .scrollbar-rare-wind::-webkit-scrollbar {
  width: 12px;
  background-color: #F5F5F5;
}

.craftBoxDisplay .scrollbar-rare-wind::-webkit-scrollbar-thumb {
  border-radius: 10px;
  background-image: linear-gradient(to top, #eda8d6 0%, #94217b93 100%);
}
.craftboxSupplyItem{
  padding-top: 2px;
  margin: 10px;
  width: 200px;
  border-radius: 10px;
  background-color: rgb(800, 800, 800, .85);
  font-weight: bold;
  text-align: center;
  overflow: hidden;
}
.craftboxSupplyItem:hover{
  background-color:rgb(255, 255, 255);
  border:black solid;
  border-width: 2px;
}
.craftboxSupplyItem:hover .supplyName{
  color:rgb(245, 97, 195);
}
.craftboxSupplyItem .edit{
  display: inline;
}
.craftboxSupplyItem .info{
  display: inline;
}
.craftboxSupplyItem .delete{
  display: inline;
}
.craftbox-view{
  display: flex;
}

.container{
  display: flex;
  justify-content: space-between;
  flex-wrap: wrap;
  align-content: center;
  row-gap: 20px;
}

.containerElement{
  display: grid;
  text-align: center;
  margin: 0px 100px 0px;
  border-radius: 25px;
  width: 300px;
}
.delete{
  display: none;
}
.detailContainer {
  display: flex;
  column-gap: 20px;
  flex-flow: row wrap;
  justify-content: space-around;
  flex-wrap: wrap;  
}
#detailProjectImg{
  border-radius: 10px;
  width: 200px;
  height: 300px;
  margin-left:auto;
}
.edit{
  display: none;
  margin-right: 30px;
}
.formPopup{
  display:flex;
  justify-content: center;
  background-color:rgba(136, 136, 136, 0.95);
  border-radius: 20px;
  border: white solid;
  margin-bottom: 10px;
}

.form-control{
  text-align: center;
  width: 300px;
  margin: 0 auto;
}
.gridImg{
  align-content: center;
  display: block;
  margin-left: auto;
  margin-right: auto;
  border-radius:10px;
  object-fit: cover;
}

h1 {
  font-family: 'Marcellus', serif;
  text-align: center;
  font-weight: bold;
}

h2 {
  font-family: 'Roboto', sans-serif;
  font-weight: bold;
  text-align: center;
}

h3 {
  font-family: 'Roboto', sans-serif;
  text-align: center;
}
.header {
  text-align: center;
  padding-top: 50px;
}
.info{
  margin-right: 30px;
  display:none;
}
input{
  border-radius: 10px;
  width: 200px;
}
label{
  font-weight: bold;
}

#link-container{
  padding-top: 40px;
  display: flex;
  justify-content: space-evenly;
}
.linkToProject {
  font-family: 'Roboto', sans-serif;
  text-align: center;
}

.logo{
  position: absolute;
  top: -5px;
  right: -5px;
  width: 15%
}
#nav{
  text-align: left;
  padding: 5px;
  position: absolute;
  /* background-color: #e3f2fd; */
  border-radius: 5px;
  margin: 0px 0px 0px 15px;
  opacity: .8;
  font-family: 'Roboto', sans-serif;
}


.notesInput{
  margin: 10px;
  max-width: 160px;
}

p {
  font-family: 'Roboto', sans-serif;
}

#possibleProjects{
    grid-area: possibleProjects;
    font-family: 'Roboto', sans-serif;
    opacity: 0.7;
  }

.project{
    background-color: rgb(43, 131, 226, 0.45);
    border-radius: 10px;
    width: 260px;
    padding: 10px;
    font-family: 'Roboto', sans-serif;
    text-align: center;
/* cornflower blue = 100,149,237 or 6495ed */
    
}


#projectSupplies{
    grid-area: projectSupplies;
    background-color:rgba(53, 211, 250, 0.2);
    overflow-y: scroll;
    height:70vh;
    border-radius: 20px;
}
.projectName{
  text-align: center;
  font-weight: medium;
  font-family: 'Roboto', sans-serif;
  color: white;
}
.spaceFiller{
  height: 49px;
}
.subheader {
  text-align: center;
  margin-top: 0;
  font-size: 18px;
}
.submit{
  width: 100px;
}
.supplies {
  font-family: 'Roboto', sans-serif;
}
.supplyBox{
  display: flex;
  margin: 20px;
  flex-wrap: wrap;
  justify-content: space-evenly;
  align-items: flex-start;
  flex-direction: row;
  padding: 10px;
  flex-basis: auto;
  background-color:rgba(255, 255, 255, 0.5);
  border-radius: 20px;
  overflow-y: auto;
  height:65vh;
}
.supplyBoxBorderDiv{
  display:flex;
  background-color:rgb(131, 226, 43, 0.2);
  border: white solid;
  border-radius: 20px;
  margin-right: 15px; 
  margin-left: 15px;
}
.supplyContainer{
  background-color:rgb(127, 255, 212, .90);
  width: 400px;
  height: 400px;
  border-radius: 10px;
  display:grid;
  flex-wrap:  wrap;
  justify-content: space-around;
  flex-direction: column;
  grid-template-areas: 
    "supplyTitle supplyTitle"
    "supplyList ageRange";
  

}
.supplyDisplay{
  display:flex;
  justify-content: center;
  flex-direction: column;
  /* background-color:rgba(136, 136, 136, 0.10); */
  /*   border-radius: 20px;
  border: white solid; */
  margin-bottom: 10px;
  margin-top: 20px;
}
.supply-display{
  flex-basis: 65%;
  margin-bottom: 20px;
}
.supplyDisplay .scrollbar-rare-wind::-webkit-scrollbar-track {
  -webkit-box-shadow: inset 0 0 6px rgba(0, 0, 0, 0.1);
  box-shadow: inset 0 0 6px rgba(0, 0, 0, 0.1);
  background-color: #F5F5F5;
  border-radius: 10px;
}

.supplyDisplay .scrollbar-rare-wind::-webkit-scrollbar {
  width: 12px;
  background-color: #F5F5F5;
}

.supplyDisplay .scrollbar-rare-wind::-webkit-scrollbar-thumb {
  border-radius: 10px;
  background-image: linear-gradient(to top, #a8edb1 0%, #54cc6ec9 100%);
}
.supplyFormDisplay{
  max-width: 400px;
  margin: auto;
  white-space: nowrap;
  overflow:hidden;
}
.supplyInfo{
  border: black solid;
  padding:5px;
  overflow-x: auto;
  min-height: 50px;
  overflow-y: auto;
  max-height: 250px;
}
.supplyItem{
  display:flex;
  justify-content: center;
  padding: 2px;
  margin: 10px;
  width: 200px;
  height: auto;
  border-radius: 10px;
  background-color: rgb(800, 800, 800, .85);
  font-weight: bold;
  text-align: center;
}
.supplyItem:hover{
  border:black solid;
  border-width: 2px;
}

.supplyItem:hover .supplyName{
  color:rgb(53, 211, 250);
}
.supplyItem .add{
  display: inline;
}
.supplyList{
  grid-area: supplyList;
}
.supplyTitle{
  grid-area: supplyTitle;
}

.text-center{
  padding-top: 50px;
}

.transparent{
  background-color: rgb(800, 800, 800, .5);
}

/* CornflowerBlue 100 149 237
CadetBlue  95 158 160
!Teal or Dark Cyan   0 128 128 or  0 139 139
MediumSeaGreen  60 179 113
Tomato or Salmon 255  99  71 or 250 128 114
Medium Orchid 186  85 211
!Medium Purple 147 112 219 */
</style>
