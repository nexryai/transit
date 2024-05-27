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

    const timeModeArrival = document.getElementById("timeModeArrival").checked;
    const timeModeDeparture = document.getElementById("timeModeDeparture").checked;
    const date = document.getElementById("date").value;
    const time = document.getElementById("time").value;

    console.log("from: " + from);
    console.log("to: " + to);

    let url = "/result?from=" + from + "&to=" + to;
    if (date !== "" && time !== "") {
        url += "&time=" + date + "T" + time + ":00";
    }

    if (timeModeArrival) {
        url += "&timeMode=a";
    } else if (timeModeDeparture) {
        url += "&timeMode=d";
    }


    window.location.href = url;
}