var request = require('request');
var rest_api = require('../../config/rest_api');

function Message(content, author, messageType, target, postedAt, messageID){ 
	  this.local = {
	    content : content,
	    author : author,
	    messageType : messageType,
	    target : target,
	    postedAt : postedAt,
	    messageID : messageID
	  };
	}


Message.getPublicMessages = function(callback){
	request(rest_api.get_public_messages, {json:true}, function(err, res, body){
		if(err)
		{
			callback(err,null);
			return;
		}
		if(res.statusCode === 200)
		{
			var messages = body.map(function(item, idx, arr)
			{
		        return new Message(item.content, item.author, item.messageType, item.target, item.postedAt, item.messageID);
			});
			callback(null, messages);
			return;
		}
		if(res.statusCode !== 200) 
		{
			callback(null, null);
			return;
		}
	});
}

Message.postMessageOnWall = function(author, content, postedAt, callback){
	var options = {
		url : rest_api.post_public_messages + author,
	    body : {author : author, content : content, postedAt : postedAt},
	    json: true
	};
	request.post(options, function(err, res, body){
		if(err)
		{
			callback(err,null);
			return;
	    }
		if(res.statusCode !== 200 && res.statusCode !== 201)
		{
			callback(res.body, null);
			return;
		}
		
		callback(null, res);
		return;
	});
}

Message.postPrivateMessage = function(author, target, content, postedAt, callback){
	var options = {
		url : rest_api.post_private_messages + author + '/' + target,
	    body : {content : content, postedAt : postedAt},
	    json: true
	};
	request.post(options, function(err, res, body){
		if(err)
		{
			callback(err,null);
			return;
	    }
		if(res.statusCode !== 200 && res.statusCode !== 201)
		{
			callback(res.body, null);
			return;
		}
		callback(null, res);
		return;
	});
}

Message.getPrivateMessages = function(user1, user2, callback){
	var options = {
		url : rest_api.get_private_messages + user1 + '/' + user2,
	    json: true
	};
	request(options, function(err, res, body){
		if(err)
		{
			callback(err,null);
			return;
		}
		if(res.statusCode === 200)
		{
			var messages = body.map(function(item, idx, arr)
			{
		        return new Message(item.content, item.author, item.messageType, item.target, item.postedAt, item.messageID);
			});
			callback(null, messages);
			return;
		}
		if(res.statusCode !== 200) 
		{
			callback(null, null);
			return;
		}
	});
}

module.exports = Message;