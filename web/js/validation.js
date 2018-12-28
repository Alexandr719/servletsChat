let checker_true = $(".checkMark_true").clone();
let checker_false = $(".checkMark_false").clone();


$('.log_input').keyup(function () {
    let event = this;
    delay(function () {

        validate(event, event.value);

    }, 500);
});

$('.log_input').bind("focusout", function () {
    let event = this;
    validate(event, event.value);
});


let delay = (function () {
    let timer = 0;
    return function (callback, ms) {
        clearTimeout(timer);
        timer = setTimeout(callback, ms);
    };
})();


function validate(event, value) {
    let valid_condition = false;
    if (event.id === "reg_login" && event.name === "login" && value.length > 0 && value.length < 20) {
        let dataValue = '{ "login": "' + value + '"}';
        $.postJSON("existlogin", dataValue, function (data) {
            insertChacker(event, data.condition);
            if (data.condition === true) {
                $("#error").text("");

            } else {
                $("#error").text(data.cause);

            }
        });
    } else if (event.name === "firstName" && value.length > 0 && value.length < 20) {
        valid_condition = true;
        insertChacker(event, valid_condition);
    } else if (event.name === "lastName" && value.length > 0 && value.length < 20) {
        valid_condition = true;
        insertChacker(event, valid_condition);
    } else if (event.name === "email" && value.length > 0 && value.length < 20 && value.includes("@") && value.includes(".")) {
        valid_condition = true;
        insertChacker(event, valid_condition);
    } else if (event.name === "password" && value.length > 0 && value.length < 20) {
        valid_condition = true;
        insertChacker(event, valid_condition);
    }


}

function insertChacker(event, coundition) {
    console.log(coundition);
    let chackMark = $(event).parent().find(".checkMark");
    chackMark.empty();
    if (coundition) {
        chackMark.append(checker_true.clone());
        checker_true.show();
    } else {
        chackMark.append(checker_false.clone());
        checker_false.show();
    }


}
