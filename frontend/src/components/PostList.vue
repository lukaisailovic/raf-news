<template>
    <div>
        <b-row v-if="title !== null">
            <b-col>
                <h2>
                    {{title}}
                </h2>
            </b-col>
        </b-row>
        <b-row>
            <b-col>
                <b-list-group class="mt-2 mb-5">
                    <Post v-for="post in posts" :post="post" :key="post.id" :display-category="displayCategory"/>
                </b-list-group>
            </b-col>
        </b-row>
    </div>

</template>

<script>
import Post from "@/components/Post";

export default {
    name: "PostList",
    props: ['posts','title','displayCategory'],
    components: {
        Post
    }
}
</script>

<style scoped>

</style>