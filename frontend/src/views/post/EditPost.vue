<template>
    <div>
        <b-row>
            <b-col>
                <h2>
                    Create new post
                </h2>
            </b-col>
        </b-row>
        <b-row>
            <b-col>
                <b-form @submit.prevent="onSubmit">

                    <b-form-group
                        id="input-group-1"
                        label="Title:"
                        label-for="text-input"
                        class="my-3"
                    >
                        <b-form-input
                            id="title-input"
                            v-model="form.title"
                            type="text"
                            placeholder="Enter post title"
                            required
                            min="1"
                            max="254"
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
                            placeholder="Enter post text..."
                            rows="6"
                            max-rows="10"
                            min="1"
                            max="1000"
                            required
                        ></b-form-textarea>
                    </b-form-group>


                    <b-form-group
                        id="input-group-3"
                        label="Category:"
                        label-for="category-input"
                        class="my-3"
                    >
                        <b-form-select
                            id="category-input"
                            v-model="form.categoryId"
                            :options="categories"
                        ></b-form-select>
                    </b-form-group>

                    <b-form-group
                        id="input-group-4"
                        label="Tags:"
                        label-for="tags-input"
                        class="my-3"
                    >
                        <b-form-tags
                            input-id="tags-input"
                            v-model="form.tags"
                            remove-on-delete
                        ></b-form-tags>
                    </b-form-group>

                    <b-button type="submit" variant="primary">Edit</b-button>
                </b-form>
            </b-col>
        </b-row>
    </div>
</template>

<script>
import axios from "@/plugins/axios";

export default {
    name: "EditPost",
    data(){
        return {
            form: {
                title: "",
                text: "",
                categoryId: "",
                tags: [],
            },
            categories: [],
            categoriesLastFetched: 1,
            categoriesPageNumber: 1,
            postId: '',
        }
    },
    async mounted(){
        this.postId = this.$route.params.id;
        await this.fetchPost(this.postId);
        let categories = await this.fetchCategories(1);
        for (const category of categories) {
            this.categories.push({value: category.id, text: category.name})
        }
        while (this.categoriesLastFetched < this.categoriesPageNumber){
            categories = await this.fetchCategories(this.categoriesLastFetched+1);
            for (const category of categories) {
                this.categories.push({value: category.id, text: category.name})
            }
        }
    },
    methods: {
        async fetchCategories(page){
            try {
                const res = await axios.get(`/categories?page=${page}`);
                const categories = res.data.data;
                this.categoriesPageNumber = res.data.pagination.pageNumber;
                this.categoriesLastFetched = res.data.pagination.currentPage;
                return categories;
            } catch (error) {
                this.$bvToast.toast("Could not load categories", {
                    title: 'Fetch error',
                    variant: 'danger',
                    solid: true,
                    autoHideDelay: 3000,
                    appendToast: true
                })
                return [];
            }
        },
        async fetchPost(id){
            try {
                const token = this.$store.getters.getToken;
                const response = await axios.get('/posts/'+id,this.form,{
                    headers: {
                        'Authorization': `Bearer ${token}`
                    }
                });
                if (response.status === 200){
                    const post = response.data.data;
                    this.form.title = post.title;
                    this.form.text = post.text;
                    this.form.categoryId = post.category.id;
                    for (const tag of post.tags) {
                        this.form.tags.push(tag.description);
                    }

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
        async onSubmit(){
            try {
                const token = this.$store.getters.getToken;
                const response = await axios.patch('/posts/'+this.postId,this.form,{
                    headers: {
                        'Authorization': `Bearer ${token}`
                    }
                });
                if (response.status === 200){
                    this.$bvToast.toast("Post has been edited successfully", {
                        title: 'Create successful',
                        variant: 'success',
                        solid: true,
                        autoHideDelay: 3000,
                        appendToast: true
                    });
                    await this.fetchPost(this.postId);

                } else {

                    this.$bvToast.toast(response.data.data.message, {
                        title: 'Edit error',
                        variant: 'danger',
                        solid: true,
                        autoHideDelay: 3000,
                        appendToast: true
                    })
                }
                console.log(response)
            } catch (error){
                const data = error.response.data.data;
                let message = "Invalid data";
                if (data[0] !== undefined){
                    message = data[0];
                }
                if (data.message !== undefined){
                    message = data.message;
                }
                this.$bvToast.toast(message, {
                    title: 'Edit error',
                    variant: 'danger',
                    solid: true,
                    autoHideDelay: 3000,
                    appendToast: true
                })
            }
        }
    }
}
</script>

<style scoped>

</style>