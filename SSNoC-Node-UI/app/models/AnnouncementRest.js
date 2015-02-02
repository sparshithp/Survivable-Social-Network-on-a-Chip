//var bcrypt = require('bcrypt-nodejs');
var request = require('request');
var rest_api = require('../../config/rest_api');

module.exports = {

    getAllAnnouncements: function(callback) {

		  request.get(rest_api.getAllAnnouncements, {json:true}, function(err, res, body) {
		    if (err){
		      callback(err,null);
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
		},

    insert_announcement:function(postBody, callback) {

        var options = {
            url : rest_api.insert_announcement,
            body : postBody,
            json: true
        };

	    request.post(options, function(err, res, body) {
            if (err){
                callback(err,res);
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
;
