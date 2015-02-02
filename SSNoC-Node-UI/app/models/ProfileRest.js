var request = require('request');
var rest_api = require('../../config/rest_api');

function Profile(userId, userName, password, accountStatus, privilegeLevel){ 
	  this.local = {
	    userId : userId,
	    userName : userName,
	    password : password,
	    accountStatus : accountStatus,
	    privilegeLevel : privilegeLevel
	  };
	}

Profile.getProfile = function(uId, callback){
	var options = {
			url : rest_api.get_profile + uId,
		    json: true
	};
	request(options, function(err, res, body){
		if(err)
		{
			console.log('nullnullnull');
			callback(err,null);
			return;
		}
		if(res.statusCode === 200)
		//else
		{
			/*var profile = body.map(function(item, idx, arr)
			{
		        return new Profile(item.userId, item.userName, item.password, item.accountStatus, item.privilegeLevel);
			});*/
			console.log(body);
			var profile = new Profile(body.userId, body.userName, body.password, body.accountStatus, body.privilegeLevel);
			callback(null, profile);
			return;
		}
		if(res.statusCode !== 200) 
		{
			console.log('not 200 null');
			callback(null, null);
			return;
		}
	});
}

Profile.authorizationCheck = function(objectUserIdOrName, callback) {

    var tmpURL;

    if (objectUserIdOrName) {

        if (objectUserIdOrName.userId) {
            tmpURL = rest_api.getAuthorizationProfileById + objectUserIdOrName.userId;
        }
        else if (objectUserIdOrName.userName) {
            tmpURL = rest_api.getAuthorizationProfileByName + objectUserIdOrName.userName;
        }

        var options = {
            url: tmpURL,
            json: true
        };
        request.get(options, function (err, res, body) {
            if (err) {
                callback(err, null);
                return;
            }
            if (res.statusCode === 200) {
                callback(null, body);
                return;
            }
            if (res.statusCode !== 200) {
                callback(null, null);
                return;
            }
        });

    }

}

Profile.postProfile = function(userId, userName, password, accountStatus, privilegeLevel, callback){
	var options = {
		url : rest_api.post_profile,
	    body : {userId : userId, 
	    		userName : userName, 
	    		password : password,
	    		accountStatus : accountStatus,
	    		privilegeLevel : privilegeLevel},
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
			console.log('codecodecode  '+res.statusCode);
			callback(res.body, null);
			return;
		}
		
		callback(null, res);
		return;
	});
}

module.exports = Profile;