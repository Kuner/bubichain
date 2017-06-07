package cases;

import org.testng.annotations.Test;

import utils.HttpPool;
import utils.Result;
import base.TestBase;
@Test
public class GetPeerNodeAddress extends TestBase{

//	@Test
	public void getPeerCheck(){
		String url = "getPeerNodeAddress";
		String key = "token";
		String value = "bubiokqwer";
		String result = HttpPool.doGet(url, key, value);
		check.assertNotNull(result, "��ȡpeerNodeAddressʧ��");
	}
	
//	@Test
	public void getPeerInvalidCheck(){
		String url = "getPeerNodeAddress";
		String key = "token";
		String value = "aa";
		String result = HttpPool.doGet(url, key, value);
		check.assertEquals(result, "Access is not valid","�Ƿ�token��ȡPeerNodeAddressʧ��");
	}
}
