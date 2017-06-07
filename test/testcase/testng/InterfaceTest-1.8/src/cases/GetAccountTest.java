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

@Test
public class GetAccountTest extends TestBase{

	Object address = TxUtil.createAccount().get("address");
	
//	@Test
	public void getAccount(){
		// System.out.println("address===="+address);
		String accountInfo = Result.getAccInfo(address);
		if(Result.getoutErrCodeFromGet(accountInfo)==0){
			String balance = Result.getBalanceInResponse(accountInfo);
			String add = Result.getAddress(accountInfo);
			check.assertEquals(add, address.toString(), "�˻���ַ����");
		}else{
			Log.info("��ַ["+address+"]������");
		}
	}
	
//	@Test
	@SuppressWarnings({ "rawtypes", "unused" })
	public void balanceCheck(){

		/**
		 * ת�˺��ѯ���a����m+fee��b����m
		 * a1 transfer to a2
		 */
		//account1
		int asset_amount = 1000;
		Map acc1 = TxUtil.createAccount();
		Object ad1 = acc1.get("address");
		String balance1 = Result.getBalanceInAcc(led_acc);
		String balanc2 = Result.getBalanceInAcc(ad1);
		long sequence_number = Result.seq_num(led_acc);
		List opers = TxUtil
				.opertransfer(1, 0, ad1, asset_amount, led_acc, "abc");
		String result = SignUtil.tx(opers, led_acc, fee, sequence_number,
				"1234", led_pri, led_pub);
		int err_code = Result.getErrorCode(result);
		if (err_code == 0) {
			String balance1_r = Result.getBalanceInAcc(led_acc);
			long b1 = Long.parseLong(balance1) - asset_amount - fee;
			check.equals(Long.parseLong(balance1_r), b1, "ת�˺�Դ�˻��������");
			String balance2_r = Result.getBalanceInAcc(ad1);
			long b2_r = Long.parseLong(balance2_r);
			check.assertEquals(b2_r, Long.parseLong(balanc2) + asset_amount,
					"ת�˺�Ŀ���˻��������");
		}
	}
	
//	@Test
	public void balance_reserveCheck(){
		//ERRCODE_ACCOUNT_LOW_RESERVE
		Map acc = TxUtil.createAccount();
		Object source_address = acc.get("address");
		String pri = acc.get("private_key").toString();
		Object pub = acc.get("public_key");
		List opers = TxUtil.operIssue(2, 1, source_address, "abc", 10);	
		String balance = Result.getBalanceInAcc(source_address);
		Long bal = Long.parseLong(balance);
		String result = SignUtil.tx(opers, source_address, fee+bal, "1234", pri, pub);
		System.out.println(result);
		int err_code = Result.getErrorCode(result);
		check.assertEquals(err_code, 4, "�˻�����У��ʧ��");
	}
	/**
	 * �����ʲ���������ȷ
	 * ת���ʲ���������ȷ
	 * ��ʼ��ת�˺���ȷ
	 */
//	@Test
	public void assetCheck() {
		Map acc = TxUtil.createAccount();
		Object dest_address = acc.get("address");
		String pri = led_pri;
		Object pub = led_pub;
		Object type = 5;
		Object asset_type = 1;
		Object asset_code = "test";
		Object source_address = led_acc;
		Object asset_issuer = led_acc;
		int asset_amount = 10;
		String metadata = "abcd";
		long sequence_number = Result.seq_num(source_address);

		int type_ = 2;
		int asset_type_ = 1;
		List opers_ = TxUtil.operIssue(type_, asset_type_, source_address,
				asset_code, asset_amount);
		String result_ = SignUtil.tx(opers_, source_address, fee,
				sequence_number, metadata, pri, pub);
		int err_code1 = Result.getErrorCode(result_);
		check.equals(err_code1, 0, "�ʲ�����ʧ��"); // �ȷ����ʲ�
		sequence_number = Result.seq_num(source_address);
		List opers = TxUtil.operInitTransfer(type, asset_type, dest_address,
				asset_amount, asset_issuer, asset_code);
		String result = SignUtil.tx(opers, source_address, fee,
				sequence_number, metadata, pri, pub);
		int err_code = Result.getErrorCode(result);
		check.equals(err_code, 0, "��ʼ��ת��ʧ��");

		if (err_code == 0) {
			String hash = Result.getHash(result);
			System.out.println("hash=" + hash);
			String re = Result.getResult("getTransactionHistory", "hash", hash);
			int err = Result.getErrorCode(re);
			check.equals(err, 0, "ͨ��hash��ѯ���׼�¼ʧ��");

			int ass_amo = Result.getasset_amount(re);

			check.assertEquals(ass_amo, asset_amount, "��ʼ��ת�˺�asset_amount���ִ���");
		}
	}
	
	//����metadata���ѯ������ֵ�Ƿ�һ��
	public void metadataCheck() {
		@SuppressWarnings({ "rawtypes", "unused" })
		Object source_address = led_acc;
		long sequence_number = Result.seq_num(source_address);
		String pri = led_pri;
		String pub = led_pub;
		String account_metadata = "ab";
		String metadata = "8989";
		int type = 0;

		Map acc_gen = APIUtil.generateAcc();
		Object dest_add = acc_gen.get("address");
		List opers = TxUtil.operCreateAccount(type, dest_add, init_balance,
				account_metadata);
		String result = SignUtil.tx(opers, source_address, fee,
				sequence_number, metadata, pri, pub);

		String meta_data = Result.getMetadata(dest_add);

		check.assertEquals(meta_data, account_metadata, "metadata�����õĲ�һ��");
	}
	
	/**
	 * 1.����дmetadata_version��ÿ���޸�metadata��version�Ƿ��Զ���1
	 * 2.ֻ��дmetadata_version������дmetadata���ᱨ��
	 * 3.��д��ȷ��metadata_version,�ύ��ȷ
	 * 4.��д�����metadata_version,�ύ����
	 */
	public void metadata_versionCheck() {
		Object source_address = led_acc;
		long sequence_number = Result.seq_num(source_address);
		String pri = led_pri;
		String pub = led_pub;
		String account_metadata = "ab";
		String metadata = "";
		int type = 0;

		Map acc_gen = APIUtil.generateAcc();
		Object dest_add = acc_gen.get("address");
		List opers = TxUtil.operCreateAccount(type, dest_add, init_balance,
				account_metadata);
		String result = SignUtil.tx(opers, source_address, fee,
				sequence_number, metadata, pri, pub);

		int metadata_version = Result.getMetadata_version(dest_add);

		check.assertEquals(metadata_version, 1, "�´������˻�metadata_version����["
				+ metadata_version + "]");
	}
	
//	@Test
	//�û�����Ȩ�سɹ�������ѯȨ���Ƿ�������ȷ
	public void thresholdCheck(){
		int master_weight = 5;
		int med_threshold = 2;
		int low_threshold = 2;
		int high_threshold = 2;
		int type = 4;
		Map acc = TxUtil.createAccount();
		Object source_address = acc.get("address");
		String pri = acc.get("private_key").toString();
		Object pub = acc.get("public_key");
		long sequence_number = Result.seq_num(source_address);
		String metadata = "1234";
		
		
		JSONObject threshold = TxUtil.threshold(master_weight, med_threshold, low_threshold, high_threshold);
		List operations = TxUtil.operSetOption(type, threshold);
		String response = TxUtil.tx_result(operations, source_address,
				fee, sequence_number, metadata, pri, pub);//SignUtil.unnormal_tx(operations, source_address,
//				fee, sequence_number, metadata, pri, pub);
		int error_code = Result.getErrorCode(response);
		if(error_code==0){
			String hash = Result.getHash(response);
			System.out.println("hash="+hash);
			String re = Result.getResult("getTransactionHistory", "hash", hash);
			int err = Result.getErrorCode(response);
			check.equals(err, 0, "ͨ��hash��ѯ���׼�¼ʧ��");
			
			int mas_wei = Result.getThresholdTh(re,"master_weight");
			check.equals(mas_wei, master_weight, "master_weightУ��ʧ��");
			int med_thr = Result.getThresholdTh(re,"low_threshold");
			check.equals(med_thr, med_threshold, "low_thresholdУ��ʧ��");
			int low_thr = Result.getThresholdTh(re,"med_threshold");
			check.equals(low_thr, low_threshold, "med_thresholdУ��ʧ��");
			int hig_thr = Result.getThresholdTh(re,"high_threshold");
			check.assertEquals(hig_thr, high_threshold, "high_thresholdУ��ʧ��");
		}
	}

	//�û���������ǩ���󣬲�ѯǩ����Ϣ�Ƿ���ȷ
	public void signersCheck() {
		Map acc = TxUtil.createAccount(); // ����һ���˻���������������ǩ��
		Object source_address = acc.get("address");
		String pri = acc.get("private_key").toString();
		Object pub = acc.get("public_key");
		long sequence_number = Result.seq_num(source_address);
		// ��������ǩ�����˻�Ȩ��Ϊ6
		String address1 = TxUtil.createAccount().get("address").toString();
		Object weight1 = 6;
		int type = 4;
		String metadata = "1234";
		//�����˻�����
		List signers = TxUtil.signers(address1, weight1);
		List operations = TxUtil.operSetOption(type,signers);
		String response = SignUtil.tx(operations, source_address, fee, sequence_number, metadata, pri, pub);
		int error_code = Result.getErrorCode(response);
		if(error_code==0){
			String result = Result.getAccInfo(source_address);
			String addr = Result
.getThInAccountInfo(result, "signers",
					"address");
			check.equals(addr, address1, "�������Ժ��˻���signersУ��ʧ��");
			String weight = Result.getThInAccountInfo(result, "signers",
					"weight");
			check.assertEquals(weight, weight1.toString(),
					"�������Ժ��˻���weightУ��ʧ��");
		}

	}
	
	
	
}
