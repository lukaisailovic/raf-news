import Vue from 'vue'
import Vuex from 'vuex'
import axios from "../plugins/axios"

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        token: localStorage.getItem('token') || '',
        user: null,
        loading: false,
        admin: false,
    },
    getters: {
        isLoggedIn: state => {
            return state.loading === false && state.user !== null;
        },
        getUser: state => {
            return state.user
        },
        isLoading: state => {
            return state.loading === true;
        },
        isAdmin: state => {
            return state.admin === true;
        },
        getToken: state => {
            return state.token;
        }
    },
    mutations: {
        setUser: (state, user) => {
            state.user = user;
            if (user !== null){
                if (user.userType === 'ADMIN'){
                    state.admin = true;
                    localStorage.setItem('adminAccess','true');
                } else {
                    state.admin = false;
                    localStorage.removeItem('adminAccess');
                }
            } else {
                state.admin = false;
            }
        },
        setLoading: (state, value) => {
            state.loading = value;
        },
    },
    actions: {

        async fetchUser({commit}){
            commit('setLoading',true)
            let token = localStorage.getItem('token');
            if (token == null){
                return;
            }
            try {
                const res =  await axios.get('/users/me',{
                    headers: {
                        'Authorization': `Bearer ${token}`
                    }
                });
                if (res.status === 200){
                    commit('setUser',res.data.data.user)
                } else {
                    commit('setUser',null);
                    localStorage.removeItem('token');
                }
            } catch (error){
                console.log(error)
                commit('setUser',null);
                localStorage.removeItem('token');
            } finally {
                commit('setLoading',false)
            }
        },
        async logOut({commit}){
            commit('setUser',null);
            localStorage.removeItem('token');
            localStorage.removeItem('adminAccess');
        }
    },
    modules: {}
})
