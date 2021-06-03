<template>
    <div>
        <b-row>
            <b-col>
                <h2>
                    Posts
                </h2>
            </b-col>
        </b-row>
        <b-row v-if="isLoggedIn" class="my-3">
            <b-col>
                <b-button variant="outline-primary" pill :to="{name:'CreatePost'}"><b-icon icon="plus-circle"></b-icon> Create new post </b-button>
            </b-col>
        </b-row>
        <b-row>
            <b-col>
                <b-table
                    id="postsTable"
                    :busy.sync="isBusy"
                    :items="postsProvider"
                    :fields="fields"
                    :current-page="currentPage"
                    :per-page="perPage"
                >
                    <template #cell(title)="data">
                        <a href="">{{ data.item.title }}</a>
                    </template>
                    <template #cell(text)="data">
                        {{ shortText(data.item.text) }}
                    </template>
                    <template #cell(category)="data">
                        <router-link :to="{name: 'EditCategory', params: {id:data.item.category.id}}">{{ data.item.category.name }}</router-link>
                    </template>
                    <template #cell(action)="data">
                        <b-button variant="warning" pill :to="{name: 'EditPost', params: {id:data.item.id}}"><b-icon icon="pencil"></b-icon></b-button>
                        <b-button variant="danger" class="ml-2" pill @click.prevent="onDelete(data.item.id)"><b-icon icon="trash"></b-icon></b-button>
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
    name: 'Posts',
    components: {},
    data() {
        return {
            isBusy: false,
            fields: [
                {key: 'title', sortable: false},
                {key: 'category', sortable: false},
                {key: 'text', sortable: false, label: 'Short text'},
                {key: 'action', sortable: false}

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
    methods: {
        shortText(text){
            if (text.length > 150){
                return text.substring(0,150) + "...";
            }
            return text;
        },
        async postsProvider(ctx) {
            let items = []
            this.isBusy = true;
            try {
                const res = await axios.get(`/posts?page=${ctx.currentPage}`);
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
        async onDelete(postId){
            try {
                const token = this.getToken;
                const response = await axios.delete(`/posts/${postId}`,{
                    headers: {
                        'Authorization': `Bearer ${token}`
                    }
                });
                if (response.status === 200){
                    this.$bvToast.toast("Post has been deleted successfully", {
                        title: 'Delete successful',
                        variant: 'success',
                        solid: true,
                        autoHideDelay: 3000,
                        appendToast: true
                    })
                }
                this.$root.$emit('bv::refresh::table', 'postsTable')
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
