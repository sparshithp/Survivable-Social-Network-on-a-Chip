var monperformanceRest = require('../models/MonperformanceRest');

module.exports = function(_, io, participants, passport) {

    return {
        getMonperformance: function(req, res) {

            res.render("monperformance");

        },

        performance_setup: function(req, res) {

            monperformanceRest.performance_setup(function(err,body){
                if(body){
                    res.send(body);
                }
            });

        },

        performance_teardown: function(req, res) {

            monperformanceRest.performance_teardown(function(err,body){
                if(body){
                    res.send(body);
                }
            });

        },
        get_test_msg: function(req, res) {

            monperformanceRest.get_test_msg(function(err,body){
                if(body){
                    res.json(200, body);
                }
            });

        },
        insert_test_msg: function(req, res) {

            var postBody = req.body;
            monperformanceRest.insert_test_msg(postBody, function(err,body){
                if(body){
                    res.send(body);
                }
            });

        },
        get_performance_crumb: function(req, res) {

            monperformanceRest.get_performance_crumb(function(err,body){
                if(body){
                    res.json(200, body);
                }
            });

        }

        //res.render("people", {userId: req.session.userId, title:"People", user_name:req.session.passport.user.user_name, statusText:statusTxt});
    };
};
