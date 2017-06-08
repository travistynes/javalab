var app = angular.module("app", []);

app.controller("main", function($scope) {
    // Footer text.
    $scope.footer = "Javalab Angular Event & Data Binding - " + moment().format("YYYY");
    
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
    
    $scope.welcomeNextButton_click = function() {
        $("#welcomeNextButton").hide();
        
        $("#welcomePanel .panelBody").slideUp(function() {
            $("#informationPanel").slideDown(function() {
                
            });
        });
    };
    
    // Select (drop down) lists.
    $scope.criticalities = [
        "None",
        "Transportation (Low)",
        "Transportation (Medium)",
        "Transportation (High)",
        "Transportation (Critical)",
        "Infrastructure (Low)",
        "Infrastructure (Medium)",
        "Infrastructure (High)",
        "Infrastructure (Critical)"
    ];
    
    $scope.dbtypes = [
        "Microsoft SQL 2008",
        "Microsoft SQL 2014",
        "Microsoft SQL 2016",
        "Oracle",
        "Teradata",
        "DB2 LUW (AIX)",
        "DB2zOS",
        "MySQL"
    ];
    
    $scope.usages = [
        "OLTP",
        "DSS",
        "Mixed"
    ];
    
    $scope.sourcecontrols = [
        "TFS",
        "PVCS",
        "Changeman",
        "None"
    ];
    
    $scope.dataclasses = [
        "Public",
        "Restricted",
        "Confidential"
    ];
    
    $scope.usercounts = [
        "0-100",
        "100-300",
        "300+"
    ];
    
    $scope.featurelist = [
        "None",
        "Purescale",
        "BLU",
        "Partitioning",
        "Spacial (licensed)",
        "Other"
    ];
    
    // Set initial values.
    $scope.applicationName = "";
    $scope.projectNumber = "";
    $scope.racf = "";
    $scope.managerRacf = "";
    $scope.group = "";
    $scope.databaseName = "";
    $scope.growth = 1;
    $scope.growthUnit = "MB";
    $scope.retention = 1;
    $scope.retentionUnit = "Month";
    $scope.envte = false;
    $scope.envqa = false;
    $scope.envpr = false;
    $scope.dbsizeT = 1;
    $scope.dbsizeQ = 1;
    $scope.dbsizeP = 1;
    $scope.sizete = "MB";
    $scope.sizeqa = "MB";
    $scope.sizepr = "MB";
    
    $scope.submitRequest = function() {
        var sb = $("#statusbar");
        
        var teDate = $("#fld-teDate").val();
        var qaDate = $("#fld-qaDate").val();
        var prDate = $("#fld-prDate").val();

        // Validate input.
        if(true) {
            if($scope.applicationName.length === 0) {
                alert("Please provide the application name.");
                return;
            } else if($scope.projectNumber.length === 0) {
                alert("Please provide the project number.");
                return;
            } else if($scope.racf.length === 0) {
                alert("Please provide your RACF.");
                return;
            } else if($scope.managerRacf.length === 0) {
                alert("Please provide your manager's RACF.");
                return;
            } else if($scope.group.length === 0) {
                alert("Please provide the name of your group.");
                return;
            } else if($scope.databaseName.length === 0) {
                alert("Please give your database a name.");
                return;
            }

            if(!$scope.envte && !$scope.envqa && !$scope.envpr) {
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
                applicationName: $scope.applicationName,
                projectNumber: $scope.projectNumber,
                racf: $scope.racf,
                managerRacf: $scope.managerRacf,
                group: $scope.group,
                criticality: $scope.criticality,
                external: $scope.external,
                dr: $scope.dr,
                databaseName: $scope.databaseName,
                dbtype: $scope.dbtype,
                sourcecontrol: $scope.sourcecontrol,
                usage: $scope.usage,
                dataclass: $scope.dataclass,
                usercount: $scope.usercount,
                features: $scope.features,
                growth: $scope.growth,
                growthUnit: $scope.growthUnit,
                retention: $scope.retention,
                retentionUnit: $scope.retentionUnit,
                envte: $scope.envte,
                envqa: $scope.envqa,
                envpr: $scope.envpr,
                teDate: teDate,
                qaDate: qaDate,
                prDate: prDate,
                dbsizeT: $scope.dbsizeT,
                sizeUnitT: $scope.sizeUnitT,
                dbsizeQ: $scope.dbsizeQ,
                sizeUnitQ: $scope.sizeUnitQ,
                dbsizeP: $scope.dbsizeP,
                sizeUnitP: $scope.sizeUnitP
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

    $scope.dbtypeChange = function() {
        //var dbtype = $("#fld-dbtype").val();
        
        if($scope.dbtype === "Teradata") {
            $scope.dr = false; $("#fld-dr").prop("disabled", true);
            $scope.usage = "DSS"; $("#fld-usage").prop("disabled", true);
            $scope.sourcecontrol = "PVCS"; $("#fld-sourcecontrol").prop("disabled", true);
            $("#envMsg").text("Select the environments you need.");
        } else {
            $("#fld-dr").prop("disabled", false);
            $("#fld-usage").prop("disabled", false);
            $("#fld-sourcecontrol").prop("disabled", false);
            $("#envMsg").text("Select the environments you need and the date you need them by. Allow us at least 10 days to provide each environment.");
        }
    };
});
