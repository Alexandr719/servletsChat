let msgBlock = $("#message");
let webSocket = new WebSocket(getRelativeWSUrl());


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
webSocket.onclose = function (event) {
   console.log("Connection closed");
};

function onMessage(event) {
    console.log(event.data);
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

function getRelativeWSUrl() {
    const WEBSOCKET_PATH = "/user/message";
    let loc = window.location;
    let new_uri;
    if (loc.protocol === "https:") {
        new_uri = "wss:";
    } else {
        new_uri = "ws:";
    }
    new_uri += "//" + loc.host;
    new_uri+= loc.pathname + WEBSOCKET_PATH;
    return new_uri;

}