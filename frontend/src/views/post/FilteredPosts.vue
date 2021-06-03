<template>
    <div>
        <b-row>
            <b-col>
                <h4>
                    Posts filtered by parameter: {{parameter}} <span>, value: {{paramValue}}</span>
                </h4>
            </b-col>
        </b-row>
        <b-row>
            <b-col>
                <PostList :posts="posts" title="Posts" v-if="parameter==='category'" />
                <PostList :posts="posts" title="Posts" v-else display-category="true" />
            </b-col>
        </b-row>
        <b-row class="mt-3">
            <b-col>
                <b-pagination
                    v-model="page"
                    :total-rows="totalRows"
                    per-page="10"
                    pills
                    @change="pageChanged"
                ></b-pagination>
            </b-col>
        </b-row>
    </div>
</template>

<script>
import axios from "@/plugins/axios";
import PostList from "@/components/PostList";

export default {
    name: "FilteredPosts",
    components: {
        PostList

    },
    data(){
        return {
            parameter: '',
            value: '',
            posts: [],
            page: 1,
            totalRows: 10,
        }
    },
    computed: {
        paramValue(){
            if (this.parameter === 'category' && this.posts.length > 0){
                return this.posts[0].category.name;
            }
            if (this.parameter === 'tag'){
                return this.value;
            }
            return '';
        }
    },
    watch: {
        '$route': async function (to, from) {
            this.parameter = to.params.param;
            this.value = to.params.value;
            await this.fetchPosts();
        }
    },
    methods: {
        async pageChanged(page){
            this.page = page;
            await this.fetchPosts();
        },
        async fetchPosts(){
            try {
                const res = await axios.get('/posts/filter?param='+this.parameter+'&value='+this.value+'&page='+this.page);
                this.posts = res.data.data;
                this.totalRows = res.data.pagination.count;
            } catch (error){
                this.$bvToast.toast("Could not load posts", {
                    title: 'Fetch error',
                    variant: 'danger',
                    solid: true,
                    autoHideDelay: 3000,
                    appendToast: true
                })
            }
        },
    },
    async mounted() {
        this.parameter = this.$route.params.param;
        this.value = this.$route.params.value;
        await this.fetchPosts();
    }
}
</script>

<style scoped>

</style>