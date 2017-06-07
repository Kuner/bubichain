package cases;

import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import utils.APIUtil;
import utils.Result;
import utils.SignUtil;
import utils.TxUtil;
import base.TestBase;
import model.Account;

@Test
public class UniIssueTransferTest extends TestBase{

	//ת��Ψһ�ʲ�
	int type = 8;
	Map acc = TxUtil.createAccount(); // ����һ���˻�������Ψһ�ʲ�
	Object source_address = acc.get("address");
	String pri = acc.get("private_key").toString();
	Object pub = acc.get("public_key");
	Object dest_address = TxUtil.createAccount().get("address");
	Object address = APIUtil.generateAcc().get("address");
	String asset_code = "abcd";
	Object asset_issuer = source_address;
//	long sequence_number = Result.seq_num(source_address);
	String metadata = "1234";
	String asset_detailed = "1234";

	@SuppressWarnings("rawtypes")
//	 @Test
	public void uniIssueTransferCheck() {
		Account srcAcc = TxUtil.createNewAccount();
		TxUtil.issueUnique(srcAcc, asset_code, asset_detailed);
		List opers = TxUtil.operUniIssueTransfer(type, dest_address, srcAcc.getAddress(), asset_code);
		String result = SignUtil.tx(opers, srcAcc.getAddress(), fee, metadata, srcAcc.getPri_key(),
				srcAcc.getPub_key());
		int err_code = Result.getErrorCode(result);
		check.assertEquals(err_code, 0, "ת��Ψһ�ʲ�����");
	}

//	 @Test
	public void uniIssueTransferwithoutIssueCheck() {
		 Map acc = TxUtil.createAccount(); // ����һ���˻�������Ψһ�ʲ�
			Object source_address = acc.get("address");
			String pri = acc.get("private_key").toString();
			Object pub = acc.get("public_key");
		List opers = TxUtil.operUniIssueTransfer(type, dest_address,
				asset_issuer, asset_code);
		String result = SignUtil.tx(opers, source_address, fee,
				metadata, pri, pub);
		int err_code = Result.getErrorCode(result);
		check.assertEquals(err_code, 104, "û��Ψһ�ʲ�����ת�ˣ�У��ʧ��");
	}

//	 @Test
	public void typeCheck(){
		Object[] types = {0,20,-1,"abc","!@#","",null};
		for (Object type : types) {
			List opers = TxUtil.operUniIssueTransfer(type, dest_address, asset_issuer, asset_code);
			String result = SignUtil.unnormal_tx(opers, source_address, fee,
					metadata, pri, pub);
			int error_code = Result.getErrorCode(result);
			check.assertNotEquals(error_code, 0, "ת���ʲ�type[" + type + "]У�����");
			
		}
	}
	
//	@Test
	public void asset_typeCheck(){
		Object[] asset_types = {0,20,-1,"abc","!@#","",null};
		for (Object asset_type : asset_types) {
			List opers = TxUtil.operUniIssueTransfer(type, asset_type,dest_address, asset_issuer, asset_code);
			String result = SignUtil.unnormal_tx(opers, source_address, fee,
					metadata, pri, pub);
			int err_code = Result.getErrorCode(result);
			check.assertEquals(err_code, 2, "ת��Ψһ�ʲ�asset_type[" + asset_type + "]У��ʧ��");
		}
	}
	
	public void dest_addressCheck(){
		Object[] dest_adds = {0,-1,"abc","!@#","",null};
		for (Object dest_address : dest_adds) {
			List opers = TxUtil.operUniIssueTransfer(type, dest_address, asset_issuer, asset_code);
			String result = SignUtil.unnormal_tx(opers, source_address, fee,
					 metadata, pri, pub);
			int error_code = Result.getErrorCode(result);
			check.assertNotEquals(error_code, 0, "ת���ʲ�dest_address["
					+ dest_address
					+ "]У�����");
		}
	}
//	@Test
	public void dest_addressEqualSourceAddCheck(){
		//��֤Ψһ�ʲ�ת�˵�Ŀ�ĵ�ַ�������Լ�
		int type_ = 7; // ����Ψһ�ʲ�������
		List opers_ = TxUtil.operUniIssue(type_, asset_issuer, asset_code,
				asset_detailed);
		String result_ = SignUtil.tx(opers_, source_address, fee,
				 metadata, pri, pub);
		int error_code = Result.getErrorCode(result_);
		if(error_code==0){
			List opers = TxUtil.operUniIssueTransfer(type, source_address, asset_issuer, asset_code);
			String result = SignUtil.unnormal_tx(opers, source_address, fee,
					metadata, pri, pub);
			int err_code = Result.getErrorCode(result);
			check.assertEquals(err_code, 4, "ת���ʲ�dest_address["
					+ dest_address
					+ "]У�����");
		}else {
			System.out.println("Ψһ�ʲ�����ʧ��");
		}
	}
	
	public void asset_issuerCheck(){
		Object[] asset_issuers = { -1, "abc", "!@#", "", null, address };
		for (Object asset_issuer : asset_issuers) {
			List opers = TxUtil.operUniIssueTransfer(type, dest_address, asset_issuer, asset_code);
			String result = SignUtil.unnormal_tx(opers, source_address, fee,
					metadata, pri, pub);
			int error_code = Result.getErrorCode(result);
			check.assertNotEquals(error_code, 0, "ת���ʲ�asset_issuer["
					+ asset_issuer
					+ "]У�����");
			
		}
	}
	
	public void asset_codeCheck(){
		Object[] asset_codes = { -1, 0, "qqqqqqqq", null };
		for (Object asset_code : asset_codes) {
			List opers = TxUtil.operUniIssueTransfer(type, dest_address, asset_issuer, asset_code);
			String result = SignUtil.unnormal_tx(opers, source_address, fee,
					metadata, pri, pub);
			int error_code = Result.getErrorCode(result);
			check.assertNotEquals(error_code, 0, "ת��Ψһ�ʲ�asset_code["
					+ asset_code
					+ "]У��ʧ��");
		}
	}
	
//	@Test
	public void feeCheck(){
		Object[] fees = {-1,0,fee-1,"abc","!@#","",null};
		for (Object fee : fees) {
			List opers = TxUtil.operUniIssueTransfer(type, dest_address, asset_issuer, asset_code);
			String result = SignUtil.unnormal_tx(opers, source_address, fee,
					metadata, pri, pub);
			int error_code = Result.getErrorCode(result);
			check.assertNotEquals(error_code, 0, "ת��Ψһ�ʲ�fee[" + fee + "]У�����");
		}
	}
	
//	@Test
	public void source_addressCheck(){
		Object[] source_adds = {-1,0,"abc","!@#",address};
		for (Object source_address : source_adds) {
			List opers = TxUtil.operUniIssueTransfer(type, dest_address, asset_issuer, asset_code);
			String result = SignUtil.unnormal_tx(opers, source_address, fee,
					metadata, pri, pub);
			int error_code = Result.getErrorCode(result);
			check.assertNotEquals(error_code, 0, "ת��Ψһ�ʲ�source_address["
					+ source_address + "]У�����");
		}
	}
	
	// @Test
	public void private_keyCheck(){
		Object pri1 = TxUtil.createAccount().get("private_key");
		Object pri2 = APIUtil.generateAcc().get("private_key");

		Object[] private_keys = { pri1, pri2 };
		for (Object private_key : private_keys) {
			String pri = private_key.toString();
			List opers = TxUtil.operUniIssueTransfer(type, dest_address, asset_issuer, asset_code);
			String result = SignUtil.unnormal_tx(opers, source_address, fee,
					metadata, pri, pub);
			int error_code = Result.getErrorCode(result);
			check.assertNotEquals(error_code, 0, "ת��Ψһ�ʲ�private_key[" + pri
					+ "]У�����");
		}
	}
}
