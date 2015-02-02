var bcrypt = require('bcrypt-nodejs');
var request = require('request');
var rest_api = require('../../config/rest_api');

function User(user_name, password, userStatusCode, locationDesc, lastPostTime){ //modified by feng
  this.local = {
	user_id : '',
    name : user_name,
    password : password,
    userStatusCode : userStatusCode,
    locationDesc : locationDesc,
    lastPostTime : lastPostTime
  };
}

User.generateHash = function(password) {
  return bcrypt.hashSync(password, bcrypt.genSaltSync(8), null);
};

User.prototype.isValidPassword = function(password, callback) {
  request.post(rest_api.is_password_valid + this.local.name + '/authenticate', {json:true, body:{password:password}}, function(err, res, body) {
    if (err || res.statusCode !== 200){
      callback(false);
      return;
    }

    callback(true);
  });
};

User.getUser = function(user_name, callback) {
  request(rest_api.get_user + user_name, {json:true}, function(err, res, body) {
    if (err){
      callback(err,null);
      return;
    }
    if (res.statusCode === 200) {
      var user = new User(body.userName, body.password, body.status, body.locationDesc, body.lastPostTime); //modified by feng
      user.local.user_id = body.userId; //user id add in this restful api by qihao, so this place needs to be modified
      callback(null, user);

      return;
    }
    if (res.statusCode !== 200) {
      callback(null, null);
      return;
    }
  });
};

User.getAllUsers = function(callback) {
  request(rest_api.get_all_active_users, {json:true}, function(err, res, body) {
    if (err){
      callback(err,null);
      return;
    }
    if (res.statusCode === 200) {
      var users = body.map(function(item, idx, arr){

        console.log("############### item.status" + item.status + " from UserRest.js");
        var tmpUser =  new User(item.userName, item.password, item.status, item.locationDesc, item.lastPostTime);//modified by feng
        tmpUser.local.user_id = item.userId;
        return tmpUser;
      });

      users.sort(function(a,b) {
        return a.userName > b.userName;
      });

      console.log("@@@@@ in User.getAllUser succeed users :" + JSON.stringify(users));
      callback(null, users);
      return;
    }
    if (res.statusCode !== 200) {
      callback(null, null);
      return;
    }
  });
};

User.getUsers = function(callback) {
	  request(rest_api.get_all_users, {json:true}, function(err, res, body) {
	    if (err){
	      callback(err,null);
	      return;
	    }
	    if (res.statusCode === 200) {
	      var users = body.map(function(item, idx, arr){

	        console.log("############### item.status" + item.status + " from UserRest.js");
	        var tmpUser =  new User(item.userName, item.password, item.status, item.locationDesc, item.lastPostTime);//modified by feng
	        tmpUser.local.user_id = item.userId;
	        return tmpUser;
	      });

	      users.sort(function(a,b) {
	        return a.userName > b.userName;
	      });

	      console.log("@@@@@ in User.getAllUser succeed users :" + JSON.stringify(users));
	      callback(null, users);
	      return;
	    }
	    if (res.statusCode !== 200) {
	      callback(null, null);
	      return;
	    }
	  });
	};


User.saveNewUser = function(user_name, password, callback) {
  var options = {
    url : rest_api.post_new_user,
    body : {userName: user_name, password: password},
    json: true
  };

  request.post(options, function(err, res, body) {
    if (err){
      callback(err,null);
      return;
    }
    if (res.statusCode !== 200 && res.statusCode !== 201) {
      callback(res.body, null);
      return;
    }
    var new_user = new User(body.userName, password, undefined);
    callback(null, new_user);
    return;
  });
};

module.exports = User;
