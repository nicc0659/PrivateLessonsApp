<template>
  <v-container fil-height>
    <v-layout align-self-center justify-center>
      <v-flex>
  <div class="pagina">
    <v-app-bar
        absolute
        color="deep-purple accent-4"
        dense
        dark
    >
      <v-toolbar-title>Amministratore</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-btn
          rounded
          color="primary"
          dark
          @click="backToSelection()"
      >
        Indietro
      </v-btn>
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
    </v-app-bar>

    <!-- TODO: Inserimento doc/corsi/associazioni -->
    <div id="app">
      <v-app id="inspire">
        <v-expansion-panels focusable inset>
          <v-expansion-panel>
            <v-expansion-panel-header>Inserisci docente</v-expansion-panel-header>
            <v-expansion-panel-content>
              <v-form>
                <v-container>
                  <v-row>
                    <v-col
                        cols="12"
                        sm="6"
                        md="3"
                    >
                      <v-text-field
                          label="Nome"
                          v-model="formData.nome"
                      ></v-text-field>
                    </v-col>
                    <v-spacer></v-spacer>
                    <v-col
                        cols="12"
                        sm="6"
                        md="3"
                    >
                    <v-btn
                        rounded
                        color="primary"
                        dark
                        @click="inserisciDocente()"
                    >
                      Invia!
                    </v-btn>
                    </v-col>
                  </v-row>
                </v-container>
              </v-form>
            </v-expansion-panel-content>
          </v-expansion-panel>
          <v-expansion-panel>
            <v-expansion-panel-header>Rimuovi docente</v-expansion-panel-header>
            <v-expansion-panel-content>
              <v-autocomplete
                  :items="prof"
                  item-text="nome"
                  @input="handleProf">
              </v-autocomplete>
              <v-spacer></v-spacer>
              <v-btn
                  rounded
                  color="primary"
                  dark
                  @click="rimuoviDocente()"
              >
                Invia!
              </v-btn>
            </v-expansion-panel-content>
          </v-expansion-panel>
          <v-expansion-panel>
            <v-expansion-panel-header>Inserisci corso</v-expansion-panel-header>
            <v-expansion-panel-content>
              <v-form>
                <v-container>
                  <v-row>
                    <v-col
                        cols="12"
                        sm="6"
                        md="3"
                    >
                      <v-text-field
                          label="Materia"
                          v-model="formData.subj"
                      ></v-text-field>
                    </v-col>
                    <v-spacer></v-spacer>
                    <v-col
                        cols="12"
                        sm="6"
                        md="3"
                    >
                      <v-btn
                          rounded
                          color="primary"
                          dark
                          @click="inserisciMateria()"
                      >
                        Invia!
                      </v-btn>
                    </v-col>
                  </v-row>
                </v-container>
              </v-form>
            </v-expansion-panel-content>
          </v-expansion-panel>
          <v-expansion-panel>
            <v-expansion-panel-header>Rimuovi corso</v-expansion-panel-header>
            <v-expansion-panel-content>
              <v-autocomplete
                  :items="corsi"
                  item-text="materia"
                  @input="handleCorso">
              </v-autocomplete>
              <v-spacer></v-spacer>
              <v-btn
                  rounded
                  color="primary"
                  dark
                  @click="rimuoviMateria()"
              >
                Invia!
              </v-btn>
            </v-expansion-panel-content>
          </v-expansion-panel>
          <v-expansion-panel>
            <v-expansion-panel-header>Inserisci associazione corso-docente</v-expansion-panel-header>
            <v-expansion-panel-content>
              <v-autocomplete
                  :items="prof"
                  item-text="nome"
                  @input="handleAssProf">
              </v-autocomplete>
              <v-autocomplete
                  :items="corsi"
                  item-text="materia"
                  @input="handleAssCorso">
              </v-autocomplete>
              <v-spacer></v-spacer>
              <v-btn
                  rounded
                  color="primary"
                  dark
                  @click="inserisciAssociazione()"
              >
                Invia!
              </v-btn>
            </v-expansion-panel-content>
          </v-expansion-panel>
          <v-expansion-panel>
            <v-expansion-panel-header>Rimuovi associazione corso-docente</v-expansion-panel-header>
            <v-expansion-panel-content>
              <v-autocomplete
                  :items="prof"
                  item-text="nome"
                  @input="handleAssProfElimina">
              </v-autocomplete>
              <v-autocomplete
                  :items="corsi"
                  item-text="materia"
                  @input="handleAssCorsoElimina">
              </v-autocomplete>
              <v-spacer></v-spacer>
              <v-btn
                  rounded
                  color="primary"
                  dark
                  @click="cancellaAssociazione()"
              >
                Invia!
              </v-btn>
            </v-expansion-panel-content>
          </v-expansion-panel>
        </v-expansion-panels>
      </v-app>
    </div>
    <!-- TODO: Tabella che mostra lo storico di TUTTI I CLIENTI -->
    <vue-snotify></vue-snotify>
  </div>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import { mapState, mapActions } from "vuex";
//import axios from "axios";
import router from "@/router/index";
import axios from "axios";

export default {
  name: "Pagina",
  data() {
    return {
      formData: {
        nome: "",
        subj: ""
      },
      corsi: [],
      prof: [],
      prof_elimina: '',
      ass_prof_elimina: '',
      ass_prof_inserisci: '',
      corso_elimina: '',
      ass_corso_elimina: '',
      ass_corso_inserisci: '',
    }
  },
  methods: {
    ...mapState(["professori", "loginIn", "toggle1", "toggle2", "id_cliente"]),
    ...mapActions(["logout", "controlla"]),
    backToSelection() {
      this.$store.state.toggle1 = true;
      this.$store.state.toggle2 = true;
      router.push('/about');
    },
    backToSignIn() {
      this.$store.state.toggle1 = true;
      this.$store.state.toggle2 = true;
      router.push('/');
    },
    inserisciDocente() {
      const {nome} = this.formData;
      console.log(nome);
      axios.put('http://localhost:8080/ProgettoTWeb_2021Back_war_exploded/servletdoc', null, { params: {nomeh:nome, id_bot: 1}})
      this.$snotify.success('Inserito!', {
        timeout: 5000,
        showProgressBar: true,
        closeOnClick: false,
        pauseOnHover: true,
        position: "rightBottom"
      });
    },
    handleProf(value) {
      this.prof_elimina = value;
    },
    handleAssProf(value) {
      this.ass_prof_inserisci = value;
    },
    handleAssProfElimina(value) {
      this.ass_prof_elimina = value;
    },
    handleCorso(value) {
      this.corso_elimina = value;
    },
    handleAssCorso(value) {
      this.ass_corso_inserisci = value;
    },
    handleAssCorsoElimina(value) {
      this.ass_corso_elimina = value;
    },
    rimuoviDocente() {
      axios.put('http://localhost:8080/ProgettoTWeb_2021Back_war_exploded/servletdoc', null, { params: {nomeh: this.prof_elimina, id_bot: 2}})
      this.$snotify.success('Rimosso!', {
        timeout: 5000,
        showProgressBar: true,
        closeOnClick: false,
        pauseOnHover: true,
        position: "rightBottom"
      });
    },
    inserisciMateria() {
      const {subj} = this.formData;
      console.log(subj);
      axios.post('http://localhost:8080/ProgettoTWeb_2021Back_war_exploded/servletcorsi', null, { params: {subj: subj, id_bot: 1}})
        .then(resp => {
          console.log(resp);
          this.$snotify.success('Inserito!', {
            timeout: 5000,
            showProgressBar: true,
            closeOnClick: false,
            pauseOnHover: true,
            position: "rightBottom"
          });
        })
        .catch(err => {
          if(err.response.status === 409) {
            this.$snotify.error("Materia già presente nel database", {
              timeout: 2000,
              showProgressBar: true,
              closeOnClick: false,
              pauseOnHover: true,
              position: "rightTop"
            });
          }
      });
    },
    rimuoviMateria() {
      axios.post('http://localhost:8080/ProgettoTWeb_2021Back_war_exploded/servletcorsi', null, { params: {subj: this.corso_elimina, id_bot: 2}})
          .then(resp => {
            console.log(resp);
            this.$snotify.success('Rimossa!', {
              timeout: 5000,
              showProgressBar: true,
              closeOnClick: false,
              pauseOnHover: true,
              position: "rightBottom"
            });
          })
    },
    inserisciAssociazione() {
      axios.put("http://localhost:8080/ProgettoTWeb_2021Back_war_exploded/servletinsegna", null, {params: {nome: this.ass_prof_inserisci, subj: this.ass_corso_inserisci, id_bot: 1}})
          .then(resp => {
            console.log(resp);
            this.$snotify.success('Inserita!', {
              timeout: 5000,
              showProgressBar: true,
              closeOnClick: false,
              pauseOnHover: true,
              position: "rightBottom"
            });
          })
    },
    cancellaAssociazione() {
      axios.put("http://localhost:8080/ProgettoTWeb_2021Back_war_exploded/servletinsegna", null, {params: {nome: this.ass_prof_elimina, subj: this.ass_corso_elimina, id_bot: 2}})
          .then(resp => {
            console.log(resp);
            this.$snotify.success('Rimossa!', {
              timeout: 5000,
              showProgressBar: true,
              closeOnClick: false,
              pauseOnHover: true,
              position: "rightBottom"
            });
          })
    },
  },
  created() {
    axios.get('http://localhost:8080/ProgettoTWeb_2021Back_war_exploded/servletcorsi').then(resp => {
      this.corsi = resp.data;
      console.log(this.corsi);
    }),
        axios.get('http://localhost:8080/ProgettoTWeb_2021Back_war_exploded/servletdoc').then(resp => {
          this.prof = resp.data;
          console.log(this.prof);
        })
  }
}
</script>

<style scoped>

</style>