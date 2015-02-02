//var bcrypt = require('bcrypt-nodejs');
var request = require('request');
var rest_api = require('../../config/rest_api');

module.exports = {
	 
	 getStatus: function(user_name, callback) {
		  request(rest_api.get_user + user_name, {json:true}, function(err, res, body) {
		    if (err){
		      callback(err,null);
		      return;
		    }
		    if (res.statusCode === 200) {
		      var user = {
				userName: body.userName	,
				status: body.status,
				locationDesc: body.locationDesc,
				lastPostTime: body.lastPostTime
		      }	
		      callback(null, user);
		      return;
		    }
		    if (res.statusCode !== 200) {
		      callback(null, null);
		      return;
		    }
		  });
		},
	 
	postStatus:function(user_name, status, locationDesc, callback) {
	    var options = {
	    url : rest_api.put_user_status + user_name,
	    body : {userName: user_name, status: status, locationDesc: locationDesc},
	    json: true
	    };
	
	    request.put(options, function(err, res, body) {
	    if (err){
	      callback(err,res);
	      return;
	    }
	    if (res.statusCode !== 200 && res.statusCode !== 201) {
	      callback(null, res);
	      return;
	    }
	    
//	    var new_user = new User(body.userName, password, undefined);
	    callback(null, res);
	    return;
	    });
	}
		
}
;
