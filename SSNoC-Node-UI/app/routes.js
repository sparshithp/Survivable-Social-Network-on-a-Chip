var User = require('./models/UserRest');
var Profile = require('./models/ProfileRest');
module.exports = function(app, _, io, participants, passport) {
  var user_controller = require('./controllers/user')(_, io, participants, passport, refreshAllUsers);
  var people_controller = require('./controllers/people')(_, io, participants, passport);

  
  var exchangeInformation_controller = require('./controllers/exchangeInformation')(_, io, passport, participants, refreshAllUsers);
  
  var sharestatus_controller = require('./controllers/sharestatus')(_, io, participants, passport, refreshAllUsers);
  var monmemory_controller = require('./controllers/monmemory')(_, io, participants, passport);
  var monperformance_controller = require('./controllers/monperformance')(_, io, participants, passport);
  var announcement_controller = require('./controllers/announcement')(_, io, participants, passport);

  var admin_controller = require('./controllers/admin')(_, io, passport, participants, refreshAllUsers);
  var analysis_controller = require('./controllers/analysis')(_, io, participants, passport);
  var call_controller = require('./controllers/call')(_, io, passport, participants, refreshAllUsers);

  app.get("/", user_controller.getLogin);

  app.post("/signup", user_controller.postSignup);

  app.get("/welcome", isLoggedIn, user_controller.getWelcome);

  app.get("/user", isLoggedIn, user_controller.getUser);
  app.get('/signup', user_controller.getSignup);
  app.get("/logout", isLoggedIn, user_controller.getLogout);
  app.post("/login", passport.authenticate('local-login', {
    successRedirect : '/people',
    failureRedirect : '/',
    failureFlash: true
  }));

  app.get("/people", isLoggedIn, people_controller.getPeople);
  app.get("/sharestatus", isLoggedIn, sharestatus_controller.displayShareStatusPage);
  app.post("/sharestatus", isLoggedIn, sharestatus_controller.postShareStatusPage);

  
  //added by Qihao
  app.get("/wall", isLoggedIn, exchangeInformation_controller.displayWall);
  app.get("/wallMessage", isLoggedIn, exchangeInformation_controller.getPublicMessages);
  app.post("/getPrivateMessage", isLoggedIn, exchangeInformation_controller.getPrivateMessages);
  app.post("/privateChat", isLoggedIn, exchangeInformation_controller.getPrivateChat);
  app.post("/postOnWall", isLoggedIn, exchangeInformation_controller.postMessageOnWall);
  app.post("/postPrivateMessage", isLoggedIn, exchangeInformation_controller.postPrivateMessage);

  app.get("/monmemory", isLoggedIn, monmemory_controller.getMonmemory);
  app.post("/startmonmemory", isLoggedIn, monmemory_controller.start_memory_monitor);
  app.post("/stopmonmemory", isLoggedIn, monmemory_controller.stop_memory_monitor);
  app.get("/memorycrumb1", isLoggedIn, monmemory_controller.get_memory_crumb_1);

  app.get("/monperformance", isLoggedIn, monperformance_controller.getMonperformance);
  app.post("/performance_setup", isLoggedIn, monperformance_controller.performance_setup);
  app.post("/performance_teardown", isLoggedIn, monperformance_controller.performance_teardown);
  app.get("/get_test_msg", isLoggedIn, monperformance_controller.get_test_msg);
  app.post("/insert_test_msg", isLoggedIn, monperformance_controller.insert_test_msg);
  app.get("/get_performance_crumb", isLoggedIn, monperformance_controller.get_performance_crumb);

  app.get("/announcement", isLoggedIn, announcement_controller.getAnnouncement);
  app.get("/announcement/getall", isLoggedIn, announcement_controller.getAllAnnouncements);
  app.post("/insert_announcement", isLoggedIn, announcement_controller.insert_announcement);

  app.post("/authorizationCheck",isLoggedIn, admin_controller.authorizationCheck);
  app.post("/getProfile", isLoggedIn, admin_controller.getProfile);
  app.post("/postProfile", isLoggedIn, admin_controller.postProfile);
  app.get("/adminUserProfile", isLoggedIn, admin_controller.display);
  app.get("/getAdminUserProfileList", isLoggedIn, admin_controller.getAdminUserProfileList);
    
  app.get("/analysis", isLoggedIn, analysis_controller.showPage);
  app.post("/clusters", isLoggedIn, analysis_controller.getClusters);

  app.get("/makeCall", isLoggedIn, call_controller.display);

  app.post("/videoCall", isLoggedIn, call_controller.display);
};

function isLoggedIn(req, res, next) {
  if (req.isAuthenticated())
    return next();
  res.redirect('/');
}

function isActive(req, res, next){
	User.getUser(req.body.name, function(err, user){
		if(user != null){
			Profile.getProfile(user.local.user_id, function(err, profile){
				if(profile.local.accountStatus != 'inactive'){
					console.log('ACTIVE!!!');
					return next();
				}
				else{
					console.log('INACTIVE!!!');
					res.redirect('/');
				}
			});
		}
	});
}



function refreshAllUsers(participants, callback) {
  participants.all = [];
  User.getAllUsers(function(err, users) {
    users.forEach(function(user) {
      participants.all.push({userName : user.local.name, userStatusCode : user.local.userStatusCode, userLocationDesc: user.local.locationDesc, userLastPostTime: user.local.lastPostTime}); //
        console.log("############### userStatusCode" + user.local.userStatusCode + " from routes.js");
    });
    callback();
  });
  

  
}
