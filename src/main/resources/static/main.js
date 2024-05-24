console.log("Hello from main.js!")

function jumpToResult() {
    // Get value from input field
    const from = document.getElementById("from").value;
    if (from === "") {
        const errorLabel = document.getElementById("errorLabel");
        errorLabel.innerHTML = "Please enter a departure location";
        return;
    }

    const to = document.getElementById("to").value;
    if (to === "") {
        const errorLabel = document.getElementById("errorLabel");
        errorLabel.innerHTML = "Please enter a destination location";
        return;
    }

    const date = document.getElementById("date").value;

    console.log("from: " + from);
    console.log("to: " + to);

    window.location.href = "/result?from=" + from + "&to=" + to;
}