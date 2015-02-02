//var bcrypt = require('bcrypt-nodejs');
var request = require('request');
var rest_api = require('../../config/rest_api');

//'start_memory_monitor':   host_url + '/memory/start',
//    'stop_memory_monitor':   host_url + '/memory/stop',
//    'get_memory_crumb_1':   host_url + '/memory/interval/1'

module.exports = {

    start_memory_monitor: function(callback) {

		  request.post(rest_api.start_memory_monitor, {json:true}, function(err, res, body) {
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

    stop_memory_monitor:function(callback) {
	
	    request.post(rest_api.stop_memory_monitor, {json:true}, function(err, res, body) {
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
    get_memory_crumb_1 :function(callback) {

        request.get(rest_api.get_memory_crumb_1, {json:true}, function(err, res, body) {
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
