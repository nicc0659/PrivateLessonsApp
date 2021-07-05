import axios from "axios"
import router from "@/router";

export default {
    doSignIn({commit}, payload) {
        commit("signInStart");
        return axios
            .post('http://localhost:8080/ProgettoTWeb_2021Back_war_exploded/login', null, { params: payload})
            .then(response => {
                localStorage.setItem("accessToken", response.data.token);
                localStorage.setItem("nickname", response.data.username);
                localStorage.setItem("admin", response.data.admin);
                commit("updateAccessData", response.data);
                router.push("/about");
                return null;
            })
            .catch(error => {
                if (error.response.status === 404) {
                    console.log("ERRORE" + JSON.stringify(error));
                    commit("loginStop", error);
                    throw error;
                }
            });
    },

    logout({commit}) {
        return axios
            .post('http://localhost:8080/ProgettoTWeb_2021Back_war_exploded/logout', null, { params: {token: localStorage.getItem("accessToken")}})
            .then(response => {
                console.log(JSON.stringify(response));
                localStorage.removeItem("accessToken");
                localStorage.removeItem("nickname");
                localStorage.removeItem("admin");
                commit("exitlog");
                router.push("/");
            })
            .catch(error => {
                console.log(error);
            });
    }
};