const BASE_URL = "http://localhost:8080/api";

// LOGIN
async function loginUser() {
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    if (!email || !password) {
        alert("Please enter email and password");
        return;
    }

    try {
        const response = await fetch("/api/auth/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ email, password })
        });

        // 🔴 THIS IS IMPORTANT
        if (response.status !== 200) {
            alert("Invalid email or password");
            return;
        }

        const data = await response.json();

        // ✅ BACKEND RETURNS token (NOT jwt)
        localStorage.setItem("token", data.token);

        // (optional) store user
        localStorage.setItem("user", JSON.stringify(data.user));

        // ✅ redirect
        window.location.href = "/dashboard.html";

    } catch (err) {
        console.error(err);
        alert("Server error");
    }
}

// REGISTER
async function registerUser() {
  const name = document.getElementById("name").value;
  const email = document.getElementById("email").value;
  const password = document.getElementById("password").value;

  const response = await fetch("/api/auth/login", {
      method: "POST",
      headers: {
          "Content-Type": "application/json"
      },
      body: JSON.stringify({
          email: email,
          password: password
      })
  });


  if (response.ok) {
    alert("Registered!");
    window.location.href = "login.html";
  } else {
    alert("Register Failed");
  }
}
