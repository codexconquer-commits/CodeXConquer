import axios from "axios";

const api = axios.create({
  baseURL: "http://localhost:8080", // change if backend URL is different
  headers: {
    "Content-Type": "application/json",
  },
  withCredentials: true, // important if backend uses cookies
});

api.interceptors.request.use((config) => {
  const token = localStorage.getItem("token");

  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }

  return config;
});

export default api;
