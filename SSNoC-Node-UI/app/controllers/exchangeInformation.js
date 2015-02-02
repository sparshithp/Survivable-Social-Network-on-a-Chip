var Message = require('../models/ExchangeInformationRest')

module.exports = function(_, io, passport, participants, refreshAllUsers, publicMessages) {
	return{

		displayWall : function(req, res)
		{
			res.render('wall');
		},
		
		getPublicMessages : function(req, res)
		{
			Message.getPublicMessages(function(err, messages){
				if(err)
				{
					console.log('fail to get public messages');
				}
				else
				{
					res.json(200, messages);
				}
				
			})
		},
		
		postMessageOnWall : function(req, res)
		{
			var parentRes = res;
			Message.postMessageOnWall(req.body.author, req.body.content, 
					req.body.postedAt, function(err, res){
				if(err)
				{
					console.log("fail to send message" + "status code: " + res.statusCode);
					parentRes.render('wall');
				}
				else //if(res.statusCode === 201)
				{
					console.log("successfully send message");
					parentRes.render('wall');
				}
			});
		},
		
		postPrivateMessage : function(req, res)
		{
			var parentRes = res;
			console.log("&&&&&&&&&"+req.body.author, req.body.target, req.body.content);
			Message.postPrivateMessage(req.body.author, req.body.target,
					req.body.content, req.body.postedAt, function(err, res){
				if(err)
				{
					console.log("fail to send messgae, status code: " + res.statusCode);
					parentRes.render('privateChat');
				}
				else
				{
					console.log("successfully send private messgae");
					parentRes.render('privateChat');
				}
			});
		},
		
		getPrivateChat : function(req, res)
		{
			var target = req.body.target;
			var author = req.body.author;
			console.log("!!!!!!!!!"+target + "@@@@" + author);
			res.render('privateChat',{target : target, author : author});
		},
		
		getPrivateMessages : function(req, res)
		{
			var parentRes = res;
			var user1 = req.body.author;
			var user2 = req.body.target;
			
			Message.getPrivateMessages(user1, user2, function(err,messages){
				if(err)
				{
					console.log('fail to get private messages');
				}
				else
				{
					res.json(200, messages);
				}
			});
		}
	};
};