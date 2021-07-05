export default {
    signInStart: state => (state.loginIn = true),
    loginStop: (state, errorMessage) => {
        state.loginIn = false;
        state.loginError = errorMessage;
    },
    updateAccessData: (state, data) => {
        console.log(data);
        state.accessToken = data.accessToken;
        state.nickname = data.username;
        state.admin = data.admin;
    },
    exitlog: state => {
        state.accessToken = null;
        state.nickname = null;
        state.admin = null;
        state.loginIn = false;
        state.toggle1 = true;
        state.toggle2 = true;
    }
};