package edu.cmu.sv.ws.ssnoc.data;

/**
 * This class contains all the SQL related code that is used by the project.
 * Note that queries are grouped by their purpose and table associations for
 * easy maintenance.
 * 
 */
public class SQL {
	/*
	 * List the USERS table name, and list all queries related to this table
	 * here.
	 */
	public static final String SSN_USERS = "SSN_USERS";
	public static final String STATUS_CRUMB = "STATUS_CRUMB";

	public static final String SSN_MESSAGE = "SSN_MESSAGE";

	public static final String MEMORY_CRUMB = "MEMORY_CRUMB";
	public static final String PERFORMANCE_CRUMB = "PERFORMANCE_CRUMB";
	public static final String ANNOUNCEMENT_CRUMB = "ANNOUNCEMENT_CRUMB";
	
	public static final String AUTHORIZATION = "AUTHORIZATION";


	/**
	 * Query to check if a given table exists in the H2 database.
	 */
	public static final String CHECK_TABLE_EXISTS_IN_DB = "SELECT count(1) as rowCount "
			+ " FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = SCHEMA() "
			+ " AND UPPER(TABLE_NAME) = UPPER(?)";
	
	
	// ****************************************************************
	// All queries related to USER AUTHORIZATION CHECK
	// ****************************************************************
	public static final String CREATE_AUTHORIZATION_TABLE = "create table IF NOT EXISTS "
			+ AUTHORIZATION + " ( id IDENTITY PRIMARY KEY,"
			+ " userId LONG," + " accountStatus VARCHAR(10) DEFAULT 'active'," + " privilegeLevel VARCHAR(20) DEFAULT 'citizen', " + " changeAt TIMESTAMP )" ;
	public static final String INSERT_AUTHORIZATION_WITH_USERID = "insert into "+ AUTHORIZATION + 
			" (userId, accountStatus, privilegeLevel, changeAt) values (?, ?, ?, CURRENT_TIMESTAMP() )";
	public static final String GET_AUTHORIZATION_BY_USERID = "select a.userId, s.user_name, s.password, a.accountStatus, a.privilegeLevel from AUTHORIZATION as a, SSN_USERS as s where a.userId = s.user_id AND a.userId = ?";
	public static final String GET_AUTHORIZATION_BY_USERNAME = "select * from AUTHORIZATION where userId in (select user_id from "
			+ SSN_USERS
			+ " where user_name = ?) ";	

	public static final String GET_ALL_ACTIVE_USERPROFILE = "select * from SSN_USERS where user_id in (select userId from "
			+ AUTHORIZATION
			+ " where accountStatus = 'active') ";	
	
	public static final String UPDATE_AUTHORIZATION_BY_USERID = "update " + AUTHORIZATION 
			+ " set accountStatus = ?, privilegeLevel = ?, changeAt = CURRENT_TIMESTAMP() where userId = ?" ;
	
	public static final String UPDATE_AUTHORIZATION_BY_USERNAME = "update " + AUTHORIZATION 
			+ " set accountStatus = ?, privilegeLevel = ?, changeAt = CURRENT_TIMESTAMP() where userId = (select user_id from "
			+ SSN_USERS
			+ " where user_name = ?) " ;
	
	public static final String DELETE_ALL_AUTHORIZATION = "delete from AUTHORIZATION";
	
	// ****************************************************************
	// All queries related to ANNOUNCEMENT
	// ****************************************************************
	public static final String CREATE_ANNOUNCEMENT_CRUMB = "create table IF NOT EXISTS "
			+ ANNOUNCEMENT_CRUMB + " ( announcementID IDENTITY PRIMARY KEY,"
			+ " title VARCHAR(50)," + " content VARCHAR(512)," + " author VARCHAR(50), " + " locationDesc VARCHAR(50), " + " postAt TIMESTAMP )" ;
	public static final String INSERT_ANNOUNCEMENT_CRUMB = "insert into "+ ANNOUNCEMENT_CRUMB + 
			" (title, content, author, locationDesc, postAt) values (?, ?, ?, ?, CURRENT_TIMESTAMP() )";
	public static final String GET_ANNOUNCEMENT_CRUMB = "select * from ANNOUNCEMENT_CRUMB order by postAt DESC";
	public static final String DELETE_ALL_ANNOUNCEMENT_CRUMB = "delete from ANNOUNCEMENT_CRUMB";
	
	// ****************************************************************
	// All queries related to PERFORMANCE
	// ****************************************************************
	/**
	 * Query to create the PERFORMANCE table.
	 */
	public static final String CREATE_PERFORMANCE_CRUMB = "create table IF NOT EXISTS "
			+ PERFORMANCE_CRUMB + " ( crumbID IDENTITY PRIMARY KEY,"
			+ " postsTotal INT," + " getsTotal INT," + " postsPerSecond INT, " + " getsPerSecond INT, " + " createAt TIMESTAMP )" ;
	
	public static final String INSERT_PERFORMANCE_CRUMB = "insert into "+ PERFORMANCE_CRUMB + 
			" (postsTotal, getsTotal, postsPerSecond, getsPerSecond, createAt) values (?, ?, ?, ?, CURRENT_TIMESTAMP() )";
	public static final String GET_PERFORMANCE_CRUMB = "select top 1 * from PERFORMANCE_CRUMB order by createAt DESC";
	public static final String DELETE_ALL_PERFORMANCE_CRUMB = "delete from PERFORMANCE_CRUMB";
	
	public static final String CREATE_TEST_MSG = "create table IF NOT EXISTS "
			+ "TEST_MSG " + " ( messageID IDENTITY PRIMARY KEY,"
			+ " author VARCHAR(50)," + " target VARCHAR(50)," + " content VARCHAR(250), " + " messageType VARCHAR(10), " + " postedAt TIMESTAMP )" ;
	
	public static final String DELETE_TEST_MSG = "drop table IF EXISTS TEST_MSG";
	
	//insert
	public static final String INSERT_TEST_MSG = "insert into " + "TEST_MSG" + 
			 " (author, target, content, messageType, postedAt) values (?, ?, ?, ?, CURRENT_TIMESTAMP() )";
	//select latest 100 test msg
	public static final String SELECT_ALL_TEST_MSG ="select top 100 * from TEST_MSG order by postedAt DESC";
	
	
	// ****************************************************************
	// All queries related to MEMORY_CRUMB
	// ****************************************************************
	/**
	 * Query to create the MEMORY_CRUMB table.
	 */
	public static final String CREATE_MEMORY_CRUMB = "create table IF NOT EXISTS "
			+ MEMORY_CRUMB + " ( crumbID IDENTITY PRIMARY KEY,"
			+ " usedVolatile VARCHAR(50)," + " remainingVolatile VARCHAR(50)," + " usedPersistent VARCHAR(50), " + " remainingPersistent VARCHAR(50), " + " createAt TIMESTAMP )" ;
	
	//insert
	public static final String INSERT_MEMORY_CRUMB = "insert into " + MEMORY_CRUMB + 
			 " (usedVolatile, remainingVolatile, usedPersistent, remainingPersistent, createAt) values (?, ?, ?, ?, ?)"; ;
	//delete all
	public static final String DELETE_ALL_MEMORY_CRUMB="delete from MEMORY_CRUMB";
	//select from now to previous X hours
	public static final String SELECT_MEMORY_CRUMB_BY_GIVEN_HOUR = "select * from MEMORY_CRUMB where createAt > ? order by createAt DESC";
	
	// ****************************************************************
	// All queries related to STATUS_CRUMB
	// ****************************************************************
	/**
	 * Query to create the STATUS_CRUMB table.
	 */
	public static final String CREATE_STATUS_CRUMB = "create table IF NOT EXISTS "
			+ STATUS_CRUMB + " ( crumbID IDENTITY PRIMARY KEY,"
			+ " user_name VARCHAR(100)," + " statusCode VARCHAR(10) DEFAULT 'GREEN'," + " locationDesc VARCHAR(512), " + " createdAt TIMESTAMP )" ;
	

	// ****************************************************************
	// All queries related to USERS
	// ****************************************************************
	/**
	 * Query to create the USERS table.
	 */
	public static final String CREATE_USERS = "create table IF NOT EXISTS "
			+ SSN_USERS + " ( user_id IDENTITY PRIMARY KEY,"
			+ " user_name VARCHAR(100)," + " password VARCHAR(512)," + "salt VARCHAR(512)," + "statusCode VARCHAR(50) DEFAULT 'GREEN',"
			+ "  locationDesc VARCHAR(512), lastPostDate TIMESTAMP )" ;
	
	/**
	 * Query to load all users in the system.
	 */
	//public static final String FIND_ALL_USERS = "select user_id, user_name, password,"
	//		+ " salt " + " from " + SSN_USERS + " order by user_name"; //may need to add status + location

	public static final String FIND_ALL_USERS = "select * "+ "from " + SSN_USERS + " order by user_name"; 
	
	/**
	 * Query to find a user details depending on his name. Note that this query
	 * does a case insensitive search with the user name.
	 */

	
	public static final String FIND_USER_BY_NAME = "select *  "
			+ " from "
			+ SSN_USERS
			+ " where UPPER(user_name) = UPPER(?)";	

	/**
	 * Query to find a user details depending on his user_id. 
	 */
	public static final String FIND_USER_BY_ID = "select * from "
			+ SSN_USERS
			+ " where user_id = ?";
	
	/**
	 * Query to insert a row into the users table.
	 */
	public static final String INSERT_USER = "insert into " + SSN_USERS
			+ " (user_name, password, salt) values (?, ?, ?)";
	
	/**
	 * Query to update a row into the users table.
	 * And you also have to insert one into the history table STATUS_CRUMB
	 */
	public static final String UPDATE_USER = "update " + SSN_USERS
			+ " SET statusCode = ? , locationDesc = ? , lastPostDate = CURRENT_TIMESTAMP() where user_name= ?";	
	
	public static final String UPDATE_USER_NAME_PWD = "update " + SSN_USERS
			+ " SET user_name = ? , password = ? , salt = ? , lastPostDate = CURRENT_TIMESTAMP() where user_id = ?";	
	
	public static final String INSERT_STATUS_CRUMB = "insert into " + STATUS_CRUMB + 
			 " (user_name, statusCode, locationDesc, createdAt) values (?, ?, ?, CURRENT_TIMESTAMP())"; 
	
	public static final String FIND_STATUSCRUMB_BY_ID = "select * from " + STATUS_CRUMB + " where crumbID = ?";		
	public static final String FIND_ALL_STATUS_CRUMB_BY_USERNAME = "select * from " + STATUS_CRUMB + " where user_name = ? order by createdAt DESC";
	
	// ****************************************************************
	// All queries related to MESSAGES
	// ****************************************************************
	/**
	 * Query to create the MESSAGE table.
	 */
	public static final String CREATE_MESSAGE = "create table IF NOT EXISTS "
			+ SSN_MESSAGE + " ( message_id IDENTITY PRIMARY KEY,"
			+ " content VARCHAR(512)," + " author BIGINT,"
			+ " messageType VARCHAR(100) , target BIGINT, postedAt VARCHAR(100))"; 
	
	/**
	 * Query to insert message in the system.
	 */
	public static final String INSERT_MESSAGE = "insert into " + SSN_MESSAGE
			+ " (content, author, messageType, target, postedAt) values (?, "
			+ "(select user_id from "
			+ SSN_USERS
			+ " where user_name = ?), ?, "
			+ "(select user_id from "
			+ SSN_USERS
			+ " where user_name = ?), ?)";
	
	/**
	 * Query to find messages on wall
	 */
	public static final String FIND_MESSAGES_ON_WALL = "select message_id, content, "
			+ "user_name, messageType, user_name, postedAt from "
			+ SSN_MESSAGE + ", " + SSN_USERS + " where "
			+ SSN_MESSAGE+".author = "+ SSN_USERS +".user_id AND"
			+" messageType = 'WALL' order by postedAt DESC";
	
	/**
	 * Query to find private chat messages
	 */
	public static final String FIND_CHAT_MESSAGES = "select message_id, content, "
			+ "USER1.user_name, messageType, USER2.user_name, postedAt from "
			+ SSN_MESSAGE + ", " + SSN_USERS + " as USER1, "
			+ SSN_USERS + " as USER2 where "
			+ SSN_MESSAGE+".author = USER1.user_id AND "
			+ SSN_MESSAGE+ ".target = USER2.user_id AND messageType = 'CHAT' "
			+ "AND ((UPPER(USER1.user_name) = UPPER(?) AND UPPER(USER2.user_name) = UPPER(?)) OR "
			+ "(UPPER(USER2.user_name) = UPPER(?) AND UPPER(USER1.user_name) = UPPER(?))) order by postedAt DESC";
	
	/**
	 * Query to find chatbuddies by userName
	 */
	/*
	public static final String FIND_CHATBUDDIES_BY_USER = "select user_name from "
			+ SSN_USERS
			+ " where user_id = ((select target from "
			+ SSN_MESSAGE + ", " + SSN_USERS
			+ " where " + SSN_MESSAGE + ".author = " + SSN_USERS + ".user_id"
			+ " AND " + SSN_MESSAGE + ".messageType = 'CHAT' AND UPPER("
			+ SSN_USERS +".user_name) = UPPER(?)) "
			+ "OR (select author from "
			+ SSN_MESSAGE + ", " + SSN_USERS
			+ " where " + SSN_MESSAGE + ".target = " + SSN_USERS + ".user_id"
			+ " AND " + SSN_MESSAGE + ".messageType = 'CHAT' AND UPPER("
			+ SSN_USERS +".user_name) = UPPER(?)))";
	*/
	public static final String FIND_CHATBUDDIES_BY_USER = "select USER1.user_id, "
			+ "USER1.user_name, USER1.password, USER1.salt, USER1.statusCode, "
			+ "USER1.locationDesc, USER1.lastPostDate"
			+ " from " + SSN_USERS + " as USER1, " + SSN_USERS + " as USER2, "
			+ SSN_MESSAGE + " as MESSAGE where (MESSAGE.author = USER1.user_id "
			+ "AND MESSAGE.target = USER2.user_id AND MESSAGE.messageType = 'CHAT' "
			+ "AND UPPER(USER2.user_name) = UPPER(?)) OR (MESSAGE.author = USER2.user_id "
			+ "AND MESSAGE.target = USER1.user_id AND MESSAGE.messageType = 'CHAT' "
			+ "AND UPPER(USER2.user_name) = UPPER(?)) GROUP BY USER1.user_name";
	/**
	 * Query to find message by ID
	 */
	public static final String FIND_MESSAGE_BY_ID = "select message_id, content,"
			+ "AUTHOR.user_name, messageType, TARGET.user_name, postedAt"
			+ " from "
			+ SSN_MESSAGE + ", " + SSN_USERS + " as AUTHOR, "+ SSN_USERS + " as TARGET"
			+ " where " + SSN_MESSAGE + ".author = AUTHOR.user_id AND "
			+ SSN_MESSAGE + ".target = TARGET.user_id AND "
			+ "message_id = ?";
	
	public static final String FIND_ALL_USER_ID = "select user_name from "+SSN_USERS ;
}

