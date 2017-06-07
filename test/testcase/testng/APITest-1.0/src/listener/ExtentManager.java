package listener;

import java.io.File;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.NetworkMode;

public class ExtentManager {
	private static ExtentReports extent;

	public synchronized static ExtentReports getReporter(String filePath) {
		if (extent == null) {
			extent = new ExtentReports(filePath, true); // NetworkMode.OFFLINEhtml������ʹ�����ߵ�CSS��JS,
			// extent.assignProject("aaa"); // ONLINEʹ�ñ��汾��Ŀ¼���е�CSS��JS
			extent.loadConfig(new File("./extent-config.xml"));
		}
		return extent;
	}
}
