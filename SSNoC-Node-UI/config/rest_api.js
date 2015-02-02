var host_url = "http://localhost:1234/ssnoc";

module.exports = {
  'get_all_active_users' : host_url + '/users',
  'get_all_users' : host_url + '/users/all',
  'is_password_valid' : host_url + '/user/',
  'get_user' : host_url + '/user/',
  'post_new_user' : host_url + '/user/signup',
  'put_user_status' : host_url + '/user/',
  'start_memory_monitor':   host_url + '/memory/start',
  'stop_memory_monitor':   host_url + '/memory/stop',
  'get_memory_crumb_1':   host_url + '/memory/interval/1',

  'performance_setup': host_url + '/performance/setup',
  'performance_teardown': host_url + '/performance/teardown',
    // this is a GIT test
  'get_test_msg': host_url + '/performance/gettestmessage',
  'insert_test_msg': host_url + '/performance/insertmessage',
  'get_performance_crumb': host_url + '/performance/getperformancecrumb',

  'getAllAnnouncements': host_url + '/announcement/getall',
  'insert_announcement': host_url + '/announcement/insert',
    // /user/userName any subset of keys in User except Location and Status (if userName is present, user name is updated)
  'get_public_messages' : host_url + '/messages/wall',
  'get_private_messages' : host_url + '/messages/',
  'post_public_messages' : host_url + '/message/',
  'post_private_messages' : host_url + '/message/',
  //analyze social network
  'analyze': host_url + '/usergroups/unconnected/',


  //get profile
  'get_profile' : host_url + '/userprofileadmin/getProfileByUserId/',
  'post_profile' : host_url + '/userprofileadmin/update',
  'getAuthorizationProfileById' : host_url + '/userprofileadmin/getProfileByUserId/',
  'getAuthorizationProfileByName' : host_url + '/userprofileadmin/getProfileByUserName/',
  'updateAuthorization' : host_url + '/userprofileadmin/update'  //include user profile like name/password/accountStatus/privilegeLevel


};
