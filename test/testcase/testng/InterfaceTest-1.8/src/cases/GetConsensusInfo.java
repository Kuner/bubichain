package cases;

import org.testng.annotations.Test;

import utils.HttpPool;
import utils.Result;
import base.TestBase;

@Test
public class GetConsensusInfo extends TestBase{

//	@Test
	public void getConsensusInfoCheck(){
		String url = "getConsensusInfo";
		String key = "token";
		String value = "bubiokqwer";
		String result = HttpPool.doGet(url, key, value);
		String type = Result.getTypeFromConInfo(result);
		check.assertNotNull(type, "GetConsensusInfo["+type+"]Ϊnull");
	}
//	@Test
	public void validTokenCheck(){
		String url = "getConsensusInfo";
		String key = "token";
		String value = "aa";
		String result = HttpPool.doGet(url, key, value);
		check.assertEquals(result, "Access is not valid", "��ЧtokenУ��ʧ��");
	}
}
