import { create } from 'zustand';

// Create a store for authentication
const useAuthStore = create((set) => ({
  token: localStorage.getItem('authToken') || null, // Load token from storage
  setToken: (newToken) => {
    localStorage.setItem('authToken', newToken); // Save token in localStorage
    set({ token: newToken });
  },
  clearToken: () => {
    localStorage.removeItem('authToken'); // Remove token on logout
    set({ token: null });
  },
}));

export default useAuthStore;
