var stompClient = null;

function connect(subscriptionId, handler) {
    var socket = new SockJS('/tictactoe');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe(subscriptionId, function (msg) {
            handler(JSON.parse(msg.body));
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}
