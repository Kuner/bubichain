package cases;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.testng.annotations.Test;

import utils.APIUtil;
import utils.HttpUtil;
import utils.Result;
import utils.SignUtil;
import utils.TxUtil;
import base.Log;
import base.TestBase;

@Test
public class GetTranHistoryTest extends TestBase{

	String transaction = "getTransactionHistory";

	// @Test
	//ͨ��hash��ѯ���׼�¼����֤error_code=0
	public void hashCheckAfterIssue(){
		int type = 2;
		int asset_type = 1;
		Map acc = TxUtil.createAccount();
		Object address = acc.get("address");
		String pri = acc.get("private_key").toString();
		Object pub = acc.get("public_key");
		// System.out.println("pri=" + pri);
		// System.out.println("pub=" + pub);
		Object asset_issuer = address;
		String asset_code = "abc" ;
		int asset_amount = 100;
		Object source_address = address;
		long sequence_number = Result.seq_num(address);
		String metadata = "abcd";
		//����һ�ʷ����ʲ���ͨ��hashȥ��ѯ���׼�¼
		List opers = TxUtil.operIssue(type, asset_type, asset_issuer, asset_code, asset_amount);		//ledger����δ��ʼ���ʲ�
		String result = SignUtil.tx(opers, source_address, fee, sequence_number, metadata, pri, pub);
//		System.out.println("rrrrrrrr"+result);
		String hash = Result.getHash(result);
		int err_code = Result.getErrorCode(result);
		check.assertEquals(err_code, 0, "�ʲ�����ʧ��");
		
		//��֤���׼�¼�е�operation
		
	}
//@Test
	//��֤��ȡ���׼�¼ʱ��start�ֶηǷ�ֵ
	public void startinValidCheck(){
		String url = "getTransactionHistory";
		String key = "start";
		String value = "abc";
		String result = HttpUtil.doget(url, key, value);
		int err_code = Result.getErrorCode(result);
		check.assertEquals(err_code, 2,"startֵ�Ƿ�У��ʧ��");
	}
//@Test
	public void startValidCheck(){
		String url = "getTransactionHistory";
		String key = "start";
		String value = "1";
		String result = HttpUtil.doget(url, key, value);
		int err_code = Result.getErrorCode(result);
		check.assertEquals(err_code, 0,"startΪ1ʱУ��ʧ��");
	}
//	@Test
	public void limitinValidCheck(){
		String url = "getTransactionHistory";
		String key = "limit";
		String value = "abc";
		String result = HttpUtil.doget(url, key, value);
		int err_code = Result.getErrorCode(result);
		check.assertEquals(err_code, 2,"limitֵ�Ƿ�ʱУ��ʧ��");
	}
//	@Test
	public void limitValidCheck(){
		String url = "getTransactionHistory";
		String key = "limit";
		int value = 3;
		String result = HttpUtil.doget(url, key, value);
		int err_code = Result.getErrorCode(result);
		check.equals(err_code, 0,"limitֵΪ3ʱУ��ʧ��");
		
		int total_count = Result.getTotalCount(result);
		if (total_count>value) {
			int tran_size = Result.getTranSize(result);
			check.assertEquals(tran_size, value,"�趨��limitΪ[" + value+"]ʱ����ѯ�������");
		}else{
			value = 1;
			String result_ = HttpUtil.doget(url, key, value);
			int err_code_ = Result.getErrorCode(result_);
			check.assertEquals(err_code_, 0,"�趨��limitΪ[" + value+"]ʱ����ѯ�������");
		}
		
	}
//	@Test
	public void ledger_seqValidCheck(){
		//�����ʲ����У��ٻ�ȡ���׼�¼�е�ledger_seq,Ȼ��ʹ�ô�ledger_seq���в�ѯ
		int type = 2;
		int asset_type = 1;
		long sequence_number = Result.seq_num(led_acc);
		List opers = TxUtil.operIssue(type, asset_type, led_acc, "abc", 10);		//ledger����δ��ʼ���ʲ�
		String result = SignUtil.tx(opers, led_acc, fee, sequence_number, "1234", led_pri, led_pub);
		int err_code = Result.getErrorCode(result);
		if (err_code==0) {
			String hash = Result.getHash(result);
			String response = HttpUtil.doget("getTransactionHistory", "hash", hash);
			String ledger_seq = Result.getLed_seqInTranHistory(response);
			
			String re = HttpUtil.doget("getTransactionHistory", "ledger_seq", ledger_seq);
			int err_code_ = Result.getErrorCode(re);
			check.assertEquals(err_code_, 0, "ͨ��ledger_seq��ѯ���׼�¼ʧ��");
		}else {
			Log.info("�ʲ�����ʧ�ܣ��޷���ȡledger_seq�����ܽ���ledger_seq��ѯ���׼�¼����");
		}
	}
//	@Test
//	public void ledger_seqinValidCheck(){
//		String url = "getTransactionHistory";
//		String key = "ledger_seq";
//		String value = "a*";
//		String result = HttpUtil.doget(url, key, value);
//		int err_code = Result.getErrorCode(result);
//		check.assertEquals(err_code, 2,"ledger_seqֵΪ["+value+"]ʱУ��ʧ��");
//	}
//	@Test
	public void begin_timeValidCheck(){
		String url = "getTransactionHistory";
		String key = "begin_time";
//		long value = System.currentTimeMillis()*1000;
//		System.out.println(value);
		String value = "1481519617434068";
		String result = HttpUtil.doget(url, key, value);
		int err_code = Result.getErrorCode(result);
		check.assertEquals(err_code, 0,"begin_timeֵΪ["+value+"]ʱУ��ʧ��");
	}
	
//	@Test
	public void end_timeValidCheck(){
		String url = "getTransactionHistory";
		String key = "end_time";
		long value = System.currentTimeMillis()*1000-10000000;
		String result = HttpUtil.doget(url, key, value);
		int err_code = Result.getErrorCode(result);
		check.assertEquals(err_code, 0,"end_timeֵΪ["+value+"]ʱУ��ʧ��");
	}
//	@Test
	public void addressValidCheck(){
		String url = "getTransactionHistory";
		String key = "address";
		String value = led_acc;
		String result = HttpUtil.doget(url, key, led_acc);
		int err_code = Result.getErrorCode(result);
		check.assertEquals(err_code, 0,"addressֵΪ["+value+"]ʱУ��ʧ��");
	}

	// @Test
	//�����˻��ɹ��󣬲�ѯ������operation��������ֶ�
	public void createAccount(){
		Object type = 0;
		int init_balance = 200000;
		String account_metadata = "abcd";
		String metadata = "abcd";
		
		Map acc = TxUtil.createAccount(); // ������һ���˻�
		Object address = acc.get("address");
		String pri = acc.get("private_key").toString();
		Object pub = acc.get("public_key");
		Object source_address = address;
		Object dest_add = APIUtil.generateAddress(); // ����һ��Ŀ���˻�
		//ͨ���˻���ַ��ȡbalance
		String balance1 = Result.getBalanceInAcc(source_address);
		long sequence_number = Result.seq_num(address);

		List opers = TxUtil.operCreateAccount(type, dest_add, init_balance, account_metadata);	//ledger����δ��ʼ���ʲ�
		String result = SignUtil.tx(opers, source_address, fee, sequence_number, metadata, pri, pub);
		int err_code = Result.getErrorCode(result);
		check.equals(err_code, 0, "�����˻�ʧ��");
		
		//���׳ɹ����ȡhash
		String hash = Result.getHash(result);
		// System.out.println("hash="+hash);
		String re = Result.getResult(transaction, "hash", hash);
		int err = Result.getErrorCode(re);
		check.equals(err,0,"���׼�¼����error_code������0");
		
		String dest_addr = Result.getOperthInTranHistory(re, "dest_address");
		String init_br = Result.getOperthInTranHistory(re, "init_balance");
		String feer = Result.getTranthInTranHistory(re, "fee");
		check.equals(feer, String.valueOf(fee), "���׳ɹ��������Ѳ�һ��");
		check.equals(err, 0, "���׳ɹ���ͨ��hash��ѯ���׼�¼ʧ��");
		check.equals(dest_addr, dest_add.toString(), "���׳ɹ��󣬲�ѯ��Ŀ���˻���ַ��һ��");
		check.equals(init_br, String.valueOf(init_balance), "���׳ɹ��󣬲�ѯ��init_balance��һ��");
		
		//��ȡ���׼�¼�е�ledger_seq,�ٻ�ȡ�˻���previous_ledger_seq,��֤����ֵ�Ƿ�һ��
//		String led_seqr = Result.getTranthInTranHistory(re, "ledger_seq");
//		String led_seqa = Result.getPre_ledger_seqFromAddress(source_address);
//		check.equals(led_seqr, led_seqa, "���׳ɹ��󣬽��׼�¼�е�ledger_seq��Դ�˻���previous_ledger_seq��һ��");
		
		//��ȡ���׼�¼��operations���type,�鿴ֵ�Ƿ���ύ��typeһ��
		String typer = Result.getOperthInTranHistory(re, "type");
		check.assertEquals(typer, type.toString(), "���׳ɹ��󣬽��׼�¼�е�type�봴��ʱ��һ��");
		
		// ��ʼ�˺����̫�󣬲���ȡ��int��long
		// int ba = Integer.valueOf(balance1) - fee;
		// String balancer = Result.getAccBalance(source_address);
		// check.equals(balancer, String.valueOf(ba), "���׳ɹ���Դ�˻�����ȷ");
		
	}

	 @Test
	//ת�˳ɹ��󣬲�ѯ���׼�¼��operation��������ֶ�
	public void transfer(){
		Map acc = TxUtil.createAccount();
		Object address = acc.get("address");
		String d_balan = Result.getBalanceInAcc(address);
		Map acc1 = TxUtil.createAccount();
		Object source_address = acc1.get("address");
		String pri = acc1.get("private_key").toString();
		Object pub = acc1.get("public_key");
		String balance1 = Result.getBalanceInAcc(source_address);
		Object type = 1;
		int asset_type = 0;
		Object dest_address = address;
		int asset_amount = 10;
		Object asset_issuer = source_address;
		Object asset_code = "abcd";
		long sequence_number = Result.seq_num(source_address);
		String metadata = "1234";
		List opers = TxUtil.opertransfer(type, asset_type, dest_address, asset_amount, asset_issuer, asset_code);
		String result = SignUtil.tx(opers, source_address, fee, sequence_number, metadata, pri, pub);
		int err_code = Result.getErrorCode(result);
		check.equals(err_code,0,"ת��ʧ��");
		
		//���׳ɹ����ȡhash
		String hash = Result.getHash(result);
		// System.out.println("hash="+hash);
		String re = Result.getResult(transaction, "hash", hash);
		int err = Result.getErrorCode(re);
		check.equals(err, 0, "���׳ɹ���ͨ��hash��ѯ���׼�¼ʧ��");
		String feer = Result.getTranthInTranHistory(re, "fee");
		check.equals(feer, String.valueOf(fee), "���׳ɹ��������Ѳ�һ��");
		String dest_addr = Result.getOperthInTranHistory(re, "dest_address");
		check.equals(dest_addr, dest_address.toString(), "���׳ɹ��󣬲�ѯ��Ŀ���˻���ַ��һ��");
		
		//��ȡ���׼�¼�е�ledger_seq,�ٻ�ȡ�˻���previous_ledger_seq,��֤����ֵ�Ƿ�һ��
		String led_seqr = Result.getTranthInTranHistory(re, "ledger_seq");
		String led_seqa = Result.getPre_ledger_seqFromAddress(source_address);
		check.equals(led_seqr, led_seqa, "���׳ɹ��󣬽��׼�¼�е�ledger_seq��Դ�˻���previous_ledger_seq��һ��");
		
		//��ȡ���׼�¼��operations���type,�鿴ֵ�Ƿ���ύ��typeһ��
		String typer = Result.getOperthInTranHistory(re, "type");
		check.equals(typer, type.toString(), "���׳ɹ��󣬽��׼�¼�е�type�봴��ʱ��һ��");
		
		long ba = Integer.valueOf(balance1) - asset_amount - fee;
		String balancer = Result.getBalanceInAcc(source_address);
		check.equals(balancer, String.valueOf(ba), "���׳ɹ���Դ�˻�����ȷ");
		
		//��ȡ���׼�¼�е�asset_mount
		String asset_r = Result.getOperthInTranHistory(re, "asset_amount");
		check.equals(Integer.valueOf(asset_r), asset_amount, "���׳ɹ���asset_mount��һ��");
		
		//��ȡĿ���˻�������֤�Ƿ��amountһ��
		String d_balanr = Result.getBalanceInAcc(dest_address);
		int d_b = Integer.valueOf(d_balan)+asset_amount;
		check.assertEquals(d_balanr, String.valueOf(d_b), "���׳ɹ���Ŀ���˻�������");

	}

	// @Test
	//��ʼ��ת�˳ɹ��󣬲�ѯ���׼�¼��operation��������ֶ�
	public void initTransfer(){
		Map acc = TxUtil.createAccount(); // ����һ��Ŀ���˻�
		Object dest_address = acc.get("address");
		Map acc1 = TxUtil.createAccount(); // ����һ��Դ�˻�
		Object source_address = acc1.get("address");
		String pri = acc1.get("private_key").toString();
		Object pub = acc1.get("public_key");

		int type_ = 2;
		int asset_type_ = 1;
		Object type = 5;
		Object asset_type = 1;
		Object asset_code = "abc";
		Object asset_issuer = source_address;
		int asset_amount = 10;
		String metadata = "abcd";
		long sequence_number = Result.seq_num(source_address);
		List opers_ = TxUtil.operIssue(type_, asset_type_, source_address,
				asset_code, asset_amount);
		String result_ = SignUtil.tx(opers_, source_address, fee,
				sequence_number, metadata, pri, pub);
		int err_code1 = Result.getErrorCode(result_);
		check.equals(err_code1, 0, "�ʲ�����ʧ��");

		String balance1 = Result.getBalanceInAcc(source_address);
		sequence_number = Result.seq_num(source_address);
		List opers = TxUtil.operInitTransfer(type, asset_type, dest_address, asset_amount, asset_issuer, asset_code);
		String result = SignUtil.tx(opers, source_address, fee, sequence_number, metadata, pri, pub);
		int err_code = Result.getErrorCode(result);
		check.equals(err_code, 0,"��ʼ��ת��ʧ��");
		
		String hash = Result.getHash(result);
		// System.out.println("hash="+hash);
		String re = Result.getResult(transaction, "hash", hash);
		 int err = Result.getErrorCode(re);
		 check.equals(err, 0, "���׳ɹ���ͨ��hash��ѯ���׼�¼ʧ��");
		 String feer = Result.getTranthInTranHistory(re, "fee");
		 check.equals(feer, String.valueOf(fee), "���׳ɹ��������Ѳ�һ��");
		 String dest_addr = Result.getOperthInTranHistory(re, "dest_address");
		check.equals(dest_addr, dest_address.toString(), "���׳ɹ��󣬲�ѯ��Ŀ���˻���ַ��һ��");
		
		 //��ȡ���׼�¼�е�ledger_seq,�ٻ�ȡ�˻���previous_ledger_seq,��֤����ֵ�Ƿ�һ��
		 String led_seqr = Result.getTranthInTranHistory(re, "ledger_seq");
		 String led_seqa =
		 Result.getPre_ledger_seqFromAddress(source_address);
		 check.equals(led_seqr, led_seqa,
		 "���׳ɹ��󣬽��׼�¼�е�ledger_seq��Դ�˻���previous_ledger_seq��һ��");
		
		 //��ȡ���׼�¼��operations���type,�鿴ֵ�Ƿ���ύ��typeһ��
		 String typer = Result.getOperthInTranHistory(re, "type");
		 check.equals(typer, type.toString(), "���׳ɹ��󣬽��׼�¼�е�type�봴��ʱ��һ��");
		
		long ba = Integer.valueOf(balance1) - fee;
		String balancer = Result.getBalanceInAcc(source_address);
		check.equals(balancer, String.valueOf(ba), "���׳ɹ���Դ�˻�����ȷ");
		
		//��ȡ���׼�¼�е�asset_mount
		String asset_r = Result.getOperthInTranHistory(re, "asset_amount");
		check.equals(Integer.valueOf(asset_r), asset_amount, "���׳ɹ���asset_mount��һ��");
		
		//��ȡ���׼�¼�е�asset_code
		String asset_cr = Result.getOperthInTranHistory(re, "asset_code");
		check.assertEquals(asset_cr, asset_code.toString(),
				"���׳ɹ���asset_code��һ��");
	}
	
	// @Test
	//�����ʲ��ɹ��󣬲�ѯ���׼�¼��operation��������ֶ�
	public void issue(){
		Object type = 2;
		int asset_type = 1;
		Map acc = TxUtil.createAccount();
		Object address = acc.get("address");
		String pri = acc.get("private_key").toString();
		Object pub = acc.get("public_key");
		
		Object asset_issuer = address;
		String asset_code = "abc" ;
		int asset_amount = 100;
		Object source_address = address;
		String balance1 = Result.getBalanceInAcc(source_address);
		long sequence_number = Result.seq_num(address);
		String metadata = "abcd";
		List opers = TxUtil.operIssue(type, asset_type, asset_issuer, asset_code, asset_amount);		//ledger����δ��ʼ���ʲ�
		String result = SignUtil.tx(opers, source_address, fee, sequence_number, metadata, pri, pub);
		int err_code = Result.getErrorCode(result);
		check.equals(err_code, 0, "�ʲ�����ʧ��");
		
		String hash = Result.getHash(result);
		// System.out.println("hash="+hash);
		String re = Result.getResult(transaction, "hash", hash);
		int err = Result.getErrorCode(re);
		check.equals(err, 0, "���׳ɹ���ͨ��hash��ѯ���׼�¼ʧ��");
		String feer = Result.getTranthInTranHistory(re, "fee");
		check.equals(feer, String.valueOf(fee), "���׳ɹ��������Ѳ�һ��");
		
		//��ȡ���׼�¼�е�ledger_seq,�ٻ�ȡ�˻���previous_ledger_seq,��֤����ֵ�Ƿ�һ��
		String led_seqr = Result.getTranthInTranHistory(re, "ledger_seq");
		String led_seqa = Result.getPre_ledger_seqFromAddress(source_address);
		check.equals(led_seqr, led_seqa, "���׳ɹ��󣬽��׼�¼�е�ledger_seq��Դ�˻���previous_ledger_seq��һ��");
		
		//��ȡ���׼�¼��operations���type,�鿴ֵ�Ƿ���ύ��typeһ��
		String typer = Result.getOperthInTranHistory(re, "type");
		check.equals(typer, type.toString(), "���׳ɹ��󣬽��׼�¼�е�type�봴��ʱ��һ��");
		
		long ba = Integer.valueOf(balance1) - fee;
		String balancer = Result.getBalanceInAcc(source_address);
		check.equals(balancer, String.valueOf(ba), "���׳ɹ���Դ�˻�����ȷ");
		
		//��ȡ���׼�¼�е�asset_mount
		String asset_r = Result.getOperthInTranHistory(re, "asset_amount");
		check.equals(Integer.valueOf(asset_r), asset_amount, "���׳ɹ���asset_mount��һ��");
		
		//��ȡ���׼�¼�е�asset_code
		String asset_cr = Result.getOperthInTranHistory(re, "asset_code");
		check.equals(asset_cr, asset_code, "���׳ɹ���asset_code��һ��");
		
		String asset_issuerr = Result.getOperthInTranHistory(re, "asset_issuer");
		check.assertEquals(asset_issuerr, asset_issuer.toString(),
				"�ʲ����е�asset_issuer��һ��");
		
		//������е���details����У��details���ֵ
	}

	// @Test
	//����Ψһ�ʲ��ɹ��󣬲�ѯ���׼�¼��operation��������ֶ�
//	public void uniIssue(){
//		Object type = 7;
//		@SuppressWarnings("rawtypes")
//		Map acc = TxUtil.createAccount();
//		Object source_address = acc.get("address");
//		String pri = acc.get("private_key").toString();
//		Object pub = acc.get("public_key");
//		String balance1 = Result.getBalanceInAcc(source_address);
//		String asset_code = "abc" ;
//		Object asset_issuer = source_address;
//		String asset_detailed = "1234";
//		long sequence_number = Result.seq_num(source_address);
//		String metadata = "1234";
//		List opers = TxUtil.operUniIssue(type, asset_issuer, asset_code, asset_detailed);
//		String result = SignUtil.tx(opers, source_address, fee, sequence_number, metadata, pri, pub);
//		int err_code = Result.getErrorCode(result);
//		check.equals(err_code,0,"����Ψһ�ʲ�ʧ��");
//		
//		String hash = Result.getHash(result);
//		// System.out.println("hash="+hash);
//		String re = Result.getResult(transaction, "hash", hash);
//		int err = Result.getErrorCode(re);
//		check.equals(err, 0, "���׳ɹ���ͨ��hash��ѯ���׼�¼ʧ��");
//		String feer = Result.getTranthInTranHistory(re, "fee");
//		check.equals(feer, String.valueOf(fee), "���׳ɹ��������Ѳ�һ��");
//		
//		//��ȡ���׼�¼�е�ledger_seq,�ٻ�ȡ�˻���previous_ledger_seq,��֤����ֵ�Ƿ�һ��
//		String led_seqr = Result.getTranthInTranHistory(re, "ledger_seq");
//		String led_seqa = Result.getPre_ledger_seqFromAddress(source_address);
//		check.equals(led_seqr, led_seqa, "���׳ɹ��󣬽��׼�¼�е�ledger_seq��Դ�˻���previous_ledger_seq��һ��");
//		
//		//��ȡ���׼�¼��operations���type,�鿴ֵ�Ƿ���ύ��typeһ��
//		String typer = Result.getOperthInTranHistory(re, "type");
//		check.equals(typer, type.toString(), "���׳ɹ��󣬽��׼�¼�е�type�봴��ʱ��һ��");
//		
//		long ba = Integer.valueOf(balance1) - fee;
//		String balancer = Result.getBalanceInAcc(source_address);
//		check.equals(balancer, String.valueOf(ba), "���׳ɹ���Դ�˻�����ȷ");
//		
//		//��ȡ���׼�¼�е�asset_code
//		String asset_cr = Result.getOperthInTranHistory(re, "asset_code");
//		check.equals(asset_cr, asset_code, "���׳ɹ���asset_code��һ��");
//		
//		//��ȡ���׼�¼�е�asset_issuer
//		String asset_issuerr = Result.getOperthInTranHistory(re, "asset_issuer");
//		check.equals(asset_issuerr, asset_issuer.toString(),
//				"�ʲ����е�asset_issuer��һ��");
//		
//		//��ȡ���׼�¼�е�asset_detailed
//		String asset_detailedr = Result.getOperthInTranHistory(re, "asset_detailed");
//		check.equals(asset_detailedr, asset_detailed, "�ʲ����е�asset_detailed��һ��");
//		
//		String seq_numr = Result.getTranthInTranHistory(re, "sequence_number");
//		String seq_numa = Result.getledger_seqFromAddress(source_address);
//		check.assertEquals(seq_numr, seq_numa, "sequence_number��Դ�˻���ǰ�Ĳ�һ��");
//	}
//		
//	 @Test
//	//ת��Ψһ�ʲ��ɹ��󣬲�ѯ���׼�¼��operation��������ֶ�
//	public void uniIssueTran(){
//		Object type = 8;
//		Map acc = TxUtil.createAccount(); // �ȴ���һ��Դ�˻�
//		Object source_address = acc.get("address");
//		String pri = acc.get("private_key").toString();
//		Object pub = acc.get("public_key");
//
//		Object dest_address = TxUtil.createAccount().get("address"); // �ٴ���һ��Ŀ���˻�
//		String asset_code = "abcd";
//		Object asset_issuer = source_address;
//		int type_ = 7;
//		String asset_detailed = "1234";
//		String metadata = "1234";
//		long sequence_number_ = Result.seq_num(source_address);
//		List opers_ = TxUtil.operUniIssue(type_, asset_issuer, asset_code,
//				asset_detailed); // �ȷ���Ψһ�ʲ�
//		String result_ = SignUtil.tx(opers_, source_address, fee,
//				sequence_number_, metadata, pri, pub);
//		// ����ת��Ψһ�ʲ�
//		String balance1 = Result.getBalanceInAcc(source_address);
//		long sequence_number = sequence_number_ + 1;
//		// System.out.println(sequence_number);
//		List opers = TxUtil.operUniIssueTransfer(type, dest_address, asset_issuer, asset_code);
//		String result = SignUtil.tx(opers, source_address, fee, sequence_number, metadata, pri, pub);
//		int err_code = Result.getErrorCode(result);
//		check.equals(err_code,0,"ת��Ψһ�ʲ�����");
//		
//		String hash = Result.getHash(result);
//		 System.out.println("hash="+hash);
//		String re = Result.getResult(transaction, "hash", hash);
//		int err = Result.getErrorCode(re);
//		check.equals(err, 0, "���׳ɹ���ͨ��hash��ѯ���׼�¼ʧ��");
//		String feer = Result.getTranthInTranHistory(re, "fee");
//		check.equals(feer, String.valueOf(fee), "���׳ɹ��������Ѳ�һ��");
//		
//		//��ȡ���׼�¼�е�dest_address�Ƿ���������һ��
//		String dest_addr = Result.getOperthInTranHistory(re, "dest_address");
//		check.equals(dest_addr, dest_address.toString(), "���׳ɹ��󣬲�ѯ��Ŀ���˻���ַ��һ��");
//		
//		//��ȡ���׼�¼�е�ledger_seq,�ٻ�ȡ�˻���previous_ledger_seq,��֤����ֵ�Ƿ�һ��
//		String led_seqr = Result.getTranthInTranHistory(re, "ledger_seq");
//		String led_seqa = Result.getPre_ledger_seqFromAddress(source_address);
//		check.equals(led_seqr, led_seqa, "���׳ɹ��󣬽��׼�¼�е�ledger_seq��Դ�˻���previous_ledger_seq��һ��");
//		
//		//��ȡ���׼�¼��operations���type,�鿴ֵ�Ƿ���ύ��typeһ��
//		String typer = Result.getOperthInTranHistory(re, "type");
//		check.equals(typer, type.toString(), "���׳ɹ��󣬽��׼�¼�е�type�봴��ʱ��һ��");
//		
//		long ba = Integer.valueOf(balance1) - fee - fee;
//		String balancer = Result.getBalanceInAcc(source_address);
//		check.equals(balancer, String.valueOf(ba), "���׳ɹ���Դ�˻�����ȷ");
//		
//		//��ȡ���׼�¼�е�asset_code
//		String asset_cr = Result.getOperthInTranHistory(re, "asset_code");
//		check.equals(asset_cr, asset_code, "���׳ɹ���asset_code��һ��");
//		
//		//��ȡ���׼�¼�е�asset_issuer
//		String asset_issuerr = Result.getOperthInTranHistory(re, "asset_issuer");
//		check.equals(asset_issuerr, asset_issuer.toString(),
//				"�ʲ����е�asset_issuer��һ��");
//		
//		//��ѯ���׼�¼�е�sequence_number
//		String seq_numr = Result.getTranthInTranHistory(re, "sequence_number");
//		String seq_numa = Result.getledger_seqFromAddress(source_address);
//		check.assertEquals(seq_numr, seq_numa, "sequence_number��Դ�˻���ǰ�Ĳ�һ��");
////		check.result("���׼�¼У��ͨ��");
//		
//	}

	// @Test
	//��֤�����ɹ��󣬲�ѯ���׼�¼��operation��������ֶ�
	public void storage(){
		Object type = 9;
		Map acc = TxUtil.createAccount();
		Object source_address = acc.get("address");
		String pri = acc.get("private_key").toString();
		Object pub = acc.get("public_key");
		String balance1 = Result.getBalanceInAcc(source_address);
//		String s_address = ledger ;
//		String s_key = led_pri;
		String record_id = "123";
		String record_ext = "1234";
		long sequence_number = Result.seq_num(source_address);
		String metadata = "1234";
		List opers = TxUtil.operStorage(type, record_id, record_ext);
		String result = SignUtil.tx(opers, source_address, fee, sequence_number, metadata, pri, pub);
		int error_code = Result.getErrorCode(result);
		check.equals(error_code,0,"��֤���׳���");
		
		String hash = Result.getHash(result);
		// System.out.println("hash="+hash);
		String re = Result.getResult(transaction, "hash", hash);
		int err = Result.getErrorCode(re);
		check.equals(err, 0, "���׳ɹ���ͨ��hash��ѯ���׼�¼ʧ��");
		String feer = Result.getTranthInTranHistory(re, "fee");
		check.equals(feer, String.valueOf(fee), "���׳ɹ��������Ѳ�һ��");
		
		//��ȡ���׼�¼�е�ledger_seq,�ٻ�ȡ�˻���previous_ledger_seq,��֤����ֵ�Ƿ�һ��
		String led_seqr = Result.getTranthInTranHistory(re, "ledger_seq");
		String led_seqa = Result.getPre_ledger_seqFromAddress(source_address);
		check.equals(led_seqr, led_seqa, "���׳ɹ��󣬽��׼�¼�е�ledger_seq��Դ�˻���previous_ledger_seq��һ��");
		
		//��ȡ���׼�¼��operations���type,�鿴ֵ�Ƿ���ύ��typeһ��
		String typer = Result.getOperthInTranHistory(re, "type");
		check.equals(typer, type.toString(), "���׳ɹ��󣬽��׼�¼�е�type�봴��ʱ��һ��");
		
		long ba = Integer.valueOf(balance1) - fee;
		String balancer = Result.getBalanceInAcc(source_address);
		check.equals(balancer, String.valueOf(ba), "���׳ɹ���Դ�˻�����ȷ");
		
		//��ȡoperations���record_id
		String record_idr = Result.getOperthInTranHistory(re, "record_id");
		check.equals(record_idr, record_id, "���׳ɹ���record_id����ȷ");
		
		//��ȡoperations���record_ext
		String record_extr = Result.getOperthInTranHistory(re, "record_ext");
		check.equals(record_extr, record_ext, "���׳ɹ���record_ext����ȷ");
		
		//��ѯ���׼�¼�е�sequence_number
		String seq_numr = Result.getTranthInTranHistory(re, "sequence_number");
		String seq_numa = Result.getledger_seqFromAddress(source_address);
		check.assertEquals(seq_numr, seq_numa, "sequence_number��Դ�˻���ǰ�Ĳ�һ��");
		
	}
		
	// @Test
	//�����˻����Գɹ��󣬲�ѯ���׼�¼��operation��������ֶ�
	public void setOption(){
		Object master_weight = 2;
		Object low_threshold = 2;
		Object med_threshold = 2;
		Object high_threshold = 2;
		Object type = 4;
		Map acc = TxUtil.createAccount();
		Object source_address = acc.get("address");
		String pri = acc.get("private_key").toString();
		Object pub = acc.get("public_key");
		String balance1 = Result.getBalanceInAcc(source_address);
		Map acc1 = TxUtil.createAccount();
		Object s1_address = acc1.get("address");
		Map acc2 = TxUtil.createAccount();
		Object s2_address = acc2.get("address");
		long sequence_number = Result.seq_num(source_address);
		String metadata = "1234";
		String address1 = s1_address.toString();
		Object weight1 = 2;
		JSONObject threshold = TxUtil.threshold(master_weight, med_threshold, low_threshold, high_threshold);
		List signers = TxUtil.signers(address1, weight1);
		List operations = TxUtil.operSetOption(type, threshold,signers);
		String result = SignUtil.tx(operations, source_address, fee, sequence_number, metadata, pri, pub);
		int error_code = Result.getErrorCode(result);
		check.equals(error_code,0,"��������ʧ��");
		
		String hash = Result.getHash(result);
		// System.out.println("hash="+hash);
		String re = Result.getResult(transaction, "hash", hash);
		int err = Result.getErrorCode(re);
		check.equals(err, 0, "���׳ɹ���ͨ��hash��ѯ���׼�¼ʧ��");
		String feer = Result.getTranthInTranHistory(re, "fee");
		check.equals(feer, String.valueOf(fee), "���׳ɹ��������Ѳ�һ��");
		
		//��ȡ���׼�¼�е�ledger_seq,�ٻ�ȡ�˻���previous_ledger_seq,��֤����ֵ�Ƿ�һ��
		String led_seqr = Result.getTranthInTranHistory(re, "ledger_seq");
		String led_seqa = Result.getledger_seqFromAddress(source_address);
		check.equals(led_seqr, led_seqa, "���׳ɹ��󣬽��׼�¼�е�ledger_seq��Դ�˻���previous_ledger_seq��һ��");
		
		//��ȡ���׼�¼��operations���type,�鿴ֵ�Ƿ���ύ��typeһ��
		String typer = Result.getOperthInTranHistory(re, "type");
		check.equals(typer, type.toString(), "���׳ɹ��󣬽��׼�¼�е�type�봴��ʱ��һ��");
		
		long ba = Integer.valueOf(balance1) - fee;
		String balancer = Result.getBalanceInAcc(source_address);
		check.equals(balancer, String.valueOf(ba), "���׳ɹ���Դ�˻�����ȷ");
		
		//��ѯ���׼�¼�е�sequence_number
		String seq_numr = Result.getTranthInTranHistory(re, "sequence_number");
		String seq_numa = Result.getledger_seqFromAddress(source_address);
		check.equals(seq_numr, seq_numa, "sequence_number��Դ�˻���ǰ�Ĳ�һ��");
		
		//��֤signer_address�Ƿ�һ��
		String signer_adr = Result.getOperSignerthInTranHistory(re, "address"); 
		check.equals(signer_adr, address1, "���׳ɹ���signer_address��ѯ��һ��");
		//��֤signer_weight�Ƿ�һ��
		String weightr = Result.getOperSignerthInTranHistory(re, "weight");
		check.equals(weightr, weight1.toString(), "���׳ɹ���weight��ѯ��һ��");
		//��֤threshold���master_weight�Ƿ�һ��
		String master_wr = Result.getOperThresthInTranHistory(re,
				"master_weight");
		check.equals(master_wr, master_weight.toString(),
				"���׳ɹ���master_weight��ѯ��һ��");
		//��֤threshold���med_threshold�Ƿ�һ��
		String med_threr = Result.getOperThresthInTranHistory(re,
				"med_threshold");
		check.assertEquals(med_threr, med_threshold.toString(),
				"���׳ɹ���med_threshold��ѯ��һ��");
	}

	// @Test
	//������Ӧ���ɹ��󣬲�ѯ���׼�¼��operation��������ֶ�
	public void supplyChain(){
		Map acc = TxUtil.createAccount();
		Object source_address = acc.get("address");
		String pri = acc.get("private_key").toString();
		Object pub = acc.get("public_key");
		String balance1 = Result.getBalanceInAcc(source_address);
		long sequence_number = Result.seq_num(source_address);
		String metadata = "1234";
		Object type = 6;
		Map acc1 = TxUtil.createAccount();
		Object address = acc1.get("address");
		List inputs = new ArrayList<>();
		List outputs = TxUtil.outputs(address, metadata);
		// System.out.println("===inputΪ��===");
		List opers = TxUtil.operSupplyChain(type, inputs, outputs);
		String response = SignUtil.tx(opers, source_address, fee,
				sequence_number, metadata, pri, pub);
		int error_code = Result.getErrorCode(response);
		check.equals(error_code, 0, "��Ӧ������ʧ��");

		String hash = Result.getHash(response);
		// System.out.println("hash=" + hash);
		String re = Result.getResult(transaction, "hash", hash);
		int err = Result.getErrorCode(re);
		check.equals(err, 0, "���׳ɹ���ͨ��hash��ѯ���׼�¼ʧ��");
		String feer = Result.getTranthInTranHistory(re, "fee");
		check.equals(feer, String.valueOf(fee), "���׳ɹ��������Ѳ�һ��");

		// ��ȡ���׼�¼�е�ledger_seq,�ٻ�ȡ�˻���previous_ledger_seq,��֤����ֵ�Ƿ�һ��
		String led_seqr = Result.getTranthInTranHistory(re, "ledger_seq");
		String led_seqa = Result.getPre_ledger_seqFromAddress(source_address);
		check.equals(led_seqr, led_seqa,
				"���׳ɹ��󣬽��׼�¼�е�ledger_seq��Դ�˻���previous_ledger_seq��һ��");

		// ��ȡ���׼�¼��operations���type,�鿴ֵ�Ƿ���ύ��typeһ��
		String typer = Result.getOperthInTranHistory(re, "type");
		check.equals(typer, type.toString(), "���׳ɹ��󣬽��׼�¼�е�type�봴��ʱ��һ��");

		long ba = Integer.valueOf(balance1) - fee;
		String balancer = Result.getBalanceInAcc(source_address);
		check.equals(balancer, String.valueOf(ba), "���׳ɹ���Դ�˻�����ȷ");

		// ��ѯ���׼�¼�е�sequence_number
		String seq_numr = Result.getTranthInTranHistory(re, "sequence_number");
		String seq_numa = Result.getledger_seqFromAddress(source_address);
		check.equals(seq_numr, seq_numa, "sequence_number��Դ�˻���ǰ�Ĳ�һ��");

		// inputΪ��ʱȥnull�����
		// String inputr = Result.getOperthInTranHistory(re, "inputs");
		// check.equals(inputr, null, "���׳ɹ��󣬽��׼�¼�е�inputs�봴��ʱ��һ��");

		String out_addr = Result.getOperThInTranHistory(re, "outputs",
				"address");
		check.equals(out_addr, address.toString(),
				"���׳ɹ��󣬽��׼�¼�е�outputs-address�봴��ʱ��һ��");

		String metadatar = Result.getOperThInTranHistory(re, "outputs",
				"metadata");
		check.assertEquals(metadatar, metadata,
				"���׳ɹ��󣬽��׼�¼�е�outputs-metadata�봴��ʱ��һ��");

	}
	
}
