package cases;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.testng.annotations.Test;

import utils.APIUtil;
import utils.HttpPool;
import utils.Result;
import utils.SignUtil;
import utils.TxUtil;
import base.TestBase;

@Test
public class IssueTest_ledger extends TestBase{
	String geturl = get_Url2;
	int type = 2;
	int asset_type = 1;
	Map acc = TxUtil.createAccount();
	Object address = acc.get("address");
	String pri = acc.get("private_key").toString();
	Object pub = acc.get("public_key");
	Object asset_issuer = address;
	String asset_code = "abc" ;
	int asset_amount = 100;
	Object source_address = address;
	long sequence_number = Result.seq_num(address);
	String metadata = "abcd";

	// @Test
	@SuppressWarnings("rawtypes")
	public void issue(){
		sequence_number = Result.seq_num(address);
		List opers = TxUtil.operIssue(type, asset_type, asset_issuer, asset_code, asset_amount);		
		String result = SignUtil.tx(opers, source_address, fee, sequence_number, metadata, pri, pub);
		int err_code = Result.getErrorCode(geturl,result);
		check.assertEquals(err_code, 0, "�ʲ�����ʧ��");
	}
	
	public void oper_sourceAddValidCheck(){
		//check ERRCODE_ASSET_INVALID
		sequence_number = Result.seq_num(address);
		List opers = TxUtil.operIssue(type, source_address,asset_type, asset_issuer, asset_code, asset_amount);		//ledger����δ��ʼ���ʲ�
		String result = SignUtil.tx(opers, source_address, fee, sequence_number, metadata, pri, pub);
		int err_code = Result.getErrorCode(geturl,result);
		check.assertEquals(err_code, 0, "�ʲ�����ʧ��");
	}
	
	public void operationSizeCheck(){
		//Operation size is zero
		sequence_number = Result.seq_num(address);
		List opers = new ArrayList<>();
		String result = SignUtil.tx(opers, source_address, fee, sequence_number, metadata, pri, pub);
		int err_code = Result.getErrorCode(geturl,result);
		check.assertEquals(err_code, 0, "�ʲ�����operationΪ0 У��ʧ��");
	}
	
	public void tranMetadataCheck(){
		List opers = TxUtil.operIssue(type, asset_type, asset_issuer, asset_code, asset_amount);		
		sequence_number = Result.seq_num(address);
		String result = SignUtil.tx(opers, source_address, fee, sequence_number, 1048580, pri, pub);
		int err_code = Result.getErrorCode(geturl,result);
		check.assertEquals(err_code, 4, "�ʲ�����transaction metadataУ��ʧ��");
	}
//	@Test
	public void min_timeinValidCheck(){
		String response = HttpPool.doGet("getLedger");
		String close_time = Result.getResultTh(response, "close_time");
		Long max_time = Long.parseLong(close_time)+10000;
		Object[] time = {-1,"aa"};
		for (Object min_time : time) {
			JSONObject time_range = TxUtil.timeRange(min_time, max_time);
			sequence_number = Result.seq_num(source_address);
			List opers = TxUtil.operIssue(type, asset_type, asset_issuer, asset_code, asset_amount);		//ledger����δ��ʼ���ʲ�
			String result = SignUtil.tx(opers, source_address, fee, sequence_number, metadata,time_range, pri, pub);
			int err_code = Result.getErrorCode(geturl,result);
			check.assertEquals(err_code, 4,"�Ƿ�min_time["+min_time+"]У��ʧ��");
		}
	}
	
//	@Test
	public void max_timeinValidCheck(){
		String response = HttpPool.doGet("getLedger");
		String close_time = Result.getResultTh(response, "close_time");
		Long min_time = Long.parseLong(close_time)-10000;
		Object[] time = {-1,"aa"};
		for (Object max_time : time) {
			JSONObject time_range = TxUtil.timeRange(min_time, max_time);
			sequence_number = Result.seq_num(address);
			List opers = TxUtil.operIssue(type, asset_type, asset_issuer, asset_code, asset_amount);		//ledger����δ��ʼ���ʲ�
			String result = SignUtil.tx(opers, source_address, fee, sequence_number, metadata,time_range, pri, pub);
			int err_code = Result.getErrorCode(geturl,result);
			check.assertEquals(err_code, 4,"�Ƿ�max_time["+max_time+"]У��ʧ��");
		}
	}
	
//	@Test
	public void time_rangeValidCheck(){
		String response = HttpPool.doGet("getLedger");
		String close_time = Result.getResultTh(response, "close_time");
		long time = System.currentTimeMillis();
		System.out.println(time);
		Long min_time = Long.parseLong(close_time)-20000000;
		Long max_time = Long.parseLong(close_time)*2;
			JSONObject time_range = TxUtil.timeRange(min_time, max_time);
			sequence_number = Result.seq_num(address);
			List opers = TxUtil.operIssue(type, asset_type, asset_issuer, asset_code, asset_amount);		//ledger����δ��ʼ���ʲ�
			String result = SignUtil.tx(opers, source_address, fee, sequence_number, metadata,time_range, pri, pub);
			int err_code = Result.getErrorCode(geturl,result);
			check.assertEquals(err_code, 0,"timerangeУ��ʧ��");
	}
	
//	@Test
	@SuppressWarnings("rawtypes")
	public void asset_issuerCheck(){
		sequence_number = Result.seq_num(address);
		Object[] asset_issuers = {0,20,-1,"abc","!@#","",null};
		for (Object asset_issuer : asset_issuers) {
			List opers = TxUtil.operIssue(type, asset_type, asset_issuer, asset_code, asset_amount);		//ledger����δ��ʼ���ʲ�
			String result = SignUtil.unnormal_tx(opers, source_address, fee,
					sequence_number, metadata, pri, pub);
			int err_code = Result.getErrorCode(geturl,result);
			check.assertEquals(err_code, 4, "�ʲ�����asset_issuer["
					+ asset_issuer + "]У��ʧ��");
		}
		
		//�����ڵ��˻������ʲ�ʧ��
//		Map acc = APIUtil.generateAcc();
//		Object ad1 = acc.get("address");
//		Object pr1 = acc.get("private_key");
//		keys.add(pr1);
//		List opers = TxUtil.operations(type,asset_type,ad1,asset_code,asset_amount);
//		JSONObject tran = TxUtil.transaction(ledger, opers);
//		List items = TxUtil.items(keys, tran);
//		JSONObject jsonObject = TxUtil.tx(items);
//		String response = HttpUtil.dopost(jsonObject);
//		APIUtil.wait(2);
////		System.out.println("response====="+response);
//		int error_code = Result.getErrCode(response);
//		if (Integer.valueOf(error_code)!=0) {
//			System.out.println("asset_issuer[" + ad1+ "] �ʲ�����ʧ�ܣ�error_code��" + error_code + " error_desc: " + Result.getErrDesc(response));
////			check.notEquals(Result.getBalance(acinfo), null, message);
//		} else {
//			String result = Result.getReponse(Result.getHash(response));
//			APIUtil.wait(2);
//			int err_code = Result.getErrCodeTran(result);
//			if(Integer.valueOf(err_code)==0){
//				System.out.println("�ʲ����гɹ�");
//			}else{
//				System.out.println("asset_issuer[" + ad1+ "] �ʲ�����ʧ�ܣ�error_code: " + err_code);
//			}
//		}
		
//		Map acc2 = TxUtil.createAccount();
//		Object ad2 = acc2.get("address");
//		Object pr2 = acc2.get("private_key");
//		keys.add(pr2);
//		List opers2 = TxUtil.operations(type,asset_type,ad2,asset_code,asset_amount);
//		JSONObject tran2 = TxUtil.transaction(ledger, opers2);
//		List items2 = TxUtil.items(keys, tran2);
//		JSONObject jsonObject2 = TxUtil.tx(items2);
//		String response1 = HttpUtil.dopost(jsonObject2);
//		APIUtil.wait(2);
////		System.out.println("response====="+response);
//		int error_code2 = Result.getErrCode(response1);
//		if (Integer.valueOf(error_code2)!=0) {
//			System.out.println("asset_issuer[" + ad2+ "] �ʲ�����ʧ�ܣ�error_code��" + error_code2 + " error_desc: " + Result.getErrDesc(response1));
////			check.notEquals(Result.getBalance(acinfo), null, message);
//		} else {
//			String result = Result.getReponse(Result.getHash(response1));
//			APIUtil.wait(2);
//			int err_code = Result.getErrCodeTran(result);
//			if(Integer.valueOf(err_code)==0){
//				System.out.println("�ʲ����гɹ�");
//			}else{
//				System.out.println("asset_issuer[" + ad2+ "] �ʲ�����ʧ�ܣ�error_code: " + err_code);
//			}
//		}
	}
	
//	@Test
	@SuppressWarnings("rawtypes")
	public void asset_codeCheck(){

		int asset_amount = 100;
		int type = 2 ;
		int asset_type = 1;
		
		//Ԥ�ڳɹ�
		Object[] asset_codes = {"abc","!@#","1234567890abcdef"};
		for (Object asset_code : asset_codes) {
			sequence_number = Result.seq_num(address);
			List opers = TxUtil.operIssue(type, asset_type, asset_issuer, asset_code, asset_amount);		//ledger����δ��ʼ���ʲ�
			String result = SignUtil.tx(opers, source_address, fee,
					sequence_number, metadata, pri, pub);
			int err_code = Result.getErrorCode(geturl,result);
			check.equals(err_code, 0,"�ʲ�����asset_code["+asset_code+"]У��ʧ��");
		}
		//Ԥ��ʧ��
		Object[] asset_codes2 = {0,20,-1,"",null,"1234567890abcdef1234567890abcdef1234567890abcdef1234567890abcdef1"};
		for (Object asset_code : asset_codes2) {
			List opers = TxUtil.operIssue(type, asset_type, asset_issuer, asset_code, asset_amount);		//ledger����δ��ʼ���ʲ�
			String result = SignUtil.unnormal_tx(opers, source_address, fee,
					sequence_number, metadata, pri, pub);
			int err_code = Result.getErrorCode(geturl,result);
			check.assertEquals(err_code, 4, "�ʲ�����asset_code[" + asset_code
					+ "]У��ʧ��");
		}
	}
	
//	@Test
	@SuppressWarnings("rawtypes")
	public void asset_amountCheck(){
		sequence_number = Result.seq_num(address);
		Object[] asset_amounts = {0,"",null,"abc","!@#"};
		for (Object asset_amount : asset_amounts) {
			List opers = TxUtil.operIssue(type, asset_type, asset_issuer, asset_code, asset_amount);		//ledger����δ��ʼ���ʲ�
			String result = SignUtil.unnormal_tx(opers, source_address, fee,
					sequence_number, metadata, pri, pub);
			int err_code = Result.getErrorCode(geturl,result);
			check.assertEquals(err_code, 4, "�ʲ�����asset_amount["
					+ asset_amount + "]У��ʧ��");
		}
		
	}
	
//	 @Test
	@SuppressWarnings("rawtypes")
	public void details_amountCheck(){
		sequence_number = Result.seq_num(address);
		String asset_code = "abc";
		int asset_amount = 10;
		//Ԥ��ʧ��
		Object start = 0;
		Object length = -1;
		Object ext = "abcd";
		Object[] detatils_amounts = {0,-1,"",null,"abc","!@#",asset_amount+1};
		for (Object detatils_amount : detatils_amounts) {
			List detail = TxUtil.details(detatils_amount, start, length, ext);
			List opers = TxUtil.operIssue(type, asset_type, asset_issuer,
					asset_code, asset_amount, detail); // ledger����δ��ʼ���ʲ�
			String result = SignUtil.unnormal_tx(opers, source_address, fee,
					sequence_number, metadata, pri, pub);
			int err_code = Result.getErrorCode(geturl,result);
			check.assertEquals(err_code, 4, "�ʲ�����detatils_amount["
					+ detatils_amount + "]У��ʧ��");
		}
	}
	
	// @Test
	@SuppressWarnings("rawtypes")
	public void details_startCheck(){
		sequence_number = Result.seq_num(address);
		// long s = System.currentTimeMillis()/1000-10000 ;
		//Ԥ��ʧ��
		Object detatils_amount = 100;
		Object length = -1;
		Object ext = "abcd";
		Object[] detatils_starts = { -1, "", null, "abc", "!@#" };
		for (Object detatils_start : detatils_starts) {
			List detail = TxUtil.details(detatils_amount, detatils_start,
					length, ext);
			List opers = TxUtil.operIssue(type, asset_type, asset_issuer,
					asset_code, asset_amount, detail); // ledger����δ��ʼ���ʲ�
			String result = SignUtil.unnormal_tx(opers, source_address, fee,
					sequence_number, metadata, pri, pub);
			int err_code = Result.getErrorCode(geturl,result);
			check.assertEquals(err_code,4, "�ʲ�����detatils_start["
					+ detatils_start + "]У��ʧ��");
		}
	}
	
	// @Test
	@SuppressWarnings("rawtypes")
	public void details_lengthCheck(){
		sequence_number = Result.seq_num(address);
		Object detatils_amount = 100;
		Object start = 0;
		Object ext = "7777";
		Object[] detatils_lengths = { "", null, "abc", "!@#" };
		for (Object detatils_length : detatils_lengths) {
			List detail = TxUtil.details(detatils_amount, start,
					detatils_length, ext);
			List opers = TxUtil.operIssue(type, asset_type, asset_issuer,
					asset_code, asset_amount, detail); // ledger����δ��ʼ���ʲ�
			String result = SignUtil.unnormal_tx(opers, source_address, fee,
					sequence_number, metadata, pri, pub);
			int err_code = Result.getErrorCode(geturl,result);
			check.assertEquals(err_code, 4, "�ʲ�����detatils_length["
					+ detatils_length + "]У��ʧ��");
		}
		
	}
	
//	 @Test
	@SuppressWarnings("rawtypes")
	public void details_extCheck(){
		sequence_number = Result.seq_num(address);
		Object detatils_amount = 100;
		Object start = 0;
		Object length = -1;
		Object[] detatils_exts = {
				"1234567890abcdef1234567890abcdef1234567890abcdef1234567890abcdef1234567890abcdef" };
		for (Object detatils_ext : detatils_exts) {
			List detail = TxUtil.details(detatils_amount, start, length,
					detatils_ext);
			List opers = TxUtil.operIssue(type, asset_type, asset_issuer,
					asset_code, asset_amount, detail); // ledger����δ��ʼ���ʲ�
			String result = SignUtil.unnormal_tx(opers, source_address, fee,
					sequence_number, metadata, pri, pub);
			int err_code = Result.getErrorCode(geturl,result);
			check.assertEquals(err_code, 4, "�ʲ�����detatils_ext["
					+ detatils_ext + "]У��ʧ��");
		}
	}
	
//	@Test
	@SuppressWarnings("rawtypes")
	public void feeCheck(){
		sequence_number = Result.seq_num(address);
		String asset_code = "abc" ;
		int asset_amount = 10;
		Object[] fees = {-1,0,999,"abc","!@#","",null};
		for (Object fee : fees) {
			List opers = TxUtil.operIssue(type, asset_type, asset_issuer, asset_code, asset_amount);		//ledger����δ��ʼ���ʲ�
			String result = SignUtil.unnormal_tx(opers, source_address, fee,
					sequence_number, metadata, pri, pub);
			int err_code = Result.getErrorCode(geturl,result);
			check.assertEquals(err_code, 4, "�ʲ�����fee[" + fee + "]У��ʧ��");
		}
	}
//	@Test
	public void opera_metadataCheck(){
		String asset_code = "abc" ;
		int asset_amount = 10;
		Object[] metadatas = {-1,0,999,"abc","!@#",1048579};
		for (Object metadata_ : metadatas) {
			List opers = TxUtil.operIssue(type, asset_type, asset_issuer, asset_code, asset_amount,metadata_,source_address);		//ledger����δ��ʼ���ʲ�
			sequence_number = Result.seq_num(address);
			String result = SignUtil.unnormal_tx(opers, source_address, fee,
					sequence_number, metadata, pri, pub);
			int err_code = Result.getErrorCode(geturl,result);
			check.assertEquals(err_code, 2, "operation metadata[" + metadata_ + "]У��ʧ��");
			
		}
		
	}
	
//	@Test
	public void opera_sourceaddressCheck(){
		sequence_number = Result.seq_num(address);
		String asset_code = "abc" ;
		int asset_amount = 10;
		Object[] sourceadds = {-1,0,999,"abc","!@#","",null};
		for (Object sourceadd : sourceadds) {
			List opers = TxUtil.operIssue(type, asset_type, asset_issuer, asset_code, asset_amount,metadata,sourceadd);		//ledger����δ��ʼ���ʲ�
			String result = SignUtil.unnormal_tx(opers, source_address, fee,
					sequence_number, metadata, pri, pub);
			int err_code = Result.getErrorCode(geturl,result);
			check.assertEquals(err_code, 4, "operation source_address[" + sourceadd + "]У��ʧ��");
			
		}
		
	}
	
//	@Test
	@SuppressWarnings("rawtypes")
	public void source_addressCheck(){
		Object add = APIUtil.generateAddress();
		sequence_number = Result.seq_num(address);
		Object[] source_adds = {-1,0,"abc","!@#","",null,add};
		for (Object source_add : source_adds) {
			List opers = TxUtil.operIssue(type, asset_type, asset_issuer, asset_code, asset_amount);		//ledger����δ��ʼ���ʲ�
			String result = SignUtil.unnormal_tx(opers, source_add, fee,
					sequence_number, metadata, pri, pub);
			int err_code = Result.getErrorCode(geturl,result);
			check.assertEquals(err_code, 4, "�ʲ�����source_address["
					+ source_add
					+ "]У��ʧ��");
			
		}
		
	}
	
//	@Test
	@SuppressWarnings("rawtypes")
	public void private_keyCheck(){
		sequence_number = Result.seq_num(address);
		Object pri1 = TxUtil.createAccount().get("private_key");
		Object pri2 = APIUtil.generateAcc().get("private_key");

		Object[] pri_keys = { pri1, pri2 };
		// Object[] pri_keys = {-1,10,"abc","!@#","",null};
		int asset_amount = 10;
		for (Object pri_key : pri_keys) {
			String pri = pri_key.toString();
			List opers = TxUtil.operIssue(type, asset_type, asset_issuer, asset_code, asset_amount);		//ledger����δ��ʼ���ʲ�
			String result = SignUtil.unnormal_tx(opers, source_address, fee,
					sequence_number, metadata, pri, pub);
			int err_code = Result.getErrorCode(geturl,result);
			check.assertEquals(err_code, 4, "�ʲ�����private_key[" + pri_key
					+ "]У��ʧ��");
		}
	}
}
