<template>
    <div>
        <b-row>
            <b-col>
                <h2>
                    Create new user
                </h2>
            </b-col>
        </b-row>
        <b-row class="mt-2">
            <b-col>
                <b-button variant="outline-secondary" size="sm" pill :to="{name:'Users'}">
                    <b-icon icon="arrow-left-circle"></b-icon>
                    Back to the list of users
                </b-button>
            </b-col>
        </b-row>
        <b-row>
            <b-col>
                <b-form @submit.prevent="onSubmit">

                    <b-form-group
                        id="input-group-1"
                        label="First Name:"
                        label-for="fn-input"
                        class="my-3"
                    >
                        <b-form-input
                            id="fn-input"
                            v-model="form.firstName"
                            type="text"
                            min="1"
                            max="50"
                            placeholder="Enter first name"
                            required
                        ></b-form-input>
                    </b-form-group>

                    <b-form-group
                        id="input-group-2"
                        label="Last Name:"
                        label-for="ln-input"
                        class="my-3"
                    >
                        <b-form-input
                            id="ln-input"
                            v-model="form.lastName"
                            type="text"
                            min="1"
                            max="50"
                            placeholder="Enter last name"
                            required
                        ></b-form-input>
                    </b-form-group>

                    <b-form-group
                        id="input-group-3"
                        label="Email:"
                        label-for="email-input"
                        class="my-3"
                    >
                        <b-form-input
                            id="email-input"
                            v-model="form.email"
                            type="email"
                            min="1"
                            max="50"
                            placeholder="Enter email"
                            required
                        ></b-form-input>
                    </b-form-group>

                    <b-form-group
                        id="input-group-4"
                        label="Password:"
                        label-for="pw-input"
                        class="my-3"
                    >
                        <b-form-input
                            id="pw-input"
                            v-model="form.password"
                            type="password"
                            min="1"
                            max="50"
                            placeholder="Enter password"
                            required
                        ></b-form-input>
                    </b-form-group>

                    <b-form-group
                        id="input-group-5"
                        label="Confirm password:"
                        label-for="pwc-input"
                        class="my-3"
                    >
                        <b-form-input
                            id="pwc-input"
                            v-model="form.passwordConfirm"
                            type="password"
                            min="1"
                            max="50"
                            placeholder="Confirm password"
                            required
                        ></b-form-input>
                    </b-form-group>

                    <b-form-group
                        id="input-group-6"
                        label="User type:"
                        label-for="ut-input"
                        class="my-3"
                    >
                        <b-form-select
                            id="ut-input"
                            v-model="form.userType"
                            :options="userTypeOptions"
                        ></b-form-select>
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
    name: "CreateUser",
    data() {
        return {
            userTypeOptions: [
                {value: 'ADMIN', text: 'Admin'},
                {value: 'CONTENT_CREATOR', text: 'Content Creator'},
            ],
            form: {
                firstName: '',
                lastName: '',
                email: '',
                password: '',
                passwordConfirm: '',
                userType: '',
            }
        }
    },
    methods: {
        async onSubmit() {
            try {
                const token = this.$store.getters.getToken;
                const response = await axios.post('/users', this.form, {
                    headers: {
                        'Authorization': `Bearer ${token}`
                    }
                });
                if (response.status === 200) {
                    this.$bvToast.toast("User has been created successfully", {
                        title: 'Create successful',
                        variant: 'success',
                        solid: true,
                        autoHideDelay: 3000,
                        appendToast: true
                    })

                    await this.$router.push({name: 'Users'})

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
            } catch (error) {
                const data = error.response.data.data;
                let message = "Invalid data";
                if (data[0] !== undefined) {
                    message = data[0];
                }
                if (data.message !== undefined) {
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