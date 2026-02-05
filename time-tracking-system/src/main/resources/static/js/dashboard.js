// ---------------- AUTH GUARD ----------------
const token = localStorage.getItem("token");

if (!token) {
    window.location.href = "/login.html";
}

// ---------------- TIMER ----------------
let seconds = 0;
let interval = null;

function updateTimer() {
    let h = String(Math.floor(seconds / 3600)).padStart(2, '0');
    let m = String(Math.floor((seconds % 3600) / 60)).padStart(2, '0');
    let s = String(seconds % 60).padStart(2, '0');
    document.getElementById("timer").innerText = `${h}:${m}:${s}`;
}

function startTimer() {
    if (interval) return;
    interval = setInterval(() => {
        seconds++;
        updateTimer();
    }, 1000);
}

function stopTimer() {
    clearInterval(interval);
    interval = null;
}

function resetTimer() {
    stopTimer();
    seconds = 0;
    updateTimer();
}

// ---------------- API BASE ----------------
const BASE_URL = "/api";

// ---------------- PUNCH IN ----------------
async function punchIn() {
    const response = await fetch(BASE_URL + "/time/punch-in", {
        method: "POST",
        headers: {
            "Authorization": "Bearer " + token
        }
    });

    if (response.status === 401) {
        logout();
        return false;
    }

    return response.ok;
}

// ---------------- PUNCH OUT ----------------
async function punchOut() {
    const response = await fetch(BASE_URL + "/time/punch-out", {
        method: "POST",
        headers: {
            "Authorization": "Bearer " + token
        }
    });

    if (response.status === 401) {
        logout();
        return false;
    }

    return response.ok;
}

// ---------------- WORK ACTIONS ----------------
async function startWork() {
    const success = await punchIn();

    if (!success) {
        alert("Already punched in or session error");
        return;
    }

    seconds = 0;
    updateTimer();
    startTimer();
}

async function stopWork() {
    const success = await punchOut();

    if (!success) {
        alert("No active session to stop");
        return;
    }

    stopTimer();
    alert("Work session saved");
}

// ---------------- LOGOUT ----------------
function logout() {
    localStorage.removeItem("token");
    localStorage.removeItem("user");
    window.location.href = "/login.html";
}

// ---------------- WORK SUMMARY ----------------
async function loadWorkSummary() {
    try {
        const response = await fetch("/api/time/summary", {
            headers: {
                "Authorization": "Bearer " + token
            }
        });

        if (response.status === 401) {
            logout();
            return;
        }

        if (!response.ok) {
            console.error("Failed to load summary");
            return;
        }

        const data = await response.json();

        document.getElementById("todayHours").innerText =
            formatSeconds(data.todaySeconds);

        document.getElementById("weekHours").innerText =
            formatSeconds(data.weekSeconds);

        document.getElementById("monthHours").innerText =
            formatSeconds(data.monthSeconds);

    } catch (err) {
        console.error("Summary error:", err);
    }
}

// ---------------- FORMAT UTILS ----------------
function formatSeconds(sec) {
    const h = Math.floor(sec / 3600);
    const m = Math.floor((sec % 3600) / 60);
    return `${h}h ${m}m`;
}

// Load summary on page load
loadWorkSummary();
