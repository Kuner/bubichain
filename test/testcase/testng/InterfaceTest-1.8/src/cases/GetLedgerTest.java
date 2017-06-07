package cases;

import java.util.Map;

import org.testng.annotations.Test;

import utils.APIUtil;
import utils.HttpUtil;
import utils.Result;
import utils.TxUtil;
import base.TestBase;

@Test
public class GetLedgerTest extends TestBase{

	String transaction = "getLedger";
	//��ѯ���������Ѻ� ϵͳĬ�ϵ��Ƿ�һ��
	public void base_feeCheck() {
		long base_fee = TestBase.fee;
		String result = HttpUtil.doget(transaction);
		int base_fee1 = Result.getbase_fee(result);

		check.assertEquals(base_fee1, base_fee, "���������Ѻ�ϵͳĬ�ϵĲ�һ��");
	}
	//��ѯ�˻��������� ϵͳĬ�ϵ��Ƿ�һ�� ()
	public void base_reserveCheck() {
		String result = HttpUtil.doget(transaction);
		int base_reserve1 = Result.getbase_reserve(result);

		check.assertNotEquals(base_reserve1, 0, "�����base_reserve����");
	}
	
	/*
	 * 1.�Ȳ�ѯ��ǰseq
	 * 2.��������һ�ʽ���
	 * 3.���׳ɹ����ٴβ�ѯseq
	 * 4.�жϺ����seqҪ����֮ǰ��
	 */
//	@Test
	public void ledger_seqCheck() {
		String result = HttpUtil.doget(transaction);
		int ledger_seq = Result.getledger_seqDefault(result);
		// System.out.println("����ǰledger_seq:" + ledger_seq);
		Map acc = TxUtil.createAccount();
		String result1 = HttpUtil.doget(transaction);
		int ledger_seq1 = Result.getledger_seqDefault(result1);
		// System.out.println("���׺�ledger_seq:" + ledger_seq1);
		check.largerThan(ledger_seq1, ledger_seq, "ledger_seqû������");
	}
}
