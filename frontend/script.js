const serverURL = "http://localhost:5000";

async function sendRequest(endpoint, body) {
    const response = await fetch(`${serverURL}/${endpoint}`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(body)
    });
    return response.json();
}

function registerUser() {
    const name = document.getElementById("registerName").value;
    const accNumber = document.getElementById("registerAccNumber").value;
    const balance = document.getElementById("registerBalance").value;
    sendRequest("register", { name, accountNumber: accNumber, initialDeposit: parseFloat(balance) }).then(response => {
        alert(response.success ? `User ${name} registered successfully.` : `Failed to register user ${name}.`);
    });
}

function depositMoney() {
    const accNumber = document.getElementById("depositAccNumber").value;
    const amount = document.getElementById("depositAmount").value;
    sendRequest("deposit", { accountNumber: accNumber, amount: parseFloat(amount) }).then(response => {
        alert(response.message);
    });
}

function withdrawMoney() {
    const accNumber = document.getElementById("withdrawAccNumber").value;
    const amount = document.getElementById("withdrawAmount").value;
    sendRequest("withdraw", { accountNumber: accNumber, amount: parseFloat(amount) }).then(response => {
        alert(response.message);
    });
}

function checkBalance() {
    const accNumber = document.getElementById("balanceAccNumber").value;
    sendRequest("balance", { accountNumber: accNumber }).then(response => {
        alert(response.message);
    });
}
