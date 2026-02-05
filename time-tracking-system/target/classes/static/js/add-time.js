const token = localStorage.getItem("token");

if (!token) {
    window.location.href = "/login.html";
}

async function saveTime() {
    const date = document.getElementById("date").value;
    const startTime = document.getElementById("startTime").value;
    const endTime = document.getElementById("endTime").value;
    const description = document.getElementById("description").value;

    if (!date || !startTime || !endTime) {
        alert("Please fill all required fields");
        return;
    }

    try {
        const response = await fetch("/api/time/add", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + token
            },
            body: JSON.stringify({
                date,
                startTime,
                endTime,
                description
            })
        });

        if (response.status === 401) {
            logout();
            return;
        }

        if (!response.ok) {
            alert("Failed to save time");
            return;
        }

        alert("Time added successfully");
        window.location.href = "/dashboard.html";

    } catch (err) {
        console.error(err);
        alert("Server error");
    }
}

function logout() {
    localStorage.removeItem("token");
    localStorage.removeItem("user");
    window.location.href = "/login.html";
}
