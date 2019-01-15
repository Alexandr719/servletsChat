let regBlock = $(".registration");
let logBlock = $(".log");

$('input[type=radio][name=type_log]').change(function () {
    if (this.value == 'reg') {
        logBlock.hide();
        regBlock.show();

    }
    else if (this.value == 'log') {
        regBlock.hide();
        logBlock.show();
    }
});


$("#singIn").click(function () {

        let data = getFormData(regBlock);
        $.postJSON("user", data, function (data) {
            user = data;
            if (user.id != null) {
                changeHash(user.id);
                showMain(user);
            } else {
                $("#error").text(data.cause);
            }

        }, function (e) {
            $("#error").text(e.message);
        });
    }
);
//todo rename
$("#LogIn").click(function () {
    let data = getFormData(logBlock);
    $.postJSON("user/session", data, function (data) {
        user = data;
        if (user.id != null) {
            $("#error").text("");
            changeHash(user.id);
            showMain(user);
        } else {
            $("#error").text(data.cause);
        }
    }, function (e) {
        $("#error").text(e.message);
    });
});








