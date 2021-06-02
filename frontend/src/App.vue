<template>
    <div id="app">
        <Navbar/>
        <b-container class="mt-4">
            <b-row>
                <b-col>
                    <router-view/>
                </b-col>
            </b-row>
        </b-container>

    </div>
</template>
<script>
import Navbar from '@/components/Navbar.vue'

export default {
    components: {
        Navbar
    }
}
</script>
<style>

</style>
