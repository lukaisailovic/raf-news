<template>
    <div>
        <b-list-group-item >
            <h4 @click.prevent="goToPost(post.id)" style="cursor: pointer;">
                {{post.title}}
            </h4>
            <p @click.prevent="goToPost(post.id)" style="cursor: pointer;">
                {{shortText}}
            </p>
            <p v-if="displayCategory">
                Category: <b-link :to="{name:'FilteredPosts',params:{param:'category',value:post.category.id}}">{{post.category.name}}</b-link>
            </p>
            <p>
                Published at: {{publishedDate}}
            </p>
        </b-list-group-item>
    </div>
</template>

<script>
export default {
    name: "Post",
    props: ['post','displayCategory'],
    computed:{
        shortText(){
            const text = this.post.text;
            if (text.length > 150){
                return text.substring(0,150) + "...";
            }
            return text;
        },
        publishedDate(){
            const date = new Date(this.post.created);
            return date.toISOString().slice(0, 10);
        }
    },
    methods:{
        goToPost(id){
            this.$router.push({name: 'Post', params:{id:id}})
        }
    }
}
</script>

<style scoped>

</style>