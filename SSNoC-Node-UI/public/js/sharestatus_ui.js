/**
 * UI LOGIC FOR SHARE STATUS PAGE
 */

  function init() {
//	  function join() {
//	    var name = $('#name').val();
//	    $.ajax({
//	      url:  '/join',
//	      type: 'POST',
//	      dataType: 'json',
//	      data: {name: name}
//	    }).done(function(data){
//	      location.href="http://localhost:7777/";
//	    });
//	  }
/*
	  var serverBaseUrl = document.domain;
	  var socket = io.connect(serverBaseUrl);
	  socket.on('connect', function(){
			//get username and connect
			sessionId = socket.socket.sessionid;
			$.ajax({
				url : 'user',
				type : 'GET',
				dataType : 'json'
			}).done(function(data){
				author = data.name;
				socket.emit('newUser', {id: sessionId, name: name});
			});
	  });
	  */
	  var myName;
	  socket.on('connect', function(){
			//get username and connect
			sessionId = socket.socket.sessionid;
			$.ajax({
				url : 'user',
				type : 'GET',
				dataType : 'json'
			}).done(function(data){
				author = data.name;
				myName = data.name;
				//socket.emit('newUser', {id: sessionId, name: name});
			});
	  });
	  
	  socket.on('privateChat', function(msg){
			var me = myName;
			if(msg.target == me)
				alert(msg.author + ' has sent you a message!');
		});
	  
	  function validateLocationDesc() {
//		  if($('#locationDesc').val().trim() == "") {
//		    $('#alert').text('Please provide a username to continue');
//		    $('#alert').show();
//		    $('#passport_alert').remove();
//		    return false;
			alert("should validateLocationDesc");
	  }
	  
	  $('#submitStatusBtn').click(function() {
//	    if( !validateLocationDescr() ) {
//	      return false;
//	    }
		//validateLocationDesc();  
	    return true;
	  });

	  //$('#alert').hide();
	}

	$(document).on('ready', init);  