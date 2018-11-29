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






