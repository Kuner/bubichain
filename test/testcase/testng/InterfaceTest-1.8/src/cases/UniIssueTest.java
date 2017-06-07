package cases;

import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import utils.APIUtil;
import utils.Result;
import utils.SignUtil;
import utils.TxUtil;
import base.TestBase;

//����Ψһ�ʲ� 
@Test
public class UniIssueTest extends TestBase{

	int type = 7;
	@SuppressWarnings("rawtypes")
	Map acc = TxUtil.createAccount();
	Object source_address = acc.get("address");
	String pri = acc.get("private_key").toString();
	Object pub = acc.get("public_key");
	String asset_code = "abc" ;
	String asset_issuer = led_acc; 
	String asset_detailed = "1234";
	String s_address = led_acc;
	String s_key = led_pri;
//	long sequence_number = Result.seq_num(source_address);
	String metadata = "1234";

	public void uniIssueCheck(){
		Object asset_issuer = source_address;
		List opers = TxUtil.operUniIssue(type, asset_issuer, asset_code, asset_detailed);
		String result = SignUtil.tx(opers, source_address, fee, metadata, pri, pub);
		int err_code = Result.getErrorCode(result);
		check.assertEquals(err_code, 0, "����Ψһ�ʲ�ʧ��");
		
	}
	
	public void typeCheck(){
		Object[] types = {0,20,-1,"abc","!@#","",null};
		for (Object type : types) {
			List opers = TxUtil.operUniIssue(type, asset_issuer, asset_code, asset_detailed);
			String result = SignUtil.unnormal_tx(opers, source_address, fee,
					 metadata, pri, pub);
			int err_code = Result.getErrorCode(result);
			check.assertNotEquals(err_code, 0, "����Ψһ�ʲ�type[" + type + "]У��ʧ��");
		}
	}
	
//	@Test
	public void asset_typeCheck(){
		Object[] asset_types = {0,20,-1,"abc","!@#","",null};
		for (Object asset_type : asset_types) {
			List opers = TxUtil.operUniIssue(type, asset_type,asset_issuer, asset_code, asset_detailed);
			String result = SignUtil.unnormal_tx(opers, source_address, fee,
					metadata, pri, pub);
			int err_code = Result.getErrorCode(result);
			check.assertEquals(err_code, 2, "����Ψһ�ʲ�asset_type[" + asset_type + "]У��ʧ��");
		}
	}
//	@Test
	public void asset_issuerCheck(){
		Object[] asset_issuers = {-1,"abc","!@#","",null};
		for (Object asset_issuer : asset_issuers) {
			List opers = TxUtil.operUniIssue(type, asset_issuer, asset_code, asset_detailed);
			String result = SignUtil.unnormal_tx(opers, source_address, fee,
					metadata, pri, pub);
			int err_code = Result.getErrorCode(result);
			check.assertNotEquals(err_code, 0, "����Ψһ�ʲ�asset_issuer["
					+ asset_issuer
					+ "]У��ʧ��");
		}
	}
	
//	@Test
	public void asset_codeCheck(){
		Object[] asset_codes = {-1,0,"qqqqqqqq","",null};
		for (Object asset_code : asset_codes) {
			List opers = TxUtil.operUniIssue(type, asset_issuer, asset_code, asset_detailed);
			String result = SignUtil.unnormal_tx(opers, source_address, fee,
					metadata, pri, pub);
			int err_code = Result.getErrorCode(result);
			check.assertNotEquals(err_code, 0, "����Ψһ�ʲ�asset_code[" + asset_code
					+ "]У��ʧ��");
		}
	}
	
//	@Test
	public void asset_detailedCheck(){
		Object[] asset_detaileds = {"abc","qq",0,-1,"",null};
		for (Object asset_detailed : asset_detaileds) {
			List opers = TxUtil.operUniIssue(type, asset_issuer, asset_code, asset_detailed);
			String result = SignUtil.unnormal_tx(opers, source_address, fee,
					metadata, pri, pub);
			int err_code = Result.getErrorCode(result);
			check.assertNotEquals(err_code, 0, "����Ψһ�ʲ�asset_detailed["
					+ asset_detailed + "]У��ʧ��");
		}
	}
	
//	@Test
	public void feeCheck(){
		Object[] fees = {-1,0,999,"abc","!@#","",null};
		for (Object fee : fees) {
			List opers = TxUtil.operUniIssue(type, asset_issuer, asset_code, asset_detailed);
			String result = SignUtil.unnormal_tx(opers, source_address, fee,
					 metadata, pri, pub);
			int err_code = Result.getErrorCode(result);
			check.assertNotEquals(err_code, 0, "����Ψһ�ʲ�fee[" + fee + "]У��ʧ��");
		}
	}
	
	@Test(priority = 4)
	public void source_addressCheck(){
		Object[] source_adds = {-1,0,"abc","!@#"};
		for (Object source_address : source_adds) {
			List opers = TxUtil.operUniIssue(type, asset_issuer, asset_code, asset_detailed);
			String result = SignUtil.unnormal_tx(opers, source_address, fee,
					 metadata, pri, pub);
			int err_code = Result.getErrorCode(result);
			check.assertNotEquals(err_code, 0, "����Ψһ�ʲ�source_address["
					+ source_address + "]У��ʧ��");
		}
	}
	
	public void private_keyCheck(){
		Object pri1 = TxUtil.createAccount().get("private_key");
		Object pri2 = APIUtil.generateAcc().get("private_key");

		Object[] pri_keys = { pri1, pri2 };
		for (Object pri_key : pri_keys) {
			String pri = pri_key.toString();
			List opers = TxUtil.operUniIssue(type, asset_issuer, asset_code,
					asset_detailed);
			String result = SignUtil.unnormal_tx(opers, source_address, fee,
					 metadata, pri, pub);
			int err_code = Result.getErrorCode(result);
			check.assertNotEquals(err_code, 0, "����Ψһ�ʲ�[" + pri_key + "]ʧ��");
		}
	}

	
}
