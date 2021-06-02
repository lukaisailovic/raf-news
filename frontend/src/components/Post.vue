<template>
    <div>
        <b-list-group-item>
            <h4>
                {{post.title}}
            </h4>
            <p>
                {{shortText}}
            </p>
            <p v-if="displayCategory">
                Category: <b-link href="#foo">{{post.category.name}}</b-link>
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
    }
}
</script>

<style scoped>

</style>