import { createApp } from 'vue'
import App from './App.vue'

import Vant from 'vant';
import 'vant/lib/index.css';
import {createRouter, createWebHistory} from 'vue-router'
import routes from "./config/router.ts";

const app = createApp(App);
app.use(Vant);

const router = createRouter({
    history: createWebHistory(),
    routes,
})

app.use(router);

app.mount('#app')



