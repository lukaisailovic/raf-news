import axios from "axios";

const baseURL = process.env.VUE_APP_BASE_URL || 'http://localhost:8080/raf_news/api/';
if (typeof baseURL !== 'undefined')
{
    axios.defaults.baseURL = baseURL;
}

export default axios;