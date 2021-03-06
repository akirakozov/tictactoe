function toDateTime(epochSecond) {
    var dateTime = new Date(null)
    dateTime.setSeconds(epochSecond);
    return dateTime.toLocaleString();
}

function updateGameStatus(message) {
    var statusEl = $("#game_" + message.id);
    var statusHtml =
            "<td>" + message.id + "</td>" +
            "<td>" + message.playersCount + "</td>" +
            "<td>" + message.state + "</td>" +
            "<td>" + toDateTime(message.lastStepTime.epochSecond) + "</td>" +
            "<td><a href=\"/game?id=" + message.id + "\">Enter game</a></td>";

    if (statusEl.length) {
        statusEl.html(statusHtml);
    } else {
        $("#games").append("<tr id=\"game_" + message.id + "\">" + statusHtml + "</tr>");
    }
}

function onWindowLoad() {
    connect('/game-panel', updateGameStatus, onWSCloseHandler);
}
