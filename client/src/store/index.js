//import Vuex from "vuex"
import { createStore } from 'vuex'
//import createPersistedState from "vuex-persistedstate"
//import auth from "./modules/auth"

// Create store
const store = createStore({
    state: {
        auth: []
    }
})

export default store