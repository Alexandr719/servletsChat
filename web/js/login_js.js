let regBlock = $(".registration");
let logBlock = $(".log");


$('input[type=radio][name=type_log]').change(function () {
    if (this.value == 'reg') {
        console.log("Reg show");
        logBlock.hide();
        regBlock.show();

    }
    else if (this.value == 'log') {
        console.log("Log show");
        regBlock.hide();
        logBlock.show();
    }
});


$("#singIn").click(function () {

        let data = getFormData(regBlock);
        $.postJSON("registration", data, function (data) {
            user = data;
            changeHash(user.id);
            showMain(user);

        }, function (e) {
            $("#error").text(e.message);
        });
    }
);

$("#LogIn").click(function () {
    let data = getFormData(logBlock);
    $.postJSON("login", data, function (data) {
        user = data;
        changeHash(user.id);
        showMain(user);

    }, function (e) {
        $("#error").text(e.message);
    });
});








