module.exports = function(_, io, participants, passport) {
  return {
    getPeople: function(req, res) {
    	
    	var statusTxt = "<br>";
    	
        participants.all.forEach(function(userObj) {
      	 
      	  if(userObj.userName == req.session.passport.user.user_name){	 
      		 res.render("people", {userId: req.session.userId, title:"People", user_name: req.session.passport.user.user_name, 
      			                                                               statusCode: userObj.userStatusCode,
      			                                                               locationDesc: userObj.userLocationDesc,
      			                                                               lastPostTime: userObj.userLastPostTime
      			                                                               });
      	  } 	  	  

        });    	
     			
      //res.render("people", {userId: req.session.userId, title:"People", user_name:req.session.passport.user.user_name, statusText:statusTxt});
    }
  };
};
