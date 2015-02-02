function init() {
  var serverBaseUrl = document.domain;

  var socket = io.connect(serverBaseUrl);

  var sessionId = '';

  window.my_name = '';

  function updateParticipants(participants) {


    $('#participants_online').html('');
    $('#participants_offline').html('');
    var map = {};
    var userName = '';
    var userEle = '';
    for (var sId in participants.online){
      userName = participants.online[sId].userName;
      if (map[userName] == undefined || map[userName] !== sessionId){
        map[userName] = {sId:sId};
      }
    }
    keys = Object.keys(map);
    keys.sort();

    for (var i = 0; i < keys.length; i++) {
      var name = keys[i];
      var img_ele = '<img src="/img/photo4.png" height=40/>';
      var photo_ele = '<div class="col-xs-3 col-sm-2 col-md-1 col-lg-1"><img src="/img/green-dot.png" height=10/><br/>'+img_ele + '</div>';
      
      var statusTxt = "people on linestatus";
      participants.all.forEach(function(userObj) {
    	  
    	  if(userObj.userName == name){

              if(userObj.userStatusCode == "YELLOW"){
                  statusTxt = "<br><img class='img-circle' src='/img/sts_help.png' height=20/> Need Help";
              }
              else if(userObj.userStatusCode == "RED"){
                  statusTxt = "<br><img class='img-circle' src='/img/sts_danger.png' height=20/> Facing Threatening Emergency";
              }
              else{
                  statusTxt = "<br><img class='img-circle' src='/img/sts_ok.png' height=20/> I Am OK";
              }

    		  statusTxt += ". <br>LocationInfo: " + userObj.userLocationDesc + "<br>LastUpdatedTime: " + userObj.userLastPostTime ;
    	  } 	  	  

      });   
      
      var name_ele = '<div class="col-xs-8 col-sm-9 col-md-10 col-lg-10"><strong>' + name + "</strong><br>" + statusTxt + '</div>';
      
      var dropdown_symbol = map[name].sId === sessionId ? '':'<i class="glyphicon glyphicon-chevron-down text-muted"></i>';
      var dropdown_ele = '<div class="col-xs-1 col-sm-1 col-md-1 col-lg-1 dropdown-user" data-for=".' + name + '">' + dropdown_symbol + '</div>';
      var info_ele = '<div class="row user-row search_item">' + photo_ele + name_ele + dropdown_ele + '</div>';
      var detail_ele = '<div class="row user-info ' + name + '"><button type="button" name="'+name+'" class="submitButton chat btn btn-info col-xs-6 col-sm-6 col-md-6 col-lg-6 col-xs-offset-3 col-sm-offset-3 col-md-offset-3 col-lg-offset-3">chat</button><button type="button" name="'+name+'" class="submitButton videoCall btn btn-info col-xs-6 col-sm-6 col-md-6 col-lg-6 col-xs-offset-3 col-sm-offset-3 col-md-offset-3 col-lg-offset-3">video call</button><hr/></div></div>';
      if (map[name].sId === sessionId || name === my_name) {
      } else {
        $('#participants_online').append(info_ele);
        $('#participants_online').append(detail_ele);
      }
    }

    var map2={};
    var keys2=[];
    participants.all.forEach(function(userObj) {
     
    	if (map[userObj.userName] == undefined) {
    		map2[userObj.userName] = 1;
    	}
    		keys2 = Object.keys(map2);
    		keys2.sort();
    });
    
    participants.all.forEach(function(userObj) {
    	for (var i=0; i<keys2.length; i++){
        	var name = keys2[i];
        	if (userObj.userName == name){
        var img_ele = '<img class="img-circle" src="/img/photo4.png" height=40/>';
        var photo_ele = '<div class="offline col-xs-3 col-sm-2 col-md-1 col-lg-1"><img src="/img/grey-dot.png" height=10/><br/>'+img_ele + '</div>';
        
        var statusTxt = "";
              console.log("############### userStatusCode" + userObj.userStatusCode + " from ui people.js");
                if(userObj.userStatusCode == "YELLOW"){
                    statusTxt = "<br><img class='img-circle' src='/img/sts_help.png' height=20/> Need Help";
                }
                else if(userObj.userStatusCode == "RED"){
                    statusTxt = "<br><img class='img-circle' src='/img/sts_danger.png' height=20/> Facing Threatening Emergency";
                }
                else{
                    statusTxt = "<br><img class='img-circle' src='/img/sts_ok.png' height=20/> I Am OK";
                }
        statusTxt += ". <br>LocationInfo: " + userObj.userLocationDesc + "<br>LastUpdatedTime: " + userObj.userLastPostTime;
        
        var name_ele = '<div class="offline col-xs-8 col-sm-9 col-md-10 col-lg-10"><strong>' + userObj.userName + "</strong><br>" + statusTxt + '<br/></div>';
        var dropdown_ele = '<div class="col-xs-1 col-sm-1 col-md-1 col-lg-1 dropdown-user" data-for=".' + userObj.userName + '"><i class="glyphicon glyphicon-chevron-down text-muted"></i></div>';
        var info_ele = '<div class="row user-row search_item">' + photo_ele + name_ele + dropdown_ele + '</div>';
        var detail_ele = '<div class="row user-info ' + userObj.userName + '"><button type="button" name="'+name+'" class="btn btn-info chat col-xs-6 col-sm-6 col-md-6 col-lg-6 col-xs-offset-3 col-sm-offset-3 col-md-offset-3 col-lg-offset-3 submitButton">chat</button><hr/></div></div>';
        $('#participants_offline').append(info_ele);
        $('#participants_offline').append(detail_ele);
      }
    	}
    });
    $('.user-info').hide();
    $('.dropdown-user').click(function() {
      var dataFor = $(this).attr('data-for');
      var idFor = $(dataFor);
      var currentButton = $(this);
      idFor.slideToggle(400, function() {
        if(idFor.is(':visible'))
          {
            currentButton.html('<i class="glyphicon glyphicon-chevron-up text-muted"></i>');
          }
          else
            {
              currentButton.html('<i class="glyphicon glyphicon-chevron-down"></i>');
            }
      })
    });
    
  //get the target name
 // $("button").click(function(){
    
    
  }

  //var divNavbar = $('#navbar');
  var myName;
  var myUserId;
  
  socket.on('connect', function () {
    sessionId = socket.socket.sessionid;
    //alert("sessionId is "+ sessionId);
    $.ajax({
      url:  '/user',
      type: 'GET',
      dataType: 'json'
    }).done(function(data) {
      var name = data.name;
      myName = data.name;
      my_name = data.name;
      myUserId = data.userId;
      
      //divNavbar = data.role;
      /*
       * if(divNavbar == 'ADMIN')
       * 	divNavbar
       */
      //socket.emit('newUser', {id: sessionId, name: name});
      socket.emit('newUser', {id: myUserId, name: name});
    });
  });

//  $('#logout').click(function(){
//	  //alert('logout!!!!!');
//	  socket.emit('oneDisconnect');
//  });
  
  socket.on('newConnection', function (data) {
    updateParticipants(data.participants);
  });

  socket.on('userDisconnected', function(data) {
    updateParticipants(data.participants);
  });

  socket.on('updateStatusChangeToPeople', function(data){
      updateParticipants(data.participants);
  });
  
  socket.on('updateUserProfile', function(data){
      updateParticipants(data.participants);
  });
  
  socket.on('error', function (reason) {
    console.log('Unable to connect to server', reason);
  });
  
  socket.on('privateChat', function(msg){
	  var me = myName;
	  if(msg.target == me)
		  alert(msg.author + ' has sent you a message!');
  });
  
  socket.on('hasAcceptedCall', function(data){
	  var me = myName;
  	  if(data.sender == me)
  	  {
  		  var form = $("<form method='post', action='/videoCall'></form>");
  		  var inputReceiver = $("<input type='hidden', name='receiver'>");
  		  var inputSender = $("<input type='hidden', name='sender'>");
  		  var ISend = $("<input type='hidden', name='ISend'>");
  		  inputReceiver.val(data.receiver);
  		  inputSender.val(me);
  		  ISend.val(true);
  		  form.append(inputReceiver);
  		  form.append(inputSender);
  		  form.append(ISend);
  		  form.submit();
  	  }
  });
  
  socket.on('hasRejectedCall', function(data){
	  if(data.sender == myName)
		  alert(data.receiver + ' has rejected your call!'); 
  });
  
  $("body").on("click", ".chat",function(){
	  var target  = $(this).attr('name');
	  if(target != undefined)
	  {
		  var form = $("<form method='post', action='/privateChat'></form>");
		  var inputTarget = $("<input type='hidden', name='target'>");
		  var inputAuthor = $("<input type='hidden', name='author'>");
		  inputTarget.val(target);
		  inputAuthor.val(myName);
		  form.append(inputTarget);
		  form.append(inputAuthor);
		  form.submit();
	  }
  });
    
    $("body").on("click", ".videoCall",function(){
    	var receiver  = $(this).attr('name');
    	socket.emit('sendVideoCall', {sender : myName, receiver : receiver});
    	alert('Waiting for accept......')
    });
  
  
  var panels = $('.user-info');
  panels.hide();
  $('.dropdown-user').click(function() {
    var dataFor = $(this).attr('data-for');
    var idFor = $(dataFor);
    var currentButton = $(this);
    idFor.slideToggle(400, function() {
      if(idFor.is(':visible'))
        {
          currentButton.html('<i class="glyphicon glyphicon-chevron-up text-muted"></i>');
        }
        else
          {
            currentButton.html('<i class="glyphicon glyphicon-chevron-down text-muted"></i>');
          }
    })
  });
  
  
}

$(document).on('ready', init);
