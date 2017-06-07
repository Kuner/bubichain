package listener;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.xml.XmlSuite;

import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;

public class IReport implements IReporter {

	private ExtentReports extent;

	// String outputDirectory =
	@Override
	public void generateReport(List<XmlSuite> arg0, List<ISuite> arg1,
			String outputDirectory) {

		extent = new ExtentReports(outputDirectory + File.separator
				+ "Extent.html", true// trueΪ�����Ѿ����ɵı��棬false �����еı����������ɣ����Ḳ�ǾɵĽ��
				, DisplayOrder.NEWEST_FIRST // �������е���������ڵ�һ��
		);

		String PROJECTNAME = arg0.get(0).getName();

		String ENDTIMESTRING = "";

		int PASSED_NUMBER = 0;

		int FAILED_NUMBER = 0;

		int SKIPPED_NUMBER = 0;

		int TOTAL_NUMBER = 0;

		float PASSRATE = 0;
//		System.out.println("��Ŀ�� " + arg0.get(0).getName());
//		System.out.println("������������ " + arg1.get(0).getAllMethods().size());
		Date date = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
//		System.out.println("���Խ���ʱ�䣺 " + sf.format(date));

		Map<String, ISuiteResult> map = arg1.get(0).getResults();
		for (String key : map.keySet()) {
			PASSED_NUMBER = map.get(key).getTestContext().getPassedTests()
					.size();
			FAILED_NUMBER = map.get(key).getTestContext().getFailedTests()
					.size();
			SKIPPED_NUMBER = map.get(key).getTestContext().getSkippedTests()
					.size();
		}

//		System.out.println("Passed�� " + PASSED_NUMBER);
//		System.out.println("Failed�� " + FAILED_NUMBER);
//		System.out.println("Skipped�� " + SKIPPED_NUMBER);
//		System.out.println("Total��" + TOTAL_NUMBER);
//
//		System.out.println("Success Rate��" + PASSRATE);

		TOTAL_NUMBER = PASSED_NUMBER + FAILED_NUMBER + SKIPPED_NUMBER;

		PASSRATE = 100 * ((float) PASSED_NUMBER / (TOTAL_NUMBER));

		if (Double.isNaN(PASSRATE)) {

			PASSRATE = 0;

		}

		StringBuffer htmlString = new StringBuffer();

		htmlString.append("<html lang=\"en\">");

		htmlString.append("<h1>" + PROJECTNAME + "</h1>");

		htmlString.append("<p>" + ENDTIMESTRING + "</p>");

		htmlString.append("<p>�ɹ���" + PASSED_NUMBER + "</p>");

		htmlString.append("<p>ʧ�ܣ�" + FAILED_NUMBER + "</p>");

		htmlString.append("<p>������" + SKIPPED_NUMBER + "</p>");

		htmlString.append("<p>�ܼ�: " + TOTAL_NUMBER + "</p>");

		htmlString.append("<p>�ɹ��ʣ� " + PASSRATE + "%</p>");

//		htmlString.append("<p>������־��</p>");
//
//		for (String text : WebSuite.resultLog) {
//
//			htmlString.append("<p>" + text + "</p>");
//
//		}

		htmlString.append("</html>");

		write("test-output/HtmlReport/" +

		new SimpleDateFormat("YYYYMMddHHmmss").format(new Date()) + ".html"

		, htmlString.toString());

	}

	public static void write(String filePath, String data) {

		File file = new File(filePath);

		if (!file.exists()) {

			try {

				file.createNewFile();

			} catch (IOException e) {

				// TODO Auto-generated catch block

				e.printStackTrace();

			}

		}

		BufferedWriter bw = null;

		try {

			bw = new BufferedWriter(new FileWriter(file.getAbsolutePath()));

			bw.write(data);

		} catch (IOException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		} finally {

			try {

				bw.flush();

				bw.close();

			} catch (IOException e) {

				// TODO Auto-generated catch block

				e.printStackTrace();

			}

		}

	}

}
