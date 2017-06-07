package cases;

import java.util.List;

import org.testng.annotations.Test;

import base.TestBase;
import model.Account;
import model.Address;
import utils.Result;
import utils.TxUtil;

@Test
public class GetAssetRankTest extends TestBase{
	String asset_code = "123code";
	Long asset_amount = 1000L;
	String metadata = "aabc";
	Account srcAcc = TxUtil.createNewAccount();

	/*
	 * ��֤�ʲ����й�����ȷ��û�е�ַ���ˣ�
	 * ���ؽ�����и�asset_code��Ӧ���ʲ�
	 */
//	@Test
	public void getAssetRank_normalCheck(){
		Account srcAcc = TxUtil.createNewAccount();
		TxUtil.issue_transfer(srcAcc, asset_code, asset_amount, metadata);
		String result = TxUtil.getAssetRank(srcAcc, asset_code, null, null);
		System.out.println(result);
		List<Address> asd = Result.getAssetRankAdds(result);
		int err_code = Result.getErrorCode(result);
		check.assertEquals(err_code, 0, "��ȡ�ʲ�����У��ʧ��");
		int num = Result.getNumberByAddress(asd.get(0));
		check.assertEquals(num, 1,"�ʲ����з��ص�ַУ��ʧ��");
	}
	
	/*
	 * ��֤�ʲ����к�δ����ת�ƣ����ʲ����в�ѯ�����ؽ����ȷ�������ط�������Ϣ��
	 */
//	@Test
	public void getAssetRank_normal01Check(){
		TxUtil.issue(srcAcc, asset_code, asset_amount, metadata);
		String result = TxUtil.getAssetRank(srcAcc, asset_code, null, null);
		int err_code = Result.getErrorCode(result);
		String re = Result.getResultTh(result);
		check.assertEquals(err_code, 0, "��ȡ�ʲ�����errorcodeУ��ʧ��");
		check.assertEquals(re, "null","��ȡ�ʲ�����resultУ��ʧ��");
	}
	/*
	 * ��֤��֤�ʲ����й�����ȷ���е�ַ���ˣ�
	 */
//	@Test		//	���˵�ַ���������ɸ���ĵ�ַ
	public void getAssetRank_normal02Check(){
		Account srcAcc = TxUtil.createNewAccount();
		List<String> addrs = TxUtil.issue_transfer(srcAcc, asset_code, asset_amount, metadata);
		String result = TxUtil.getAssetRank(srcAcc, asset_code, addrs.get(0), null);
		List<Address> asd = Result.getAssetRankAdds(result);
		int err_code = Result.getErrorCode(result);
		check.assertEquals(err_code, 0, "��ȡ�ʲ�����У��ʧ��");
		check.assertEquals(asd.get(0).getAddress(), addrs.get(1),"�ʲ����з��ص�ַУ��ʧ��");
	}
	
	/*
	 * ��֤asset_issuer��������У��ɹ�
	 */
//	@Test
	public void asset_issuerCheck(){
		TxUtil.issue_transfer(srcAcc, asset_code, asset_amount, metadata);
		Object[] assetIssuers = {"abcd", "1234", " ", "null", null, "!@#"};
		for (Object assetIssuer : assetIssuers) {
			String result = TxUtil.getAssetRank(assetIssuer, asset_code, null, null);
			int err_code = Result.getErrorCode(result);
			check.assertEquals(err_code, 2, "��ȡ�ʲ�����asset_issuerУ��ʧ��");
		}
	}
	/*
	 * ��֤asset_issuer�ֶ�ȱʧ��������ȷ
	 */
//	@Test
	public void asset_issuerNoCheck(){
		String result = TxUtil.getAssetRank(null, asset_code, null, null);
		int err_code = Result.getErrorCode(result);
		check.assertEquals(err_code, 2, "��ȡ�ʲ�����asset_issuer�ֶ�ȱʧУ��ʧ��");
	}
	
	/*
	 * ��֤asset_issuer�˻������ڣ����ؽ��Ϊnull��error_code 0
	 */
//	@Test
	public void asset_issuerNotExistCheck(){
		String result = TxUtil.getAssetRank(Account.generateAccount().getAddress(), asset_code, null, null);
		int err_code = Result.getErrorCode(result);
		String re = Result.getResultTh(result);
		check.assertEquals(err_code, 0, "��ȡ�ʲ�����errorcodeУ��ʧ��");
		check.assertEquals(re, "null","��ȡ�ʲ�����resultУ��ʧ��");
	}
	//��֤asset_issuer��Ӧ��asset_code�����ڣ����ش�����Ϣ��ȷ
	public void asset_codeNotExistCheck(){
		String result = TxUtil.getAssetRank(srcAcc, "bc", null, null);
		int err_code = Result.getErrorCode(result);
		String re = Result.getResultTh(result);
		check.assertEquals(err_code, 0, "��ȡ�ʲ�����errorcodeУ��ʧ��");
		check.assertEquals(re, "null","��ȡ�ʲ�����resultУ��ʧ��");
	}
	//��֤asset_codeֵ�Ƿ������ش�����Ϣ��ȷ
//	@Test
	public void asset_codeCheck(){
		TxUtil.issue_transfer(srcAcc, asset_code, asset_amount, metadata);
		Object[] assetIssuers = {"abcd", "1234", " ", "!@#","-1"};
		for (Object assetIssuer : assetIssuers) {
			String result = TxUtil.getAssetRank(srcAcc, assetIssuer, null, null);
			int err_code = Result.getErrorCode(result);
			String re = Result.getResultTh(result);
			check.assertEquals(err_code, 0, "��ȡ�ʲ�����asset_codeУ��ʧ��");
			check.assertEquals(re, "null","��ȡ�ʲ�����resultУ��ʧ��");
		}
	}
	//��֤asset_codeֵΪnull�����ش�����Ϣ��ȷ
//	@Test
	public void asset_codeNullCheck(){
		TxUtil.issue_transfer(srcAcc, asset_code, asset_amount, metadata);
		Object[] assetIssuers = {"null", null};
		for (Object assetIssuer : assetIssuers) {
			String result = TxUtil.getAssetRank(srcAcc, assetIssuer, null, null);
			int err_code = Result.getErrorCode(result);
			String re = Result.getResultTh(result);
			check.assertEquals(err_code, 2, "��ȡ�ʲ�����asset_codeУ��ʧ��");
			check.assertEquals(re, "null","��ȡ�ʲ�����resultУ��ʧ��");
		}
	}
	//��֤asset_code�ֶ�ȱ�٣�������Ϣ��ȷ
//	@Test
	public void asset_codeNoCheck(){
		String result = TxUtil.getAssetRank(srcAcc, null, null, null);
		int err_code = Result.getErrorCode(result);
		check.assertEquals(err_code, 2, "��ȡ�ʲ�����asset_code�ֶ�ȱʧУ��ʧ��");
	}
	//��֤���˵�ַ�����ڣ����ؽ����ȷ
//	@Test		//	���������Է�������δ���˽��
	public void filteraddrNotExist(){
		TxUtil.issue_transfer(srcAcc, asset_code, asset_amount, metadata);
		Object[] filterAdds = {"abcd", "1234", " ", "!@#","-1"};
		for (Object filteraddr : filterAdds) {
			String result = TxUtil.getAssetRank(srcAcc, asset_code, filteraddr, null);
			int err_code = Result.getErrorCode(result);
			check.assertEquals(err_code, 2, "��ȡ�ʲ�����asset_code�ֶ�ȱʧУ��ʧ��");
		}
	}
	//��֤���˵�ֵַ�Ƿ���������Ϣ��ȷ
	
	//��֤���˵�ַΪ����������ʱ�����ؽ����ȷ
//	@Test
	public void filter_addr_twoCheck(){
		List<String> addrs = TxUtil.issue_transfer(srcAcc, asset_code, asset_amount, metadata);
		String result = TxUtil.getAssetRank(srcAcc, asset_code, addrs, null);
		int err_code = Result.getErrorCode(result);
		check.assertEquals(err_code, 0, "��ȡ�ʲ�����asset_code�ֶ�ȱʧУ��ʧ��");
		String re = Result.getResultTh(result);
		check.assertEquals(re, "null","��ȡ�ʲ�����resultУ��ʧ��");
	}
	//��֤���˵�ַ��ʽ����ȷ��������Ϣ��ȷ
	//��֤count�ֶ���ʾ������ȷ
//	@Test
	public void countCheck(){
		TxUtil.issue_transfer(srcAcc, asset_code, asset_amount, metadata);
		String result = TxUtil.getAssetRank(srcAcc, asset_code, null, 1);
		int count = Result.getAssetRankResultSize(result);
		check.assertEquals(count, 1, "���ؽ������У��ʧ��");
	}
	//��֤countֵ�Ƿ������ش�����Ϣ��ȷ
//	@Test
	public void countilligalCheck(){
		Object[] counts = {"abcd", "1234", " ", "!@#","-1"};
		for (Object count : counts) {
			String result = TxUtil.getAssetRank(srcAcc, asset_code, null, count);
			int err_code = Result.getErrorCode(result);
			check.assertEquals(err_code, 2, "��ȡ�ʲ�����count�ֶ�У��ʧ��");
		}
	}
	//��֤count����ӵ���߸��������������ȷ
//	@Test
	public void countmaxCheck(){
		Account srcAcc = TxUtil.createNewAccount();
		TxUtil.issue_transfer(srcAcc, asset_code, asset_amount, metadata);
		String result = TxUtil.getAssetRank(srcAcc, asset_code, null, 3);
		int count = Result.getAssetRankResultSize(result);
		check.assertEquals(count, 2, "���ؽ������У��ʧ��");
	}
	//��֤����amount��number��ȷ
//	@Test
	public void amountCheck(){
		Account srcAcc = TxUtil.createNewAccount();
		Account a1 = TxUtil.createNewAccount();
		Account a2 = TxUtil.createNewAccount();
		Account a3 = TxUtil.createNewAccount();
		List<Account> accounts = TxUtil.issue_transfer(srcAcc, a1, 10L, a2, 20L, a3, 40L, asset_code, asset_amount, metadata);
		String result = TxUtil.getAssetRank(srcAcc, asset_code, null, null);
		List<Address> adds = Result.getAssetRankAdds(result);
		String add1 = adds.get(0).getAddress();
		int num = adds.get(0).getNumber();
		check.assertEquals(add1, accounts.get(2).getAddress(),"���е�һ�ĵ�ַУ�����");
		check.assertEquals(num, 1,"ӵ���ʲ���������У�����");
		//��֤�ʲ��Զ�ĵ�ַ��������һ
	}
	
}
