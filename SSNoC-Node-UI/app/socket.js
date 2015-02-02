module.exports = function(_, io, participants) {

  io.on("connection", function(socket){
    socket.on("newUser", function(data) {
      participants.online[data.id] = {'userName' : data.name, 'status': data.status};
      io.sockets.emit("newConnection", {participants: participants});
    });
   
    socket.on("Disconnect", function() {
      delete participants.online[socket.id];
      io.sockets.emit("userDisconnected", {id: socket.id, sender:"system", participants:participants});
    });
    
    socket.on("userDisconnect", function(userId) {
        delete participants.online[userId];
        io.sockets.emit("userDisconnected", {id: userId, sender:"system", participants:participants});
      });

    //get the message from a user and broadcast it
    socket.on("sendWallMessage", function(msg){
    	io.sockets.emit("getWallMessage", msg);
    	console.log("emit getWallMessage");
    });
    
    //handle the private messages
    socket.on("sendPrivateMessage", function(msg){
    	var authorSelf = msg.author + 'self';
    	io.sockets.emit(authorSelf, msg);
    	io.sockets.emit(msg.target, msg);
    	io.sockets.emit('privateChat', msg);
    	console.log("emit sendPrivateMessage");
    	console.log(msg.author);
    	console.log(msg.target);
    });
    
    socket.on('sendVideoCall', function(data){
    	io.sockets.emit('youHaveACall', data);
    });
    
    socket.on('IAccept', function(data){
    	io.sockets.emit('hasAcceptedCall', data);
    });
    
    socket.on('IReject', function(data){
    	io.sockets.emit('hasRejectedCall', data);
    });
  });

  
};
