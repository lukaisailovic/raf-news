<template>
    <div>
        <b-list-group class="my-5">
            <Post v-for="post in posts" :post="post" :key="post.id" :display-category="true"/>
        </b-list-group>
    </div>

</template>

<script>
import Post from "@/components/Post";

export default {
    name: "PostList",
    props: ['posts'],
    components: {
        Post
    }
}
</script>

<style scoped>

</style>