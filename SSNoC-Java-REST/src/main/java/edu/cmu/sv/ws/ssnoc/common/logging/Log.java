package edu.cmu.sv.ws.ssnoc.common.logging;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is a custom logging class for SSNoC Project. Having this class will
 * avoid checking if the logging level applies before logging the content. This
 * results in a lot less code to log content, when compared to not having this.
 * 
 * We also provide extra methods to print enter and exit of methods - which
 * needs to be done for all method's.
 * 
 */
public class Log {
	private static Map<String, Logger> loggerMap = new HashMap<String, Logger>();
	private static final String LOG_SEP = " ## ";
	private static final String ENTER_METHOD = "Enter - method : ";
	private static final String EXIT_METHOD = "Exit - method : ";
	private static final String ERROR_PREFIX = "Error in method: ";

	private static Logger getLogger() {
		String callingClass = Thread.currentThread().getStackTrace()[3]
				.getClassName();
		Logger l = loggerMap.get(callingClass);
		if (l == null) {
			l = LoggerFactory.getLogger(callingClass);
			loggerMap.put(callingClass, l);
		}

		return l;
	}

	private static String getCallingMethodName() {
		String methodName = Thread.currentThread().getStackTrace()[3]
				.getMethodName();

		return methodName;
	}

	private static String getAsString(Object... params) {
		StringBuffer sb = new StringBuffer();
		if (params != null) {
			for (Object param : params) {
				sb.append(LOG_SEP).append(param);
			}
		}

		return sb.toString();
	}

	public static void enter() {
		Logger l = getLogger();
		if (l.isTraceEnabled()) {
			l.trace(ENTER_METHOD + getCallingMethodName());
		}
	}

	public static void enter(Object param) {
		Logger l = getLogger();
		if (l.isTraceEnabled()) {
			l.trace(ENTER_METHOD + getAsString(getCallingMethodName(), param));
		}
	}

	public static void enter(Object... params) {
		Logger l = getLogger();
		if (l.isTraceEnabled()) {
			l.trace(ENTER_METHOD + getAsString(getCallingMethodName(), params));
		}
	}

	public static void exit() {
		Logger l = getLogger();
		if (l.isTraceEnabled()) {
			l.trace(EXIT_METHOD + getCallingMethodName());
		}
	}

	public static void exit(Object param) {
		Logger l = getLogger();
		if (l.isTraceEnabled()) {
			l.trace(EXIT_METHOD + getAsString(getCallingMethodName(), param));
		}
	}

	public static void exit(Object... params) {
		Logger l = getLogger();
		if (l.isTraceEnabled()) {
			l.trace(EXIT_METHOD + getAsString(getCallingMethodName(), params));
		}
	}

	public static void trace(String msg) {
		Logger l = getLogger();
		if (l.isTraceEnabled()) {
			l.trace(msg);
		}
	}

	public static void trace(String msg, Object obj) {
		Logger l = getLogger();
		if (l.isTraceEnabled()) {
			l.trace(msg + LOG_SEP + obj);
		}
	}

	public static void trace(String msg, Object... params) {
		Logger l = getLogger();
		if (l.isTraceEnabled()) {
			l.trace(msg + getAsString(params));
		}
	}

	public static boolean isTraceEnabled() {
		return getLogger().isTraceEnabled();
	}

	public static void debug(String msg) {
		Logger l = getLogger();
		if (l.isDebugEnabled()) {
			l.debug(msg);
		}
	}

	public static void debug(String msg, Object obj) {
		Logger l = getLogger();
		if (l.isDebugEnabled()) {
			l.debug(msg + LOG_SEP + obj);
		}
	}

	public static void debug(String msg, Object... params) {
		Logger l = getLogger();
		if (l.isDebugEnabled()) {
			l.debug(msg + getAsString(params));
		}
	}

	public static boolean isDebugEnabled() {
		return getLogger().isDebugEnabled();
	}

	public static void info(String msg) {
		Logger l = getLogger();
		if (l.isInfoEnabled()) {
			l.info(msg);
		}
	}

	public static void info(String msg, Object obj) {
		Logger l = getLogger();
		if (l.isInfoEnabled()) {
			l.info(msg + LOG_SEP + obj);
		}
	}

	public static void info(String msg, Object... params) {
		Logger l = getLogger();
		if (l.isInfoEnabled()) {
			l.info(msg + getAsString(params));
		}
	}

	public static boolean isInfoEnabled() {
		return getLogger().isInfoEnabled();
	}

	public static void warn(String msg) {
		Logger l = getLogger();
		if (l.isWarnEnabled()) {
			l.warn(msg);
		}
	}

	public static void warn(String msg, Object obj) {
		Logger l = getLogger();
		if (l.isWarnEnabled()) {
			l.warn(msg + LOG_SEP + obj);
		}
	}

	public static void warn(String msg, Object... params) {
		Logger l = getLogger();
		if (l.isWarnEnabled()) {
			l.warn(msg + getAsString(params));
		}
	}

	public static boolean isWarnEnabled() {
		return getLogger().isWarnEnabled();
	}

	public static void error(Throwable t) {
		Logger l = getLogger();
		if (l.isErrorEnabled()) {
			l.error(ERROR_PREFIX + getCallingMethodName(), t);
		}
	}

	public static void error(String msg) {
		Logger l = getLogger();
		if (l.isErrorEnabled()) {
			l.error(ERROR_PREFIX + getCallingMethodName() + msg);
		}
	}

	public static void error(String msg, Object obj) {
		Logger l = getLogger();
		if (l.isErrorEnabled()) {
			l.error(ERROR_PREFIX + getCallingMethodName() + msg + LOG_SEP + obj);
		}
	}

	public static void error(String msg, Object... params) {
		Logger l = getLogger();
		if (l.isErrorEnabled()) {
			l.error(ERROR_PREFIX + getCallingMethodName() + msg
					+ getAsString(params));
		}
	}

	public static boolean isErrorEnabled() {
		return getLogger().isErrorEnabled();
	}
}
