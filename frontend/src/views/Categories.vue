<template>
    <div>
        <b-row>
            <b-col>
                <h2>
                    Categories
                </h2>
            </b-col>
        </b-row>
        <b-row v-if="isLoggedIn" class="my-3">
            <b-col>
                <b-button variant="outline-primary" pill :to="{name:'CreateCategory'}">Create new category</b-button>
            </b-col>
        </b-row>
        <b-row>
            <b-col>
                <b-table
                    id="categoryTable"
                    :busy.sync="isBusy"
                    :items="categoryProvider"
                    :fields="fields"
                    :current-page="currentPage"
                    :per-page="perPage"
                >
                    <template #cell(name)="data">
                        <a href="">{{ data.item.name }}</a>
                    </template>
                    <template #cell(action)="data">
                        <b-button variant="warning" pill>Edit</b-button>
                        <b-button variant="danger" class="ml-2" pill @click.prevent="onDelete(data.item.id)">Delete</b-button>

                    </template>
                </b-table>
                <b-pagination
                    v-model="currentPage"
                    :total-rows="totalRows"
                    :per-page="perPage"
                    aria-controls="my-table"
                ></b-pagination>
            </b-col>
        </b-row>
    </div>
</template>

<script>
import axios from "@/plugins/axios";
import {mapGetters} from 'vuex'

export default {
    name: 'Categories',
    components: {},
    data() {
        return {
            isBusy: false,
            fields: [
                {key: 'name', sortable: false},
                {key: 'description', sortable: false},

            ],
            categories: [],
            perPage: 10,
            currentPage: 1,
            totalRows: 0,
        }
    },
    computed: {
        ...mapGetters(['isLoggedIn','getToken'])
    },
    mounted() {
        if (localStorage.getItem('token') !== null) {
            this.fields.push({key: 'action', sortable: false})
        }
    },
    methods: {
        async categoryProvider(ctx) {
            let items = []
            this.isBusy = true;
            try {
                const res = await axios.get(`/categories?page=${ctx.currentPage}`);
                const categories = res.data.data;
                this.totalRows = res.data.pagination.count;
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
        },
        async onDelete(categoryId){
            try {
                const token = this.getToken;
                const response = await axios.delete(`/categories/${categoryId}`,{
                    headers: {
                        'Authorization': `Bearer ${token}`
                    }
                });
                if (response.status === 200){
                    this.$bvToast.toast("Category has been deleted successfully", {
                        title: 'Delete successful',
                        variant: 'success',
                        solid: true,
                        autoHideDelay: 3000,
                        appendToast: true
                    })
                }
                this.$root.$emit('bv::refresh::table', 'categoryTable')
            } catch (error) {
                const data = error.response.data.data;
                let message = "Invalid data";
                if (data[0] !== undefined){
                    message = data[0];
                }
                if (data.message !== undefined){
                    message = data.message;
                }
                this.$bvToast.toast(message, {
                    title: 'Delete error',
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
