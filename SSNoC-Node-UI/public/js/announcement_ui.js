/**
 * UI LOGIC FOR POSTING ANNOUNCEMENT
 */

  function init() {

	var serverBaseUrl = document.domain;
	var socket = io.connect(serverBaseUrl);
	
    var responseTxt;
    var responseBody;
    var announce_title;
    var announce_content;

    var myName;
    var myUserId;
    socket.on('connect', function(){
		//get username and connect
		sessionId = socket.socket.sessionid;
		$.ajax({
			url : 'user',
			type : 'GET',
			dataType : 'json'
		}).done(function(data){
			myName = data.name;
			myUserId = data.userId;
			
			//get the profile to check the previlege and account status
			$.ajax({
				url : '/getProfile',
				type : 'POST',
				data : {uid : myUserId},
				datatype : 'json'
			}).done(function(profile){
				if(profile.local.privilegeLevel != 'coordinator'){
					$('#divTitle').hide();
					$('#divContent').hide();
					$('#divSubmit').hide();
				}
			});
			//socket.emit('newUser', {id: sessionId, name: name});
		});		
	});
    
    
    //get method to get data of all announcement. and refresh the page
    getAllAnnouncementsAndRefresh();

    $('#btn_submit').click(function() {
        announce_title = $('#title').val() ;
        announce_content = $('#content').val();
        var author = "Coordinator";

        if(announce_title != "" && announce_title != null && announce_title != undefined){
            //POST the data to data base;
            $.ajax({
                url:  '/insert_announcement',
                type: 'POST',
                //dataType: 'json',
                data: JSON.stringify({ title: announce_title, content: announce_content, author:author }),
                processData:false,
                contentType: 'application/json'
            }).done(function(data){
                responseBody = data;
                //call the method to refresh the page.
                getAllAnnouncementsAndRefresh();
            });
        }

    });

    function getAllAnnouncementsAndRefresh(){
        $.ajax({
            url:  '/announcement/getall',
            type: 'GET',
            dataType: 'json'
        }).done(function(data){
            responseBody = data;
            if(responseBody != "" && responseBody != null && responseBody != undefined){

//                //ONE POST
               $("#resultContainer").remove();
               var postBodyParent = $('<div class="panel panel-primary" id = "resultContainer"></div>');
               var postBodyHeader = $('<div class="panel-heading">Announcement</div>');
               postBodyParent.append(postBodyHeader);

                responseBody.forEach(function(announceObj, index){
                    var collaspeClass = "";
                    if(index<1){
                        collaspeClass = "panel-collapse collapse in";
                    }
                    else{
                        collaspeClass = "panel-collapse collapse";
                    }
                    var postBody = $('<div class="panel-group" id="accordion"></div>');
                    var hrefValue = "#collapse"+index;
                    var collapseValue = "collapse"+index;

//                    var postBody1 = '<div class="panel panel-default"><div class="panel-heading"><h4 class="panel-title"><a data-toggle="collapse" data-parent="#accordion" ' +
//                        'href="#collapse'+ index + '”>'+ announceObj.title + ' - Posted By: ' + announceObj.author +  ' On: ' + announceObj.postAt +'</a></h4></div><div id="collapse'+ index +'" class="' + collaspeClass + '">' +
//                        '<div class="panel-body">'+ announceObj.content+'</div></div>';

                    var postSubBody = '<div class="panel panel-default"><div class="panel-heading"><h4 class="panel-title"><a data-toggle="collapse" data-parent="#accordion" href="'+hrefValue+'">'+ announceObj.title + '<span class="text-success"> - Posted By: ' + announceObj.author +  ' On: ' + announceObj.postAt + '</span></a></h4></div><div id="'+collapseValue+'" class="'+ collaspeClass + '"><div class="panel-body">' + announceObj.content + '</div></div></div>'

                    postBody.html(postSubBody);
                    postBodyParent.append(postBody);

                });
                $("#placeholder").append(postBodyParent);


            }
        });
    }

	}

	$(document).on('ready', init);  