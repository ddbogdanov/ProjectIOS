import axios from "axios";

const state = {
    user: null
};
const getters = {
    isAuthenticated: (state) => !!state.user,
};

const mutations = {
    setUser(state, username) {
        state.user = username;
    },
    logout(state, user) {
        state.user = user;
    },
};

export default {
    state,
    getters,
    mutations
}