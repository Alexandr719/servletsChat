let msgBlock = $("#message");
let webSocket = new WebSocket(getRelativeWSUrl());
const MAX_MESSAGE_LENGTH = 80;

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
    message.timeStamp = new Date();
    message.message = msgBlock.val();
    if (user != null && message.message.length > 0
        && message.message.length <= MAX_MESSAGE_LENGTH) {
        webSocket.send(JSON.stringify(message));
        msgBlock.val("");
    }
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
    let message = JSON.parse(event.data);
    fullMessage(message);
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
    new_uri += loc.pathname + WEBSOCKET_PATH;
    return new_uri;

}