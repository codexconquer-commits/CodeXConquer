import api from "./api";

// Status APIs
export const startStatus = (status) => api.post(`/api/status/${status}`);
export const stopStatus = () => api.post(`/api/status/stop`);
export const getStatusSummary = () => api.get(`/api/status/summary`);

// Time APIs
export const punchIn = () => api.post(`/api/time/punch-in`);
export const punchOut = () => api.post(`/api/time/punch-out`);
export const getTimeSummary = () => api.get(`/api/time/summary`);
