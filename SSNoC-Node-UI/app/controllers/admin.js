var Profile = require('../models/ProfileRest');
var authorizationMap = require('../../config/authorization.js');
var User = require('../models/UserRest');

module.exports = function(_, io, passport, participants, refreshAllUsers) {
	return{

		display : function(req, res)
		{
			res.render('adminUserProfile');
		},
		
		getAdminUserProfileList : function(req, res)
		{
			User.getUsers(function(err,users){
				if(err)
				{
					console.log('fail to get profile');
				}
				else
				{
					res.json(200, users);
				}
			});
		},
		
		getProfile : function(req, res)
		{
			var parentRes = res;
			var uid = req.body.uid;
			console.log('&%&%&%  get!!!!  id is '+uid);
            Profile.getProfile(uid, function(err,profile){
				if(err)
				{
					console.log('fail to get profile');
				}
				else
				{
					res.json(200, profile);
				}
			});
		},

        authorizationCheck : function(req, res){

            var objectUserIdOrName = req.body;
            Profile.authorizationCheck(objectUserIdOrName, function(err, body){

                if(err){
                    console.log('fail to check authorization');
                    res.send('no');
                }
                else{
                    //check
                    var useCaseName = req.body.useCaseName;

                    if(body.accountStatus === 'inactive'){
                        res.send('no');
                    }
                    else{
                        if(authorizationMap.isHasTheAuthorization(useCaseName, req.body.privilegeLevel)){
                            res.send('yes');
                        }
                        else{
                            res.send('no');
                        }

                    }

                }

            });

        },
		
		postProfile : function(req, res)
		{
			var parentRes = res;
			console.log('*&^(*&^*&^ uid = '+req.body.userId);
			Profile.postProfile(req.body.userId, req.body.userName, 
					req.body.password, req.body.accountStatus, req.body.privilegeLevel, function(err, res){
				if(err)
				{
					console.log("fail to profile" + "status code: " + res.statusCode);
					parentRes.render('adminUserProfile');
				}
				else //if(res.statusCode === 201)
				{
					console.log("successfully send profile");
					//io.socket.sessionid.
					refreshAllUsers(participants, function(){
                        io.sockets.emit('updateUserProfile', participants);
                    });
					parentRes.json(200);
					//parentRes.render('adminUserProfile');
				}
			});
		}
	};
};