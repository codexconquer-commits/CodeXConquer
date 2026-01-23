import { useEffect, useState } from "react";
import {
  startStatus,
  stopStatus,
  getStatusSummary,
  punchIn,
  punchOut,
  getTimeSummary,
} from "../services/dashboardApi";

export default function Dashboard() {
  const [currentStatus, setCurrentStatus] = useState("");
  const [summary, setSummary] = useState([]);
  const [timeSummary, setTimeSummary] = useState(null);

  const user = JSON.parse(localStorage.getItem("user"));

  const logout = () => {
    localStorage.clear();
    window.location.href = "/login";
  };

  const fetchAll = async () => {
    const s = await getStatusSummary();
    const t = await getTimeSummary();
    setSummary(s.data);
    setTimeSummary(t.data);
  };

  useEffect(() => {
    fetchAll();
  }, []);

  return (
    <div className="p-6">
      <div className="flex justify-between mb-4">
        <h2 className="text-xl">Welcome {user?.email}</h2>
        <button onClick={logout} className="bg-red-500 text-white px-3 py-1">
          Logout
        </button>
      </div>

      <select onChange={(e) => setCurrentStatus(e.target.value)}>
        <option value="">Select Status</option>
        <option value="ONLINE">ONLINE</option>
        <option value="BREAK">BREAK</option>
        <option value="IDLE">IDLE</option>
        <option value="AWAY">AWAY</option>
      </select>

      <div className="mt-2">
        <button onClick={() => startStatus(currentStatus)}>Start</button>
        <button onClick={stopStatus}>Stop</button>
      </div>
    </div>
  );
}
