//statcard.jsx
export default function StatCard({ title, value, status }) {
  return (
    <div className="bg-white rounded-xl shadow p-4 flex justify-between items-center">
      <div>
        <p className="text-gray-500 text-sm">{title}</p>
        <p
          className={`text-xl font-semibold ${status ? "text-green-500" : ""}`}
        >
          {value}
        </p>
      </div>

      <span className="text-gray-400">⋮</span>
    </div>
  );
}
