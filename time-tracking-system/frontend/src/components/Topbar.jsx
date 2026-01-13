//topbar.jsx
export default function Topbar() {
  const logout = () => {
    localStorage.clear();
    window.location.href = "/login";
  };

  return (
    <header className="h-16 bg-white shadow flex items-center justify-between px-6">
      <h2 className="text-lg font-semibold">Dashboard</h2>

      <div className="flex items-center gap-4">
        <span>🔔</span>
        <button
          onClick={logout}
          title="Logout"
          className="bg-transparent border-none cursor-pointer"
        >
          ⏻
        </button>

        <img
          src="https://i.pravatar.cc/32"
          alt="avatar"
          className="rounded-full"
        />
      </div>
    </header>
  );
}
