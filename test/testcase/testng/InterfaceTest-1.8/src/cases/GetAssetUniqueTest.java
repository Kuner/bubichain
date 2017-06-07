package cases;

import java.util.List;
import java.util.Map;
import java.util.Random;

import org.testng.annotations.Test;

import utils.APIUtil;
import utils.HttpPool;
import utils.HttpUtil;
import utils.Result;
import utils.SignUtil;
import utils.TxUtil;
import base.Log;
import base.TestBase;

//@Test
public class GetAssetUniqueTest extends TestBase {

	String tran = "getUniqueAsset";
	String asset_code = "abc";
	String asset_issuer = led_acc;
	String order = "desc";
	String asset_detailed = "1234";
	Object s_address = led_acc;
	Object s_key = led_pri;
	int start = 0;
	int limit = 10;
	String response = TxUtil.tx_uniIssue(7, asset_issuer, asset_code,
			asset_detailed, s_address, s_key);

//	 @Test
	public void asset_issuerCheck() {
		int type = 7; // �ȷ���Ψһ�ʲ�
		Map acc = TxUtil.createAccount();
		Object source_address = acc.get("address");
		String pri = acc.get("private_key").toString();
		Object pub = acc.get("public_key");
		String asset_code = "abc";
		String asset_detailed = "1234";
		String metadata = "1234";
		Object asset_issuer = source_address;
		List opers = TxUtil.operUniIssue(type, asset_issuer, asset_code,
				asset_detailed);
		long sequence_number = Result.seq_num(source_address);
		String result = SignUtil.tx(opers, source_address, fee,
				sequence_number+1, metadata, pri, pub);
		int error_code = Result.getErrorCode(result);
		if (error_code == 0) {
			String key = "asset_issuer";
			String response = HttpUtil.doget(tran, key, asset_issuer);
			// ��ѯ���ؽ���е�asset_issuer,asset_codeֵ�Ƿ�����õ�һ��
			String asset_code1 = Result.getasset_code(response);
			String asset_issuer_ = Result.getasset_issuer(response);
			int reSize = Result.getResultSize(response);
			check.equals(asset_code1, asset_code, "ͨ��asset_code��ȡΨһ�ʲ�����");
			check.equals(asset_issuer_, asset_issuer.toString(),
					"ͨ��asset_issuer��ȡΨһ�ʲ�����");
			check.assertEquals(reSize, 1, "ͨ��asset_code��ȡΨһ�ʲ�����");
		}else {
			Log.info("Ψһ�ʲ�����ʧ��");
		}
	}

//	 @Test
	@SuppressWarnings("unused")
	// ����Ψһ�ʲ����ѯasset_code��ֵ�Ƿ�һ��
	public void asset_codeCheck() {
		// �ȷ���Ψһ�ʲ�
		int type = 7;
		@SuppressWarnings("rawtypes")
		Map acc = TxUtil.createAccount();
		Object source_address = acc.get("address");
		String pri = acc.get("private_key").toString();
		Object pub = acc.get("public_key");
		String asset_code = "abc";
		Object asset_issuer = source_address;
		String asset_detailed = "1234";
		String metadata = "1234";
		List opers = TxUtil.operUniIssue(type, asset_issuer, asset_code,
				asset_detailed);
		long sequence_number = Result.seq_num(baseUrl,source_address);
		String result = SignUtil.tx(opers, source_address, fee,sequence_number, metadata, pri, pub);
		String hash = Result.getHash(result);
		// �ٽ��в�ѯ
		String key = "asset_code";
		String response = HttpUtil.doget(tran, key, asset_code);
		int error_code = Result.getErrorCode(response);
		int reSize = Result.getResultSize(response);
		String asset_code1 = Result.getasset_code(response);
		check.equals(asset_code1, asset_code, "ͨ��asset_code��ȡΨһ�ʲ�����");
		check.assertNotEquals(reSize, 0, "ͨ��asset_code��ȡΨһ�ʲ�����");

	}

	 @Test
	// ͨ��asset_code��asset_issuer����Ͻ��в�ѯ�����Ӧ��Ψһ
	public void codeissuerCheck() {
		// �ȷ���Ψһ�ʲ�
		int type = 7;
		@SuppressWarnings("rawtypes")
		Map acc = TxUtil.createAccount();
		Object source_address = acc.get("address");
		String pri = acc.get("private_key").toString();
		Object pub = acc.get("public_key");
		String asset_code = "abc";
		Object asset_issuer = source_address;
		String asset_detailed = "1234";
		String metadata = "1234";
		List opers = TxUtil.operUniIssue(type, asset_issuer, asset_code,
				asset_detailed);
		long sequence_number = Result.seq_num(source_address);
		String result = SignUtil.tx(opers, source_address, fee,
				sequence_number, metadata, pri, pub);
		// System.out.println(response);
		int error_code = Result.getErrorCode(result);
		if (error_code == 0) {
			String k1 = "asset_code";
			String k2 = "asset_issuer";
			String response = HttpUtil.doget(tran, k1, asset_code, k2,
					asset_issuer.toString());
			String asset_code1 = Result.getasset_code(response);
			String asset_issuer1 = Result.getasset_issuer(response);
			check.equals(asset_code1, asset_code, "ͨ��asset_code��ϻ�ȡΨһ�ʲ�����");
			check.assertEquals(asset_issuer1, asset_issuer,
					"ͨ��asset_issuer��ϻ�ȡΨһ�ʲ�����");
		}else {
			check.fail("Ψһ�ʲ�����ʧ��");
		}
	}

	// @Test

	// ������������ȡ����ֵ��i<=size
//	public void order_descCheck() {
//
//		String k1 = "order";
//		String value = "desc";
//		int reSize = 0;
//		int num1 = 0;
//		int num2 = 0;
//		String response = HttpPool.doGet(tran, k1, value);
//		// System.out.println(response);
//		int error_code = Result.getErrorCode(response);
//		reSize = Result.getResultSize(response);
//		if (reSize > 1) {
//			// �����ȡ����е���������ֵ��num1 < num2
//			num1 = new Random().nextInt(reSize + 1);
//			num2 = new Random().nextInt(reSize - num1) + num1;
//		} else {
//			System.out.println("���ֻ�������޷��Ƚ�");
//		}
//		int ledger_seq1 = Result.getledger_seq(response, num1);
//		int ledger_seq2 = Result.getledger_seq(response, num2);
//		check.largerThan(ledger_seq1, ledger_seq2, "�����ѯ����");
//		check.result("��ȡΨһ�ʲ�У��ɹ�");
//	}
//
//	// @Test
//	public void order_ascCheck() {
//
//		String k1 = "order";
//		String value = "asc";
//		int reSize = 0;
//		int num1 = 0;
//		int num2 = 0;
//		String response = HttpPool.doGet(tran, k1, value);
//		int error_code = Result.getErrorCode(response);
//		reSize = Result.getResultSize(response);
//		if (reSize > 1) {
//			num1 = new Random().nextInt(reSize + 1);
//			num2 = new Random().nextInt(reSize - num1) + num1;
//		} else {
//			System.out.println("���ֻ�������޷��Ƚ�");
//		}
//		int ledger_seq1 = Result.getledger_seq(response, num1);
//		int ledger_seq2 = Result.getledger_seq(response, num2);
//		check.smallerThan(ledger_seq1, ledger_seq2, "�����ѯ����");
//
//	}
}
