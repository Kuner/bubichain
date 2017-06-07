package listener;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class TestRetryAnalyzer implements IRetryAnalyzer {
	private int retryCount = 1;

	private int maxRetryTimes = 2;

	@Override
	public boolean retry(ITestResult result) {
		if (retryCount <= maxRetryTimes) {

			result.setAttribute("RETRY", retryCount);

			System.out.println("������" + result.getName() + " ���ڽ��е�" + retryCount
					+ "��ʧ������");

			retryCount++;

			return true;

		}

		return false;
	}

}
