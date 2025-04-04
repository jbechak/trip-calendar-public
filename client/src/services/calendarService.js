import axios from 'axios';

const apiUrl = import.meta.env.VITE_API_URL;
axios.defaults.baseURL = apiUrl;

const calendarService = {
  async getAll(userId) {
    try {
      const response = await axios.get(`/calendar/getByUser/${userId}`);
      return response.data;
    } catch (error) {
      throw error;
    }
  },
  async getCalendar(id) {
    try {
      const response = await axios.get(`/calendar/${id}`);
      return response.data;
    } catch (error) {
      throw error;
    }
  },
  async post(calendar) {
    try {
      const response = await axios.post('/calendar', calendar);
      return response.data;
    } catch (error) {
      throw error;
    }
  },

  async postCell(cell) {
    try {
      const response = await axios.post('/cell', cell);
      return response.data;
    } catch (error) {
      throw error;
    }
  },

  async putCell(cell) {
    try {
      await axios.put('/cell', cell);
    } catch (error) {
      throw error;
    }
  },

  async deleteCell(id) {
    try {
      await axios.delete(`/cell/${id}`);
    } catch (error) {
      console.error('Error deleting data:', error);
      throw error;
    }
  }
}

export default calendarService;