var announcementRest = require('../models/AnnouncementRest');

module.exports = function(_, io, participants, passport) {
  return {

      getAnnouncement: function(req, res) {

        res.render("announcement");

    },

      getAllAnnouncements: function(req, res) {

          announcementRest.getAllAnnouncements(function(err,body){
              if(body){
                  res.send(body);
                  //res.render("announcement", {announcements:body});
              }
          });

      },

      insert_announcement: function(req, res) {
          var postBody = req.body;
          announcementRest.insert_announcement(postBody, function(err,body){
              if(body){
                  res.send(body);
              }
          });

      }

      //res.render("people", {userId: req.session.userId, title:"People", user_name:req.session.passport.user.user_name, statusText:statusTxt});
  };
};
