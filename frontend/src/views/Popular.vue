<template>
    <div class="home">
        <PostList :posts="posts" title="Most popular posts" display-category="true"/>
    </div>
</template>

<script>
import axios from "@/plugins/axios";
import PostList from "@/components/PostList";

export default {
    name: 'Popular',
    components: {
        PostList

    },
    data(){
        return {
            posts: []
        }
    },
    async mounted() {
        try {
            const res = await axios.get('/posts/popular');
            this.posts = res.data.data;
        } catch (error){
            this.$bvToast.toast("Could not load posts", {
                title: 'Fetch error',
                variant: 'danger',
                solid: true,
                autoHideDelay: 3000,
                appendToast: true
            })
        }
    }

}
</script>
