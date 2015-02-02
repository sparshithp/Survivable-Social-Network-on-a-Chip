//var Profile = require('../models/ProfileRest');
//var authorizationMap = require('../../config/authorization.js');
//var User = require('../models/UserRest');

module.exports = function(_, io, passport, participants, refreshAllUsers) {
	return{

		display : function(req, res)
		{
			//res.render('makeCall', {user_id_actualName: req.session.passport.user.user_name});
			res.render('makeCall', {remote : req.body.receiver, me : req.session.passport.user.user_name, ISend : req.body.ISend});
		}

	};
};