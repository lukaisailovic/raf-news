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
                    <b-button type="submit" variant="primary">Create</b-button>
                </b-form>
            </b-col>
        </b-row>
    </div>
</template>

<script>
import axios from "@/plugins/axios";

export default {
    name: "CreateCategory",
    data(){
        return {
            form: {
                name: "",
                description: ""
            }
        }
    },
    methods: {
        async onSubmit(){
            try {
                const token = this.$store.getters.getToken;
                const response = await axios.post('/categories',this.form,{
                    headers: {
                        'Authorization': `Bearer ${token}`
                    }
                });
                if (response.status === 200){
                    await this.$router.push('Categories')

                    this.$bvToast.toast("Category has been created successfully", {
                        title: 'Create successful',
                        variant: 'success',
                        solid: true,
                        autoHideDelay: 3000,
                        appendToast: true
                    })

                    this.form = {
                        name: "",
                        description: ""
                    };

                } else {

                    this.$bvToast.toast(response.data.data.message, {
                        title: 'Create error',
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
                    title: 'Create error',
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