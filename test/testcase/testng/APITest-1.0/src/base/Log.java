package base;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class Log {

static final Logger logger = LogManager.getLogger(Log.class.getName());

	static{

		DOMConfigurator.configure("log4j.xml");

	}

	/**
	 * Debug����LOG
	 * @param msg �û���ֵ��������ӡ������
	 */

	public synchronized static void debug(String msg){

		logger.log(Log.class.getName(), Level. DEBUG, msg, null);

	}

	/**

	 * Info����LOG

	 * @param msg �û���ֵ��������ӡ������

	 */

	public synchronized static void info(String msg){
//		 logger.log(Log.class.getName(), Level.
//		 INFO, msg, null);
//		 logger.log(Log.class.getCanonicalName(),
//		 Level.INFO,
//		 msg, null);
		logger.log(logger.getClass().getName(),Level.INFO, msg,null);

	}



	

	/**

	 * Warn�����LOG

	 * @param msg �û���ֵ��������ӡ������

	 */

	public synchronized static void warn(String msg){

		logger.log(Log.class.getName(), Level. WARN, msg, null);

	}

	

	/**

	 * Error�����LOG

	 * @param msg �û���ֵ��������ӡ������

	 */

	public synchronized static void error(String msg){

		logger.log(Log.class.getName(), Level. ERROR, msg, null);

	}

	

	public synchronized static void error(String msg, Throwable cause){

		logger.log(Log.class.getName(), Level. ERROR, msg, cause);

	}

	


}
