let msgBlock = $("#message");

//Todo constants or auto href
let webSocket = new WebSocket('ws://localhost:8084//chat/user/message');
//let webSocket = new WebSocket('ws:epruryaw0818:8081//chat/user/message');
//let webSocket = new WebSocket('ws://1d60a975.ngrok.io//websocket');


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
    console.log(user);
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
webSocket.onclose = function (event) {
   console.log("Connection closed");
};

function onMessage(event) {
    $("#main_messages_list").append("<li>" + escapeHtml(event.data) + "</li>");
    scrollDown();

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