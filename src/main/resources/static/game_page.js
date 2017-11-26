var X = 'x'
var O = 'o'
var curSymbol = X;

function addClickCallback() {
    document.querySelector('#board').onclick = function(ev) {
        var row = ev.target.parentElement.rowIndex;
        var col = ev.target.cellIndex;
        var cellVal = ev.target.innerHTML;
        if (cellVal != X && cellVal != O) {
            makeStep(row, col);
        }
    }
}

function updateCell(message) {
    curSymbol = message.symbol === X ? O : X;
    $('#board tr:eq(' + message.x + ') td:eq(' + message.y + ')').html(message.symbol);
}

function getGameId() {
    return $('#gameId').attr('value');
}

function makeStep(x, y) {
    $.get( "/make-step?x=" + x + "&y=" + y + "&gameId=" + getGameId() + "&symbol=" + curSymbol);
}

function updateCurrentSymbol() {
    curSymbol = $('#gameId').attr("step-number") % 2 === 0 ? X : O
}

function onWindowLoad() {
    addClickCallback();
    updateCurrentSymbol();
    connect('/game/' + getGameId(), updateCell);
}
