<template>
  <v-container fil-height>
    <v-layout align-self-center justify-center>
      <v-flex xs12 sm8 md4>
        <v-form>
          <v-card class="elevation-20">
            <v-toolbar dark color="blue">
              <v-toolbar-title>
                Ripetizioni @ School
              </v-toolbar-title>
            </v-toolbar>
            <v-card-text>
              <v-text-field
                  v-model="formData.username"
                  label="Login"
                  type="text"
              >
              </v-text-field>
              <v-text-field
                  v-model="formData.password"
                  label="Password"
                  type="password"
              >
              </v-text-field>
              <v-card-actions>
                <v-btn rounded dark color="indigo" @click="submit">
                  Login
                </v-btn>
                <v-spacer></v-spacer>
                <v-btn rounded dark color="primary" to="/about"> Continue as a guest </v-btn>
              </v-card-actions>
            </v-card-text>
          </v-card>
        </v-form>
      </v-flex>
    </v-layout>
    <vue-snotify></vue-snotify>
  </v-container>
</template>

<script>
import {mapActions} from "vuex";
export default {
  name:"signIn",
  data() {
    return {
      formData: {
        username: "",
        password: ""
      }
    };
  },
  methods: {
    ...mapActions(["doSignIn"]),
    submit() {
      const {username, password} = this.formData;
      let payload = {
        username,
        password
      };
      this.doSignIn(payload)
          .then(() => {
            console.log("THEN");
          })
          .catch(err => {
            console.log(err);
            this.$snotify.error('Username e/o password errati', {
              timeout: 2000,
              showProgressBar: true,
              closeOnClick: false,
              pauseOnHover: true,
              position: "rightTop"
            });
          });
    }
  }
};
</script>