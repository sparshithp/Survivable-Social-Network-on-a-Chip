/**
 * UI LOGIC FOR MON PERFORMANCE PAGE
 */

function init() {

    var responseTxt;
    var getMsgTableContent;
    var realTestTime = 1;
    var isTestOnGoing = false;
    var isNotRunOutOfTime = true;
    var testStartTime;
    //var globalNumberOfRequest;
    var tmpTime;
    var isOutOfMemory;
    var alertText;


    var task;
    var processedPostCount = 0;
    var processedGetCount = 0;
    $("#postDiv").html(0);
    $("#getDiv").html(0);
    $("#totalPostDiv").html(0);
    $("#totalGetDiv").html(0);


    $("#spinner").hide();
    $("#alertBar").hide();

    $('#btn_start').click(function () {

        var testTime = $('#testTime').val();
        testTime = testTime * 1000;
        var testMsg = $('#testMsg').val();

        processedPostCount = 0;
        processedGetCount = 0;

        isTestOnGoing = true;
        isNotRunOutOfTime = true;
        isOutOfMemory = false;
        alertText = "";

        realTestTime = 1;
        tmpTime = 0;

        $("#alertBar").hide();

        testStartTime = new Date().getTime();

        $("#postDiv").html(0);
        $("#getDiv").html(0);
        $("#totalPostDiv").html(0);
        $("#totalGetDiv").html(0);
        $("#spinner").show();

        $.ajax({
            url: '/performance_setup',
            type: 'POST',
            dataType: 'text'

        }).done(function (data) {
            responseTxt = data;

            if (responseTxt = "TestMessageTableCreated") {

                task = setInterval(function () {

                    if (isTestOnGoing) {

                        tmpTime = new Date().getTime();
                        if (tmpTime - testStartTime >= testTime) {
                            isNotRunOutOfTime = false;
                            //isTestOnGoing = false;
                            console.log("=======OUT OF TEST TIME IsNotRunOutOfTime============     " + isNotRunOutOfTime);
                            alertText = "Test Time Is Over";
                        }

                        if (!isNotRunOutOfTime || isOutOfMemory) {
                            tmpTime = new Date().getTime();
                            realTestTime = tmpTime - testStartTime;
                            isTestOnGoing = false;
                            $("#spinner").hide();
                            $("#alertBar").html(alertText);
                            $("#alertBar").show();
                            $("#alertBar").fadeOut(10000);

                        }

                        $.ajax({
                            url: '/insert_test_msg',
                            type: 'POST',
                            //dataType: 'json',
                            data: JSON.stringify({ author: "Monitor", target: "All", content: testMsg, messageType: "WALL" }),
                            processData: false,
                            contentType: 'application/json'
                        }).done(function (data) {
                            responseTxt = data;

                            if (responseTxt === "Out Of Memory") {
                                console.log("==========OUT OF MEMORY======SET isTestOnGoing to false");
                                //isTestOnGoing = false;
                                isOutOfMemory = true;
                                alertText = "Out Of Memory In Backend";
                            }
                            else {
                                processedPostCount = processedPostCount + 1;
                                $("#totalPostDiv").html(processedPostCount);
                                //console.log("One message inserted" + "@@@@@@@@@ current post" + processedPostCount + "@@@@@@ calculated:" + ((processedPostCount / realTestTime) * 1000).toFixed(1));
                            }

                        });


                        $.ajax({
                            url: '/get_test_msg',
                            type: 'GET',
                            dataType: 'json'
                        }).done(function (data) {
                            getMsgTableContent = data;

                            if (getMsgTableContent) {
                                processedGetCount = processedGetCount + 1;
                                $("#totalGetDiv").html(processedGetCount);
                            }

                        });

                    }
                    else {
                        //停止并且更新数据
                        $("#getDiv").html(((processedGetCount / realTestTime) * 1000).toFixed(1));
                        $("#postDiv").html(((processedPostCount / realTestTime) * 1000).toFixed(1));
                        $("#spinner").hide();
                        $.ajax({
                            url: '/performance_teardown',
                            type: 'POST',
                            dataType: 'json'
                            //data: {name: name}
                        }).done(function (data) {
                            responseTxt = data;

                        });
                        clearInterval(task);
                    }

                }, 100);

            }

        });

    });

    $('#btn_stop').click(function () {
        $("#spinner").hide();
        if (isTestOnGoing === true) {
            isTestOnGoing = false;
            tmpTime = new Date().getTime();
            realTestTime = tmpTime - testStartTime;
        }
        else {
            realTestTime = realTestTime; //ugly place holder
        }

        $("#getDiv").html(((processedGetCount / realTestTime) * 1000).toFixed(1));
        $("#postDiv").html(((processedPostCount / realTestTime) * 1000).toFixed(1));

        $.ajax({
            url: '/performance_teardown',
            type: 'POST',
            dataType: 'json'
            //data: {name: name}
        }).done(function (data) {
            responseTxt = data;
        });

    });


}

$(document).on('ready', init);