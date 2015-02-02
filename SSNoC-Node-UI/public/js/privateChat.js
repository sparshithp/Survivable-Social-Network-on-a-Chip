/**
 * New node file
 */
function init()
{
	var serverBaseUrl = document.domain;
	var socket = io.connect(serverBaseUrl);
	
	var inputPrivateMessage = $('#inputPrivateMessage');
	var sendPrivateMessage = $('#sendPrivateMessage');
	var privateMessageList = $('#privateMessageList');
	var targetName = $('#targetName');
	var authorName = $('#authorName');
	
	//to build a private message <li>
	var privateChatBlockSelf1 = '<li class="media"><div class="media-body"><div class="media"><a class="pull-right" herf="#"><img src="/img/photo4.png", height=50/></a><div class="mediabody"><p>';
	var privateChatBlockSelf2 = '</p><br><br><small class="text-muted pull-right">';
	var privateChatBlockSelf3 = '</small><hr></div></div></div></li>';
	
	var privateChatBlockOther1 = '<li class="media"><div class="media-body"><div class="media"><a class="pull-left" herf="#"><img src="/img/photo4.png", height=50/></a><div class="mediabody"><p>';
	var privateChatBlockOther2 = '</p><br><br><small class="text-muted pull-right">';
	var privateChatBlockOther3 = '</small><hr></div></div></div></li>';
	
	function updatePrivateMessages(privateMessages)
	{
		console.log(privateMessages);
		privateMessages.forEach(function(msgObject) {
			if(msgObject.local.author == author)
			{
				var privateChatBlockSelf = privateChatBlockSelf1 + msgObject.local.content + privateChatBlockSelf2 + msgObject.local.author + ' | ' + msgObject.local.postedAt + privateChatBlockSelf3;
				privateMessageList.prepend(privateChatBlockSelf);
			}
			else if(msgObject.local.author == target)
			{
				var privateChatBlockOther = privateChatBlockOther1 + msgObject.local.content + privateChatBlockOther2 + msgObject.local.author + ' | ' + msgObject.local.postedAt + privateChatBlockOther3;
				privateMessageList.prepend(privateChatBlockOther);
			}
		});
	}
	
	
	var author = authorName.val();
	var target = targetName.val();
	
	socket.on('connect', function(){
		//get username and connect
		sessionId = socket.socket.sessionid;
		$.ajax({
			url : 'user',
			type : 'GET',
			dataType : 'json'
		}).done(function(data){
			author = data.name;
			//socket.emit('newUser', {id: sessionId, name: name});
		});
		//get all private messages between the two user
		var chatter = {author : author, target : target};
		$.ajax({
			url : '/getPrivateMessage',
			type : 'POST',
			data : chatter,
			dataType : 'json'
		}).done(function(privateMessages){
			if(privateMessages != null)
			{
				updatePrivateMessages(privateMessages);
			}
		}).fail(function(){
			console.log('fail to get wall messages');
		});
		
	});
	
	//receive the private message broadcast from server
	var authorSelf = author+'self';
	console.log(authorSelf);
	console.log(author);
	socket.on(authorSelf, function(msg){
		var privateChatBlockSelf = privateChatBlockSelf1+msg.content + privateChatBlockSelf2 + msg.author + ' | ' + msg.postedAt + privateChatBlockSelf3;
		privateMessageList.append(privateChatBlockSelf);
		console.log('self called!!!');
	});
	
	socket.on(author, function(msg){
		if(msg.author == target)
		{
			var privateChatBlockOther = privateChatBlockOther1+msg.content + privateChatBlockOther2 + msg.author + ' | ' + msg.postedAt + privateChatBlockOther3;
			privateMessageList.append(privateChatBlockOther);
		}
		else
			alert(msg.author+' has sent you a message!');
		console.log('other called!!!');
	});
	/*
	socket.on('privateChat', function(msg){
		  var me = author;
		  if(msg.target == me && msg.author != target)
			  alert(msg.author + ' has sent you a message!');
	  });*/
	
	//post private message
	sendPrivateMessage.click(function(){
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
		var privateMsg = {
			content : inputPrivateMessage.val(),
			author : author,
			target : target,
			postedAt : postedAt
		}
		socket.emit('sendPrivateMessage', privateMsg);
		inputPrivateMessage.val('');
		$.ajax({
			url : '/postPrivateMessage',
			type : 'POST',
			data : privateMsg,
			dataType : 'json'
		}).done(function(){
			console.log('post private message success');
		}).fail(function(){
			console.log('post private message fail');
		});
	});
	
	//post private message
	inputPrivateMessage.keydown(function(e){
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
		if(e.keyCode === 13)
		{
			var privateMsg = {
				content : inputPrivateMessage.val(),
				author : author,
				target : target,
				postedAt : postedAt
			}
			socket.emit('sendPrivateMessage', privateMsg);
			inputPrivateMessage.val('');
			console.log('message from ' + privateMsg.author + " to " + privateMsg.target);
			$.ajax({
				url : '/postPrivateMessage',
				type : 'POST',
				data : privateMsg,
				dataType : 'json'
			}).done(function(){
				console.log('post private message success');
			}).fail(function(){
				console.log('post private message fail');
			});
		}
	});
}

$(document).on('ready', init);