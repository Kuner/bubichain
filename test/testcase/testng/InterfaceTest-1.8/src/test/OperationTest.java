package test;

import org.testng.annotations.Test;

import base.TestBase;
import cn.bubi.blockchain.adapter.Message.AssetProperty.Type;
import model.Account;
import model.Operation;
import model.Threshold;
import newop.Transaction;

@Test
public class OperationTest extends TestBase{
	Transaction tran = new Transaction();
	/**
	 * ���operation��ϣ�2�������紴���˻��������ʲ����
	 */
	
	//�����˻�ת�����
//	@Test
	public void create_payment(){
		Account a = tran.createAccountOne(genesis);
		Operation op1 = new Operation();
		op1.setDest_address(Account.generateAccount().getAddress());
		op1.setType(0);
		Operation op2 = new Operation();
		op2.setDest_address(tran.createAccountOne(genesis).getAddress());
		op2.setType(1);
		op2.setAsset_issuer(a.getAddress());
		op2.setAsset_amount(100L);
		tran.submit(a, op1, op2);
		int error_code = Transaction.getErrorCode();
		check.assertEquals(error_code, 0,"create and payment check failed");
	}
	//�����˻�����Ψһ�ʲ�
//	@Test
	public void create_issueUnique(){
		Account a = tran.createAccountOne(genesis);
		Operation op1 = new Operation();
		op1.setDest_address(Account.generateAccount().getAddress());
		op1.setType(0);
		Operation op2 = new Operation();
		op2.setType(7);
		op2.setAsset_issuer(a.getAddress());
		op2.setAsset_amount(100L);
		op2.setAsset_code("123");
		op2.setAsset_detailed("1123");
		tran.submit(a, op1, op2);
		int error_code = Transaction.getErrorCode();
		check.assertEquals(error_code, 0,"create and issueUnique checkfailed");
	}
	//�����˻�ת��Ψһ�ʲ�
//	@Test
	public void create_paymentUnique(){
		Account a = tran.createAccWithUniqueAsset(genesis);
		Operation op1 = new Operation();
		op1.setDest_address(Account.generateAccount().getAddress());
		op1.setType(0);
		op1.setInit_balance(base_reserve);
		Operation op2 = new Operation();
		op2.setType(8);
		op2.setAsset_issuer(a.getAddress());
		op2.setAsset_code("123");
		op2.setAPtype(Type.NATIVE);
		op2.setDest_address(tran.createAccountOne(genesis).getAddress());
		tran.submit(a, op1, op2);
		int error_code = Transaction.getErrorCode();
		check.assertEquals(error_code, 0,"create and payment unique check failed");
	}
	//�����˻���������
//	@Test
	public void create_setOption(){
		Account a = tran.createAccountOne(genesis);
		Operation op1 = new Operation();
		op1.setDest_address(Account.generateAccount().getAddress());
		op1.setType(0);
		op1.setInit_balance(base_reserve);
		Operation op2 = new Operation();
		op2.setType(4);
		Threshold threshold = new Threshold();
		threshold.setHigh_threshold(2);
		threshold.setMed_threshold(2);
		threshold.setLow_threshold(2);
		threshold.setMaster_weight(2);
		op2.setThreshold(threshold);
		tran.submit(a, op1, op2);
		int error_code = Transaction.getErrorCode();
		check.assertEquals(error_code, 0,"create and set option check failed");
	}
	//�����˻�������֤
//	@Test
	public void create_evidence(){
		Account a = tran.createAccountOne(genesis);
		Operation op1 = new Operation();
		op1.setDest_address(Account.generateAccount().getAddress());
		op1.setType(0);
		op1.setInit_balance(base_reserve);
		Operation op2 = new Operation();
		op2.setType(9);
		op2.setRecord_ext("1111");
		op2.setRecord_id("123");
		tran.submit(a, op1, op2);
		int error_code = Transaction.getErrorCode();
		check.assertEquals(error_code, 0,"create and evidence check failed");
	}
	//�����˻���Ӧ����������֤operationΪ��Ӧ��ʱֻ����һ��operation
//	@Test
	public void create_production(){
		Account a = tran.createAccountOne(genesis);
		Operation op1 = new Operation();
		op1.setDest_address(Account.generateAccount().getAddress());
		op1.setType(0);
		op1.setInit_balance(base_reserve);
		Operation op2 = new Operation();
		op2.setType(6);
		op2.setInputs(tran.createInput());
		op2.setOutputs(tran.createOutput(tran.createAccountOne(genesis).getAddress(), "112233"));
		tran.submit(a, op1, op2);
		int error_code = Transaction.getErrorCode();
		check.assertEquals(error_code, 2,"create and production check failed");
	}
	
	//�����˺ŷ����ʲ�
//	@Test
	public void create_issue(){
		Account a = tran.createAccountOne(genesis);
		Operation op1 = new Operation();
		op1.setDest_address(Account.generateAccount().getAddress());
		op1.setType(0);
		op1.setInit_balance(base_reserve);
		Operation op2 = new Operation();
		op2.setType(2);
		op2.setAsset_issuer(a.getAddress());
		op2.setAsset_amount(100L);
		op2.setAsset_code("1234"+ System.currentTimeMillis());
		tran.submit(a, op1, op2);
		int error_code =  Transaction.getErrorCode();
		check.assertEquals(error_code, 0,"create and issue check failed");
	}
	//�����ʲ�ת��
//	@Test
	public void issue_payment(){
		Account a = tran.createAccountOne(genesis);
		Operation op1 = new Operation();
		op1.setType(2);
		op1.setAsset_issuer(a.getAddress());
		op1.setAsset_amount(100L);
		op1.setAsset_code("1234"+ System.currentTimeMillis());
		Operation op2 = new Operation();
		op2.setDest_address(tran.createAccountOne(genesis).getAddress());
		op2.setType(1);
		op2.setAsset_issuer(a.getAddress());
		op2.setAsset_amount(100L);
		tran.submit(a, op1, op2);
		int error_code =  Transaction.getErrorCode();
		check.assertEquals(error_code, 0,"issue and payment check failed");
	}
	//�����ʲ���������
//	@Test
	public void issue_setOption(){
		Account a = tran.createAccountOne(genesis);
		Operation op1 = new Operation();
		op1.setType(2);
		op1.setAsset_issuer(a.getAddress());
		op1.setAsset_amount(100L);
		op1.setAsset_code("1234"+ System.currentTimeMillis());
		Operation op2 = new Operation();
		op2.setType(4);
		Threshold threshold = new Threshold();
		threshold.setHigh_threshold(2);
		threshold.setMed_threshold(2);
		threshold.setLow_threshold(2);
		threshold.setMaster_weight(2);
		op2.setThreshold(threshold);
		tran.submit(a, op1, op2);
		int error_code =  Transaction.getErrorCode();
		check.assertEquals(error_code, 0,"issue and set option check failed");
	}
	//�����ʲ�������֤
//	@Test
	public void issue_evidence(){
		Account a = tran.createAccountOne(genesis);
		Operation op1 = new Operation();
		op1.setType(2);
		op1.setAsset_issuer(a.getAddress());
		op1.setAsset_amount(100L);
		op1.setAsset_code("1234"+ System.currentTimeMillis());
		Operation op2 = new Operation();
		op2.setType(9);
		op2.setRecord_ext("1111");
		op2.setRecord_id("123");
		tran.submit(a, op1, op2);
		int error_code =  Transaction.getErrorCode();
		check.assertEquals(error_code, 0,"issue and evidence check failed");
	}
	//�����ʲ�����Ψһ�ʲ�
//	@Test
	public void issue_issueUnique(){
		Account a = tran.createAccountOne(genesis);
		Operation op1 = new Operation();
		op1.setType(2);
		op1.setAsset_issuer(a.getAddress());
		op1.setAsset_amount(100L);
		op1.setAsset_code("1234"+ System.currentTimeMillis());
		Operation op2 = new Operation();
		op2.setType(7);
		op2.setAsset_issuer(a.getAddress());
		op2.setAsset_amount(100L);
		op2.setAsset_code("123");
		op2.setAsset_detailed("1123");
		tran.submit(a, op1, op2);
		int error_code =  Transaction.getErrorCode();
		check.assertEquals(error_code, 0,"issue and issue unique check failed");
	}
	//�����ʲ�������Ӧ��
	/*
	 * ��֤��Ӧ��������operationֻ����һ����operation.size����2������ʧ��
	 */
//	@Test
	public void issue_production(){
		Account a = tran.createAccountOne(genesis);
		Operation op1 = new Operation();
		op1.setType(2);
		op1.setAsset_issuer(a.getAddress());
		op1.setAsset_amount(100L);
		op1.setAsset_code("1234"+ System.currentTimeMillis());
		Operation op2 = new Operation();
		op2.setType(6);
		op2.setInputs(tran.createInput());
		op2.setOutputs(tran.createOutput(tran.createAccountOne(genesis).getAddress(), "112233"));
		tran.submit(a, op1, op2);
		int error_code =  Transaction.getErrorCode();
		check.assertEquals(error_code, 2,"issue and production check failed");
	}
	//�����ʲ�ת��Ψһ�ʲ�
//	@Test
	public void issue_paymentUnique(){
		Account a = tran.createAccWithUniqueAsset(genesis);
		Operation op1 = new Operation();
		op1.setType(2);
		op1.setAsset_issuer(a.getAddress());
		op1.setAsset_amount(100L);
		op1.setAsset_code("1234"+ System.currentTimeMillis());
		Operation op2 = new Operation();
		op2.setType(8);
		op2.setAsset_issuer(a.getAddress());
		op2.setAsset_code("123");
		op2.setAPtype(Type.NATIVE);
		op2.setDest_address(tran.createAccountOne(genesis).getAddress());
		tran.submit(a, op1, op2);
		int error_code =  Transaction.getErrorCode();
		check.assertEquals(error_code, 0,"issue and payment unique check failed");
	}
	/**
	 * sequence�Ⱥ�˳��У��
	 * �ȷ���һ��seq=2�Ľ��ף��ٷ���һ��seq=1�Ľ���
	 * ��֤�������׶�ִ�гɹ���������ֳ�ʱ����
	 */
//	@Test
	public void sequence_check(){
		Account a1 = tran.createAccountOne(genesis);
		tran.issue1(a1);   //issue
		tran.issue(a1);   //issue
		int error_code1 =  Transaction.getErrorCode();
		int error_code2 =  Transaction.getErrorCode();
		check.assertEquals(error_code1, 0, "����Ž��׳ɹ�");
		check.assertEquals(error_code2, 0, "С��Ž��׳ɹ�");
	}
	//ת�ˣ������ʲ�
//	@Test
	public void payment_issue(){
		Account a = tran.createAccountOne(genesis);
		Operation op1 = new Operation();
		op1.setDest_address(tran.createAccountOne(genesis).getAddress());
		op1.setType(1);
		op1.setAsset_issuer(a.getAddress());
		op1.setAsset_amount(100L);
		Operation op2 = new Operation();
		op2.setType(2);
		op2.setAsset_issuer(a.getAddress());
		op2.setAsset_amount(100L);
		op2.setAsset_code("1234"+ System.currentTimeMillis());
		tran.submit(a, op1, op2);
		int error_code =  Transaction.getErrorCode();
		check.assertEquals(error_code, 0,"payment and issue check failed");
	}
	//ת�ˣ�����Ψһ�ʲ�
//	@Test
	public void payment_issueUnique(){
		Account a = tran.createAccountOne(genesis);
		Operation op1 = new Operation();
		op1.setDest_address(tran.createAccountOne(genesis).getAddress());
		op1.setType(1);
		op1.setAsset_issuer(a.getAddress());
		op1.setAsset_amount(100L);
		Operation op2 = new Operation();
		op2.setType(7);
		op2.setAsset_issuer(a.getAddress());
		op2.setAsset_amount(100L);
		op2.setAsset_code("123");
		op2.setAsset_detailed("1123");
		tran.submit(a, op1, op2);
		int error_code =  Transaction.getErrorCode();
		check.assertEquals(error_code, 0,"payment and issue unique check failed");
	}
	//ת�ˣ������˻�
//	@Test
	public void payment_create(){
		Account a = tran.createAccountOne(genesis);
		Operation op1 = new Operation();
		op1.setDest_address(tran.createAccountOne(genesis).getAddress());
		op1.setType(1);
		op1.setAsset_issuer(a.getAddress());
		op1.setAsset_amount(100L);
		Operation op2 = new Operation();
		op2.setDest_address(Account.generateAccount().getAddress());
		op2.setType(0);
		tran.submit(a, op1, op2);
		int error_code =  Transaction.getErrorCode();
		check.assertEquals(error_code, 0,"payment and create check failed");
	}
	//ת����������
//	@Test
	public void payment_setOption(){
		Account a = tran.createAccountOne(genesis);
		Operation op1 = new Operation();
		op1.setDest_address(tran.createAccountOne(genesis).getAddress());
		op1.setType(1);
		op1.setAsset_issuer(a.getAddress());
		op1.setAsset_amount(100L);
		Operation op2 = new Operation();
		op2.setType(4);
		Threshold threshold = new Threshold();
		threshold.setHigh_threshold(2);
		threshold.setMed_threshold(2);
		threshold.setLow_threshold(2);
		threshold.setMaster_weight(2);
		op2.setThreshold(threshold);
		tran.submit(a, op1, op2);
		int error_code =  Transaction.getErrorCode();
		check.assertEquals(error_code, 0,"payment and create check failed");
	}
	//ת�˴�֤
//	@Test
	public void payment_evidence(){
		Account a = tran.createAccountOne(genesis);
		Operation op1 = new Operation();
		op1.setDest_address(tran.createAccountOne(genesis).getAddress());
		op1.setType(1);
		op1.setAsset_issuer(a.getAddress());
		op1.setAsset_amount(100L);
		Operation op2 = new Operation();
		op2.setType(9);
		op2.setRecord_ext("1111");
		op2.setRecord_id("123");
		tran.submit(a, op1, op2);
		int error_code =  Transaction.getErrorCode();
		check.assertEquals(error_code, 0,"payment and evidence check failed");
	}
	//���������У�����ǩ��
//	@Test
	public void create_issuer_signers(){
		Account a1 = tran.createAccountOne(genesis);
		Account a2 = Account.generateAccount();
		Operation op1 = new Operation();
		op1.setDest_address(a2.getAddress());
		op1.setType(0);
		op1.setSource_address(a1.getAddress());
		Operation op2 = new Operation();
		op2.setType(2);
		op2.setAsset_issuer(a2.getAddress());
		op2.setAsset_amount(100L);
		op2.setAsset_code("1234"+ System.currentTimeMillis());
		op2.setSource_address(a2.getAddress());
		tran.submitSigners(a1, a2, op1, op2);
		int error_code =  Transaction.getErrorCode();
		check.assertEquals(error_code, 0,"create and issue check failed");
	}
	
}
