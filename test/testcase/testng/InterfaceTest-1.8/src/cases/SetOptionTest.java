package cases;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.testng.annotations.Test;

import utils.APIUtil;
import utils.Result;
import utils.SignUtil;
import utils.TxUtil;
import base.Log;
import base.TestBase;
import model.Account;

//@Test
public class SetOptionTest extends TestBase{

//	JSONArray private_key = new JSONArray();
	Object master_weight = 2;
	Object low_threshold = 2;
	Object med_threshold = 2;
	Object high_threshold = 2;
	int type = 4;
	Map acc = TxUtil.createAccount();
	Object source_address = acc.get("address");
	String pri = acc.get("private_key").toString();
	
	Object pub = acc.get("public_key");
	Map acc1 = TxUtil.createAccount();
	Object s1_address = acc1.get("address");
	String pri1 = acc1.get("private_key").toString();
	Map acc2 = TxUtil.createAccount();
	Object s2_address = acc2.get("address");
	String pri2 = acc2.get("private_key").toString();
//	long sequence_number = Result.seq_num(source_address);
	String metadata = "1234";
	
//	 @Test
	@SuppressWarnings("rawtypes")
	public void setOptionCheck(){
		 System.out.println(acc);
		 Account srcAcc = TxUtil.createNewAccount();
		String address1 = s1_address.toString();
		int weight1 = 2;
		String address2 = s2_address.toString();
		int weight2 = 2;
		
		JSONObject threshold = TxUtil.threshold(master_weight, med_threshold, low_threshold, high_threshold);
		List signers = TxUtil.signers(address1, weight1,address2, weight2);
		List operations = TxUtil.operSetOption(type, threshold,signers);
		String response = SignUtil.tx(operations, srcAcc.getAddress(), fee, metadata, srcAcc.getPri_key(), srcAcc.getPub_key());
		int error_code = Result.getErrorCode(response);
		check.assertEquals(error_code, 0, "��������У��ʧ��");
	}
	
//	@Test
	@SuppressWarnings("rawtypes")
	public void typeCheck(){
		Object[] types = {-1,10,"abc","!@#","",null};
		for (Object type : types) {
			
			JSONObject threshold = TxUtil.threshold(master_weight, med_threshold, low_threshold, high_threshold);
			List operations = TxUtil.operSetOption(type, threshold);
			String response = SignUtil.unnormal_tx(operations, source_address,
					fee,  metadata, pri, pub);
			int error_code = Result.getErrorCode(response);
			check.assertNotEquals(error_code, 0, "��������type[" + type + "]У��ʧ��");
		}
		
		
	}
	
//	@Test
	@SuppressWarnings("rawtypes")
	public void master_weight(){
		Object[] thres = {-1,"abc","!@#","",null}; 
		for (Object master_weight : thres) {
			JSONObject threshold = TxUtil.threshold(master_weight, med_threshold, low_threshold, high_threshold);
			List operations = TxUtil.operSetOption(type, threshold);
			String response = SignUtil.unnormal_tx(operations, source_address,
					fee, metadata, pri, pub);
			int error_code = Result.getErrorCode(response);
			check.assertNotEquals(error_code, 0, "��������master_weight["
					+ master_weight
					+ "]У��ʧ��");
		}
	}
	public void med_threshold(){
		Object[] thres = {-1,"abc","!@#","",null}; 
		for (Object med_threshold : thres) {
			JSONObject threshold = TxUtil.threshold(master_weight, med_threshold, low_threshold, high_threshold);
			List operations = TxUtil.operSetOption(type, threshold);
			String response = SignUtil.unnormal_tx(operations, source_address,
					fee,  metadata, pri, pub);
			int error_code = Result.getErrorCode(response);
			check.assertNotEquals(error_code, 0, "��������med_threshold["
					+ master_weight
					+ "]У��ʧ��");
		}
	}
	
	public void low_threshold(){
		Object[] thres = {-1,"abc","!@#","",null}; 
		for (Object low_threshold : thres) {
			JSONObject threshold = TxUtil.threshold(master_weight, med_threshold, low_threshold, high_threshold);
			List operations = TxUtil.operSetOption(type, threshold);
			String response = SignUtil.unnormal_tx(operations, source_address,
					fee, metadata, pri, pub);
			int error_code = Result.getErrorCode(response);
			check.assertNotEquals(error_code, 0, "��������low_threshold["
					+ master_weight
					+ "]У��ʧ��");
		}
	}
	
	public void high_threshold(){
		Object[] thres = {-1,"abc","!@#","",null}; 
		for (Object high_threshold : thres) {
			JSONObject threshold = TxUtil.threshold(master_weight, med_threshold, low_threshold, high_threshold);
			List operations = TxUtil.operSetOption(type, threshold);
			String response = SignUtil.unnormal_tx(operations, source_address,
					fee, metadata, pri, pub);
			int error_code = Result.getErrorCode(response);
			check.assertNotEquals(error_code, 0, "��������high_threshold["
					+ master_weight
					+ "]У��ʧ��");
		}
	}
	
	
//	@Test
	@SuppressWarnings("rawtypes")
	public void metadata_versioninValidCheck(){
		//metadata_version���óɴ����ֵ������У��
		int meta_version = Result.getMetadata_version(source_address);
		Object[] meta_vs = {-1,"qq",null,"",meta_version+2};
		for (Object meta_v : meta_vs) {
			JSONObject threshold = TxUtil.setMv(master_weight, med_threshold, low_threshold, high_threshold,meta_v);
			List operations = TxUtil.operSetOption(type, threshold);
			String response = SignUtil.unnormal_tx(operations, source_address,
					fee, metadata, pri, pub);
			int error_code = Result.getErrorCode(response);
			check.assertNotEquals(error_code, 0, "��������metadata_version["
					+ meta_vs + "]ʧ��");
			
		}
	}
//	@Test
	public void metadata_versionOnlyCheck(){
		//ֻ����metadata_versionӦ����ʾ'high_threshold'  parameter error or 'metadata' not exist
		Log.info("=======ֻ����metadata_version=======");
		int meta_version1 = Result.getMetadata_version(source_address);
		JSONObject threshold = TxUtil.setMv(master_weight, med_threshold, low_threshold, high_threshold,meta_version1);
		List operations = TxUtil.operSetOption(type, threshold);
		String response = SignUtil.unnormal_tx(operations, source_address, fee,
				 metadata, pri, pub);
		int error_code = Result.getErrorCode(response);
		check.assertEquals(error_code, 2, "��������ֻ����metadata_versionʧ��");
	}
//	@Test
	public void metadata_versionValidCheck(){
		//��ȷ����metada_version������У��
		int meta_version1 = Result.getMetadata_version(source_address);
		JSONObject threshold = TxUtil.threshold(master_weight, med_threshold, low_threshold, high_threshold,meta_version1,"1234");
		List operations = TxUtil.operSetOption(type, threshold);
		String response = SignUtil.tx(operations, source_address, fee,
				metadata, pri, pub);
		int error_code = Result.getErrorCode(response);
		check.assertEquals(error_code, 0, "��������ֻ����metadata_versionʧ��");
	}
	
	// @Test
	@SuppressWarnings("rawtypes")
	public void metadata_dataCheck(){
		Object[] meta_ds = {
				"123",
				"qq",
				"0123456789abcdef0123456789abcdef0123456789abcdef0123456789abcdef0123456789abcdef1" };
		for (Object meta_d : meta_ds) {
			JSONObject threshold = TxUtil.setMd(master_weight, med_threshold, low_threshold, high_threshold, meta_d);
			List operations = TxUtil.operSetOption(type, threshold);
			String response = SignUtil.unnormal_tx(operations, source_address,
					fee, metadata, pri, pub);
			int error_code = Result.getErrorCode(response);
			check.assertEquals(error_code, 2, "��������metadata[" + meta_d
					+ "]У��ʧ��");
		}

	}

//	 @Test
	// ֻ����metadata,metadata_version���Զ���1
	public void setMeta_dataOnlyCheck() {
		System.out.println("=======ֻ����metadata=======");
		Map acc1 = TxUtil.createAccount();
		Object source_address = acc1.get("address");
		String pri = acc1.get("private_key").toString();
		Object pub = acc1.get("public_key");
		System.out.println(source_address);
		System.out.println(pri);
		System.out.println(pub);
		int meta_version = Result.getMetadata_version(source_address);
		JSONObject threshold = TxUtil.setMd(master_weight, med_threshold, low_threshold, high_threshold, metadata);
		List operations = TxUtil.operSetOption(type, threshold);
		String response = SignUtil.tx(operations, source_address, fee,
				metadata, pri, pub);
		int error_code = Result.getErrorCode(response);
			int meta_version1 = Result.getMetadata_version(source_address);
		check.assertEquals(meta_version1, meta_version + 1, "ֻ����meta_dataУ��ʧ��");
	}
	
//	@Test
	@SuppressWarnings("rawtypes")
	public void feeCheck(){
		Object master_weight = 1;
		Object low_threshold = 1;
		Object med_threshold = 1;
		Object high_threshold = 1;
		Map acc = TxUtil.createAccount();
		Object source_address = acc.get("address");
		String pri = acc.get("private_key").toString();
		Object pub = acc.get("public_key");
		JSONObject threshold = TxUtil.threshold(master_weight, med_threshold, low_threshold, high_threshold);
		Object[] fees = {-1,0,999,"abc","!@#"};
		for (Object fee : fees) {
			
			List operations = TxUtil.operSetOption(type, threshold);
			String response = SignUtil.unnormal_tx(operations, source_address,
					fee, metadata, pri, pub);
//			System.out.println("response: " + response);
			int error_code = Result.getErrorCode(response);
			check.assertEquals(error_code, 2, "��������fee[" + fee + "]У��ʧ��");
		}
		
	}
	

//	@Test
	@SuppressWarnings("rawtypes")
	public void source_addressCheck(){
		String address = APIUtil.generateAcc().get("address");
		Object[] source_adds = {-1,0,"abc","!@#",address};
		for (Object source_address : source_adds) {
			JSONObject threshold = TxUtil.threshold(master_weight, med_threshold, low_threshold, high_threshold);
			List operations = TxUtil.operSetOption(type, threshold);
			String response = SignUtil.unnormal_tx(operations, source_address,
					fee, metadata, pri, pub);
			int error_code = Result.getErrorCode(response);
			check.assertNotEquals(error_code, 0, "��������source_address["
					+ source_address + "]ʧ��");
		}
		
	}
	
//	@Test
	@SuppressWarnings("rawtypes")
	public void private_keyCheck(){
		Object pri1 = TxUtil.createAccount().get("private_key");
		Object pri2 = APIUtil.generateAcc().get("private_key");

		Object[] pri_keys = { pri1, pri2 };
		// Object[] pri_keys = {-1,10,"abc","!@#","",null};
		for (Object pri_key : pri_keys) {
			String pri = pri_key.toString();
			JSONObject threshold = TxUtil.threshold(master_weight, med_threshold, low_threshold, high_threshold);
			List operations = TxUtil.operSetOption(type, threshold);
			String response = SignUtil.unnormal_tx(operations, source_address,
					fee, metadata, pri, pub);
			int error_code = Result.getErrorCode(response);
			check.assertNotEquals(error_code, 0, "��������private_key[" + pri_key
					+ "]У��ʧ��");
		}
		
	}
	
//	@Test
	public void signersCheck() {
		Map acc = TxUtil.createAccount();
		Object source_address = acc.get("address");
		String pri = acc.get("private_key").toString();
		Object pub = acc.get("public_key");
		//������������ǩ�����˻�Ȩ��Ϊ6
		String address1 = s1_address.toString();
		int weight1 = 6;
		String address2 = s2_address.toString();
		int weight2 = 6;
		//����Դ�˻���Ȩ�أ����޶���10
		Object m_weight = 10;
		Object l_threshold = 10;
		Object m_threshold = 10;
		Object h_threshold = 10;
		System.out.println("address1: " + address1);
		System.out.println("address2: " + address2);
		JSONObject threshold = TxUtil.threshold(m_weight, m_threshold, l_threshold, h_threshold);
		List signers = TxUtil.signers(address1, weight1,address2, weight2);
		List operations = TxUtil.operSetOption(type, threshold,signers);
		String response = SignUtil.tx(operations, source_address, fee, metadata, pri, pub);
		//�������óɹ�
		int error_code = Result.getErrorCode(response);
		check.assertEquals(error_code, 0, "��������signersУ��ʧ��");
	}

	// @Test
	@SuppressWarnings("rawtypes")
	public void signer_addressCheck() {
		Object[] sign_adds = {-1,10,"abc","!@#","",null};
		for (Object sign_add : sign_adds) {
			int weight = 2;
			List signers = TxUtil.signers(sign_add, weight);
			List operations = TxUtil.operSetOption(type, signers);
			String response = SignUtil.unnormal_tx(operations, source_address,
					fee, metadata, pri, pub);
			//�������óɹ�
			int error_code = Result.getErrorCode(response);
			check.assertNotEquals(error_code, 0, "��������signer_address["
					+ sign_add
					+ "]У��ʧ��");
		}
	}

	// @Test
	public void signer_weightCheck() {
		Object[] weights = { -1, "abc", "!@#", "", null };
		for (Object weight : weights) {
			String address = s1_address.toString();
			List signers = TxUtil.signers(address, weight);
			List operations = TxUtil.operSetOption(type, signers);
			String response = SignUtil.unnormal_tx(operations, source_address,
					fee, metadata, pri, pub);
			//�������óɹ�
			int error_code = Result.getErrorCode(response);
			check.assertNotEquals(error_code, 0, "��������signer_weight[" + weight
					+ "]У��ʧ��");
		}
	}
	
}
