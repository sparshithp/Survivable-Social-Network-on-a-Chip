//var bcrypt = require('bcrypt-nodejs');
var request = require('request');
var rest_api = require('../../config/rest_api');

//'performance_setup': host_url + '/performance/setup',
//    'performance_teardown': host_url + '/performance/teardown',
//    'get_test_msg': host_url + '/performance/gettestmessage',
//    'insert_test_msg': host_url + '/performance/insertmessage',
//    'get_performance_crumb': host_url + '/performance/getperformancecrumb'

module.exports = {

    performance_setup: function(callback) {

		  request.post(rest_api.performance_setup, {json:true}, function(err, res, body) {
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

    performance_teardown:function(callback) {
	
	    request.post(rest_api.performance_teardown, {json:true}, function(err, res, body) {
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
    get_test_msg :function(callback) {

        request.get(rest_api.get_test_msg, {json:true}, function(err, res, body) {
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
    },
    insert_test_msg :function(postBody, callback) {

        var options = {
            url : rest_api.insert_test_msg,
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
    },
    get_performance_crumb :function(callback) {

        request.get(rest_api.get_performance_crumb, {json:true}, function(err, res, body) {
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
