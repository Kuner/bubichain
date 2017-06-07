package cases;

import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import base.TestBase;
import model.Account;
import net.sf.json.JSONObject;
import utils.Result;
import utils.SignUtil;
import utils.TxUtil;

//����ǩ��
//@Test
public class CosignatureTest extends TestBase {

	/*
	 * 1.����һ���˻�acc1 
	 * 2.������������ǩ���˻�acc2��acc3
	 * 3.����acc���˻����ԣ��޸�threshold=10����signers��Ȩ��6
	 * 4.����һ�����ף�����һ�����ף�������н��׶���һ�飩������ʹ������ǩ������֤�����Ƿ�ͨ��
	 */
	// ����ǩ�������ʲ�������ǩ��Ȩ�ش�������ֵ��
	public void cosign01() {
		Map acc1 = TxUtil.createAccount(); // ������һ���˻�
		Object source_address = acc1.get("address");
		String pri = acc1.get("private_key").toString();
		Object pub = acc1.get("public_key");
		long sequence_number = Result.seq_num(source_address);

		Map acc2 = TxUtil.createAccount(); // �����ڶ����˻�
		Object address2 = acc2.get("address");
		Object pri2 = acc2.get("private_key");
		Object pub2 = acc2.get("public_key");
		int weight2 = 6;

		Map acc3 = TxUtil.createAccount(); // �����������˻�
		Object address3 = acc3.get("address");
		Object pri3 = acc3.get("private_key").toString();
		Object pub3 = acc3.get("public_key");
		int weight3 = 6;

		Object master_weight = 10;
		Object low_threshold = 10;
		Object med_threshold = 10;
		Object high_threshold = 10;

		String metadata = "1234";
		int type = 4;

		JSONObject threshold = TxUtil.threshold(master_weight, med_threshold,
				low_threshold, high_threshold);
		List signers = TxUtil.signers(address3, weight3, address2, weight2);
		List operations = TxUtil.operSetOption(type, threshold, signers);
		String response = SignUtil.tx(operations, source_address, fee,
				sequence_number, metadata, pri, pub);
		int error_code = Result.getErrorCode(response);
		check.equals(error_code, 0, "��������ʧ��");

		if(error_code==0){
			int typeaction = 2;
			int asset_type = 1;
			Object asset_issuer = source_address;
			String asset_code = "abc" ;
			int asset_amount = 100;
			long sequence_number1 = Result.seq_num(source_address);
			// ��ʼ����ǩ��
			List opers = TxUtil.operIssue(typeaction, asset_type, asset_issuer, asset_code, asset_amount);	
			JSONObject tran = TxUtil.tran_json(source_address, fee,
					sequence_number1, metadata, opers);
			String tran_blob = TxUtil.getBlob(tran);
			JSONObject s2 = TxUtil.signature(tran, pri2, pub2);
			JSONObject s3 = TxUtil.signature(tran, pri3, pub3);
			List signatures = TxUtil.signatures(s2, s3);
			List items = TxUtil.itemlist(signatures, tran_blob);
			String result = TxUtil.tx_cosign(items);

			int err_code = Result.getErrorCode(result);
			check.assertEquals(err_code, 0, "�����˻�Ȩ�غʹ���Դ�˻�����ֵ���ʲ�����ʧ��");
		}
		
	}
	
	/**
	 * ����һ�������˻�A��master_w = 10,Low = 1,Med = 10, Hig = 255
	 * signerB weight=255
	 * ��֤A�����ף�ֻ��Bǩ�������׳ɹ�
	 */
//	@Test
	public void consign_test_with_HighWeightSigner(){
		Map acc1 = TxUtil.createAccount(); // ������һ���˻�
		Object source_address = acc1.get("address");
		String pri = acc1.get("private_key").toString();
		Object pub = acc1.get("public_key");
		long sequence_number = Result.seq_num(source_address);

		Map acc2 = TxUtil.createAccount(); // �����ڶ����˻�
		Object address2 = acc2.get("address");
		Object pri2 = acc2.get("private_key");
		Object pub2 = acc2.get("public_key");
		int weight2 = 255;
		
		Object master_weight = 10;
		Object low_threshold = 1;
		Object med_threshold = 10;
		Object high_threshold = 255;

		String metadata = "1234";
		int type = 4;

		JSONObject threshold = TxUtil.threshold(master_weight, med_threshold,
				low_threshold, high_threshold);
		List signers = TxUtil.signers(address2, weight2);
		List operations = TxUtil.operSetOption(type, threshold, signers);
		String response = SignUtil.tx(operations, source_address, fee,
				sequence_number, metadata, pri, pub);
		int error_code = Result.getErrorCode(response);
		check.equals(error_code, 0, "��������ʧ��");
		if(error_code==0){
			int typeaction = 2;
			int asset_type = 1;
			Object asset_issuer = source_address;
			String asset_code = "abc" ;
			int asset_amount = 100;
			long sequence_number1 = Result.seq_num(source_address);
			List opers = TxUtil.operIssue(typeaction, asset_type, asset_issuer, asset_code, asset_amount);	
			JSONObject tran = TxUtil.tran_json(source_address, fee,
					sequence_number1, metadata, opers);
			String tran_blob = TxUtil.getBlob(tran);
			JSONObject s1 = TxUtil.signature(tran, pri, pub);
//			JSONObject s2 = TxUtil.signature(tran, pri2, pub2);
			List signatures = TxUtil.signatures(s1);
			List items = TxUtil.itemlist(signatures, tran_blob);
			String result = TxUtil.tx_cosign(items);
			int err_code = Result.getErrorCode(result);
			check.assertEquals(err_code, 0, "Դ�˻�����Ȩ��Ϊ10��signersȨ�طֱ�Ϊ6��Դ�˻������Է��н���ǩ��������ʧ��");
		}
		
	}
	
	/*
	 * 1.����һ���˻�acc1 
	 * 2.������������ǩ���˻�acc2��acc3
	 * 3.����acc1���˻����ԣ��޸�threshold=10����signers��Ȩ��6
	 * 4.ʹ��acc1����һ�����ף�ֻ��acc1ǩ��������֤���׳ɹ�
	 */
//	@Test
	public void cosign015() {
		Map acc1 = TxUtil.createAccount(); // ������һ���˻�
		Object source_address = acc1.get("address");
		String pri = acc1.get("private_key").toString();
		Object pub = acc1.get("public_key");
		long sequence_number = Result.seq_num(source_address);

		Map acc2 = TxUtil.createAccount(); // �����ڶ����˻�
		Object address2 = acc2.get("address");
		Object pri2 = acc2.get("private_key");
		Object pub2 = acc2.get("public_key");
		int weight2 = 6;

		Map acc3 = TxUtil.createAccount(); // �����������˻�
		Object address3 = acc3.get("address");
		Object pri3 = acc3.get("private_key").toString();
		Object pub3 = acc3.get("public_key");
		int weight3 = 6;

		Object master_weight = 10;
		Object low_threshold = 10;
		Object med_threshold = 10;
		Object high_threshold = 10;

		String metadata = "1234";
		int type = 4;

		JSONObject threshold = TxUtil.threshold(master_weight, med_threshold,
				low_threshold, high_threshold);
		List operations = TxUtil.operSetOption(type, threshold);
		String response = SignUtil.tx(operations, source_address, fee,
				sequence_number, metadata, pri, pub);
		int error_code = Result.getErrorCode(response);
		check.equals(error_code, 0, "��������ʧ��");

		if(error_code==0){
			int typeaction = 2;
			int asset_type = 1;
			Object asset_issuer = source_address;
			String asset_code = "abc" ;
			int asset_amount = 100;
			long sequence_number1 = Result.seq_num(source_address);
			List opers = TxUtil.operIssue(typeaction, asset_type, asset_issuer, asset_code, asset_amount);	
			JSONObject tran = TxUtil.tran_json(source_address, fee,
					sequence_number1, metadata, opers);
			String tran_blob = TxUtil.getBlob(tran);
			//ֻ��acc1����ǩ��
			JSONObject s1 = TxUtil.signature(tran, pri, pub);
//			JSONObject s2 = TxUtil.signature(tran, pri2, pub2);
//			JSONObject s3 = TxUtil.signature(tran, pri3, pub3);
			List signatures = TxUtil.signatures(s1);
			List items = TxUtil.itemlist(signatures, tran_blob);
			String result = TxUtil.tx_cosign(items);
			int err_code = Result.getErrorCode(result);
			check.assertEquals(err_code, 0, "Դ�˻�����Ȩ��Ϊ10��signersȨ�طֱ�Ϊ6��Դ�˻������Է��н���ǩ��������ʧ��");
		}
		
	}

	/*
	 * 1.����һ���˻�acc1 2.������������ǩ���˻�acc2��acc3 3.����acc���˻����ԣ��޸�threshold
	 * 10����signers��Ȩ��5
	 */
	// ����ǩ�������ʲ�������ǩ��Ȩ�ص�������ֵ��
	public void cosign02() {
		Map acc1 = TxUtil.createAccount(); // ������һ���˻�
		Object source_address = acc1.get("address");
		String pri = acc1.get("private_key").toString();
		Object pub = acc1.get("public_key");
		long sequence_number = Result.seq_num(source_address);

		Map acc2 = TxUtil.createAccount(); // �����ڶ����˻�
		Object address2 = acc2.get("address");
		Object pri2 = acc2.get("private_key");
		Object pub2 = acc2.get("public_key");
		int weight2 = 5;

		Map acc3 = TxUtil.createAccount(); // �����������˻�
		Object address3 = acc3.get("address");
		Object pri3 = acc3.get("private_key").toString();
		Object pub3 = acc3.get("public_key");
		int weight3 = 5;

		Object master_weight = 10;
		Object low_threshold = 10;
		Object med_threshold = 10;
		Object high_threshold = 10;

		String metadata = "1234";
		int type = 4;

		JSONObject threshold = TxUtil.threshold(master_weight, med_threshold,
				low_threshold, high_threshold);
		List signers = TxUtil.signers(address3, weight3, address2, weight2);
		List operations = TxUtil.operSetOption(type, threshold, signers);
		String response = SignUtil.tx(operations, source_address, fee,
				sequence_number, metadata, pri, pub);
		int error_code = Result.getErrorCode(response);
		check.equals(error_code, 0, "��������ʧ��");

		if (error_code == 0) {
			int typeaction = 2;
			int asset_type = 1;
			Object asset_issuer = source_address;
			String asset_code = "abc";
			int asset_amount = 100;
			sequence_number = Result.seq_num(source_address);
			// ��ʼ����ǩ��
			List opers = TxUtil.operIssue(typeaction, asset_type, asset_issuer,
					asset_code, asset_amount);
			JSONObject tran = TxUtil.tran_json(source_address, fee,
					sequence_number, metadata, opers);
			String tran_blob = TxUtil.getBlob(tran);
			JSONObject s2 = TxUtil.signature(tran, pri2, pub2);
			JSONObject s3 = TxUtil.signature(tran, pri3, pub3);
			List signatures = TxUtil.signatures(s2, s3);
			List items = TxUtil.itemlist(signatures, tran_blob);
			String result = TxUtil.tx_cosign(items);

			int err_code = Result.getErrorCode(result);
			check.assertEquals(err_code, 0, "�����˻�Ȩ�غʹ���Դ�˻�����ֵ���ʲ�����ʧ��");
		}
	}

	/*
	 * 1.���������˻�A,B 
	 * 2.����A�˻�Ȩ����10��B�˻�������9��A\Bû�й�ϵ�� 
	 * 3.B�˻������ף�ʹ��A�˻�ǩ��������ʧ��
	 */
//	@Test
	public void consign03() {
		int type = 4;
		String metadata = "1234";
		Account a1 = TxUtil.createNewAccount();
//		Map acc1 = TxUtil.createAccount(); // ������һ���˻�
//		Object address1 = acc1.get("address");
//		String pri1 = acc1.get("private_key").toString();
//		Object pub1 = acc1.get("public_key");

		JSONObject threshold = TxUtil.threshold(10, 10, 10, 10); // �����˻�Ȩ����10
		List operations = TxUtil.operSetOption(type, threshold);
		String response = SignUtil.tx(operations, a1.getAddress(), fee,
				metadata, a1.getPri_key(), a1.getPub_key());
		int error_code = Result.getErrorCode(response);
		check.equals(error_code, 0, "�˻�1��������ʧ��");

		Account a2 = TxUtil.createNewAccount();
//		Map acc2 = TxUtil.createAccount(); // �����ڶ����˻�
//		Object address2 = acc2.get("address");
//		String pri2 = acc2.get("private_key").toString();
//		Object pub2 = acc2.get("public_key");

		JSONObject threshold2 = TxUtil.threshold(9, 9, 9, 9); // �����˻�������9
		List operations2 = TxUtil.operSetOption(type, threshold2);
		String response2 = SignUtil.tx(operations2, a2.getAddress(), fee,
				metadata, a2.getPri_key(), a2.getPub_key());
		int error_code2 = Result.getErrorCode(response2);
		check.equals(error_code2, 0, "�˻�2��������ʧ��");

		if (error_code == 0 && error_code2 == 0) {
			int typeaction = 2;
			int asset_type = 1;
			Object asset_issuer = a2.getAddress();
			String asset_code = "abc";
			int asset_amount = 100;
			// ��ʼ����ǩ��
			List opers = TxUtil.operIssue(typeaction, asset_type, asset_issuer, // B�˻������ף�ʹ��A�˻�ǩ��
					asset_code, asset_amount);
			JSONObject tran = TxUtil.tran_json(a2.getAddress(), fee, 
					metadata, opers);
			String tran_blob = TxUtil.getBlob(tran);
			JSONObject s2 = TxUtil.signature(tran, a1.getPri_key(), a1.getPub_key());
			List signatures = TxUtil.signatures(s2);
			List items = TxUtil.itemlist(signatures, tran_blob);
			String result = TxUtil.tx_cosign(items);
			int err_code = Result.getErrorCode(result);
			check.assertNotEquals(err_code, 0, "A�˻�ǩ��B�Ľ��ף���֤ʧ��");
		}

	}

	/*
	 * 1.���������˻�A,B 
	 * 2.����A�˻�Ȩ����10��B�˻�������11��A��B��signer�� 
	 * 3.B�˻������ף�ʹ��A�˻�ǩ��������ʧ��
	 */
	public void consign04() {
		int type = 4;
		String metadata = "1234";
		Map acc1 = TxUtil.createAccount(); // ������һ���˻�
		Object address1 = acc1.get("address");
		String pri1 = acc1.get("private_key").toString();
		Object pub1 = acc1.get("public_key");
		long sequence_number1 = Result.seq_num(address1);

		JSONObject threshold = TxUtil.threshold(10, 10, 10, 10); // ����A�˻�Ȩ����10
		List operations = TxUtil.operSetOption(type, threshold);
		String response = SignUtil.tx(operations, address1, fee,
				sequence_number1, metadata, pri1, pub1);
		int error_code = Result.getErrorCode(response);
		check.equals(error_code, 0, "�˻�1��������ʧ��");

		Map acc2 = TxUtil.createAccount(); // �����ڶ����˻�
		Object address2 = acc2.get("address");
		String pri2 = acc2.get("private_key").toString();
		Object pub2 = acc2.get("public_key");
		long sequence_number2 = Result.seq_num(address2);

		JSONObject threshold2 = TxUtil.threshold(11, 11, 11, 11); // ����B�˻�������11
		List signers = TxUtil.signers(address1, 10);
		List operations2 = TxUtil.operSetOption(type, threshold2, signers);
		String response2 = SignUtil.tx(operations2, address2, fee,
				sequence_number2, metadata, pri2, pub2);
		int error_code2 = Result.getErrorCode(response2);
		check.equals(error_code2, 0, "�˻�2��������ʧ��");

		if (error_code == 0 && error_code2 == 0) { // �������óɹ���B�˻���ʼ�����ʲ�����
			int typeaction = 2;
			int asset_type = 1;
			Object asset_issuer = address2;
			String asset_code = "abc";
			int asset_amount = 100;
			sequence_number2 = Result.seq_num(address2);
			// ��ʼ����ǩ��
			List opers = TxUtil.operIssue(typeaction, asset_type, asset_issuer, // B�˻������ף�ʹ��A�˻�ǩ��
					asset_code, asset_amount);
			JSONObject tran = TxUtil.tran_json(address2, fee, sequence_number2,
					metadata, opers);
			String tran_blob = TxUtil.getBlob(tran);
			JSONObject s2 = TxUtil.signature(tran, pri1, pub1);
			List signatures = TxUtil.signatures(s2);
			List items = TxUtil.itemlist(signatures, tran_blob);
			String result = TxUtil.tx_cosign(items);
			int err_code = Result.getErrorCode(result);
			check.assertNotEquals(err_code, 0, "A�˻�ǩ��B�Ľ��ף���֤ʧ��");
		}
	}
	// 1.���������˻�A\B
	// 2.����A��Ȩ��Ϊ10����ΪB��signer����B��high_threshold��10
	// 3.B���������Բ�������Aǩ�������׳ɹ�
//	@Test
	public void consign05() {
		int type = 4;
		String metadata = "1234";
		Map acc1 = TxUtil.createAccount(); // ������һ���˻�A
		Object address1 = acc1.get("address");
		String pri1 = acc1.get("private_key").toString();
		Object pub1 = acc1.get("public_key");
		long sequence_number1 = Result.seq_num(address1);

		Map acc2 = TxUtil.createAccount(10, 10, 10, 10, address1, 10); // �����ڶ����˻�B,����������A��ΪB��signer
		Object address2 = acc2.get("address");
		String pri2 = acc2.get("private_key").toString();
		Object pub2 = acc2.get("public_key");
		long sequence_number2 = Result.seq_num(address2);
		
		JSONObject threshold = TxUtil.threshold(10, 10, 10, 10);
		List operations = TxUtil.operSetOption(type, threshold);
		String response = SignUtil.tx(operations, address2, fee,
				sequence_number2, metadata, pri1, pub1);
		int error_code = Result.getErrorCode(response);
		check.assertEquals(error_code, 0, "A��Ȩ�ص���B��high_threshold����B������������ʧ��");

	}
	// 1.���������˻�A\B
	// 2.����A��Ȩ��Ϊ10����ΪB��signer����B��high_threshold��11
	// 3.B���������Բ�������Aǩ��������ʧ��
//	@Test
	public void consign06() {
		int type = 4;
		String metadata = "1234";
		Map acc1 = TxUtil.createAccount(); // ������һ���˻�A
		Object address1 = acc1.get("address");
		String pri1 = acc1.get("private_key").toString();
		Object pub1 = acc1.get("public_key");
		long sequence_number1 = Result.seq_num(address1);

		Map acc2 = TxUtil.createAccount(10, 10, 10, 11, address1, 10); // �����ڶ����˻�B,����������A��ΪB��signer
		Object address2 = acc2.get("address");
		String pri2 = acc2.get("private_key").toString();
		Object pub2 = acc2.get("public_key");
		long sequence_number2 = Result.seq_num(address2);

		JSONObject threshold = TxUtil.threshold(10, 10, 10, 10);
		List operations = TxUtil.operSetOption(type, threshold);
		String response = SignUtil.tx(operations, address2, fee,
				sequence_number2, metadata, pri1, pub1);
		int error_code = Result.getErrorCode(response);
		check.assertNotEquals(error_code, 0,
				"A��Ȩ��С��B��high_threshold����B������������ʧ��");
	}
	// 1.���������˻�A\B
	// 2.����A��Ȩ��Ϊ10����ΪB��signer����B��high_threshold��9
	// 3.B���������Բ�������Aǩ�������׳ɹ�
//	@Test
	public void consign07() {
		int type = 4;
		String metadata = "1234";
		Map acc1 = TxUtil.createAccount(); // ������һ���˻�A
		Object address1 = acc1.get("address");
		String pri1 = acc1.get("private_key").toString();
		Object pub1 = acc1.get("public_key");
		long sequence_number1 = Result.seq_num(address1);

		Map acc2 = TxUtil.createAccount(10, 10, 10, 9, address1, 10); // �����ڶ����˻�B,����������A��ΪB��signer
		Object address2 = acc2.get("address");
		String pri2 = acc2.get("private_key").toString();
		Object pub2 = acc2.get("public_key");
		long sequence_number2 = Result.seq_num(address2);

		JSONObject threshold = TxUtil.threshold(10, 10, 10, 10);
		List operations = TxUtil.operSetOption(type, threshold);
		String response = SignUtil.tx(operations, address2, fee,
				sequence_number2, metadata, pri1, pub1);
		int error_code = Result.getErrorCode(response);
		check.assertEquals(error_code, 0, "A��Ȩ�ش���B��high_threshold����B������������ʧ��");
	}
	// 1.����һ���˻�A��med_threshold������10
	// 2.���������˻�B,C��Ȩ�غ�С��10
	// 3.�����ʲ�������������������ԣ�����B��Cǩ��������ʧ��
	public void consign08() {

		Map acc1 = TxUtil.createAccount(); // ������һ���˻�
		Object source_address = acc1.get("address");
		String pri = acc1.get("private_key").toString();
		Object pub = acc1.get("public_key");
		long sequence_number = Result.seq_num(source_address);

		Map acc2 = TxUtil.createAccount(10, 10, 10, 10); // �����ڶ����˻�
		Object address2 = acc2.get("address");
		Object pri2 = acc2.get("private_key");
		Object pub2 = acc2.get("public_key");
		int weight2 = 4;

		Map acc3 = TxUtil.createAccount(10, 10, 10, 10); // �����������˻�
		Object address3 = acc3.get("address");
		Object pri3 = acc3.get("private_key").toString();
		Object pub3 = acc3.get("public_key");
		int weight3 = 5;

		String metadata = "1234";
		int type = 4;

		JSONObject threshold = TxUtil.threshold(10, 10, 10, 10);
		List signers = TxUtil.signers(address3, weight3, address2, weight2);
		List operations = TxUtil.operSetOption(type, threshold, signers);
		String response = SignUtil.tx(operations, source_address, fee,
				sequence_number, metadata, pri, pub);
		int error_code = Result.getErrorCode(response);
		check.assertEquals(error_code, 0, "��������ʧ��");

		if (error_code == 0) {
			int typeaction = 2;
			int asset_type = 1;
			Object asset_issuer = source_address;
			String asset_code = "abc";
			int asset_amount = 100;
			sequence_number = Result.seq_num(source_address);
			// ��ʼ����ǩ��
			List opers = TxUtil.operIssue(typeaction, asset_type, asset_issuer,
					asset_code, asset_amount);
			JSONObject tran = TxUtil.tran_json(source_address, fee,
					sequence_number, metadata, opers);
			String tran_blob = TxUtil.getBlob(tran);
			JSONObject s2 = TxUtil.signature(tran, pri2, pub2);
			JSONObject s3 = TxUtil.signature(tran, pri3, pub3);
			List signatures = TxUtil.signatures(s2, s3);
			List items = TxUtil.itemlist(signatures, tran_blob);
			String result = TxUtil.tx_cosign(items);

			int err_code = Result.getErrorCode(result);
			check.assertNotEquals(err_code, 0, "�����˻�Ȩ�غ�С��Դ�˻�����ֵ��У��ʧ��");
		}

	}
	// 1.����һ���˻�A��threshold=20,master_weight=10
	@SuppressWarnings("rawtypes")
	// 2.signerB��weigh=10
	// 3.A�����ף�����ǩ�����׳ɹ�
//	 @Test
	public void consign09() {
		int type = 4;
		String metadata = "1234";
		// Object master_weight, Object med_threshold,
		// Object low_threshold, Object high_threshold
		Map acc1 = TxUtil.createAccount(); // �����ڶ����˻�B
		Object source_address = acc1.get("address");
		String pri = acc1.get("private_key").toString();
		Object pub = acc1.get("public_key");
		long sequence_number = Result.seq_num(source_address);

		Map acc2 = TxUtil.createAccount(10, 20, 20, 20); // ������һ���˻�A
		Object address2 = acc2.get("address");
		Object pri2 = acc2.get("private_key");
		Object pub2 = acc2.get("public_key");
		int weight2 = 10;
		long sequence_number2 = Result.seq_num(source_address);

		JSONObject threshold = TxUtil.threshold(10, 10, 10, 10);
		List signers = TxUtil.signers(address2, weight2);
		List operations = TxUtil.operSetOption(type, threshold, signers);
		String response = SignUtil.tx(operations, source_address, fee,
				sequence_number, metadata, pri, pub);
		int error_code = Result.getErrorCode(response);
		check.equals(error_code, 0, "��������ʧ��");
		if (error_code == 0) {
			int typeaction = 2;
			int asset_type = 1;
			Object asset_issuer = source_address;
			String asset_code = "abc";
			int asset_amount = 100;
			sequence_number2 = Result.seq_num(source_address);
			// ��ʼ����ǩ��
			List opers = TxUtil.operIssue(typeaction, asset_type, asset_issuer, // B�˻������ף�ʹ��A�˻�ǩ��
					asset_code, asset_amount);
			JSONObject tran = TxUtil.tran_json(source_address, fee,
					sequence_number2, metadata, opers);
			String tran_blob = TxUtil.getBlob(tran);
			JSONObject s1 = TxUtil.signature(tran, pri, pub);
			JSONObject s2 = TxUtil.signature(tran, pri2, pub2);
			List signatures = TxUtil.signatures(s2, s1);
			List items = TxUtil.itemlist(signatures, tran_blob);
			String result = TxUtil.tx_cosign(items);
			int err_code = Result.getErrorCode(result);
			check.assertEquals(err_code, 0, "AB����ǩ����AB��Ȩ�غ͵���A�����޵Ľ��ף���֤ʧ��");
		}
	}
	// 1.����һ���˻�A��threshold=20,master_weight=9
	// 2.signerB��weigh=10
	// 3.A�����ף�����ǩ�����׳ɹ�
//	 @Test
	public void consign10() {
		int type = 4;
		String metadata = "1234";
		Map acc1 = TxUtil.createAccount(); // ������һ���˻�A
		Object source_address = acc1.get("address");
		System.out.println("source_address: " + source_address);
		String pri = acc1.get("private_key").toString();
		Object pub = acc1.get("public_key");
		long sequence_number = Result.seq_num(source_address);

		Map acc2 = TxUtil.createAccount(); // �����ڶ����˻�B
		Object address2 = acc2.get("address");
		System.out.println("address2: " + address2);
		Object pri2 = acc2.get("private_key");
		Object pub2 = acc2.get("public_key");
		int weight2 = 10;
		long sequence_number2 = Result.seq_num(source_address);

		JSONObject threshold = TxUtil.threshold(9, 20, 20, 20); // ���õ�һ���˻������޺�Ȩ��
		List signers = TxUtil.signers(address2, weight2);
		List operations = TxUtil.operSetOption(type, threshold, signers);
		String response = SignUtil.tx(operations, source_address, fee,
				sequence_number, metadata, pri, pub);
		int error_code = Result.getErrorCode(response);
		check.equals(error_code, 0, "��������ʧ��");
		if (error_code == 0) {
			int typeaction = 2;
			int asset_type = 1;
			Object asset_issuer = source_address;
			String asset_code = "abc";
			int asset_amount = 100;
			sequence_number2 = Result.seq_num(source_address);
			// ��ʼ����ǩ��
			List opers = TxUtil.operIssue(typeaction, asset_type, asset_issuer, // B�˻������ף�ʹ��A�˻�ǩ��
					asset_code, asset_amount);
			JSONObject tran = TxUtil.tran_json(source_address, fee,
					sequence_number2, metadata, opers);
			String tran_blob = TxUtil.getBlob(tran);
			JSONObject s1 = TxUtil.signature(tran, pri, pub);
			JSONObject s2 = TxUtil.signature(tran, pri2, pub2);
			List signatures = TxUtil.signatures(s2, s1);
			List items = TxUtil.itemlist(signatures, tran_blob);
			String result = TxUtil.tx_cosign(items);
			int err_code = Result.getErrorCode(result);
			check.assertEquals(err_code, 0, "AB����ǩ����AB��Ȩ�غ�С��A�����޵Ľ��ף���֤ʧ��");
		}
	}
	// 1.����һ���˻�A��threshold=20,master_weight=10
	// 2.signerB��weigh=11
	// 3.A�����ף�����ǩ�����׳ɹ�
//	 @Test
	public void consign11() {
		int type = 4;
		String metadata = "1234";
		Map acc1 = TxUtil.createAccount(); // ������һ���˻�
		Object source_address = acc1.get("address");
		String pri = acc1.get("private_key").toString();
		Object pub = acc1.get("public_key");
		long sequence_number = Result.seq_num(source_address);

		Map acc2 = TxUtil.createAccount(20, 20, 20, 20); // �����ڶ����˻�
		Object address2 = acc2.get("address");
		Object pri2 = acc2.get("private_key");
		Object pub2 = acc2.get("public_key");
		int weight2 = 11;
		long sequence_number2 = Result.seq_num(source_address);

		JSONObject threshold = TxUtil.threshold(10, 20, 20, 20);
		List signers = TxUtil.signers(address2, weight2);
		List operations = TxUtil.operSetOption(type, threshold, signers);
		String response = SignUtil.tx(operations, source_address, fee,
				sequence_number, metadata, pri, pub);
		int error_code = Result.getErrorCode(response);
		check.equals(error_code, 0, "��������ʧ��");
		if (error_code == 0) {
			int typeaction = 2;
			int asset_type = 1;
			Object asset_issuer = source_address;
			String asset_code = "abc";
			int asset_amount = 100;
			sequence_number2 = Result.seq_num(source_address);
			// ��ʼ����ǩ��
			List opers = TxUtil.operIssue(typeaction, asset_type, asset_issuer, // B�˻������ף�ʹ��A�˻�ǩ��
					asset_code, asset_amount);
			JSONObject tran = TxUtil.tran_json(source_address, fee,
					sequence_number2, metadata, opers);
			String tran_blob = TxUtil.getBlob(tran);
			JSONObject s1 = TxUtil.signature(tran, pri, pub);
			JSONObject s2 = TxUtil.signature(tran, pri2, pub2);
			List signatures = TxUtil.signatures(s2, s1);
			List items = TxUtil.itemlist(signatures, tran_blob);
			String result = TxUtil.tx_cosign(items);
			int err_code = Result.getErrorCode(result);
			check.assertEquals(err_code, 0, "AB����ǩ����AB��Ȩ�غʹ���A�����޵Ľ��ף���֤ʧ��");
			check.result("CosignatureУ��ɹ�");
		}
	}

	/**
	 * A����������20��signer B��w=9),C(w=10),Ȩ�غ�С��A������ B,C��������ǩ����ǩ��ʧ��
	 */
	// @Test
	public void consign12() {
		int type = 4;
		String metadata = "1234";
		Map acc1 = TxUtil.createAccount(); // ������һ���˻�A
		Object source_address = acc1.get("address");
		// System.out.println("source_address: " + source_address);
		String pri = acc1.get("private_key").toString();
		Object pub = acc1.get("public_key");
		// long sequence_number = Result.seq_num(source_address);

		Map acc2 = TxUtil.createAccount(); // �����ڶ����˻�B
		Object address2 = acc2.get("address");
		// System.out.println("address2: " + address2);
		Object pri2 = acc2.get("private_key");
		Object pub2 = acc2.get("public_key");
		int weight2 = 9;

		Map acc3 = TxUtil.createAccount(); // �����������˻�C
		Object address3 = acc3.get("address");
		// System.out.println("address3: " + address2);
		Object pri3 = acc3.get("private_key");
		Object pub3 = acc3.get("public_key");
		int weight3 = 10;

		long sequence_number = Result.seq_num(source_address);

		JSONObject threshold = TxUtil.threshold(9, 20, 20, 20); // ���õ�һ���˻������޺�Ȩ��
		List signers = TxUtil.signers(address2, weight2, address3, weight3);
		List operations = TxUtil.operSetOption(type, threshold, signers);
		String response = SignUtil.tx(operations, source_address, fee,
				sequence_number, metadata, pri, pub);
		int error_code = Result.getErrorCode(response);
		check.equals(error_code, 0, "��������ʧ��");
		if (error_code == 0) {
			int typeaction = 2;
			int asset_type = 1;
			Object asset_issuer = source_address;
			String asset_code = "abc";
			int asset_amount = 100;
			sequence_number = Result.seq_num(source_address);
			// ��ʼ����ǩ��
			List opers = TxUtil.operIssue(typeaction, asset_type, asset_issuer, // B�˻������ף�ʹ��A�˻�ǩ��
					asset_code, asset_amount);
			JSONObject tran = TxUtil.tran_json(source_address, fee,
					sequence_number, metadata, opers);
			String tran_blob = TxUtil.getBlob(tran);
			JSONObject s1 = TxUtil.signature(tran, pri3, pub3);
			JSONObject s2 = TxUtil.signature(tran, pri2, pub2);
			List signatures = TxUtil.signatures(s2, s1);
			List items = TxUtil.itemlist(signatures, tran_blob);
			String result = TxUtil.tx_cosign(items);
			int err_code = Result.getErrorCode(result);
			check.assertNotEquals(err_code, 0,
					"A��������B,C����ǩ����BC��Ȩ�غ�С��A�����޵Ľ��ף���֤ʧ��");
		}
	}

	/**
	 * A����������20��signer B��w=10),C(w=10),Ȩ�غ͵���A������ B,C��������ǩ����ǩ���ɹ�
	 */
//	@Test
	public void consign13() {
		int type = 4;
		String metadata = "1234";
		Map acc1 = TxUtil.createAccount(); // ������һ���˻�A
		Object source_address = acc1.get("address");
		// System.out.println("source_address: " + source_address);
		String pri = acc1.get("private_key").toString();
		Object pub = acc1.get("public_key");
		// long sequence_number = Result.seq_num(source_address);

		Map acc2 = TxUtil.createAccount(); // �����ڶ����˻�B
		Object address2 = acc2.get("address");
		// System.out.println("address2: " + address2);
		Object pri2 = acc2.get("private_key");
		Object pub2 = acc2.get("public_key");
		int weight2 = 10;

		Map acc3 = TxUtil.createAccount(); // �����������˻�C
		Object address3 = acc3.get("address");
		// System.out.println("address3: " + address2);
		Object pri3 = acc3.get("private_key");
		Object pub3 = acc3.get("public_key");
		int weight3 = 10;

		long sequence_number = Result.seq_num(source_address);

		JSONObject threshold = TxUtil.threshold(9, 20, 20, 20); // ���õ�һ���˻������޺�Ȩ��
		List signers = TxUtil.signers(address2, weight2, address3, weight3);
		List operations = TxUtil.operSetOption(type, threshold, signers);
		String response = SignUtil.tx(operations, source_address, fee,
				sequence_number, metadata, pri, pub);
		int error_code = Result.getErrorCode(response);
		check.equals(error_code, 0, "��������ʧ��");
		if (error_code == 0) {
			int typeaction = 2;
			int asset_type = 1;
			Object asset_issuer = source_address;
			String asset_code = "abc";
			int asset_amount = 100;
			sequence_number = Result.seq_num(source_address);
			// ��ʼ����ǩ��
			List opers = TxUtil.operIssue(typeaction, asset_type, asset_issuer, // B�˻������ף�ʹ��A�˻�ǩ��
					asset_code, asset_amount);
			JSONObject tran = TxUtil.tran_json(source_address, fee,
					sequence_number, metadata, opers);
			String tran_blob = TxUtil.getBlob(tran);
			JSONObject s1 = TxUtil.signature(tran, pri3, pub3);
			JSONObject s2 = TxUtil.signature(tran, pri2, pub2);
			List signatures = TxUtil.signatures(s2, s1);
			List items = TxUtil.itemlist(signatures, tran_blob);
			String result = TxUtil.tx_cosign(items);
			int err_code = Result.getErrorCode(result);
			check.assertEquals(err_code, 0,
					"A��������B,C����ǩ����BC��Ȩ�غ͵���A�����޵Ľ��ף���֤ʧ��");
		}
	}

	/**
	 * A����������20��signer B��w=11),C(w=10),Ȩ�غʹ���A������ B,C��������ǩ����ǩ���ɹ�
	 */
//	@Test
	public void consign14() {
		int type = 4;
		String metadata = "1234";
		Map acc1 = TxUtil.createAccount(); // ������һ���˻�A
		Object source_address = acc1.get("address");
		// System.out.println("source_address: " + source_address);
		String pri = acc1.get("private_key").toString();
		Object pub = acc1.get("public_key");
		// long sequence_number = Result.seq_num(source_address);

		Map acc2 = TxUtil.createAccount(); // �����ڶ����˻�B
		Object address2 = acc2.get("address");
		// System.out.println("address2: " + address2);
		Object pri2 = acc2.get("private_key");
		Object pub2 = acc2.get("public_key");
		int weight2 = 10;

		Map acc3 = TxUtil.createAccount(); // �����������˻�C
		Object address3 = acc3.get("address");
		// System.out.println("address3: " + address2);
		Object pri3 = acc3.get("private_key");
		Object pub3 = acc3.get("public_key");
		int weight3 = 10;

		long sequence_number = Result.seq_num(source_address);

		JSONObject threshold = TxUtil.threshold(9, 20, 20, 20); // ���õ�һ���˻������޺�Ȩ��
		List signers = TxUtil.signers(address2, weight2, address3, weight3);
		List operations = TxUtil.operSetOption(type, threshold, signers);
		String response = SignUtil.tx(operations, source_address, fee,
				sequence_number, metadata, pri, pub);
		int error_code = Result.getErrorCode(response);
		check.equals(error_code, 0, "��������ʧ��");
		if (error_code == 0) {
			int typeaction = 2;
			int asset_type = 1;
			Object asset_issuer = source_address;
			String asset_code = "abc";
			int asset_amount = 100;
			sequence_number = Result.seq_num(source_address);
			// ��ʼ����ǩ��
			List opers = TxUtil.operIssue(typeaction, asset_type, asset_issuer, // B�˻������ף�ʹ��A�˻�ǩ��
					asset_code, asset_amount);
			JSONObject tran = TxUtil.tran_json(source_address, fee,
					sequence_number, metadata, opers);
			String tran_blob = TxUtil.getBlob(tran);
			JSONObject s1 = TxUtil.signature(tran, pri3, pub3);
			JSONObject s2 = TxUtil.signature(tran, pri2, pub2);
			List signatures = TxUtil.signatures(s2, s1);
			List items = TxUtil.itemlist(signatures, tran_blob);
			String result = TxUtil.tx_cosign(items);
			int err_code = Result.getErrorCode(result);
			check.assertEquals(err_code, 0,
					"A��������B,C����ǩ����BC��Ȩ�غ͵���A�����޵Ľ��ף���֤ʧ��");
		}
	}
}
