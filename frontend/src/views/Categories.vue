<template>
    <div>
        <b-row>
            <b-col>
                <h2>
                    Categories
                </h2>
            </b-col>
        </b-row>
        <b-row>
            <b-col>
                <b-table
                    id="categoryTable"
                    :busy.sync="isBusy"
                    :items="categoryProvider"
                    :fields="fields"
                    :per-page="10"
                >
                    <template #cell(name)="data">
                        <a href="">{{data.item.name}}</a>
                    </template>
                    <template #cell(action)="data">
                        <a href="" class="btn btn-warning">Edit</a>
                        <a href="" class="btn btn-danger ml-2">Delete</a>
                    </template>
                </b-table>
            </b-col>
        </b-row>
    </div>
</template>

<script>
import axios from "@/plugins/axios";
import PostList from "@/components/PostList";
import { mapGetters } from 'vuex'
export default {
    name: 'Categories',
    components: {
    },
    data(){
        return {
            isBusy: false,
            fields: [
                { key: 'name', sortable: false },
                { key: 'description', sortable: false },

            ],
            categories: []
        }
    },
    getters: {
        ...mapGetters(['isLoggedIn'])
    },
    mounted() {
        if (localStorage.getItem('token') !== null){
            this.fields.push({ key: 'action', sortable: false})
        }
    },
    methods:{
        async categoryProvider(ctx){
            let items = []
            this.isBusy = true;
            try {
                const res = await axios.get(`/categories?page=${ctx.currentPage}`);
                const categories = res.data.data;
                this.categories = categories;
                this.isBusy = false;
                return categories;
            } catch (error) {
                this.$bvToast.toast("Could not load categories", {
                    title: 'Fetch error',
                    variant: 'danger',
                    solid: true,
                    autoHideDelay: 3000,
                    appendToast: true
                })
                this.isBusy = false;
                return [];
            }
        }
    },


}
</script>
