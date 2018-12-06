let msgBlock = $("#message");


$("#message_sent").click(function () {
    sendMessage();
});

$("#message").keydown(function (event) {
    if (event.keyCode == 13) {
        sendMessage();
    }
});


function sendMessage() {
    webSocket.send(msgBlock.val());
    msgBlock.val("");
}

let webSocket = new WebSocket('ws://localhost:8081//websocket');

webSocket.onerror = function (event) {
    onError(event)
};

webSocket.onopen = function (event) {
    onOpen(event)
};

webSocket.onmessage = function (event) {
    onMessage(event)
};

function onMessage(event) {
    $("#main_messages_list").append("<li>" + event.data + "</li>");
    console.log("Event data" + event.data);
}

function onOpen(event) {
    console.log("Now Connection established");

}

function onError(event) {
    alert(event.data);
}


$(document).ready(function () {
    $(window).keydown(function (event) {
        if (event.keyCode == 13) {
            event.preventDefault();
            return false;
        }
    });
});