var app = {};

app.init = function() {
    var now = moment();
    
    $("#footer").html("Javalab Web Application - " + now.format("YYYY"));
};

app.init();