<template>
    <div>
        <b-row class="justify-content-center">
            <b-col class="col-md-4 ">
                <b-card class="mt-4">
                    <b-card-text>
                        <h4 class="text-center">
                            Login
                        </h4>
                        <b-form @submit.prevent="onSubmit">

                            <b-form-group
                                id="input-group-1"
                                label="Email address:"
                                label-for="email-input"
                                class="my-3"
                            >
                                <b-form-input
                                    id="email-input"
                                    v-model="form.email"
                                    type="email"
                                    placeholder="Enter email"
                                    required
                                ></b-form-input>
                            </b-form-group>

                            <b-form-group
                                id="input-group-2"
                                label="Password:"
                                label-for="password-input"
                                class="my-3"
                            >
                                <b-form-input
                                    id="password-input"
                                    v-model="form.password"
                                    type="password"
                                    placeholder="Enter Password"
                                    required
                                ></b-form-input>
                            </b-form-group>
                            <b-button type="submit" variant="primary">Submit</b-button>
                        </b-form>
                    </b-card-text>
                </b-card>

            </b-col>
        </b-row>
    </div>
</template>

<script>
import axios from "@/plugins/axios";

export default {
    name: "Login",
    data(){
        return {
            form: {
                email: "",
                password: ""
            }
        }
    },
    methods: {
        async onSubmit(){
            try {
                const response = await axios.post('/auth/signin',this.form);
                if (response.status === 200){
                    localStorage.setItem('token',response.data.data.token);
                    await this.$store.dispatch('fetchUser');
                    await this.$router.push('/');
                } else {

                    this.$bvToast.toast(response.data.message, {
                        title: 'Login error',
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
                    title: 'Login error',
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