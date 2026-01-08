// sidebar.jsx
import { BarChart, LogOut } from "lucide-react";

export default function Sidebar() {
  return (
    <aside className="w-64 bg-white shadow flex flex-col p-4">
      {/* Logo */}
      <div className="flex justify-center items-center gap-2 mb-8">
        <img src="../../unnamed.jpg" alt="logo" className="w-14" />
        <h1 className="text-3xl font-semibold text-gray-800">WorkTrack</h1>
      </div>

      {/* User Info */}
      <div className="flex items-center gap-3 mb-6">
        <img
          src="https://i.pravatar.cc/40"
          alt="avatar"
          className="rounded-full"
        />
        <div>
          <p className="text-sm text-gray-500">Welcome,</p>
          <p className="font-semibold">Harshit</p>
        </div>
      </div>

      {/* Nav */}
      <nav className="space-y-2">
        <button className="w-full flex gap-2 items-center text-left px-3 py-2 rounded-lg bg-blue-100 text-blue-600">
          <BarChart size={18} /> Dashboard
        </button>
        <button className="w-full flex gap-2 items-center text-left px-3 py-2 rounded-lg text-gray-600 hover:bg-gray-100 active:bg-gray-200">
          <LogOut size={18} /> Logout
        </button>
      </nav>
    </aside>
  );
}
