var shareStatusRest = require('../models/ShareStatusRest');

module.exports = function(_, io, participants, passport, refreshAllUsers) {
  return {
    displayShareStatusPage : function(req, res) {     
    	//res.writeHead(200, { 'Content-Type': 'text/html' });

    	shareStatusRest.getStatus(req.session.passport.user.user_name, 
    			                  function(err, user){
    								//res.render('sharestatus',{userInfo :{errMessage:'failed test method'}} )
    								if(err) {
    							    	res.render('sharestatus',
    							    			{userInfo : {
    							    				errMessage: "Failed To Get User Status From Database.",
    							    				infoMessage:null,
    							    				successMessage:null,
    							    				userName:null	,
    							    				status:null,
    							    				locationDesc:null,
    							    				lastPostTime: null
    							    				}
    							    	        });
    								}
       								
    						    	res.render('sharestatus',
    						    			{userInfo : {
    						    				errMessage: null,
    						    				infoMessage: null,
    						    				successMessage: null,
    						    				userName: user.userName	,
    						    				status: user.status,
    						    				locationDesc: user.locationDesc,
    						    				lastPostTime: user.lastPostTime
    						    				}
    						    	        });
    								
    		                      }  
    	);
    	 
    	
    },

    postShareStatusPage : function(req, res) {     
    	
    	var parentRes = res;
    	shareStatusRest.postStatus(req.session.passport.user.user_name, 
    			                   req.body.statusOption,
    			                   req.body.locationDesc,
    			                   function(err, res){
    		if(err){
    			console.log("did not put data" + "status code: " + res.statusCode);
    			parentRes.render('sharestatus', { userInfo:{errMessage: 'Failed To Update User Status. Status Code: ' + res.statusCode}});
    		}
    		else if(res.statusCode === 200){
    			//Nothing happened. no record has been updated.
    			console.log("putdata 200, nothing changed: "+ req.body.locationDesc);
    	    	shareStatusRest.getStatus(req.session.passport.user.user_name, 
		                  function(err, user){
							//res.render('sharestatus',{userInfo :{errMessage:'failed test method'}} )
							if(err) {
								console.log("err when trying to refresh uer info from share status");
								return;
							}
							
							parentRes.render('sharestatus',
					    			{userInfo : {
					    				errMessage: null,
					    				infoMessage: 'Update Status Not Done in Database. Status Code: ' + res.statusCode,
					    				successMessage: null,
					    				userName: user.userName	,
					    				status: user.status,
					    				locationDesc: user.locationDesc,
					    				lastPostTime: user.lastPostTime
					    				}
					    	        });
							
	                      }  
    	    	);
    			
    		}
    		else if(res.statusCode === 201){ //created as in spec, no idea, no updated?
    			console.log("putdata successfully: "+ req.body.locationDesc);
    			//parentRes.render('sharestatus',{userInfo:{successMessage: 'User Status Successfully Changed. Status Code: ' + res.statusCode }});
    	    	shareStatusRest.getStatus(req.session.passport.user.user_name, 
		                  function(err, user){
							//res.render('sharestatus',{userInfo :{errMessage:'failed test method'}} )
							if(err) {
								console.log("err when trying to refresh uer info from share status");
								return;
							}

                              //io.socket.sessionid.
                              refreshAllUsers(participants, function(){
                                  io.sockets.emit('updateStatusChangeToPeople', participants);
                              });

//		                            console.log("send user status change socket info from controller" + req.session.id);

							parentRes.render('sharestatus',
					    			{userInfo : {
					    				errMessage: null,
					    				infoMessage: null,
					    				successMessage: 'User Status Successfully Changed. Status Code: ' + res.statusCode,
					    				userName: user.userName	,
					    				status: user.status,
					    				locationDesc: user.locationDesc,
					    				lastPostTime: user.lastPostTime
					    				}
					    	        });
							
	                      }  
    	    	);
    		
    		}
    		
    	})
    
    }   

    
    
//    postSignup : function(req, res, next) {
//      passport.authenticate('local-signup', function(err, user, info) {
//        if (err)
//          return next(err);
//        if (!user)
//          return res.redirect('/signup');
//        req.logIn(user, function(err) {
//          if (err)
//            return next(err);
//          participants.all.push({'userName' : user.local.name});
//          return res.redirect('/welcome');
//        });
//      })(req, res, next);
//    },

//    getWelcome : function(req, res) {
//      res.render('welcome', {title: "Hello " + req.session.passport.user.user_name + " !!"} );
//    }
  };
};
