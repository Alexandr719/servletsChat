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



$("#singIn").click(function () {
      $.post( "registration", {data: regBlock.serialize() }, function(data ) {
        $( ".result" ).html( data );
    });
});

$("#LogIn").click(function () {

    let data = getFormData(logBlock);

    $.post( "login",  data, function(data ) {
        $( ".result" ).html( data );
    },"json");
});



function getFormData($form){
    var unindexed_array = $form.serializeArray();
    var indexed_array = {};

    $.map(unindexed_array, function(n, i){
        indexed_array[n['name']] = n['value'];
    });

    return indexed_array;
}




