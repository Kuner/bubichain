package cases;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.sf.json.JSONObject;

import org.testng.annotations.Test;

import utils.APIUtil;
import utils.Result;
import utils.SignUtil;
import utils.TxUtil;
import base.TestBase;

//@Test
public class SmokeTest_V2 extends TestBase {

	String metadata = "1234";

	//�����˻�����
	public void createAccount() {
		Object source_address = led_acc; // Դ�˻���Ϣ
		String pri = led_pri;
		Object pub = led_pub;
		long sequence_number = Result.seq_num(source_address);
		int type = 0;
		String account_metadata = "abcd";
		Object dest_add = APIUtil.generateAddress();
		List opers = TxUtil.operCreateAccount(type, dest_add, init_balance, account_metadata);	
		String result = SignUtil.tx(opers, source_address, fee, sequence_number, metadata, pri, pub);
		int err_code = Result.getErrorCode(result);
		check.equals(err_code, 0, "�����˻�ʧ��");
	}
	
	//�ʲ�����
//	@Test
	@SuppressWarnings("rawtypes")
	public void issue(){
		int type = 2;
		int asset_type = 1;
		Map acc = TxUtil.createAccount();
		Object address = acc.get("address");
		String pri = acc.get("private_key").toString();
		Object pub = acc.get("public_key");
		Object asset_issuer = address;
		String asset_code = "abc";
		int asset_amount = 100;
		Object source_address = address;
		long sequence_number = Result.seq_num(address);
		String metadata = "abcd";
		List opers = TxUtil.operIssue(type, asset_type, asset_issuer,
				asset_code, asset_amount); // ledger����δ��ʼ���ʲ�
		String result = SignUtil.tx(opers, source_address, fee, sequence_number, metadata, pri, pub);
		int err_code = Result.getErrorCode(result);
		check.equals(err_code, 0,"�ʲ�����ʧ��");
	}
	//ת��
	@SuppressWarnings("rawtypes")
	public void bubiTransfer(){
		Map acc = TxUtil.createAccount();
		Object source_address = acc.get("address");
		String pri = acc.get("private_key").toString();
		Object pub = acc.get("public_key");
		long sequence_number = Result.seq_num(source_address);
		Object dest_address = TxUtil.createAccount().get("address");
		int asset_amount = 10;
		int type = 1;
		int asset_type = 0;
//		List opers = TxUtil.opertransfer1(type, asset_type, dest_address, asset_amount);
//		String result = SignUtil.tx(opers, source_address, fee, sequence_number, metadata, pri, pub);
//		int err_code = Result.getErrorCode(result);
//		check.equals(err_code,0,"����ת��ʧ��");
	}
	//�����ʲ�ת�� 
	public void IssueTransfer(){
		Map acc = TxUtil.createAccount();
		Object source_address = acc.get("address");
		String pri = acc.get("private_key").toString();
		Object pub = acc.get("public_key");
		long sequence_number = Result.seq_num(source_address);
		Object dest_address1 = TxUtil.createAccount().get("address");
		int asset_amount = 10;
		int type = 1;
		int asset_type = 1;
		Object asset_code = "abc";
		Object asset_issuer = source_address;
		//�����ʲ�
		List opers = TxUtil.operIssue(2, asset_type, asset_issuer, asset_code, asset_amount);	
		String result = SignUtil.tx(opers, source_address, fee, sequence_number, metadata, pri, pub);
		//�����ʲ�ת��
		if(Result.getErrorCode(result)==0){
			List opers1 = TxUtil.opertransfer(type, asset_type, dest_address1,
					asset_amount, asset_issuer, asset_code);
			String result1 = SignUtil.tx(opers1, source_address, fee, sequence_number, metadata, pri, pub);
			int err_code = Result.getErrorCode(result1);
			check.equals(err_code,0,"�����ʲ�ת��ʧ��");

		}else{
			check.result("�ʲ�����ʧ�ܣ��޷����в���ת��");
		}
		
	}
	//��ʼ��ת��
	@SuppressWarnings("rawtypes")
	@Test
	public void initTransfer(){
		Map acc = TxUtil.createAccount();
		Object source_address = acc.get("address");
		String pri = acc.get("private_key").toString();
		Object pub = acc.get("public_key");
		long sequence_number = Result.seq_num(source_address);
		
		Object dest_address1 = TxUtil.createAccount().get("address");
		Object dest_address2 = APIUtil.generateAddress();

		int asset_amount = 10;
		int type = 5;
		int asset_type = 1;
		Object asset_code = "abc";
		Object asset_issuer = source_address;
		//�����ʲ�
		List opers = TxUtil.operIssue(2, asset_type, asset_issuer, asset_code, asset_amount);	
		String result = SignUtil.tx(opers, source_address, fee, sequence_number, metadata, pri, pub);
		//��ʼ��ת��
		if(Result.getErrorCode(result)==0){
			List opers1 = TxUtil.operInitTransfer(type, asset_type,
					dest_address1, asset_amount, asset_issuer, asset_code);
			String result1 = SignUtil.tx(opers1, source_address, fee, sequence_number, metadata, pri, pub);
			int err_code = Result.getErrorCode(result1);
			check.equals(err_code, 0,"��ʼ��ת��ʧ��");

			// Ŀ���˻������ڣ���ʼ��ת�˳ɹ�
			List opers2 = TxUtil.opertransfer(type, asset_type, dest_address2,
					asset_amount, asset_issuer, asset_code);
			String result2 = SignUtil.tx(opers2, source_address, fee,
					sequence_number, metadata, pri, pub);
			int err_code2 = Result.getErrorCode(result2);
			check.equals(err_code2, 0, "�����ʲ�ת��ʧ��");
		}else{
			check.result("�ʲ�����ʧ�ܣ��޷����з����ʲ�ת��");
		}
		
	}
	//�����˻�����
	@SuppressWarnings("rawtypes")
	public void setOpt(){
		Map acc = TxUtil.createAccount();
		Object source_address = acc.get("address");
		String pri = acc.get("private_key").toString();
		Object pub = acc.get("public_key");
		long sequence_number = Result.seq_num(source_address);

		Map acc1 = TxUtil.createAccount();
		Object s1_address = acc1.get("address");
		String address1 = s1_address.toString();
		int weight1 = 2;
		Object s2_address = acc1.get("address");
		String address2 = s2_address.toString();
		int weight2 = 2;
		Object master_weight = 2;
		Object low_threshold = 2;
		Object med_threshold = 2;
		Object high_threshold = 2;
		int type = 4;
		JSONObject threshold = TxUtil.threshold(master_weight, med_threshold, low_threshold, high_threshold);
		List signers = TxUtil.signers(address1, weight1,address2, weight2);
		List operations = TxUtil.operSetOption(type, threshold,signers);
		String response = SignUtil.tx(operations, source_address, fee, sequence_number, metadata, pri, pub);
		int error_code = Result.getErrorCode(response);
		check.equals(error_code,0,"��������ʧ��");
	}
	//��Ӧ��
	@SuppressWarnings("rawtypes")
	public void supChain(){
		Map acc = TxUtil.createAccount();
		Object source_address = acc.get("address");
		String pri = acc.get("private_key").toString();
		Object pub = acc.get("public_key");
		long sequence_number = Result.seq_num(source_address);
		int type = 6;
		Map acc1 = TxUtil.createAccount();
		Object address = acc1.get("address");
		List inputs = new ArrayList<>();
		List outputs = TxUtil.outputs(address, metadata);
		List opers = TxUtil.operSupplyChain(type,inputs,outputs);
		String response = SignUtil.tx(opers, source_address, fee, sequence_number, metadata, pri, pub);
		int error_code = Result.getErrorCode(response);
		check.equals(error_code,0,"��Ӧ������ʧ��");
	}
	//��ȡ�˻���Ϣ
	public void getAccountCheck(){
		Map acc = TxUtil.createAccount();
		Object address = acc.get("address");
		String result = Result.getAccInfo(address);
		int error_code = Result.getErrorCode(result);
		String balance = Result.getBalanceInResponse(result);
		check.equals(error_code, 0,"��ȡ�˻���Ϣʧ��");
		check.equals(balance,String.valueOf(init_balance),"�����Ϣ����");
		
	}
	//��ȡ������Ϣ
//	@Test
	@SuppressWarnings("rawtypes")
	public void getTransactionHistoryCheck(){
		Map acc = TxUtil.createAccount();
		Object address = acc.get("address");
		String result = Result.getTranHisByAdd(address);
		int error_code = Result.getErrorCode(result);
		check.equals(error_code, 0, "��ѯ���׼�¼ʧ��");
	}
	//��ȡ������Ϣ
	public void getLedger(){
		int ledgerHeight = Result.getDefaultLedgerHeight();
		Random random = new Random();
		int value = random.nextInt(ledgerHeight);
		int led_seq = Result.getLedgerBySeq(value);
//		System.out.println("led_seq=="+led_seq);
		check.notEquals(led_seq, 0, "�������߶Ȳ�ѯʧ��");
	}
	//��Ӧ����Դ
	@SuppressWarnings("rawtypes")
	public void getSource(){
		Map acc = TxUtil.createAccount();
		Object source_address = acc.get("address");
		String pri = acc.get("private_key").toString();
		Object pub = acc.get("public_key");
		long sequence_number = Result.seq_num(source_address);
		int type = 6;
		Map acc1 = TxUtil.createAccount();
		Object address = acc1.get("address");
//		System.out.println("public_key=="+pub);
		List inputs = new ArrayList<>();
		List outputs = TxUtil.outputs(address, metadata);
		//������Ӧ��
		List opers = TxUtil.operSupplyChain(type,inputs,outputs);
		String response = SignUtil.tx(opers, source_address, fee, sequence_number, metadata, pri, pub);
//		System.out.println("������Ӧ�����" + response);
		int error_code = Result.getErrorCode(response);
		if(error_code==0){
			String hash = Result.getHash(response);
//			System.out.println("��Ӧ��hash=" + hash);
			APIUtil.wait(2);
			String re = Result.getSources(hash);
//			System.out.println("��Դ���"+re);
			int er = Result.getErrorCode(re);
			check.equals(er,0,"��Ӧ����Դʧ��");
		}else{
			check.result("��Ӧ������ʧ�ܣ��޷�������Դ��ѯ");
		}
	}
	
	//����Ψһ�ʲ�
	public void uniIssueCheck(){
		Map acc = TxUtil.createAccount();
		Object source_address = acc.get("address");
		String pri = acc.get("private_key").toString();
		Object pub = acc.get("public_key");
		long sequence_number = Result.seq_num(source_address);
		int type = 7;
		String asset_code = "abc" ;
		String asset_issuer = led_acc; 
		String asset_detailed = "1234";
		List opers = TxUtil.operUniIssue(type, asset_issuer, asset_code, asset_detailed);
		String result = SignUtil.tx(opers, source_address, fee, sequence_number, metadata, pri, pub);
		int err_code = Result.getErrorCode(result);
		check.equals(err_code,0,"����Ψһ�ʲ�ʧ��");
	}
	
	//ת��Ψһ�ʲ�
	public void uniIssueTransferCheck (){
		Map acc = TxUtil.createAccount();
		Object source_address = acc.get("address");
		String pri = acc.get("private_key").toString();
		Object pub = acc.get("public_key");
		long sequence_number = Result.seq_num(source_address);
		Object dest_address = TxUtil.createAccount().get("address");
		int type = 8;
		String asset_code = "abcd";
		Object asset_issuer = source_address;
		List opers = TxUtil.operUniIssueTransfer(type, dest_address, asset_issuer, asset_code);
		String result = SignUtil.tx(opers, source_address, fee, sequence_number, metadata, pri, pub);
		int err_code = Result.getErrorCode(result);
		check.equals(err_code,0,"ת��Ψһ�ʲ�����");
	}
	
	//��֤
	public void storageCheck(){
		Map acc = TxUtil.createAccount();
		Object source_address = acc.get("address");
		String pri = acc.get("private_key").toString();
		Object pub = acc.get("public_key");
		long sequence_number = Result.seq_num(source_address);
		int type = 9;
		String record_id = "123";
		String record_ext = "1234";
		List opers = TxUtil.operStorage(type, record_id, record_ext);
		String response = SignUtil.tx(opers, source_address, fee, sequence_number, metadata, pri, pub);
		int error_code = Result.getErrorCode(response);
		check.equals(error_code,0,"��֤���׳���");
	}
	//��ѯ��֤
	public void getStorageCheck(){}
	//��ѯΨһ�ʲ�
	public void getUniIsset(){}
}
