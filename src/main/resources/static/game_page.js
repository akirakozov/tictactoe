var X = 'x'
var O = 'o'
var curSymbol = X;
var gameState;
var wasUpdateAfterClick = true;
var lastStepNumber = 0;

function addClickCallback() {
    document.querySelector('#board').onclick = function(ev) {
        var row = ev.target.parentElement.rowIndex;
        var col = ev.target.cellIndex;
        var cellVal = ev.target.innerHTML;
        if (cellVal != X && cellVal != O && gameState != "FINISHED" && wasUpdateAfterClick) {
            makeStep(row, col);
            wasUpdateAfterClick = false;
        }
    }
}

function updateStatus(curSymbol, state) {
    gameState = state;
    var text = state === "ACTIVE" ? "Next step: " + curSymbol : "The game is over";
    $("#game-status").html("<p>" + text + "</p>");
}

function updateLastStepNumber(stepNumber) {
    if (lastStepNumber + 1 == stepNumber) {
        lastStepNumber = stepNumber;
    } else {
        window.location.reload(true);
    }
}

function updateCell(message) {
    updateLastStepNumber(message.stepNumber);
    curSymbol = message.symbol === X ? O : X;
    updateStatus(curSymbol, message.state);
    $('#board tr:eq(' + message.x + ') td:eq(' + message.y + ')').html(message.symbol);
    wasUpdateAfterClick = true;
}

function getGameId() {
    return $('#gameId').attr('value');
}

function makeStep(x, y) {
    $.get( "/make-step?x=" + x + "&y=" + y + "&gameId=" + getGameId() + "&symbol=" + curSymbol);
}

function updateCurrentState() {
    lastStepNumber = $('#gameId').attr("step-number");
    curSymbol = lastStepNumber % 2 === 0 ? X : O;
    updateStatus(curSymbol, $('#gameId').attr("state"));
}

function onWindowLoad() {
    addClickCallback();
    updateCurrentState();
    connect('/game/' + getGameId(), updateCell);
}
