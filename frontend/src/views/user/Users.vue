<template>
    <div>
        <b-row>
            <b-col>
                <h2>
                    Users
                </h2>
            </b-col>
        </b-row>
        <b-row v-if="isLoggedIn" class="my-3">
            <b-col>
                <b-button variant="outline-primary" pill :to="{name:'CreateCategory'}">
                    <b-icon icon="plus-circle"></b-icon>
                    Create new user
                </b-button>
            </b-col>
        </b-row>
        <b-row>
            <b-col>
                <b-table
                    id="usersTable"
                    :busy.sync="isBusy"
                    :items="userProvider"
                    :fields="fields"
                    :current-page="currentPage"
                    :per-page="perPage"
                >
                    <template #cell(name)="data">
                        {{ data.item.firstName + ' ' + data.item.lastName }}
                    </template>
                    <template #cell(type)="data">
                        <b-badge variant="warning" v-if="data.item.userType ==='ADMIN'">Admin</b-badge>
                        <b-badge variant="info" v-else>Content Creator</b-badge>
                    </template>
                    <template #cell(status)="data">
                        <b-badge variant="success" v-if="data.item.active ===true">Active</b-badge>
                        <b-badge variant="danger" v-else>Inactive</b-badge>
                    </template>
                    <template #cell(action)="data">
                        <b-button variant="warning"  pill :to="{name: 'EditCategory', params: {id:data.item.id}}">
                            <b-icon icon="pencil"></b-icon>
                        </b-button>
                        <b-button variant="success"  pill @click.prevent="onToggle(data.item.id,data.item.active)"
                                  v-if="data.item.active === false"
                        >
                            <b-icon icon="arrow-left-right"></b-icon>
                        </b-button>
                        <b-button variant="danger"  pill @click.prevent="onToggle(data.item.id,data.item.active)"
                                  v-if=" data.item.active === true && data.item.userType !=='ADMIN'"
                        >
                            <b-icon icon="arrow-left-right"></b-icon>
                        </b-button>

                    </template>
                </b-table>
                <b-pagination
                    v-model="currentPage"
                    :total-rows="totalRows"
                    :per-page="perPage"
                    aria-controls="usersTable"
                ></b-pagination>
            </b-col>
        </b-row>
    </div>
</template>

<script>
import axios from "@/plugins/axios";
import {mapGetters} from 'vuex'

export default {
    name: 'Users',
    components: {},
    data() {
        return {
            isBusy: false,
            fields: [
                {key: 'id', sortable: false},
                {key: 'email', sortable: false},
                {key: 'name', sortable: false},
                {key: 'type', sortable: false},
                {key: 'status', sortable: false},
                {key: 'action', sortable: false},

            ],
            users: [],
            perPage: 10,
            currentPage: 1,
            totalRows: 0,
        }
    },
    computed: {
        ...mapGetters(['isLoggedIn', 'getToken'])
    },
    methods: {
        async userProvider(ctx) {
            let items = []
            this.isBusy = true;
            try {
                const token = this.$store.getters.getToken;
                const res = await axios.get(`/users?page=${ctx.currentPage}`, {
                    headers: {
                        'Authorization': `Bearer ${token}`
                    }
                });
                const users = res.data.data;
                this.totalRows = res.data.pagination.count;
                this.users = users;
                this.isBusy = false;
                return users;
            } catch (error) {
                this.$bvToast.toast("Could not load users", {
                    title: 'Fetch error',
                    variant: 'danger',
                    solid: true,
                    autoHideDelay: 3000,
                    appendToast: true
                })
                this.isBusy = false;
                return [];
            }
        },
        async onToggle(userId,current) {
            try {
                const token = this.getToken;
                const response = await axios.patch(`/users/${userId}/toggle/active`, {
                    'active': !current
                },{
                    headers: {
                        'Authorization': `Bearer ${token}`
                    }
                });
                if (response.status === 200) {
                    this.$bvToast.toast("Users active status has been changed successfully", {
                        title: 'Delete successful',
                        variant: 'success',
                        solid: true,
                        autoHideDelay: 3000,
                        appendToast: true
                    })
                }
                this.$root.$emit('bv::refresh::table', 'usersTable')
            } catch (error) {
                const data = error.response.data.data;
                let message = "Invalid data";
                if (data[0] !== undefined) {
                    message = data[0];
                }
                if (data.message !== undefined) {
                    message = data.message;
                }
                this.$bvToast.toast(message, {
                    title: 'Fetch error',
                    variant: 'danger',
                    solid: true,
                    autoHideDelay: 3000,
                    appendToast: true
                })
            }
        }
    },


}
</script>
