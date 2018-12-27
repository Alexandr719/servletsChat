$('.log_input').keyup(function() {
    let event = this;
    delay(function(){
       if(validate(event.name, event.value)){
           insertChacker(event,true);
       }

    }, 1000 );
});


let delay = (function(){
    let timer = 0;
    return function(callback, ms){
        clearTimeout (timer);
        timer = setTimeout(callback, ms);
    };
})();


function validate(inputName, value) {
    return true;
}
function insertChacker(event, coundition) {
    let checkMark = $(".checkMark_true").clone();

        event.append(checkMark);

    checkMark.show();
}