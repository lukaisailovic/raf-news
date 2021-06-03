<template>
    <div>
        <b-row>
            <b-col>
                <h2>
                    Create new category
                </h2>
            </b-col>
        </b-row>
        <b-row>
            <b-col>
                <b-form @submit.prevent="onSubmit">

                    <b-form-group
                        id="input-group-1"
                        label="Name:"
                        label-for="name-input"
                        class="my-3"
                    >
                        <b-form-input
                            id="name-input"
                            v-model="form.name"
                            type="text"
                            placeholder="Enter category name"
                            required
                        ></b-form-input>
                    </b-form-group>

                    <b-form-group
                        id="input-group-2"
                        label="Description:"
                        label-for="description-input"
                        class="my-3"
                    >
                        <b-form-input
                            id="description-input"
                            v-model="form.description"
                            type="text"
                            placeholder="Enter short category description"
                            required
                        ></b-form-input>
                    </b-form-group>
                    <b-button type="submit" variant="warning" >Edit</b-button>
                </b-form>
            </b-col>
        </b-row>
    </div>
</template>

<script>
import axios from "@/plugins/axios";

export default {
    name: "EditCategory",
    data(){
        return {
            categoryId: '',
            category: null,
            form: {
                name: "",
                description: ""
            }
        }
    },
    mounted() {
        this.categoryId = this.$route.params.id;
        this.loadCategory(this.categoryId)
    },
    methods: {
        async loadCategory(id){
            try {
                const token = this.$store.getters.getToken;
                const response = await axios.get('/categories/'+id,{
                    headers: {
                        'Authorization': `Bearer ${token}`
                    }
                });
                if (response.status === 200){
                    this.category = response.data.data;
                    this.form.name = this.category.name;
                    this.form.description = this.category.description;
                }
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
                    title: 'Could ot load category',
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
                const response = await axios.patch('/categories/'+this.categoryId,this.form,{
                    headers: {
                        'Authorization': `Bearer ${token}`
                    }
                });
                if (response.status === 200){
                    this.$bvToast.toast("Category has been edited successfully", {
                        title: 'Edit successful',
                        variant: 'success',
                        solid: true,
                        autoHideDelay: 3000,
                        appendToast: true
                    })
                    await this.loadCategory(this.categoryId);

                }
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