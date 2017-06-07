package utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonStruct {

	public static void createAccount(){
		
	}
	/**
	 * ƴ�����˻���JsonString
	 * @param private_keys �����˻�˽Կ
	 * @param source_address  Դ�˻�
	 * @param dest_address  Ŀ���˻�
	 * @return
	 */
	public static JSONObject itemsAll(String private_keys,String source_address, String dest_address,int init_banlance){
		
		Map<String, Object> itemMap = new LinkedHashMap<>();
		itemMap.put("items", items1(private_keys,source_address,dest_address,init_banlance));
		JSONObject jsonObject = JSONObject.fromObject(itemMap);
//		System.out.println(jsonObject);
//		return jsonObject;
//		System.out.println(jsonObject);
		return jsonObject;
	}
	
	@SuppressWarnings("rawtypes")
	public static JSONObject itemsAll(String private_keys,String source_address, List items){
		
		Map<String, Object> itemMap = new LinkedHashMap<>();
		itemMap.put("items", items);
		JSONObject jsonObject = JSONObject.fromObject(itemMap);
//		System.out.println(jsonObject);
//		return jsonObject;
//		System.out.println(jsonObject);
		return jsonObject;
	}
//	public static JSONObject items(){
//		String private_keys = "privbUVfgWsMNvFro7WDCci9NUhiR6eaLcZ3JDGBfRf8BgDULK9sx3p2";
//		Map<String, Object> items = new LinkedHashMap<String,Object>();
//		items.put("private_keys", private_keys(private_keys));
//		items.put("transaction_json", transaction_json());
//		JSONObject jsonObject = JSONObject.fromObject(items);
////		System.out.println(jsonObject);
//		return jsonObject;
//	}
	
	/**
	 * �����˻�-items-��Ҫ����init_balance
	 * @param private_keys
	 * @param source_address
	 * @param dest_address
	 * @return
	 */
	public static List<JSONObject> items1(String private_keys,String source_address, String dest_address,Object init_balance){
//		if(private_keys){
//			
//		}
		Map<String, Object> items = new LinkedHashMap<String,Object>();
		items.put("private_keys", private_keys(private_keys));
		items.put("transaction_json", transaction_json(source_address,dest_address,init_balance));
		
		JSONObject jsonObject = JSONObject.fromObject(items);
		List<JSONObject> itemsList = new ArrayList<JSONObject>();
		itemsList.add(jsonObject);
		return itemsList;
	}
	/**
	 * �����˻�-items-Ĭ��init_balance
	 * @param private_keys
	 * @param source_address
	 * @param dest_address
	 * @return
	 */
	public static List<JSONObject> items1(String private_keys,String source_address, Object dest_address){
		
		Map<String, Object> items = new LinkedHashMap<String,Object>();
		items.put("private_keys", private_keys(private_keys));
		items.put("transaction_json", transaction_json(source_address,dest_address));
		
		JSONObject jsonObject = JSONObject.fromObject(items);
		List<JSONObject> itemsList = new ArrayList<JSONObject>();
		itemsList.add(jsonObject);
		return itemsList;
	}
	
	/**
	 * �ʲ�����-items-json
	 * @param private_keys
	 * @param source_address
	 * @param asset_issuer
	 * @param asset_code
	 * @param asset_amount
	 * @return
	 */
	public static List<JSONObject> items1(String private_keys,String source_address, String asset_issuer,String asset_code,int asset_amount){
		
		Map<String, Object> items = new LinkedHashMap<String,Object>();
		items.put("private_keys", private_keys(private_keys));
		items.put("transaction_json", transaction_json(source_address,asset_issuer,asset_code,asset_amount));
		
		JSONObject jsonObject = JSONObject.fromObject(items);
		List<JSONObject> itemsList = new ArrayList<JSONObject>();
		itemsList.add(jsonObject);
		return itemsList;
	}
	/**
	 * ת��-items
	 * @param private_keys
	 * @param source_address
	 * @param asset_issuer
	 * @param asset_code
	 * @param asset_amount
	 * @return
	 */
	public static List<JSONObject> items1(String private_keys,String source_address, String dest_address,String asset_issuer,String asset_code,int asset_amount,int asset_type){
		
		Map<String, Object> items = new LinkedHashMap<String,Object>();
		items.put("private_keys", private_keys(private_keys));
		items.put("transaction_json", transaction_json(source_address,dest_address,asset_issuer,asset_code,asset_amount,asset_type));
		
		JSONObject jsonObject = JSONObject.fromObject(items);
		List<JSONObject> itemsList = new ArrayList<JSONObject>();
		itemsList.add(jsonObject);
		return itemsList;
	}
	
	/**
	 * ��ʼ��ת��-items1
	 * @param private_keys
	 * @param source_address
	 * @param dest_address
	 * @param asset_issuer
	 * @param asset_code
	 * @param asset_amount
	 * @return
	 */
	public static List<JSONObject> items1(String private_keys,String source_address, String dest_address,String asset_issuer,String asset_code,int asset_amount){
		Map<String, Object> items = new LinkedHashMap<String,Object>();
		items.put("private_keys", private_keys(private_keys));
		items.put("transaction_json", transaction_json(source_address,dest_address,asset_issuer,asset_code,asset_amount));
		
		JSONObject jsonObject = JSONObject.fromObject(items);
		List<JSONObject> itemsList = new ArrayList<JSONObject>();
		itemsList.add(jsonObject);
		return itemsList;
	}
	
	/**
	 * ��������items1
	 * @param private_keys
	 * @param source_address
	 * @param master_weight
	 * @param low_threshold
	 * @param med_threshold
	 * @param high_threshold
	 * @return
	 */
	public static List<JSONObject> items1(String private_keys,String source_address, int master_weight,int low_threshold,int med_threshold,int high_threshold){
		Map<String, Object> items = new LinkedHashMap<String,Object>();
		items.put("private_keys", private_keys(private_keys));
		items.put("transaction_json", transaction_json(source_address,master_weight, low_threshold, med_threshold, high_threshold));
		JSONObject jsonObject = JSONObject.fromObject(items);
		List<JSONObject> itemsList = new ArrayList<JSONObject>();
		itemsList.add(jsonObject);
		return itemsList;
	}
	/**
	 * ��Ӧ��-items1����inputs
	 * @param private_keys
	 * @param source_address
	 * @param inputs
	 * @param outputs
	 * @return
	 */
	public static List<JSONObject> items1(String private_keys,String source_address,JSONArray inputs,JSONArray outputs ){
		Map<String, Object> items = new LinkedHashMap<String,Object>();
		items.put("private_keys", private_keys(private_keys));
		items.put("transaction_json", transaction_json(source_address,inputs, outputs));
		JSONObject jsonObject = JSONObject.fromObject(items);
		List<JSONObject> itemsList = new ArrayList<JSONObject>();
		itemsList.add(jsonObject);
		return itemsList;
	}
	/**
	 * ��Ӧ��-items1-û��inputs
	 * @param private_keys
	 * @param source_address
	 * @param outputs
	 * @return
	 */
	public static List<JSONObject> items1(String private_keys,String source_address,JSONArray outputs ){
		Map<String, Object> items = new LinkedHashMap<String,Object>();
		items.put("private_keys", private_keys(private_keys));
		items.put("transaction_json", transaction_json(source_address, outputs));
		JSONObject jsonObject = JSONObject.fromObject(items);
		List<JSONObject> itemsList = new ArrayList<JSONObject>();
		itemsList.add(jsonObject);
		return itemsList;
	}
	public static JSONArray private_keys(String key1){
		List<String> private_keyList = new ArrayList<String>();
		private_keyList.add(key1);
		JSONArray keysArray = JSONArray.fromObject(private_keyList);
		return keysArray;
	}
	/**
	 * �����˻�-transaction
	 * @param source_address
	 * @param dest_address
	 * @return
	 */
	public static JSONObject transaction_json(String source_address , Object dest_address,Object init_balance){
		
		int fee = 1000;
		Map<String, Object> transaction = new LinkedHashMap<String, Object>();
		transaction.put("source_address", source_address);
		transaction.put("fee", fee);
		transaction.put("operations", operations(dest_address,init_balance));
		JSONObject jsonObject = JSONObject.fromObject(transaction);
		return jsonObject;
//		System.out.println(jsonObject);
	}
	/**
	 * �����˻�-transaction-ʹ��Ĭ�ϵ�init_balance
	 * @param source_address
	 * @param dest_address
	 * @return
	 */
	public static JSONObject transaction_json(String source_address , Object dest_address){
		
		int fee = 1000;
		Map<String, Object> transaction = new LinkedHashMap<String, Object>();
		transaction.put("source_address", source_address);
		transaction.put("fee", fee);
		transaction.put("operations", operations(dest_address,null));
		JSONObject jsonObject = JSONObject.fromObject(transaction);
		return jsonObject;
//		System.out.println(jsonObject);
	}
	/**
	 * �����ʲ�-transaction
	 * @param source_address  Դ�˻�
	 * @param asset_issuer  ������
	 * @param asset_code  ���д���
	 * @param asset_amount  �����ʲ�����
	 * @return
	 */
	public static JSONObject transaction_json(String source_address , String asset_issuer,String asset_code,int asset_amount){
		
		int fee = 1000;
		Map<String, Object> transaction = new LinkedHashMap<String, Object>();
		transaction.put("source_address", source_address);
		transaction.put("fee", fee);
		transaction.put("operations", operations(asset_issuer,asset_code,asset_amount));
		JSONObject jsonObject = JSONObject.fromObject(transaction);
		return jsonObject;
//		System.out.println(jsonObject);
	}
	/**
	 * ת�˽���-transaction-json
	 * @param source_address
	 * @param dest_address
	 * @param asset_issuer
	 * @param asset_code
	 * @param asset_amount
	 * @param asset_type
	 * @return
	 */
	public static JSONObject transaction_json(String source_address , String dest_address,String asset_issuer,String asset_code,int asset_amount,int asset_type){
		
		int fee = 1000;
		Map<String, Object> transaction = new LinkedHashMap<String, Object>();
		transaction.put("source_address", source_address);
		transaction.put("fee", fee);
		transaction.put("operations", operations(dest_address,asset_issuer,asset_code,asset_amount,asset_type));
		JSONObject jsonObject = JSONObject.fromObject(transaction);
		return jsonObject;
//		System.out.println(jsonObject);
	}
	/**
	 * ��ʼ��ת��-transaction
	 * @param source_address
	 * @param dest_address
	 * @param asset_issuer
	 * @param asset_code
	 * @param asset_amount
	 * @return
	 */
	public static JSONObject transaction_json(String source_address , String dest_address,String asset_issuer,String asset_code,int asset_amount){
		int fee = 1000;
		Map<String, Object> transaction = new LinkedHashMap<String, Object>();
		transaction.put("source_address", source_address);
		transaction.put("fee", fee);
		transaction.put("operations", operations(dest_address,asset_issuer,asset_code,asset_amount));
		JSONObject jsonObject = JSONObject.fromObject(transaction);
		return jsonObject;
//		System.out.println(jsonObject);
	}
	
	/**
	 * ��Ӧ��-transaction
	 * @param source_address
	 * @param inputs
	 * @param outputs
	 * @return
	 */
	public static JSONObject transaction_json(String source_address ,JSONArray inputs,JSONArray outputs){
		int fee = 1000;
		Map<String, Object> transaction = new LinkedHashMap<String, Object>();
		transaction.put("source_address", source_address);
		transaction.put("fee", fee);
		transaction.put("operations", operations(inputs,outputs));
		JSONObject jsonObject = JSONObject.fromObject(transaction);
		return jsonObject;
	}
	/**
	 * ��Ӧ��-transaction-û��inputs
	 * @param source_address
	 * @param outputs
	 * @return
	 */
	public static JSONObject transaction_json(String source_address ,JSONArray outputs){
		int fee = 1000;
		Map<String, Object> transaction = new LinkedHashMap<String, Object>();
		transaction.put("source_address", source_address);
		transaction.put("fee", fee);
		transaction.put("operations", operations(outputs));
		JSONObject jsonObject = JSONObject.fromObject(transaction);
		return jsonObject;
	}
	/**
	 * �����˻�-transaction
	 * @param source_address
	 * @return
	 */
	public static JSONObject transaction_json(String source_address){
		int fee = 1000;
		Map<String, Object> transaction = new LinkedHashMap<String, Object>();
		transaction.put("source_address", source_address);
		transaction.put("fee", fee);
		transaction.put("operations", operations(source_address));
		JSONObject jsonObject = JSONObject.fromObject(transaction);
		return jsonObject;
	}
	
	/**
	 * �������� transaction
	 * @param source_address
	 * @param master_weight
	 * @param low_threshold
	 * @param med_threshold
	 * @param high_threshold
	 * @return
	 */
	public static JSONObject transaction_json(String source_address , int master_weight,int low_threshold,int med_threshold,int high_threshold ){
		
		int fee = 1000;
		Map<String, Object> transaction = new LinkedHashMap<String, Object>();
		transaction.put("source_address", source_address);
		transaction.put("fee", fee);
		transaction.put("operations", operations(master_weight, low_threshold, med_threshold, high_threshold));
		JSONObject jsonObject = JSONObject.fromObject(transaction);
		return jsonObject;
//		System.out.println(jsonObject);
	}
	
	/**
	 * �����ʲ�-operation-json
	 * @param asset_issuer
	 * @param asset_code
	 * @param asset_amount
	 * @return
	 */
	@SuppressWarnings({ "unused", "unchecked", "rawtypes" })
	public static JSONArray operations(String asset_issuer,String asset_code,int asset_amount){
		int type = 2;
		int asset_type = 1;
		Map<String, Object> operation = new LinkedHashMap<String, Object>();
		operation.put("asset_issuer", asset_issuer);
		operation.put("asset_code", asset_code);
		operation.put("asset_amount", asset_amount);
		operation.put("type", type);
		List operList = new ArrayList<>();
		operList.add(operation);
		JSONArray operListjson = JSONArray.fromObject(operList);
		return operListjson;
	}
	/**
	 * ת�˽���-operation-json
	 * @param dest_address
	 * @param asset_issuer
	 * @param asset_code
	 * @param asset_amount
	 * @param asset_type
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static JSONArray operations(String dest_address,String asset_issuer,String asset_code,int asset_amount,int asset_type){
		int type = 1;
		JSONArray operListjson = null ;
		if(asset_type==0){  //0������
			Map<String, Object> operation = new LinkedHashMap<String, Object>();
			operation.put("asset_amount", asset_amount);
			operation.put("type", type);
			operation.put("dest_address", dest_address);
			operation.put("asset_type", asset_type);
			List operList = new ArrayList<>();
			operList.add(operation);
			operListjson = JSONArray.fromObject(operList);
			
		}else if (asset_type==1) {  //1�������ʲ�
			Map<String, Object> operation = new LinkedHashMap<String, Object>();
			operation.put("asset_issuer", asset_issuer);
			operation.put("asset_code", asset_code);
			operation.put("asset_amount", asset_amount);
			operation.put("type", type);
			operation.put("dest_address", dest_address);
			List operList = new ArrayList<>();
			operList.add(operation);
			operListjson = JSONArray.fromObject(operList);
		}else {
			System.out.println("asset_type��д����");
			System.exit(0);
		}
		return operListjson;
	}
	
	/**
	 * ��ʼ��ת��-operation
	 * @param dest_address
	 * @param asset_issuer
	 * @param asset_code
	 * @param asset_amount
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static JSONArray operations(String dest_address,String asset_issuer,String asset_code,int asset_amount){
		int asset_type = 1;
		int type = 5;
		JSONArray operListjson = null ;
		Map<String, Object> operation = new LinkedHashMap<String, Object>();
		operation.put("asset_issuer", asset_issuer);
		operation.put("asset_code", asset_code);
		operation.put("asset_amount", asset_amount);
		operation.put("type", type);
		operation.put("dest_address", dest_address);
		operation.put("asset_type", asset_type);
		List operList = new ArrayList<>();
		operList.add(operation);
		operListjson = JSONArray.fromObject(operList);
		return operListjson;
	}

	/**
	 *�����˻�-operation-json
	 * Ĭ��type=0  �����˻�����
	 * ��ʼ���˻����Ϊ200000
	 * ��ʼ��account_metadataΪ��
	 * @param dest_address  Ŀ���˻�
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static JSONArray operations(String dest_address){
		int type =0;
		int init_balance =200000;
		String account_metadata = null;
		Map<String, Object> operation = new LinkedHashMap<String, Object>();
		operation.put("type", type);
		operation.put("dest_address", dest_address);
		operation.put("init_balance", init_balance);
		operation.put("account_metadata", account_metadata);
		List operList = new ArrayList<>();
		operList.add(operation);
		JSONArray operListjson = JSONArray.fromObject(operList);
		return operListjson;
	}
	
	
	/**
	 * ��Ӧ��-operation
	 * @param inputs
	 * @param outputs
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static JSONArray operations(JSONArray inputs,JSONArray outputs){
		int type =6;
		Map<String, Object> operationMap = new LinkedHashMap<String, Object>();
		operationMap.put("inputs", inputs);
		operationMap.put("outputs", outputs);
		operationMap.put("type", type);
		List operList = new ArrayList<>();
		operList.add(operationMap);
		JSONArray operListjson = JSONArray.fromObject(operList);
		return operListjson;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static JSONArray operations(JSONArray outputs){
		int type =6;
		Map<String, Object> operationMap = new LinkedHashMap<String, Object>();
		operationMap.put("inputs", null);
		operationMap.put("outputs", outputs);
		operationMap.put("type", type);
		List operList = new ArrayList<>();
		operList.add(operationMap);
		JSONArray operListjson = JSONArray.fromObject(operList);
		return operListjson;
	}
	
	/**
	 * ��Ӧ��-outputs��û��input
	 * @param out_add
	 * @param metadata
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static JSONArray outputs(String out_add,String metadata){
		Map<String, Object> outputMap = new LinkedHashMap<String, Object>();
		outputMap.put("address", out_add);
		outputMap.put("metadata", metadata);
		List outList = new ArrayList<>();
		outList.add(outputMap);
		JSONArray operListjson = JSONArray.fromObject(outList);
		return operListjson;
	}
	/**
	 * ��Ӧ��-input
	 * @param hash
	 * @param index
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static JSONArray inputs(String hash,int index){
		Map<String, Object> inputMap = new LinkedHashMap<String, Object>();
		inputMap.put("hash", hash);
		inputMap.put("index", index);
		List inList = new ArrayList<>();
		inList.add(inputMap);
		JSONArray operListjson = JSONArray.fromObject(inList);
		return operListjson;
	}
	
	
	/**
	 * �����˻�-operation-json
	 * @param dest_address   Ŀ���˻�
	 * @param init_balance   ��ʼ���˻����
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static JSONArray operations(Object dest_address, Object init_balance){
		int type =0;
		String account_metadata = null;
		Map<String, Object> operation = new LinkedHashMap<String, Object>();
		operation.put("type", type);
		operation.put("dest_address", dest_address);
		operation.put("init_balance", init_balance);
		operation.put("account_metadata", account_metadata);
		List operList = new ArrayList<>();
		operList.add(operation);
		JSONArray operListjson = JSONArray.fromObject(operList);
		return operListjson;
	}
	/**
	 * �����˻�-operation-json
	 * @param dest_address  Ŀ���˻�
	 * @param init_balance   ��ʼ���˻����
	 * @param account_metadata  �˻�˵��   //��ѡ��������д���ʺŵ�metadata�����У�������16����Сд��ʽ
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static JSONArray operations(String dest_address, int init_balance, String account_metadata){
		int type =0;
		Map<String, Object> operation = new LinkedHashMap<String, Object>();
		operation.put("type", type);
		operation.put("dest_address", dest_address);
		operation.put("init_balance", init_balance);
		operation.put("account_metadata", account_metadata);
		List operList = new ArrayList<>();
		operList.add(operation);
		JSONArray operListjson = JSONArray.fromObject(operList);
		return operListjson;
	}
	
	/**
	 * �����˻�-operation-json
	 * @param dest_address  Ŀ���˻�
	 * @param master_weight   Ȩ��
	 * @param low_threshold    ������Сֵ����ʱ���ã�
	 * @param med_threshold   ���ޣ�����ֵ���޸ģ���ʱ�����������ֶα���һ�£�
	 * @param high_threshold   �������ֵ����ʱ���ã�
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static JSONArray operations(String dest_address,int master_weight,int low_threshold,int med_threshold,int high_threshold){
		int type =0;
		int init_balance =200000;
		String account_metadata = null;
		Map<String, Object> operation = new LinkedHashMap<String, Object>();
		operation.put("type", type);
		operation.put("dest_address", dest_address);
		operation.put("init_balance", init_balance);
		operation.put("account_metadata", account_metadata);
		operation.put("threshold", threshold(master_weight, low_threshold, med_threshold, high_threshold));
		List operList = new ArrayList<>();
		operList.add(operation);
		JSONArray operListjson = JSONArray.fromObject(operList);
		return operListjson;
	}
	
	/**
	 * �����˻�-����ֵ����-json
	 * @param master_weight  Ȩ��
	 * @param low_threshold 	������Сֵ����ʱ���ã�
	 * @param med_threshold   ���ޣ�����ֵ���޸ģ���ʱ�����������ֶα���һ�£�
	 * @param high_threshold	�������ֵ����ʱ���ã�
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List threshold(int master_weight,int low_threshold,int med_threshold,int high_threshold){
		Map<String, Integer> thresholdMap = new LinkedHashMap<String ,Integer>();
		thresholdMap.put("master_weight", master_weight);
		thresholdMap.put("low_threshold", low_threshold);
		thresholdMap.put("med_threshold", med_threshold);
		thresholdMap.put("high_threshold", high_threshold);
		List  threList = new ArrayList();
		threList.add(thresholdMap);
		return threList;
	}
	/**
	 * ��������-operation
	 * @param master_weight
	 * @param low_threshold
	 * @param med_threshold
	 * @param high_threshold
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static JSONArray operations(int master_weight,int low_threshold,int med_threshold,int high_threshold){
		int type = 4;
		Map<String, Object> operation = new LinkedHashMap<String, Object>();
		operation.put("type", type);
		operation.put("threshold", threshold(master_weight, low_threshold, med_threshold, high_threshold));
		List operList = new ArrayList<>();
		operList.add(operation);
		JSONArray operListjson = JSONArray.fromObject(operList);
		return operListjson;
	}
	/**
	 * �����˻�-operation-json�����õ�ַ������ǩ��
	 * @param dest_address  Ŀ���ַ
	 * @param signer		��������ǩ��
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static JSONArray operations(String dest_address,JSONArray signer){
		int type =0;
		int init_balance =200000;
		String account_metadata = null;
		Map<String, Object> operation = new LinkedHashMap<String, Object>();
		operation.put("type", type);
		operation.put("dest_address", dest_address);
		operation.put("init_balance", init_balance);
		operation.put("account_metadata", account_metadata);
		operation.put("signers", signer);
		List operList = new ArrayList<>();
		operList.add(operation);
		JSONArray operListjson = JSONArray.fromObject(operList);
		return operListjson;
	}
	
	/**
	 * �����˻�-operation-json ����������signer
	 * @param dest_address
	 * @param address1
	 * @param weigth1
	 * @param address2
	 * @param weigth2
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static JSONArray operations(String dest_address,String address1,int weigth1,String address2,int weigth2){
		int type =0;
		int init_balance =200000;
		String account_metadata = null;
		Map<String, Object> operation = new LinkedHashMap<String, Object>();
		operation.put("type", type);
		operation.put("dest_address", dest_address);
		operation.put("init_balance", init_balance);
		operation.put("account_metadata", account_metadata);
		operation.put("signers", signers(address1, weigth1, address2, weigth2));
		List operList = new ArrayList<>();
		operList.add(operation);
		JSONArray operListjson = JSONArray.fromObject(operList);
		return operListjson;
	}
	/**
	 * ��������ǩ����һ���˻�
	 * @param address
	 * @param weigth
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static JSONArray signers(String address, int weigth){
		Map<String, Object> signerMap = new LinkedHashMap<String ,Object>();
		signerMap.put("address", address);
		signerMap.put("weigth", weigth);
		List signerList = new ArrayList<>();
		signerList.add(signerMap);
		JSONArray signerArray = JSONArray.fromObject(signerList);
		return signerArray;
	}
	/**
	 * ��������ǩ���������û�ǩ��
	 * @param address1
	 * @param weigth1
	 * @param address2
	 * @param weigth2
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static JSONArray signers(String address1, int weigth1,String address2, int weigth2){
		Map<String, Object> signerMap1 = new LinkedHashMap<String ,Object>();
		signerMap1.put("address", address1);
		signerMap1.put("weigth", weigth1);
		Map<String, Object> signerMap2 = new LinkedHashMap<String ,Object>();
		signerMap2.put("address", address2);
		signerMap2.put("weigth", weigth2);
		List signerList = new ArrayList<>();
		signerList.add(signerMap1);
		signerList.add(signerMap2);
		JSONArray signerArray = JSONArray.fromObject(signerList);
		return signerArray;
	}
	public static void issued(){
		
	}

}

	class signer{
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public signer(String address, int weigth){
			Map<String, Object> signerMap = new LinkedHashMap<String ,Object>();
			signerMap.put("address", address);
			signerMap.put("weigth", weigth);
			List signerList = new ArrayList<>();
			signerList.add(signerMap);
		}
	}
