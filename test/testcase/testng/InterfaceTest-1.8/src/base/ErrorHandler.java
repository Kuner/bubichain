package base;

import base.Log;

public class ErrorHandler {

	/**
	 * 
	 * ��ϵͳ��־�Ͳ��Ա����м�¼�Զ�����Ϣmessage
	 * 
	 * @param message
	 *            Ҫ������Զ�����Ϣ
	 * 
	 * @param isReport
	 *            �Ƿ��Զ�����Ϣ��������Ա�����
	 */

	public static void continueRunning(String message) {

		Log.error(message);

	}

	/**
	 * 
	 * �ڳ����в����쳣�󣬼�¼message���쳣�Ķ�ջ��Ϣ����־�����ڱ���������Զ�����Ϣmessage
	 * 
	 * @param cause
	 *            ���񵽵�ԭʼ�쳣
	 * 
	 * @param message
	 *            Ҫ������Զ�����Ϣ
	 * 
	 * @param isReport
	 *            �Ƿ��Զ�����Ϣ��������Ա�����
	 */
	public static void continueRunning(Throwable cause, String message) {
		Log.error(message, cause);
	}

	/**
	 * 
	 * �׳�JuiceException������ϵͳ��־�Ͳ��Ա����м�¼�Զ�����Ϣmessage
	 * 
	 * @param message
	 *            Ҫ������Զ�����Ϣ
	 * 
	 * @param isReport
	 *            �Ƿ��Զ�����Ϣ��������Ա�����
	 */

	public static void stopRunning(String message) {

		Log.error(message);

		throw new BubiException(message);

	}

	/**
	 * 
	 * �ڳ����в����쳣�󣬼�¼message���쳣�Ķ�ջ��Ϣ����־���׳�JuiceException�����ڱ���������Զ�����Ϣmessage
	 * 
	 * @param cause
	 *            ���񵽵�ԭʼ�쳣
	 * 
	 * @param message
	 *            Ҫ������Զ�����Ϣ
	 * 
	 * @param isReport
	 *            �Ƿ��Զ�����Ϣ��������Ա�����
	 */

	public static void stopRunning(Throwable cause, String message) {

		Log.error(message, cause);

		throw new BubiException(message);

	}

}

