import { useEffect, useState } from "react";
import Sidebar from "../components/Sidebar";
import Topbar from "../components/Topbar";
import StatCard from "../components/StatCard";
import api from "../services/api";

// helper
const formatTime = (ms) => {
  if (ms <= 0) return "0h : 00m : 00s";
  const totalSec = Math.floor(ms / 1000);
  const hrs = Math.floor(totalSec / 3600);
  const mins = Math.floor((totalSec % 3600) / 60);
  const secs = totalSec % 60;
  const pad = (n) => String(n).padStart(2, "0");
  return `${pad(hrs)}h : ${pad(mins)}m : ${pad(secs)}s`;
};

export default function Dashboard() {
  const user = JSON.parse(localStorage.getItem("user"));
  const userId = user?.id;

  const [status, setStatus] = useState("OFFLINE");
  const [now, setNow] = useState(Date.now());
  const [totalTime, setTotalTime] = useState(0);
  const [breakTime, setBreakTime] = useState(0);

  // live clock
  useEffect(() => {
    const timer = setInterval(() => setNow(Date.now()), 1000);
    return () => clearInterval(timer);
  }, []);

  // ───────── FETCH SUMMARY ON LOAD ─────────
  useEffect(() => {
    if (!userId) return;

    const fetchSummary = async () => {
      try {
        const res = await api.get(`/api/time/summary/${userId}`);
        setTotalTime(res.data.totalTimeMs || 0);
        setBreakTime(res.data.breakTimeMs || 0);
        setStatus(res.data.status || "OFFLINE");
      } catch (err) {
        console.error("Summary fetch failed", err);
      }
    };

    fetchSummary();
  }, [userId]);

  // ───────── API ACTIONS ─────────
  const punchIn = async () => {
    if (status !== "OFFLINE") return;

    try {
      await api.post(`/api/time/punch-in/${userId}`);
      setStatus("ONLINE");
    } catch (err) {
      alert("Punch In failed");
    }
  };

  const punchOut = async () => {
    if (status === "OFFLINE") return;

    try {
      await api.post(`/api/time/punch-out/${userId}`);
      setStatus("OFFLINE");
    } catch (err) {
      alert("Punch Out failed");
    }
  };

  const startBreak = () => setStatus("ON_BREAK");
  const endBreak = () => setStatus("ONLINE");

  const productiveTime = totalTime - breakTime;

  // ───────── UI ─────────
  return (
    <div className="min-h-screen bg-gray-100 flex">
      <Sidebar />

      <div className="flex-1 flex flex-col pr-6">
        <Topbar />

        <main className="p-6 space-y-6">
          {/* Header */}
          <div className="flex items-center justify-between">
            <div>
              <h1 className="text-2xl font-semibold">
                Welcome, {user?.name || "User"}!
              </h1>
              <p className="text-gray-500 text-sm">
                Enjoy tracking your work hours
              </p>
            </div>

            <div className="flex gap-3">
              {status === "OFFLINE" && (
                <button
                  onClick={punchIn}
                  className="bg-green-500 text-white px-4 py-2 rounded-lg"
                >
                  Punch In
                </button>
              )}

              {status === "ONLINE" && (
                <>
                  <button
                    onClick={startBreak}
                    className="bg-red-400 text-white px-4 py-2 rounded-lg"
                  >
                    Break
                  </button>
                  <button
                    onClick={punchOut}
                    className="bg-red-500 text-white px-4 py-2 rounded-lg"
                  >
                    Punch Out
                  </button>
                </>
              )}

              {status === "ON_BREAK" && (
                <button
                  onClick={endBreak}
                  className="bg-green-500 text-white px-4 py-2 rounded-lg"
                >
                  End Break
                </button>
              )}
            </div>
          </div>

          {/* Stats */}
          <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
            <StatCard title="Total Hours" value={formatTime(totalTime)} />
            <StatCard title="Break Time" value={formatTime(breakTime)} />
            <StatCard
              title="Status"
              value={
                status === "ONLINE"
                  ? "Online"
                  : status === "ON_BREAK"
                  ? "On Break"
                  : "Offline"
              }
              status
            />
            <StatCard title="Productive" value={formatTime(productiveTime)} />
          </div>
        </main>
      </div>
    </div>
  );
}
