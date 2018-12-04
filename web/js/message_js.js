let msgBlock = $(".main_messages_field");


$("#message_sent").click(function () {
    let data = getFormData(msgBlock);
    console.log(data);
    $.postJSON( "message",  data , function(data ) {
        //toDO
    });
});

