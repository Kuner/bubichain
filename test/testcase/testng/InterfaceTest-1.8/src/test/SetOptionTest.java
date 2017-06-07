package test;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import base.TestBase;
import cn.bubi.blockchain.adapter.Message.Operation.Type;
import model.Account;
import model.Operation;
import model.Signer;
import newop.Transaction;
import utils.Result;

@Test
public class SetOptionTest extends TestBase{
	Transaction tran = new Transaction();
	/*
	 * 1.��֤�������Գɹ�
	 * 2.��֤�������Գɹ���metadata_version��1
	 * �����ֶ�����threshold ��metadata��metadata_version���䣬����1��
	 */
//	@Test
	public void setoption(){
		Account a1 = tran.createAccountOne(genesis);
		tran.setOption(a1,2,2,2,2);
		int e = Transaction.getErrorCode();
		check.assertEquals(e, 0,"set option check failed");
		int meta_version = Result.getMetadata_version(a1.getAddress());
		check.assertEquals(meta_version, 1, "set option metadata check failed");
	}
	/*
	 * ��֤�������ԣ�����operation��metadata�ɹ�
	 */
//	@Test
	public void setoption_metadata(){
		Account a1 = tran.createAccountOne(genesis);
		tran.setOption(a1,"1234");
		int e = Transaction.getErrorCode();
		check.assertEquals(e, 0,"set option metadata check failed");
	}
	
	/*
	 * ��֤�������ԣ�����Threshold��metadata�ɹ�
	 */
//	@Test
	public void setoption_thres_metadata(){
		Account a1 = tran.createAccountOne(genesis);
		tran.setOptionWithThreshold(a1,"1234");
		int e = Transaction.getErrorCode();
		check.assertEquals(e, 0,"set option metadata check failed");
	}
	
	/*
	 * ��֤�������ԣ�����Threshold��metadata�ɹ���metadata_version���2
	 */
//	@Test
	public void setoption_thres_metadata_version(){
		Account a1 = tran.createAccountOne(genesis);
		tran.setOptionWithThreshold(a1,"1234");
		int meta_version = Result.getMetadata_version(a1.getAddress());
		check.assertEquals(meta_version, 2, "set option metadata check failed");
	}
	
//	@Test
	public void setOption_High(){
		Account a1 = tran.createAccountOne(genesis);
		Operation op = new Operation();
		op.setType(Type.SET_OPTIONS_VALUE);
		tran.setOption(a1,-1,2,2,2);
		int e = Transaction.getErrorCode();
		check.assertEquals(e, 2,"set option high threshold check failed");
	}
//	@Test
	public void setOption_Med(){
		Account a1 = tran.createAccountOne(genesis);
		Operation op = new Operation();
		op.setType(Type.SET_OPTIONS_VALUE);
		tran.setOption(a1,2,2,-2,2);
		int e = Transaction.getErrorCode();
		check.assertEquals(e, 2,"set option med threshold check failed");
	}
//	@Test
	public void setOption_Low(){
		Account a1 = tran.createAccountOne(genesis);
		Operation op = new Operation();
		op.setType(Type.SET_OPTIONS_VALUE);
		tran.setOption(a1,2,-2,2,2);
		int e = Transaction.getErrorCode();
		check.assertEquals(e, 2,"set option low threshold check failed");
	}
//	@Test
	public void setOption_Master(){
		Account a1 = tran.createAccountOne(genesis);
		Operation op = new Operation();
		op.setType(Type.SET_OPTIONS_VALUE);
		tran.setOption(a1,2,2,2,-2);
		int e = Transaction.getErrorCode();
		check.assertEquals(e, 2,"set option master weight check failed");
	}
//	@Test
	public void setOption_signer_address(){
		Account a1 = tran.createAccountOne(genesis);
		List<Signer> signers = new ArrayList<>();
		Signer signer = new Signer();
		signer.setAddress("123");
		signer.setWeight(3);
		signers.add(signer);
		Transaction tran = new Transaction();
		tran.setOption(a1,signers);
		int e = Transaction.getErrorCode();
		check.assertEquals(e, 2,"set option signer address check failed");
	}
//	@Test
	public void setOption_signer_weight(){
		Account a1 = tran.createAccountOne(genesis);
		Account a2 = tran.createAccountOne(genesis);
		List<Signer> signers = new ArrayList<>();
		Signer signer = new Signer();
		signer.setAddress(a2.getAddress());
		signer.setWeight(-3);
		signers.add(signer);
		a1.seqence = Account.getSeq(a1);  //get a1 seq
		tran.setOption(a1,signers);
		int e = Transaction.getErrorCode();
		check.assertEquals(e, 2,"set option signer weight check failed");
	}	
}
