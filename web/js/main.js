$.postJSON = function(url, data, callback, error) {
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


function getFormData($form){
    let unindexed_array = $form.serializeArray();
    let indexed_array = {};
    $.map(unindexed_array, function(n, i){
        indexed_array[n['name']] = n['value'];
    });
    return JSON.stringify(indexed_array);
}

function changeHash(id) {

    try {
        history.replaceState(null,null,'?id='+ id);
    }
    catch(e) {
        location.hash = '#id_'+id;
    }

}


function showMain(user) {
    $("#login_page").hide();
    $("#main_page").show();
    innerUserInfo(user);

}
function showRegistration() {
    $("#main_page").hide();
    $("#login_page").show();
}

$("#exit_button").click(function () {
    showRegistration();
    });

function innerUserInfo(user) {
    $("#main_page_login").text(user.login);


}