let regBlock = $(".registration");
let logBlock = $(".log");



$('input[type=radio][name=type_log]').change(function() {
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


$.postJSON = function(url, data, callback) {
    return jQuery.ajax({
        'type': 'POST',
        'url': url,
        'contentType': 'application/json',
        'data': data,
        'dataType': 'json',
        'success': callback
    });
};



$("#singIn").click(function () {
    let data = getFormData(regBlock);
    console.log(data);
      $.postJSON( "registration",  data , function(data ) {
      //toDO
    });
});

$("#LogIn").click(function () {

    let data = logBlock.serializeArray();
    console.log(data);
    $.postJSON( "login",  data, function(data ) {
       //toDO
    });
});



function getFormData($form){
    var unindexed_array = $form.serializeArray();
    var indexed_array = {};

    $.map(unindexed_array, function(n, i){
        indexed_array[n['name']] = n['value'];
    });

    return indexed_array;
}




