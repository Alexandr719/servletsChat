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
    //TOdo load
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

            let data = new Date(item.timeStamp);
           console.log(data);


            $("#main_messages_list").append("<li>"
                + escapeHtml(item.user.login
                + " : " + item.message) + "</li>"+
                "<p>" + formatedData + "</p>");
        });
        scrollDown();
    });
}

function fillUsers() {

    $.get("users", function (data) {
        $("#user_list").empty();
        let users = data;
        users.forEach(function (item) {
            $("#user_list").append("<li>" + escapeHtml(item.login) + "</li>");
        });
    });
}

function checkLogIn() {
    $.get("user/session", function (data) {
        if (data.id === null) {
            showRegistration();
        } else {
            user = JSON.parse(data);
            showMain(user);
            console.log(user);

        }
    })
}

function escapeHtml(string) {
    return String(string).replace(/[&<>"'`=\/]/g, function (s) {
        return entityMap[s];
    });
}

let timerUsers = setInterval(fillUsers, 60000);


function scrollDown() {
    $('.main_messages').scrollTop($('.main_messages')[0].scrollHeight);
}

function setError(e) {
    let response = $.parseHTML(e.responseText);
    let string = $(response).filter('p').text();
    let errorMessage = string.substring(string.indexOf("Message") + 7, string.indexOf("Description"));
    $("#error").text(errorMessage);
}
function formatDate(date) {
    let diff = new Date() - date; // разница в миллисекундах

    if (diff < 1000) { // прошло менее 1 секунды
        return 'только что';
    }

    let sec = Math.floor(diff / 1000); // округлить diff до секунд

    if (sec < 60) {
        return sec + ' сек. назад';
    }

    let min = Math.floor(diff / 60000); // округлить diff до минут
    if (min < 60) {
        return min + ' мин. назад';
    }

    // форматировать дату, с учетом того, что месяцы начинаются с 0
    let d = date;
    d = [
        '0' + d.getDate(),
        '0' + (d.getMonth() + 1),
        '' + d.getFullYear(),
        '0' + d.getHours(),
        '0' + d.getMinutes()
    ];

    for (let i = 0; i < d.length; i++) {
        d[i] = d[i].slice(-2);
    }

    return d.slice(0, 3).join('.') + ' ' + d.slice(3).join(':');
}