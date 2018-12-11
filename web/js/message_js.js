let msgBlock = $("#message");
//let webSocket = new WebSocket('ws://localhost:8081//websocket');
 let webSocket = new WebSocket('ws:epruryaw0818:8081//websocket');

$("#message_sent").click(function () {
    sendMessage();
});

$("#message").keydown(function (event) {
    if (event.keyCode == 13) {
        sendMessage();
    }
});


function sendMessage() {
    let message = {};
    message.user = user;
    message.message = msgBlock.val();
    webSocket.send(JSON.stringify(message));
    msgBlock.val("");
}




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