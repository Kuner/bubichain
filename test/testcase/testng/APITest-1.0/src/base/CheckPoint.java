package base;

import java.util.List;

import org.testng.Reporter;
import org.testng.asserts.Assertion;
import org.testng.asserts.IAssert;

public class CheckPoint extends Assertion{

	private int flag = 0;

	private String caseName = "";
	
	
//	public CheckPoint(String caseName) {
//
//		this.caseName = caseName;
//
//	}
	@Override
	public void onAssertFailure(IAssert assertCommand){
		// System.out.println("[Log]:����ʧ��: ʵ��: "+assertCommand.getActual()
		// +" Ԥ��: "+assertCommand.getExpected() + " "
		// +assertCommand.getMessage() );

		Log.info("����ʧ��: ʵ��: " + assertCommand.getActual() + " Ԥ��: "
				+ assertCommand.getExpected() + " "
				+ assertCommand.getMessage());
//		Reporter.log(caseName+":"+assertCommand.getMessage());

		flag = flag+1;
	}
	
	@Override

	public void onAssertSuccess( IAssert assertCommand){

		// System.out.println("[Log]:���Գɹ�: ʵ��: "+assertCommand.getActual()
		// +" Ԥ��: "+assertCommand.getExpected() );
		// Log.info(assertCommand.getMessage());
		Log.info("���Գɹ�: ʵ��: " + assertCommand.getActual() + " Ԥ��: "
				+ assertCommand.getExpected());

	}

	

	

	public void equals(boolean actual,boolean expected,String message){

		try{

			assertEquals(actual, expected, message);

		}catch(Error e){}

	}

	public void equals(long actual, long expected, String message) {

		try {

			assertEquals(actual, expected, message);

		} catch (Error e) {
		}

	}

		

	public void equals(String actual,String expected,String message){

		try{

			assertEquals(actual, expected, message);

		}catch(Error e){}

	}
	
	
	public void equals(int actual,int expected,String message){

		try{

			assertEquals(actual, expected, message);

		}catch(Error e){}

	}
	
	@Override
	public void fail(String msg) {
		try {
			assertEquals(true, false, msg);
		} catch (Error e) {
		}
	}

	/**
	 * ��Ҫ�Ƚϵ�����ֵ��������ڷ���true��С�ڷ���false
	 */
	public boolean compare(int a,int b){
		if ((a-b) >0) {
			return true;
		} else {
			return false;
		}
	}
	
	public void largerThan(int actual,int expected,String message){
		try{
			boolean act = compare(actual, expected);
			assertEquals(act, true, message);
		}catch(Error e){}
	}
	
	public void smallerThan(int actual,int expected,String message){

		try{
			boolean act = compare(actual, expected);
			assertEquals(act, false, message);

		}catch(Error e){}

	}

	

	public void equals(int actual,int expected){

		try{

			assertEquals(actual, expected);

		}catch(Error e){}

	}

	

	public void equalsNotNull(Object actual, String message){

		try{

			this.assertNotNull(actual, message);

		}catch(Error e){}

	}

	

	public void notEquals(String actual, String expected, String message){

		try{
			assertNotEquals(actual, expected, message);

		}catch(Error e){}				

	}

	public void notEquals(int actual, int expected, String message){

		try{

			assertNotEquals(actual, expected, message);

		}catch(Error e){}				

	}
	
	public void notNull(int actual, String message){

		try{
			assertNotNull(actual, message);

		}catch(Error e){}				

	}
	
	

	public void equals(List<String> actuals, String expected ,String message){

		if(actuals.size()!=0){

			for(String actual:actuals){

				try{

					assertEquals(actual, expected, message);

				}catch(Error e){}	

			}	

		}else System.out.println("���㺯��:ʵ�ʽ�� ���� ����Ϊ��!");

	}

		

	public void notEquals(List<String> actuals, String expected ,String message){

		if(actuals.size()!=0){

			for(String actual:actuals){

				try{

					assertNotEquals(actual, expected, message);

				}catch(Error e){}	

			}	

		}else System.out.println("���㺯��:ʵ�ʽ�� ���� ����Ϊ��!");

	}

	public void contains(String actual, String expected, String message){

		if(!actual.contains(expected)){

			equals(true, false, message);

		}

	}

	public void result(String message){	
		assertEquals(flag, 0,"ʧ�ܴ���"+flag);
		Log.info(message);
//		org.testng.Assert.assertEquals(flag, 0);
//		Reporter.log(caseName+":"+message);

	}
	
//	public void result(){	
////		System.out.println(message);
//			assertEquals(flag, 0);
////		org.testng.Assert.assertEquals(flag, 0);
////		Reporter.log(caseName+":"+message);
//
//	}

	public void initFlag(){

		flag = 0;		

	}

	

	public void isFailed(String message){

		assertEquals(true, false, message);

//		WebSuite.resultLog.add(caseName+":"+message);

		Reporter.log(caseName+":"+message);

	}

	
}
