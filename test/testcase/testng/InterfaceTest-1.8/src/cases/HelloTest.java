package cases;

import org.testng.annotations.Test;

import base.TestBase;
import utils.HttpPool;
import utils.Result;

@Test
public class HelloTest extends TestBase{

	public void helloCheck(){
		String result = HttpPool.doGet("hello");
		String bubi_version = Result.gethelloTh(result, "bubi_version");
		check.notEquals(bubi_version, null, "��ȡbubi_version����");
		
		String ledger_version = Result.gethelloTh(result, "ledger_version");
		check.notEquals(ledger_version, null, "��ȡledger_version����");
		
		String overlay_version = Result.gethelloTh(result, "overlay_version");
		check.assertNotEquals(overlay_version, null, "��ȡoverlay_version����");
	}
}
