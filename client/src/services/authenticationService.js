import axios from 'axios';
import useAuthStore from '../store/authStore';

const apiUrl = import.meta.env.VITE_API_URL;
axios.defaults.baseURL = apiUrl;

const authenticationService = {
  // async login(user) {
  //   return await axios.post('/login', user, { withCredentials: true })
  // },

  async login(user) {
    const response = await axios.post('/login', user);
    useAuthStore.getState().setToken(response.data.token); // Store token
    return response;
  },

  async register(user) {
    //return await axios.post('/register', user, { withCredentials: true })
    return await axios.post('/register', user)
  },

  async logout() {
    useAuthStore.getState().clearToken(); // Clear token
  }
}

export default authenticationService;