import api from "./axios";

// Login API
export const login = (data) => {
  return api.post("/api/auth/login", data);
};

// Register API (for later use)
export const register = (data) => {
  return api.post("/api/auth/register", data);
};
