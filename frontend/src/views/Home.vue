<template>
    <div class="home">
        <PostList :posts="posts"/>
    </div>
</template>

<script>
import axios from "@/plugins/axios";
import PostList from "@/components/PostList";

export default {
    name: 'Home',
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
            const res = await axios.get('/posts');
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
