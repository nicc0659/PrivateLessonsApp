<!-- TODO: Gestire tutti i casi di errore delle richieste AXIOS-->
<!-- TODO: Settare bene la formattazione testo e varie-->
<!-- TODO: Controllare UpdateProfessori che nella console si ripete-->
<!-- TODO: Rivedere il GRID SYSTEM-->

<template>
  <div class="about">
    <v-app-bar
        absolute
        color="deep-purple accent-4"
        dense
        dark
    >
      <v-toolbar-title>Ripetizioni</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-btn
          rounded
          color="primary"
          dark
          @click="backToSignIn()"
          v-if="!($store.state.loginIn)"
      >
        Login
      </v-btn>
      <v-btn
          rounded
          color="primary"
          dark
          @click="logout"
          v-else
      >
        Logout
      </v-btn>
      <v-btn
          rounded
          color="primary"
          dark
          @click="goToAdmin()"
          v-if="$store.state.admin === 1"
      >
        Amministratore
      </v-btn>
    </v-app-bar>
    <br>
    <v-container fil-height>
      <v-layout align-self-center justify-center>
        <v-flex>
          <h1> Tabella delle disponibilità/prenotazioni </h1>
          <br/>
    <div id="app" v-if="$store.state.toggle1 && $store.state.toggle2">
      <v-app id="inspire">
        <v-data-table
            dense
            :headers="choices"
            :items="materie"
            class="elevation-1"
        >
          <template v-slot:item="row">
            <tr>
              <td>{{row.item.materia}}</td>
              <td>
                <v-btn
                    class="ma-2"
                    outlined
                    color="indigo"
                    @click="OnButtonClick(row.item)"
                >
                  Seleziona!
                </v-btn>
              </td>
            </tr>
          </template>
        </v-data-table>
      </v-app>
    </div>
    <div id="app_2" v-if="(!($store.state.toggle1)) && $store.state.toggle2">
      <v-app id="inspire" >
        <v-data-table
            dense
            :headers="headers"
            :items="prof_2"
            class="elevation-1"
        >
          <template v-slot:item="row_2" > <!-- TODO: Qua emerge il nome e cognome -->
            <tr>
              <td>{{row_2.item.id}}</td>
              <td>{{row_2.item.nome}}</td>
              <td>
                <v-btn
                    class="ma-2"
                    outlined
                    color="indigo"
                    @click="ripetizioniMateria(row_2.item)"
                >
                  Seleziona!
                </v-btn>
              </td>
            </tr>
          </template>
          <template v-slot:body.append>
            <v-btn
                class="ma-2"
                outlined
                color="indigo"
                @click="$store.state.toggle1 = true"
            >
              Indietro
            </v-btn>
          </template>
        </v-data-table>
      </v-app>
    </div>
    <div id="app_3" v-if="(!($store.state.toggle1))&&(!($store.state.toggle2))">
      <v-app id="inspire">
        <v-data-table
            :headers="ripetizioni"
            :items="prof_3"
            class="elevation-1"
        >
          <template v-slot:item="row_3">
            <tr>
              <td>{{row_3.item.giorno}}</td>
              <td>{{row_3.item.orarioIn}}</td>
              <td>

                <v-dialog
                    v-model="dialog"
                    persistent
                    max-width="290"
                    :retain-focus="false"
                    v-if="$store.state.loginIn"
                >
                  <template v-slot:activator="{ on, attrs }">
                    <v-btn
                        color="primary"
                        dark
                        v-bind="attrs"
                        v-on="on"
                        @click="prenotare(row_3.item, prof_scelto, materia_scelta);"
                    >
                      Prenota subito!
                    </v-btn>
                  </template>
                  <v-card v-if="$store.state.loginIn ">
                    <v-card-title class="headline">
                      Prenotazione effettuata!
                    </v-card-title>
                    <v-card-text>Complimenti!<br>Hai effettuato la tua prenotazione!</v-card-text>
                    <v-card-actions>
                      <v-spacer></v-spacer>
                      <v-btn
                          color="green darken-1"
                          text
                          @click="dialog = false; servlet_insegna();"
                      >
                        OK
                      </v-btn>
                    </v-card-actions>
                  </v-card>
                </v-dialog>
                <v-dialog
                    v-model="dialog"
                    persistent
                    max-width="290"
                    :retain-focus="false"
                    v-if="!($store.state.loginIn)"
                >
                  <template v-slot:activator="{ on, attrs }">
                    <v-btn
                        color="primary"
                        dark
                        v-bind="attrs"
                        v-on="on"
                    >
                      Prenota subito!
                    </v-btn>
                  </template>
                  <v-card>
                    <v-card-title class="headline">
                      Ci dispiace...
                    </v-card-title>
                    <v-card-text>Sembra che tu non sia ancora dei nostri!<br>Prova a cliccare sul pulsante Login e inseire la tua mail e password.</v-card-text>
                    <v-card-actions>
                      <v-spacer></v-spacer>
                      <v-btn
                          color="green darken-1"
                          text
                          @click="dialog = false"
                      >
                        OK
                      </v-btn>
                    </v-card-actions>
                  </v-card>
                </v-dialog>
              </td>
            </tr>
          </template>
          <template v-slot:body.append>
            <v-btn
                class="ma-2"
                outlined
                color="indigo"
                @click="$store.state.toggle2 = true"
            >
              Indietro
            </v-btn>
          </template>
        </v-data-table>
      </v-app>
    </div>
    <br>
        </v-flex>
      </v-layout>
    </v-container>
    <v-container fil-height>
      <v-layout align-self-center justify-center>
        <v-flex>
    <div id="prenotaz" v-if="$store.state.loginIn">
      <h1> Le mie prenotazioni </h1>
      <br/>
      <v-app id="inspire">
        <v-data-table
            dense
            :headers="prenotazioni"
            :items="prenoto"
            class="elevation-1"
        >
          <template v-slot:item="row"> <!-- TODO: Qua emerge il nome e cognome-->
            <tr>
              <td>{{row.item.nome}}</td>
              <td>{{row.item.mat}}</td>
              <td>{{row.item.giorno}}</td>
              <td>{{row.item.orarioIn}}</td>
              <td>{{row.item.stato}}</td>
              <td v-if="row.item.stato === 'attiva'">
                <v-btn
                  class="ma-2"
                  outlined
                  color="red"
                  @click="disdetta(row.item); riaggiornamento(row.item);"
              >
                Voglio disdire
              </v-btn>
                <v-btn
                    class="ma-2"
                    outlined
                    color="green"
                    @click="conferma(row.item); riaggiornamento(row.item);"
                >
                  Ripetizione effettuata
                </v-btn></td>
              <td v-else-if="row.item.stato === 'disdetta'">
                <v-icon>
                  mdi-close-thick
                  x-large
                </v-icon>
              </td>
              <td v-else-if="row.item.stato === 'effettuata'">
                <v-icon>
                  mdi-check-bold
                  x-large
                </v-icon>
              </td>
            </tr>
          </template>
        </v-data-table>
      </v-app>
    </div>
        </v-flex>
      </v-layout>
    </v-container>
    <v-container fil-height>
      <v-layout align-self-center justify-center>
        <v-flex>
    <div id="prenotaz_admin" v-if="$store.state.loginIn && ($store.state.admin === 1)">
      <h1> Prenotazioni di ogni utente </h1>
      <br>
      <v-app id="inspire">
        <v-data-table
            dense
            :headers="prenotazioni_admin"
            :items="prenoto_admin"
            class="elevation-1"
        >
          <template v-slot:item="row">
            <tr>
              <td>{{row.item.user_name}}</td>
              <td>{{row.item.nome}}</td>
              <td>{{row.item.mat}}</td>
              <td>{{row.item.giorno}}</td>
              <td>{{row.item.orarioIn}}</td>
              <td>{{row.item.stato}}</td>
              <td v-if="row.item.stato === 'attiva'">
                <v-icon>
                  mdi-clock-time-four
                  x-large
                </v-icon>
              </td>
              <td v-else-if="row.item.stato === 'disdetta'">
                <v-icon>
                  mdi-close-thick
                  x-large
                </v-icon>
              </td>
              <td v-else-if="row.item.stato === 'effettuata'">
                <v-icon>
                  mdi-check-bold
                  x-large
                </v-icon>
              </td>
            </tr>
          </template>
        </v-data-table>
      </v-app>
    </div>
        </v-flex>
      </v-layout>
    </v-container>
    <vue-snotify></vue-snotify>
  </div>
</template>
<!-- TODO: In "value" devi scrivere esattamente il nome presente all'interno del model -->
<script>
import { mapState, mapActions } from "vuex";
import axios from "axios";
import router from "@/router/index";
export default {
  data() {
    return {
      expanded: [],
      materie: [],
      dialog: false,
      search: '',
      materia_scelta: '',
      prof_scelto: '',
      choices: [
        { text: 'Materia', align: 'center', value: 'materia' },
        { text: 'Scegli il/la prof', value: 'choice', align: 'center' },
      ],
      headers: [
        {
          text: 'ID',
          align: 'center',
          value: 'id',
        },
        { text: 'Nome',align: 'center', value: 'nome' }, //TODO: Qua emerge il nome e cognome
        { text: ' ', align: 'center', value: 'blank' },
      ],
      professory:[],
      prof_2:[],
      ripetizioni: [
        { text: 'Giorno', align: 'center', value: 'giorno' },
        { text: 'Orario inizio', align: 'center', value: 'orarioIn' },
        { text: ' ', align: 'center', value: 'blank' },
      ],
      prof_3:[],
      prenotazioni: [
        { text: 'Nome prof', align: 'center', value: 'nome' }, //TODO: Qua emerge il nome e cognome
        { text: 'Materia', align: 'center', value: 'mat' },
        { text: 'Giorno', align: 'center', value: 'giorno' },
        { text: 'Orario inizio', align: 'center', value: 'orarioIn' },
        { text: 'Stato', align: 'center', value: 'stato' },
        { text: ' ', align: 'center', value: 'blank' },
      ],
      prenoto:[],
      prenotazioni_admin: [
        { text: 'Utente', align: 'center', value: 'user_name' },
        { text: 'Nome prof', align: 'center', value: 'nome' },
        { text: 'Materia', align: 'center', value: 'mat' },
        { text: 'Giorno', align: 'center', value: 'giorno' },
        { text: 'Orario inizio', align: 'center', value: 'orarioIn' },
        { text: 'Stato', align: 'center', value: 'stato' },
        { text: ' ', align: 'center', value: 'blank' },
      ],
      prenoto_admin:[],
    }
  },
  methods: {
    ...mapState(["professori", "loginIn", "toggle1", "toggle2", "id_cliente"]),
    ...mapActions(["logout", "controlla"]),
    backToSignIn() {
      this.$store.state.toggle1 = true;
      this.$store.state.toggle2 = true;
      router.push('/');
    },
    OnButtonClick(item){
      //console.log(item.materia);
      this.materia_scelta = item.materia;
      return axios
          .post('http://localhost:8080/ProgettoTWeb_2021Back_war_exploded/servletdoc', null, {params: {token: localStorage.getItem("accessToken"), ...item}})
          .then(respo => {
            this.$store.state.toggle1 = false;
            this.prof_2 = respo.data;
            console.log(this.prof_2);
          })
          .catch(error => {
            if (error.response.status === 401) {
              localStorage.removeItem("accessToken");
              localStorage.removeItem("nickname");
              localStorage.removeItem("admin");
              this.$store.state.accessToken = null;
              this.$store.state.nickname = null;
              this.$store.state.admin = null;
              this.$store.state.loginIn = false;
              setTimeout( () => this.$router.push({ path: '/'}), 6000);
              setTimeout( () => this.$store.state.toggle1 = true, 6000);
              setTimeout( () => this.$store.state.toggle2 = true, 6000);
              this.$snotify.error('Tempo scaduto!', {
                timeout: 5000,
                showProgressBar: true,
                closeOnClick: false,
                pauseOnHover: true,
                position: "rightBottom"
              });
            }
            console.log(error);
          });
    },
    ripetizioniMateria(item){
      console.log(item.id);
      this.prof_scelto = item.id;
      return axios
        .post('http://localhost:8080/ProgettoTWeb_2021Back_war_exploded/servletinsegna', null, {params: {token: localStorage.getItem("accessToken"), id:this.prof_scelto}})
        .then(resp => {
          this.$store.state.toggle2 = false;
          this.prof_3 = resp.data;
          console.log(this.prof_3);
        })
        .catch(error => {
            if (error.response.status === 401) {
              localStorage.removeItem("accessToken");
              localStorage.removeItem("nickname");
              localStorage.removeItem("admin");
              this.$store.state.accessToken = null;
              this.$store.state.nickname = null;
              this.$store.state.admin = null;
              this.$store.state.loginIn = false;
              setTimeout( () => this.$router.push({ path: '/'}), 6000);
              setTimeout( () => this.$store.state.toggle1 = true, 6000);
              setTimeout( () => this.$store.state.toggle2 = true, 6000);
              this.$snotify.error('Tempo scaduto!', {
                timeout: 5000,
                showProgressBar: true,
                closeOnClick: false,
                pauseOnHover: true,
                position: "rightBottom"
              });
            }
            console.log(error);
          });
    },
    prenotare(item, prof_scelto, materia_scelta) {
      return axios
          .post('http://localhost:8080/ProgettoTWeb_2021Back_war_exploded/servletprenotazioni', null, {params: {materia: materia_scelta, prof:prof_scelto, token: localStorage.getItem("accessToken"), ...item}})
          .then(resp => {
            this.prenoto = resp.data;
            console.log(this.prenoto);
          })
          .catch(error => {
            if (error.response.status === 401) {
              localStorage.removeItem("accessToken");
              localStorage.removeItem("nickname");
              localStorage.removeItem("admin");
              this.$store.state.accessToken = null;
              this.$store.state.nickname = null;
              this.$store.state.admin = null;
              this.$store.state.loginIn = false;
              //this.$store.state.sloggato = true;
              setTimeout( () => this.$router.push({ path: '/'}), 6000);
              setTimeout( () => this.$store.state.toggle1 = true, 6000);
              setTimeout( () => this.$store.state.toggle2 = true, 6000);
              //setTimeout( () => this.$store.state.sloggato = false, 6000);
              this.$snotify.error('Tempo scaduto!', {
                timeout: 5000,
                showProgressBar: true,
                closeOnClick: false,
                pauseOnHover: true,
                position: "rightBottom"
              });
            }
            console.log(error);
          });
    },
    servlet_insegna() {
      console.log(this.prof_scelto)
      axios.post('http://localhost:8080/ProgettoTWeb_2021Back_war_exploded/servletinsegna', null, {params: {token: localStorage.getItem("accessToken"), id: this.prof_scelto}})
          .then(resp => {
            this.prof_3 = resp.data;
            console.log(this.prof_3);
          })
      axios.post('http://localhost:8080/ProgettoTWeb_2021Back_war_exploded/servletprenotazioni', null, {params: {token: localStorage.getItem("accessToken"), admin: 1}})
          .then(resp => {
            this.prenoto_admin = resp.data;
            console.log(this.prenoto_admin);
          })
      },
    disdetta(item) {
      axios.get('http://localhost:8080/ProgettoTWeb_2021Back_war_exploded/servletprenotazioni', {params: {token: localStorage.getItem("accessToken"), id_button: 1,...item}})
              .then(resp => {
              this.prenoto = resp.data;
              this.prenoto_admin = resp.data;
              console.log(this.prenoto);
          })
    },
    conferma(item) {
      axios.get('http://localhost:8080/ProgettoTWeb_2021Back_war_exploded/servletprenotazioni', {params: {token: localStorage.getItem("accessToken"), id_button: 2,...item}})
          .then(resp => {
            this.prenoto = resp.data;
            this.prenoto_admin = resp.data;
            console.log(this.prenoto);
          })
    },
    riaggiornamento (item) {
      console.log("Ciaone: " + item.id_prof);
      axios.post('http://localhost:8080/ProgettoTWeb_2021Back_war_exploded/servletinsegna', null, {params: {token: localStorage.getItem("accessToken"), id: item.id_prof}})
          .then(resp => {
            this.prof_3 = resp.data;
            console.log(this.prof_3);
          })
    },
    /*nuovo_aggior() {
      console.log("Ciao");
      axios.post('http://localhost:8080/ProgettoTWeb_2021Back_war_exploded/servletprenotazioni', null, {params: {token: localStorage.getItem("accessToken"), admin: 1}})
          .then(resp => {
            console.log("helo");
            this.prenoto_admin = resp.data;
            console.log(this.prenoto_admin);
          })
    },*/
    goToAdmin() {
      router.push("/admin")
    }
  },
  created(){
    axios.get('http://localhost:8080/ProgettoTWeb_2021Back_war_exploded/servletcorsi').then(resp => {
      this.materie = resp.data;
      console.log(this.materie);
    }),
    axios.post('http://localhost:8080/ProgettoTWeb_2021Back_war_exploded/servletprenotazioni', null, {params: {token: localStorage.getItem("accessToken")}})
          .then(resp => {
            this.prenoto = resp.data;
            console.log(this.prenoto);
          }),
    axios.post('http://localhost:8080/ProgettoTWeb_2021Back_war_exploded/servletprenotazioni', null, {params: {token: localStorage.getItem("accessToken"), admin: 1}})
         .then(resp => {
            this.prenoto_admin = resp.data;
            console.log(this.prenoto_admin);
         })
  }
};
</script>