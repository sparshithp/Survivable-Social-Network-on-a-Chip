/**
 * New node file
 */
function init()
{
	var serverBaseUrl = document.domain;
	var socket = io.connect(serverBaseUrl);
	
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
				if(profile.local.privilegeLevel == 'citizen'){
					$('#monperformance').hide();
					$('#monmemory').hide();
					$('#adminUserProfile').hide();
					$('#analysis').hide();
				  }
				if(profile.local.privilegeLevel == 'coordinator'){
					$('#monperformance').hide();
					$('#monmemory').hide();
					$('#adminUserProfile').hide();
				}
				if(profile.local.privilegeLevel == 'monitor'){
					$('#adminUserProfile').hide();
					$('#analysis').hide();
				}
				if(profile.local.privilegeLevel == 'administrator'){
					$('#monperformance').hide();
					$('#monmemory').hide();
					$('#analysis').hide();
				}
			});
			//socket.emit('newUser', {id: sessionId, name: name});
		});		
	});
	
	socket.on('privateChat', function(msg){
		var me = myName;
		if(msg.target == me)
			alert(msg.author + ' has sent you a message!');
	});
	
	socket.on('youHaveACall', function(data){
		var me = myName;
		if(data.receiver == me){
			var conf = confirm(data.sender + ' is calling you! Accept?');
			if(conf == true){
				socket.emit('IAccept', data);
				location.href='/makeCall';
			}
			else{
				socket.emit('IReject', data);
			}
		}
	});
	
	$('#logout').click(function(){
		socket.emit("userDisconnect", myUserId);
	});
}

$(document).on('ready', init);