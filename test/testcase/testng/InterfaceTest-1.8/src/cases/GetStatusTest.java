package cases;

import org.testng.annotations.Test;

import utils.HttpPool;
import utils.Result;
import base.TestBase;

@Test
public class GetStatusTest extends TestBase{

	public void getStatusCheck(){
		String result = HttpPool.doGet("getStatus");
		int error_code = Result.getoutErrCodeFromGet(result);
		check.equals(error_code,0,"getStatus���׳���");
		
		String account_count = Result.getResultTh(result, "account_count");
		check.notEquals(account_count, "0", "getStatus.account_countΪ0");
		check.notEquals(account_count, null, "getStatus.account_countΪnull");
		
		String transaction_count = Result.getResultTh(result, "transaction_count");
		check.notEquals(transaction_count, "0", "getStatus.transaction_countΪ0");
		check.assertNotEquals(transaction_count, null, "getStatus.transaction_countΪnull");
	}
}
