<template>
    <div>
        <b-navbar toggleable="lg" type="dark" variant="dark">
            <b-container>
                <b-navbar-brand to="/">{{ appName }}</b-navbar-brand>

                <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

                <b-collapse id="nav-collapse" is-nav>
                    <b-navbar-nav>
                        <b-nav-item to="/">Home</b-nav-item>
                        <b-nav-item to="/">Popular</b-nav-item>
                        <b-nav-item to="/">Categories</b-nav-item>
                    </b-navbar-nav>

                    <b-navbar-nav class="ml-auto">

                        <b-nav-item-dropdown right v-if="isLoggedIn">
                            <template #button-content>
                                <em>{{getUser.email}}</em>
                            </template>
                            <b-dropdown-item href="#" @click.prevent="logOut">Sign Out</b-dropdown-item>
                        </b-nav-item-dropdown>
                        <b-nav-item :to="{name:'Login'}" v-if="!isLoggedIn">Login</b-nav-item>
                        <b-nav-item :to="{name:'Register'}" v-if="!isLoggedIn">Register</b-nav-item>
                    </b-navbar-nav>


                </b-collapse>
            </b-container>
        </b-navbar>



    </div>
</template>

<script>
import { mapGetters, mapActions } from 'vuex'
export default {
    name: "Navbar",
    async mounted(){
        await this.$store.dispatch('fetchUser');
    },
    computed: {
        appName() {
            return process.env.VUE_APP_NAME;
        },
        ...mapGetters(['isLoggedIn','getUser'])
    },
    methods:{
        ...mapActions(['logOut'])
    }
}
</script>

<style scoped>

</style>