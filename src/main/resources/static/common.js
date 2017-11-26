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

function getUserId() {
    var idName = "tictactoeUserId";
    var id = sessionStorage.getItem(idName);
    if (id === null) {
        var response = $.get("/create-user-id",
            function(data, status){
                alert("Data: " + data + "\nStatus: " + status);
            });
        alert(response.responseText);
        sessionStorage.setItem(idName, id);
        return id;
    } else {
        return id;
    }
}
