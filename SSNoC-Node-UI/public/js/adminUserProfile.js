/**
 * New node file
 */
function init()
{
	var serverBaseUrl = document.domain;
	var socket = io.connect(serverBaseUrl);
	
	var userList = $('#userList');
	
	var uid = $('#uid');
	var uname = $('#uname');
	var pwd = $('#pwd');
	var acct = $('#acct');
	var pl = $('#pl');
	
	function updateUserList(users)
	{
		console.log(users);
		users.forEach(function(userObject) {
			console.log('@@@@@######@@@@@'+userObject.local.user_id);
			name = userObject.local.name;
			var user_id = userObject.local.user_id;
			var icon_ele = '<div class="col-xs-3 col-sm-2 col-md-1 col-lg-1"><a name="'+ user_id +'" class="editProfile" href="#profileModal" data-toggle="modal"><i class="glyphicon glyphicon glyphicon-pencil text-muted"></i></a></div>'
			var img_ele = '<div class="col-xs-3 col-sm-2 col-md-1 col-lg-1"><img src="/img/photo4.png" height=50/></div>';
			var name_ele = '<div class="col-xs-8 col-sm-9 col-md-10 col-lg-10"><strong>' + name + '</strong></div>';
			var info_ele = '<div class="row user-row search_item">' + img_ele + name_ele + icon_ele + '</div>';
			userList.prepend(info_ele);
		});
	}
	
	var myName;
	socket.on('connect', function(){
		//get username and connect
		sessionId = socket.socket.sessionid;
		$.ajax({
			url : 'user',
			type : 'GET',
			dataType : 'json'
		}).done(function(data){
			myName = data.name;
			//socket.emit('newUser', {id: sessionId, name: name});
		});
		//get user list
		$.ajax({
			url : '/getAdminUserProfileList',
			type : 'GET',
			dataType : 'json'
		}).done(function(users){
			if(users != null)
			{
				updateUserList(users);
			}
		}).fail(function(){
			console.log('fail to get user list!');
		});
		
	});
	
	socket.on('privateChat', function(msg){
		var me = myName;
		if(msg.target == me)
			alert(msg.author + ' has sent you a message!');
	});
	
	$("body").on("click", ".editProfile",function(){
		var id = $(this).attr('name');
		//alert('&&&*******'+id);
		$.ajax({
			url : '/getProfile',
			type : 'POST',
			data : {uid : id},
			dataType : 'json'
		}).done(function(profile){
			if(profile != null)
			{
				uid.val(profile.local.userId);
				uname.val(profile.local.userName);
				pwd.val("");
				acct.val(profile.local.accountStatus);
				pl.val(profile.local.privilegeLevel);
				//console.log(uname.val());
			}
			console.log(profile);
		}).fail(function(){
			console.log('fail get profile!');
		});
	});
	
	$('#SaveBtn').click(function(){
		var profile = {
			userId : uid.val(),
			userName : uname.val(),
			password : pwd.val(),
			accountStatus : acct.val(),
			privilegeLevel : pl.val()
		}
		$.ajax({
			url : '/postProfile',
			type : 'POST',
			data : profile,
			dataType : 'json'
		}).done(function(){
			console.log('update profile!');
		}).fail(function(){
			console.log('fail to update profile!');
		});
	});
	
//	$('#closeBtn').click(function(){
//		uname.val('');
//		pwd.val('');
//		acct.val('active');
//		pl.val('citizen');
//		alert(pwd.val() == '');
//	});
	
	/*
	socket.on('privateChat', function(msg){
		  var me = author;
		  if(msg.target == me && msg.author != target)
			  alert(msg.author + ' has sent you a message!');
	  });*/
	/*
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
	});*/
}

$(document).on('ready', init);