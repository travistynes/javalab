var app = {};

app.init = function() {
    // Test datepicker.
    $("#fld-teDate").val(moment().add(10, "days").format("YYYY-MM-DD"));
    $("#fld-teDate").datepicker({
        dateFormat: "yy-mm-dd",
        defaultDate: new Date(),
        autoClose: true,
        onSelect: function(dt, picker) {
            
        }
    });
    
    // QA datepicker.
    $("#fld-qaDate").val(moment().add(20, "days").format("YYYY-MM-DD"));
    $("#fld-qaDate").datepicker({
        dateFormat: "yy-mm-dd",
        defaultDate: new Date(),
        autoClose: true,
        onSelect: function(dt, picker) {
            
        }
    });
    
    // Prod datepicker.
    $("#fld-prDate").val(moment().add(30, "days").format("YYYY-MM-DD"));
    $("#fld-prDate").datepicker({
        dateFormat: "yy-mm-dd",
        defaultDate: new Date(),
        autoClose: true,
        onSelect: function(dt, picker) {
            
        }
    });
    
    // Panel title click handlers.
    $(".panelTitle").click(function() {
        $(this).parent().find(".panelBody").toggle();
    });
    
    // Welcome panel Next button.
    $("#welcomeNextButton").click(function() {
        $("#welcomeNextButton").hide();
        
        $("#welcomePanel .panelBody").slideUp(function() {
            $("#informationPanel").slideDown(function() {
                
            });
        });
    });
    
    // Submit request button handler.
    $("#submitButton").click(function() {
        app.submitRequest();
    });
    
    $("#fld-dbtype").change(app.dbtypeChange);
};

app.submitRequest = function() {
    var sb = $("#statusbar");
    
    var applicationName = $("#fld-applicationName").val().trim();
    var projectNumber = $("#fld-projectNumber").val().trim();
    var userRacf = $("#fld-racf").val().trim();
    var managerRacf = $("#fld-managerRacf").val().trim();
    var group = $("#fld-group").val().trim();
    var criticality = $("#fld-criticality").val();
    var external = $("#fld-external").is(":checked");
    var dr = $("#fld-dr").is(":checked");
    var databaseName = $("#fld-databaseName").val().trim();
    var dbtype = $("#fld-dbtype").val();
    var sourcecontrol = $("#fld-sourcecontrol").val();
    var usage = $("#fld-usage").val();
    var dataclass = $("#fld-dataclass").val();
    var usercount = $("#fld-usercount").val();
    var features = $("#fld-features").val();
    var growth = $("#fld-growth").val();
    var growthUnit = $("input[type='radio'][name='growth']:checked").val();
    var retention = $("#fld-retention").val();
    var retentionUnit = $("input[type='radio'][name='retention']:checked").val();
    var envte = $("#fld-envte").is(":checked");
    var envqa = $("#fld-envqa").is(":checked");
    var envpr = $("#fld-envpr").is(":checked");
    var teDate = $("#fld-teDate").val();
    var qaDate = $("#fld-qaDate").val();
    var prDate = $("#fld-prDate").val();
    var dbsizeT = $("#fld-dbsizeT").val();
    var sizeUnitT = $("input[type='radio'][name='sizete']:checked").val();
    var dbsizeQ = $("#fld-dbsizeQ").val();
    var sizeUnitQ = $("input[type='radio'][name='sizeqa']:checked").val();
    var dbsizeP = $("#fld-dbsizeP").val();
    var sizeUnitP = $("input[type='radio'][name='sizepr']:checked").val();
    
    // Validate input.
    if(true) {
        if(applicationName.length === 0) {
            alert("Please provide the application name.");
            return;
        } else if(projectNumber.length === 0) {
            alert("Please provide the project number.");
            return;
        } else if(userRacf.length === 0) {
            alert("Please provide your RACF.");
            return;
        } else if(managerRacf.length === 0) {
            alert("Please provide your manager's RACF.");
            return;
        } else if(group.length === 0) {
            alert("Please provide the name of your group.");
            return;
        } else if(databaseName.length === 0) {
            alert("Please give your database a name.");
            return;
        }
        
        if(!envte && !envqa && !envpr) {
            alert("Please select at least one environment.");
            return;
        }
    }
    
    sb.text("Submitting request...");
    sb.show();
    
    $("#submitButton").hide();
    $("#submitLoading").show();
    
    $.ajax({
        method: "POST",
        url: "dbrequest",
        timeout: 15000,
        data: {
            ts: new Date().getTime(),
            applicationName: applicationName,
            projectNumber: projectNumber,
            userRacf: userRacf,
            managerRacf: managerRacf,
            group: group,
            criticality: criticality,
            external: external,
            dr: dr,
            databaseName: databaseName,
            dbtype: dbtype,
            sourcecontrol: sourcecontrol,
            usage: usage,
            dataclass: dataclass,
            usercount: usercount,
            features: features,
            growth: growth,
            growthUnit: growthUnit,
            retention: retention,
            retentionUnit: retentionUnit,
            envte: envte,
            envqa: envqa,
            envpr: envpr,
            teDate: teDate,
            qaDate: qaDate,
            prDate: prDate,
            dbsizeT: dbsizeT,
            sizeUnitT: sizeUnitT,
            dbsizeQ: dbsizeQ,
            sizeUnitQ: sizeUnitQ,
            dbsizeP: dbsizeP,
            sizeUnitP: sizeUnitP
        },
        success: function(data, textStatus, jqXHR) {
            sb.text("Your request has been submitted. You can close this page. Thank you.");
        },
        error: function(jqXHR, textStatus, errorThrown) {
            sb.text("There was an error submitting your request.");
        },
        complete: function(jqXHR, textStatus) {
            $("#submitLoading").hide();
            $("#submitButton").show();
        }
    });
};

app.dbtypeChange = function() {
    var dbtype = $("#fld-dbtype").val();
    
    if(dbtype === "Teradata") {
        $("#fld-dr").prop("checked", false); $("#fld-dr").prop("disabled", true);
        $("#fld-usage").val("DSS"); $("#fld-usage").prop("disabled", true);
        $("#fld-sourcecontrol").val("PVCS"); $("#fld-sourcecontrol").prop("disabled", true);
        $("#envMsg").text("Select the environments you need.");
    } else {
        $("#fld-dr").prop("disabled", false);
        $("#fld-usage").prop("disabled", false);
        $("#fld-sourcecontrol").prop("disabled", false);
        $("#envMsg").text("Select the environments you need and the date you need them by. Allow us at least 10 days to provide each environment.");
    }
};

/*
This function isn't used at present. It is useful to get the query parameter values in the url.
For example, if the url in the browser is http://www.example.com/test?value1=one&value2=two
Then calling app.getParameterByName("value2") will return the value "two".
*/
app.getParameterByName = function(name) {
    var match = RegExp('[?&]' + name + '=([^&]*)').exec(window.location.search);
    return match && decodeURIComponent(match[1].replace(/\+/g, ' '));
};

app.init();