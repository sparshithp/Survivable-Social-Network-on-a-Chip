var monMemoryRest = require('../models/MonmemoryRest');

module.exports = function(_, io, participants, passport) {
  return {
    getMonmemory: function(req, res) {

        res.render("monmemory");

    },

      start_memory_monitor: function(req, res) {

          monMemoryRest.start_memory_monitor(function(err,body){
              if(body){
                  res.send(body);
              }
          });

      },

      stop_memory_monitor: function(req, res) {

          monMemoryRest.stop_memory_monitor(function(err,body){
              if(body){
                  res.send(body);
              }
          });

      },
      get_memory_crumb_1: function(req, res) {

          monMemoryRest.get_memory_crumb_1(function(err,body){
            if(body){
                res.json(200, body);
            }
          });

      }

      //res.render("people", {userId: req.session.userId, title:"People", user_name:req.session.passport.user.user_name, statusText:statusTxt});
  };
};
