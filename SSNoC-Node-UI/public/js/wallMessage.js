/**
 * New node file
 */


function init()
{
	var serverBaseUrl = document.domain;
	var socket = io.connect(serverBaseUrl);
	

	var wallMessageList = $('#wallMessageList');
	var inputWallMessage = $('#inputWallMessage');
	var sendWallMessage = $('#sendWallMessage');
	
	
	//to build a wall message <li>
	var wallChatBlock1 = '<li class="media"><div class="media-body"><div class="media"><a class="pull-left" herf="#"><img src="/img/photo4.png", height=50/></a><div class="mediabody">';
	var wallChatBlock2 = '<br><br><small class="text-muted">';
	var wallChatBlock3 = '</small><hr></div></div></div></li>';
	
	
	function updatePublicMessages(publicMessages)
	{
		console.log(publicMessages);
		publicMessages.forEach(function(msgObject) {
			var wallChatBlock = wallChatBlock1 + msgObject.local.content + wallChatBlock2 + msgObject.local.author + ' | ' + msgObject.local.postedAt + wallChatBlock3;
			wallMessageList.prepend(wallChatBlock);
		});
	}
	
	var author;
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
		
		//get all wall messages
		$.ajax({
			url : '/wallMessage',
			type : 'GET',
			dataType : 'json'
		}).done(function(publicMessages){
			if(publicMessages != null)
			{
				updatePublicMessages(publicMessages);
			}
		}).fail(function(){
			console.log('fail to get wall messages');
		});
	});
	
	//receive the wall message broadcast from server
	socket.on('getWallMessage', function(msg){
		var chatBlock = wallChatBlock1 + msg.content + wallChatBlock2 + msg.author + ' | ' + msg.postedAt + wallChatBlock3;
		wallMessageList.append(chatBlock);
	});
	
	socket.on('privateChat', function(msg){
		var me = myName;
		if(msg.target == me)
			alert(msg.author + ' has sent you a message!');
	});
	
	/*socket.on('newConnection', function (data) {
		updateParticipants(data.participants);
	});*/
		
	//post wall message
	inputWallMessage.keydown(function(e){
		if(e.keyCode === 13)
		{
			//transfer the time
			var myDate = new Date();
			var year = myDate.getFullYear();
			var month = myDate.getMonth()+1;
			var date = myDate.getDate();
			var hours = myDate.getHours();
			var minutes = myDate.getMinutes();
			var seconds = myDate.getSeconds();
			if(month >= 1 && month <= 9)
				month = "0"+month;
			if(date >= 1 && date <= 9)
				date = "0"+date;
			if(hours >= 1 && hours <= 9)
				hours = "0"+hours;
			if(minutes >= 1 && minutes <= 9)
				minutes = "0"+minutes;
			if(seconds >= 1 && seconds <= 9)
				seconds = "0"+seconds;
			var postedAt = year+"-"+month+"-"+date+" "+hours+":"+minutes+":"+seconds;
			
			var wallMsg = {
				content : inputWallMessage.val(),
				author : author,
				postedAt : postedAt
			}
			socket.emit('sendWallMessage', wallMsg);
			inputWallMessage.val('');
			$.ajax({
				url : '/postOnWall',
				type : 'POST',
				data : wallMsg,
				dataType : 'json'
			}).done(function(){
				console.log('post on wall success');
			}).fail(function(){
				console.log('post on wall fail');
			});
		}
	});
	
	//post wall message
	sendWallMessage.click(function(){
		//transfer the time
		var myDate = new Date();
		var year = myDate.getFullYear();
		var month = myDate.getMonth()+1;
		var date = myDate.getDate();
		var hours = myDate.getHours();
		var minutes = myDate.getMinutes();
		var seconds = myDate.getSeconds();
		if(month >= 1 && month <= 9)
			month = "0"+month;
		if(date >= 1 && date <= 9)
			date = "0"+date;
		if(hours >= 1 && hours <= 9)
			hours = "0"+hours;
		if(minutes >= 1 && minutes <= 9)
			minutes = "0"+minutes;
		if(seconds >= 1 && seconds <= 9)
			seconds = "0"+seconds;
		var postedAt = year+"-"+month+"-"+date+" "+hours+":"+minutes+":"+seconds;
		
		var wallMsg = {
			content : inputWallMessage.val(),
			author : author,
			postedAt : postedAt
		}
		socket.emit('sendWallMessage', wallMsg);
		inputWallMessage.val('');
		
		$.ajax({
			url : '/postOnWall',
			type : 'POST',
			data : wallMsg,
			dataType : 'json'
		}).done(function(){
			console.log('post on wall success');
		}).fail(function(){
			console.log('post on wall fail');
		});
	});
	
}

$(document).on('ready', init);  