var stompClient = null;

function connect(subscriptionId, handler, onCloseHandler) {
    var socket = new SockJS('/tictactoe');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe(subscriptionId, function (msg) {
            handler(JSON.parse(msg.body));
        });
    });

    var stompClientOnclose = stompClient.ws.onclose;
    stompClient.ws.onclose = function() {
        console.log("Websocket connection closed and handled from our app.");
        onWSCloseHandler();
        stompClientOnclose();
    };
}

function onWSCloseHandler() {
    $("#connect-status").html(
        '<p style="color:red;">Connection to server lost, please <a href=".">refresh</a> page</p><br/>');
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
}
