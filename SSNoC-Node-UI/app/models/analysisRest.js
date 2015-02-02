//var bcrypt = require('bcrypt-nodejs');
var request = require('request');
var rest_api = require('../../config/rest_api');



module.exports = {

		getClusters : function(dur, callback) {
			console.log(dur);
			var options = {
					url : rest_api.analyze + dur,
//					body : {dur:dur},
					json : true
			};
        request.post(options, function(err, res) {
            if (err!=null)
                callback(err,null);
            else if (res.statusCode === 200) {
                callback(null, res.body);
            }
            else
            	callback(err, "");
        });
    }
    
}
;
