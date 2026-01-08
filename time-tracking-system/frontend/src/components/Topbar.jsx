//topbar.jsx
export default function Topbar() {
  return (
    <header className="h-16 bg-white shadow flex items-center justify-between px-6">
      <h2 className="text-lg font-semibold">Dashboard</h2>

      <div className="flex items-center gap-4">
        <span>🔔</span>
        <span>⏻</span>
        <img
          src="https://i.pravatar.cc/32"
          alt="avatar"
          className="rounded-full"
        />
      </div>
    </header>
  );
}
