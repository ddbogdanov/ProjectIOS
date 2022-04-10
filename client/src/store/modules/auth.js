import axios from "axios";

const state = {
    user: null
};
const getters = {
    isAuthenticated: (state) => !!state.user,
    StateUser: (state) => state.user,
};

const actions = {
    async LogIn({commit}, user) {
        let apiUrl = '/user/login'

        await axios.post(apiUrl, user);
        await commit("setUser", user.get("username"));
    },
}

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
    actions,
    mutations
}