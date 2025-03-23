const serverURL = "http://localhost:5000";

async function sendRequest(command) {
    const response = await fetch(serverURL, {
        method: "POST",
        body: command,
    });
    const data = await response.json();
    document.getElementById("result").innerText = JSON.stringify(data);
}

function registerUser() {
    const name = document.getElementById("name").value;
    const accNumber = document.getElementById("accNumber").value;
    sendRequest(`REGISTER ${name} ${accNumber} 1000`);
}

function depositMoney() {
    const accNumber = document.getElementById("accNumber").value;
    const amount = document.getElementById("amount").value;
    sendRequest(`DEPOSIT ${accNumber} ${amount}`);
}

function withdrawMoney() {
    const accNumber = document.getElementById("accNumber").value;
    const amount = document.getElementById("amount").value;
    sendRequest(`WITHDRAW ${accNumber} ${amount}`);
}

function checkBalance() {
    const accNumber = document.getElementById("accNumber").value;
    sendRequest(`BALANCE ${accNumber}`);
}
