var analysisRest = require('../models/analysisRest');

module.exports = function(_, io, participants, passport) {

    return {
        showPage: function(req, res) {
        	res.render("analysis", {clusters: undefined});
        },
    	
    	getClusters: function(req, res) {
    		var dur = req.body.duration;
            analysisRest.getClusters(dur,function(err,body){
                if(err != null){
                	res.render("analysis", {enable: "False", coordinator: "True", clusters: undefined});
                }
                else if(err==null){
                	console.log(body);
                	res.render("analysis", {clusters: body, enable: "False", coordinator: "True"});
                }
                });

        }
        
    }
        
   
};
