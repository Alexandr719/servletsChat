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
    if (event.id === "reg_login" && event.name === "login") {
        if (value.length > 0 && value.length < 20) {
            $.get("users", function (data) {
                let condition = true;
                $("#user_list").empty();
                data.forEach(function (item) {
                    if (item.login === value) {
                        condition = false;
                    }
                });
                insertChacker(event, condition);
            });
        } else {
            insertChacker(event, false);
        }
    }
    else if (event.name === "firstName") {
        if (value.length > 0 && value.length < 20) {
            insertChacker(event, true);
        } else {
            insertChacker(event, false);
        }
    } else if (event.name === "lastName") {
        if (value.length > 0 && value.length < 20) {
            insertChacker(event, true);
        } else {
            insertChacker(event, false);
        }
    } else if (event.name === "email") {
        if (value.length > 0 && value.length < 40 && value.includes("@")
            && value.includes(".")) {
            insertChacker(event, true);
        } else {
            insertChacker(event, false);
        }
    } else if (event.name === "password") {
        if (value.length > 0 && value.length < 40) {
            insertChacker(event, true);
        } else {
            insertChacker(event, false);
        }
    }


}

function insertChacker(event, coundition) {
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
