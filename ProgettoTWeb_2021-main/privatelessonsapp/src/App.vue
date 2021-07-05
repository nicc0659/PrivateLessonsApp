<template>
  <v-app>
    <div id="app">
      <div id="nav" align-center justify-center>
      </div>
    </div>
    <router-view/> <!--: QUESTO MOSTRA LE COMPONENTI EFFETTIVE-->
  </v-app>
</template>

<!-- TODO: TUTTO QUELLO CHE E' PRESENTE SU App.vue, TE LO PORTI DIETRO IN TUTTE LE PAGINE!-->

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  background-image: url("https://lh3.googleusercontent.com/proxy/HLPzhOUADD7yT4o4rwOCV8AFBkn5hIQqdrfFBfVBiu2FTfW02--toCZHvYBq1hWNwIy0ZlNtaSzksChu5aa6Vc_HsHeMpryDXgeaEX7r6B2ARC1-VjiunNzM3JX5F9oRU3Dc");
  background-repeat: no-repeat;
  background-size: cover;
}

#nav {
  padding: 30px;
}
#nav a {
  font-weight: bold;
  color: #2c3e50;
}
</style>