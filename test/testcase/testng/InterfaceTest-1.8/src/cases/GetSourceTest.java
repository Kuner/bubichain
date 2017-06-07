package cases;

import utils.Result;
import utils.TxUtil;

import org.testng.annotations.Test;

import base.TestBase;
import model.Account;
import model.InputInfo;

//��Ӧ����Դ��ѯ
@Test
public class GetSourceTest extends TestBase{

	Account srcAcc = TxUtil.createNewAccount();
	/*
	 * 1.����һ�ʹ�Ӧ��������ȡ�ý��׳ɹ���hash
	 * 2.ͨ����hash���й�Ӧ����Դ��ѯ
	 * 3.��Ӧ�������е�source_address�Ƿ�ͷ����е�addressһ��
	 * 4.��Ӧ�������е�input-��Ӧ��address�Ƿ�ͷ����е�fromһ��
	 */
//	@Test
	public void getSource01Check() {
		InputInfo info = TxUtil.input();
		String hash = info.getHash();
		String result = Result.getSources(hash); // �õ���Ӧ����Դ�Ľ��
		String address = Result.getAddress(result);
		check.assertEquals(address, info.getFirstLevel().getAddress(), "һ����Ӧ����Դ����Դ��ַ����");
	}
	//��֤������Ӧ����Դ�����ϼ���ַ��ȷ��һ��input��һ��output��
//	@Test
	public void getSource02Check(){
		InputInfo info = TxUtil.input2();
		String result = Result.getSources(info.getHash(),"2");
		System.out.println(result);
		String address = Result.getAddInFrom(result);
		check.assertEquals(address, info.getFirstLevel().getAddress(), "һ����Ӧ����Դ����Դ��ַ����");
	}
	
	//��֤������Ӧ����Դ��depthΪ1�����ϼ����ϼ���ַ��ȷ
//	@Test
	public void getSource03Check(){
		InputInfo info = TxUtil.input2();
		String result = Result.getSources(info.getHash(),"1");
		String address = Result.getAddInFrom(result);
		String firstLevel = info.getFirstLevel().getAddress();
		check.assertEquals(address, firstLevel, "һ����Ӧ����ַ����");
	}
	
	/*
	 * ����������Ӧ����Ȼ��depth���ȡֵ����address�Ƿ���ʼ��һ��
	 */
//	@Test
	public void getSources04Check(){
		InputInfo info = TxUtil.input3();
		String result = Result.getSources(info.getHash(),"1");
		String address = Result.getAddInFrom(result);
		String secondLevel = info.getSecondLevel().getAddress();
		check.assertEquals(address, secondLevel,"��Ӧ��������ַ����");
	}
}
