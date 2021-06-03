<template>
    <div>
        <div v-if="post !== null">
            <b-row>
                <b-col>
                    <h1>
                        {{post.title}}
                    </h1>
                </b-col>
            </b-row>
            <b-row>
                <b-col>
                    <p>
                        Author: {{post.author.firstName}} {{post.author.lastName}}
                    </p>
                    <p>
                        Category: <b-link :to="{name:'FilteredPosts',params:{param:'category',value:post.category.id}}">{{post.category.name}}</b-link>
                    </p>
                    <p>
                        Published date: {{getFormattedDate(post.created)}}
                    </p>
                </b-col>
            </b-row>
            <b-row>
                <b-col>
                    <hr>
                    <p>
                        {{post.text}}
                    </p>
                    <hr>
                </b-col>
            </b-row>
            <b-row>
                <b-col>
                    <b-button size="sm" pill v-for="tag in post.tags" variant="secondary" class="mr-2" :to="{name:'FilteredPosts',params:{param:'tag',value:tag.description}}">{{tag.description}}</b-button>
                </b-col>
            </b-row>
        </div>
        <b-row class="mt-3">
            <b-col>
                <h4>
                    Comments
                </h4>
            </b-col>
        </b-row>
        <b-row class="my-4">
            <b-col>
                <h5>
                    New Comment
                </h5>
                <b-form @submit.prevent="onSubmit">

                    <b-form-group
                        id="input-group-1"
                        label="Author:"
                        label-for="author-input"
                        class="my-3"
                    >
                        <b-form-input
                            id="name-input"
                            v-model="form.author"
                            type="text"
                            placeholder="Enter your name"
                            required
                            min="1"
                            max="140"
                        ></b-form-input>
                    </b-form-group>

                    <b-form-group
                        id="input-group-2"
                        label="Text:"
                        label-for="text-input"
                        class="my-3"
                    >
                        <b-form-textarea
                            id="textarea"
                            v-model="form.text"
                            placeholder="Enter comment text..."
                            rows="3"
                            max-rows="4"
                            min="1"
                            max="500"
                            required
                        ></b-form-textarea>
                    </b-form-group>
                    <b-button type="submit" variant="primary" pill>Leave comment</b-button>
                </b-form>
            </b-col>
        </b-row>
        <b-row v-if="comments.length === 0 && !loadingComments">
            <b-col>
                <h4>
                    No comments
                </h4>
            </b-col>
        </b-row>

        <div v-if="comments.length > 0 && !loadingComments">
            <b-row >
                <b-col>
                    <b-list-group>
                        <b-list-group-item v-for="comment in comments">
                            <h6>
                                {{comment.author}} <span class="text-small">({{getFormattedDate(comment.created)}})</span>
                            </h6>
                            <p>
                                {{comment.text}}
                            </p>
                        </b-list-group-item>
                    </b-list-group>
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

    </div>
</template>

<script>
import axios from "@/plugins/axios";

export default {
    name: "Post",
    data(){
        return {
            postId: '',
            post: null,
            comments: [],
            loadingComments: true,
            page: 1,
            totalRows: 10,
            form: {
                author: '',
                text: '',
            }
        }
    },
    async mounted(){
        this.postId = this.$route.params.id;
        await this.fetchPost(this.postId);
        await this.fetchComments(this.postId);
    },
    computed: {

    },
    methods: {
        getFormattedDate(d){
            const date = new Date(d);
            return date.toISOString().slice(0, 10);
        },
        async pageChanged(page){
            this.page = page;
            await this.fetchComments(this.postId);
        },
        async onSubmit(){
            try {
                const response = await axios.post('/comments/'+this.postId,this.form);
                if (response.status === 200){
                    this.$bvToast.toast('Comment has been created successfully', {
                        title: 'Comment creation',
                        variant: 'success',
                        solid: true,
                        autoHideDelay: 3000,
                        appendToast: true
                    });
                    this.form = {
                        author: '',
                        text: '',
                    };
                    this.page = 1;
                    await this.fetchComments(this.postId);
                }
            } catch (error){
                const data = error.response.data.data;
                let message = "Could not create new comment";
                if (data[0] !== undefined){
                    message = data[0];
                }
                if (data.message !== undefined){
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
        },
        async fetchPost(id){
            try {
                const response = await axios.get('/posts/'+id);
                if (response.status === 200){
                    this.post = response.data.data;
                }
            } catch (error){
                const data = error.response.data.data;
                let message = "Could not fetch post";
                if (data[0] !== undefined){
                    message = data[0];
                }
                if (data.message !== undefined){
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
        },
        async fetchComments(postId){
            this.loadingComments = true;
            try {
                const response = await axios.get('/comments/'+postId+'?page='+this.page);
                if (response.status === 200){
                    this.comments = response.data.data;
                    this.totalRows = response.data.pagination.count;
                }
            } catch (error){
                const data = error.response.data.data;
                let message = "Could not fetch comments for post";
                if (data[0] !== undefined){
                    message = data[0];
                }
                if (data.message !== undefined){
                    message = data.message;
                }
                this.$bvToast.toast(message, {
                    title: 'Fetch error',
                    variant: 'danger',
                    solid: true,
                    autoHideDelay: 3000,
                    appendToast: true
                })
            } finally {
                this.loadingComments = false;
            }
        },
    }
}
</script>

<style scoped>

</style>