var app = {};

app.init = function() {
    var now = moment();
    
    $("#footer").html("Norfolk Southern " + now.format("YYYY"));
};

app.init();