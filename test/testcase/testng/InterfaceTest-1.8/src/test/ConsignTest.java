package test;

import java.util.List;

import org.testng.annotations.Test;

import base.TestBase;
import model.Account;
import newop.Transaction;

//@Test
public class ConsignTest extends TestBase{

	Transaction tran = new Transaction();
	/**
	 * ����ǩ�������ʲ�
	 * ����һ���˻�a1����������ǩ�����˺�a2,a3
	 * master_weight=10,a.weight=6,b.weight=6
	 * a2,a3����ǩ�����з����ʲ�
	 */
//	@Test
	public void consign_issue_enoughThreshold(){
		List<Account>  accounts = tran.createAccountwithSigners(6, 6);
		tran.issue(accounts.get(0), accounts.get(1), accounts.get(2));
		int error_code = Transaction.getErrorCode();
		check.assertEquals(error_code, 0, "consign issue failed with enough threshold");
	}
	/**
	 * ����һ���˻�����������ǩ�����˺�a,b
	 * master_weight=10,a.weight=5,b.weight=5
	 * a,b����ǩ�����д����˺�
	 */
//	@Test
	public void consign_issue_equalThreshold(){
		Transaction tran = new Transaction();
		List<Account>  accounts = tran.createAccountwithSigners(5, 5);
		tran.issue(accounts.get(0), accounts.get(1), accounts.get(2));
		int error_code = Transaction.getErrorCode();
		check.assertEquals(error_code, 0, "consign issue failed with same threshold");
	}
	
	/**
	 * ��֤һ������ǩ���˻�A��signerB
 	 * A��master_weight=10,low=1,med=10,high=255
	 * B��weight=255
	 * ��ֻ֤��B����ǩ�������׳ɹ�
	 * 
	 */
//	@Test
	public void consign_issue_withOneSigner(){
		Transaction tran = new Transaction();
//		Integer high, Integer low, Integer med, Integer master_weight, Integer weight
		List<Account>  accounts = tran.createAccountwithSigners(255, 1, 10, 10, 255);
		tran.issue(accounts.get(0), accounts.get(1));
		int error_code =Transaction.getErrorCode();
		check.assertEquals(error_code, 0, "consign issue failed with enough weight");
	}
	/**
	 * ����һ���˻�����������ǩ�����˺�a,b
	 * master_weight=10,a.weight=4,b.weight=4
	 * a,b����ǩ�����д����˺�,���״���ʧ��
	 */
	@Test
	public void consign_issue_lessThreshold(){
		Transaction tran = new Transaction();
		List<Account>  accounts = tran.createAccountwithSigners(4, 4);
		tran.issue(accounts.get(0), accounts.get(1), accounts.get(2));
		int error_code = Transaction.getErrorCode();
		check.assertEquals(error_code, 93, "consign issue failed with less threshold");
	}
	/**
	 * ����ǩ�������˻�
	 * ����һ���˻�a1����������ǩ�����˺�a2,a3
	 * master_weight=10,a.weight=6,b.weight=6
	 * a2,a3����ǩ�����д����˺ţ����״����ɹ�
	 */
//	 @Test  
	public void consign_create_enoughThreshold() {
		List<Account>  accounts = tran.createAccountwithSigners(6, 6);
		tran.createAccount(accounts.get(0), accounts.get(1), accounts.get(2));
		int error_code = Transaction.getErrorCode();
		check.assertEquals(error_code, 0, "consign issue failed with enough threshold");
	}
	/**
	 * ����һ���˻�a1����������ǩ�����˺�a2,a3
	 * master_weight=10,a.weight=6,b.weight=6
	 * ��֤a2,a3����ǩ�����з���Ψһ�ʲ������׳ɹ�
	 */
//	 @Test
	public void consign_issueUnique_enoughThreshold() {
		List<Account>  accounts = tran.createAccountwithSigners(6, 6);
		tran.issueUnique(accounts.get(0), accounts.get(1), accounts.get(2));
		int error_code = Transaction.getErrorCode();
		check.assertEquals(error_code, 0, "consign issue failed with enough threshold");
	}
	/**
	 * ����һ���˻�a1����������ǩ�����˺�a2,a3
	 * master_weight=10,a.weight=5,b.weight=5
	 * a2,a3����ǩ�����г�ʼ��ת�ˣ����׳ɹ�
	 */
//	 @Test
	public void consign_initPayment() {
		List<Account>  accounts = tran.createAccountwithSigners(5, 5);
		tran.initPayment(accounts.get(0), accounts.get(1), accounts.get(2));
		int error_code = Transaction.getErrorCode();
		check.assertEquals(error_code, 0, "consign issue failed with enough threshold");
	}
	/**
	 * ����һ���˻�a1����������ǩ�����˺�a2,a3
	 * master_weight=10,a.weight=5,b.weight=5
	 * a2,a3����ǩ������ת��Ψһ�ʲ�
	 */
//	 @Test
	public void consign_uniquePayment() {
		List<Account>  accounts = tran.createAccountwithSigners(5, 5);
		tran.uniquePayment(accounts.get(0), accounts.get(1), accounts.get(2));
		int error_code = Transaction.getErrorCode();
		check.assertEquals(error_code, 0, "consign issue failed with enough threshold");
	}
	
	/**
	 * ����һ���˻�a1����������ǩ�����˺�a2,a3
	 * master_weight=10,a.weight=5,b.weight=5
	 * a2,a3����ǩ�����д�֤
	 */
//	 @Test
	public void consign_evidence() {
		List<Account>  accounts = tran.createAccountwithSigners(5, 5);
		tran.evidence(accounts.get(0), accounts.get(1), accounts.get(2));
		int error_code = Transaction.getErrorCode();
		check.assertEquals(error_code, 0, "consign issue failed with enough threshold");
	}
	
	/**
	 * ����һ���˻�a1����������ǩ�����˺�a2,a3
	 * master_weight=10,a.weight=5,b.weight=5
	 * a2,a3����ǩ�������������Բ���
	 */
//	 @Test
	public void consign_setOption() {
		List<Account>  accounts = tran.createAccountwithSigners(5, 5);
		tran.setOption(accounts.get(0), accounts.get(1), accounts.get(2));
		int error_code = Transaction.getErrorCode();
		check.assertEquals(error_code, 0, "consign issue failed with enough threshold");
	}
	
}
