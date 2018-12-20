let user = null;
let entityMap = {
    '&': '&amp;',
    '<': '&lt;',
    '>': '&gt;',
    '"': '&quot;',
    "'": '&#39;',
    '/': '&#x2F;',
    '`': '&#x60;',
    '=': '&#x3D;'
};
$.postJSON = function (url, data, callback, error) {
    return jQuery.ajax({
        'type': 'POST',
        'url': url,
        'contentType': 'application/json',
        'data': data,
        'dataType': 'json',
        'success': callback,
        'error': error
    });
};

checkLogIn();

$("#exit_button").click(function () {
    $.postJSON("exit", null, function (data) {
        console.log("Click exit!!!");
        showRegistration();
    }, function (e) {
        alert("Error with exit");
    });
});


function getFormData($form) {
    let unindexed_array = $form.serializeArray();
    let indexed_array = {};
    $.map(unindexed_array, function (n, i) {
        indexed_array[n['name']] = n['value'];
    });
    return JSON.stringify(indexed_array);
}

function changeHash(id) {

    try {
        history.replaceState(null, null, '?id=' + id);
    }
    catch (e) {
        location.hash = '#id_' + id;
    }

}


function showMain(user) {
    fillMessages();
    fillUsers();
    $("#login_page").hide();
    $("#main_page").show();
    innerUserInfo(user);

}

function showRegistration() {
    $("#main_page").hide();
    $("#login_page").show();
}


function innerUserInfo(user) {
    $("#main_page_login").text(user.login);
}

function fillMessages() {
    $.postJSON("getmessages", null, function (data) {
        $("#main_messages_list").empty();
        data.forEach(function (item) {
            $("#main_messages_list").append("<li>" + escapeHtml(item.user.login
                + " : " + item.message) + "</li>");
        })
    }, function (e) {
        alert("Error with messages");
    });
}

function fillUsers() {
    $.postJSON("getusers", null, function (data) {
        $("#user_list").empty();
        data.forEach(function (item) {
            $("#user_list").append("<li>" + escapeHtml(item.login) + "</li>");
        });
    }, function (e) {
        alert("Error with users");
    });
}

function checkLogIn() {
    $.postJSON("checklog", null, function (data) {
        user = data;
        showMain(user)
    }, function (e) {
        showRegistration(user);
    });
}


function escapeHtml(string) {
    return String(string).replace(/[&<>"'`=\/]/g, function (s) {
        return entityMap[s];
    });
}
let timerUsers = setInterval(fillUsers, 60000);
let timerMessages = setInterval(fillMessages, 60000);