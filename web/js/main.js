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
$.doDelete = function (url, data, callback, error) {
    return jQuery.ajax({
        'type': 'DELETE',
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
    $.doDelete("user/session", null, function (data) {
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
    $.get("users/messages", function (data) {
        $("#main_messages_list").empty();
        data.forEach(function (item) {
            $("#main_messages_list").append("<li>" + escapeHtml(item.user.login
                + " : " + item.message) + "</li>");
        })
    });
}

function fillUsers() {

    $.get("users",  function (data) {
        console.log("dsadasd");
        $("#user_list").empty();
        data.forEach(function (item) {
            $("#user_list").append("<li>" + escapeHtml(item.login) + "</li>");
        });
    });
}

function checkLogIn() {
    $.get("user/session", function (data) {
        user = data;
        if (user.id === null) {
            showRegistration(user);
        } else {
            showMain(user)
        }
    })


}




function escapeHtml(string) {
    return String(string).replace(/[&<>"'`=\/]/g, function (s) {
        return entityMap[s];
    });
}
let timerUsers = setInterval(fillUsers, 60000);
