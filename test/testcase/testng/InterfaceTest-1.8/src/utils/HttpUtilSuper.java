package utils;


import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.SSLContext;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import base.ErrorHandler;


public class HttpUtilSuper {

	private static CloseableHttpClient httpClient = null;
	private static final String ENCODING = "utf-8";
	private static final int timeout = 3000;
	private static  RequestConfig requestConfig = null; 
	
	static{
		//����ȫ������-���ӳ�ʱʱ��
		requestConfig = RequestConfig.custom()
				 		// �����Ǵ����ַ������˿ںţ�Э������
//						.setProxy(new HttpHost("127.0.0.1", 8080, "http"))
						.setConnectionRequestTimeout(timeout)
						.setConnectTimeout(timeout)    
						.setSocketTimeout(timeout).build();

		//��ȡһ������SSL֤����֤��http client����,����ͬʱ����httpsЭ��
		httpClient = getIgnoreSSLCertificateHttpClient();	
	}
	
	@SuppressWarnings("deprecation")
	public static CloseableHttpClient getIgnoreSSLCertificateHttpClient(){

		SSLContext sslContext = null;
		try {
			sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
		    @Override
		    public boolean isTrusted(final X509Certificate[] arg0, final String arg1)
		      throws CertificateException {
		      //��Զ������֤ͨ��
		      return true;
		    }
		  }).build();
		} catch (Exception e) {
		  ErrorHandler.stopRunning(e, "����http clientʧ��!");
		}
		SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext,
		  new NoopHostnameVerifier());
		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
		  .<ConnectionSocketFactory> create()
		  .register("http", PlainConnectionSocketFactory.getSocketFactory())
		  .register("https", sslSocketFactory).build();
		PoolingHttpClientConnectionManager connMgr = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
		return HttpClientBuilder.create().setSslcontext(sslContext).setConnectionManager(connMgr)
		  .build();
	}
	
	public static String doGet(String url){
		return get(url, null, null);
	}

	public static String doGet(String url, String charset){
		return get(url, null, charset);
	}
	
	public static String doGet(String url, Map<String, Object> params){	
		url += "?" + CommonUtil.map2UrlParams(params, ENCODING);
		return get(url, null, null);		
	}
	
	public static String doGet(String url, Map<String, Object> headers, String charset){	
		return get(url, headers, charset);		
	}
	
	public static String doGet(String url, Map<String, Object> headers, Map<String, Object> params, String charset){	
		url += "?" + CommonUtil.map2UrlParams(params, ENCODING);
		return get(url, headers, null);		
	}
	
	public static String doPost(String url, String requestStr, String charset){
		return post(url, null, requestStr, null, charset);		
	}

	public static String doPost(String url, Map<String, Object> headers, String requestStr, String charset){
		return post(url, headers, requestStr, null, charset);			
	}
	
	public static String doPost(String url, Map<String,Object> params, String charset){
		return post(url, null, null, params, charset);		
	}
	
	public static String doPost(String url, Map<String, Object> headers, Map<String,Object> params, String charset){
		return post(url, headers, null, params, charset);			
	}
	
	
	private static String get(String url, Map<String, Object> headers, String charset){
		String result = "";
		try {
			//ͳһͨ��url�ַ�������get����ʵ��
			HttpGet httpGet = new HttpGet(url);
			//����ȫ�ֻ�������
			httpGet.setConfig(requestConfig);
			//���������������ͷ,���ش���
			if(!CommonUtil.isEmpty(headers)){
				for(Entry<String, Object> entry : headers.entrySet()){
					httpGet.addHeader(entry.getKey(),  entry.getValue().toString());
		         }
			}			
			
			//����CloseableHttpResponse ����ʹ��HttpResponse�ӿ�,��Ϊ�˵���close()�ر�����
			
			Date start_date=new Date();
			CloseableHttpResponse response = httpClient.execute(httpGet);
			Date end_date=new Date();
			long responseTime = end_date.getTime()-start_date.getTime();
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				//ע������
				httpGet.abort();
				ErrorHandler.stopRunning("��Ӧ״̬�쳣��"+statusCode);
		    }
			HttpEntity entity = response.getEntity();
			if (entity != null){
				//δָ�������ʽ��Ĭ��utf-8
				if(charset == null){
					result = EntityUtils.toString(entity, ENCODING);
				}else{
					result = EntityUtils.toString(entity, charset);
				}
		    }				
			
			//��Ӧʵ����input������ʹ�ú�Ҫע���൱�ڹر���,����io����
	        EntityUtils.consume(entity);
	        //�ر�����
	        response.close();
		} catch (Exception e) {
			ErrorHandler.stopRunning(e, "����http-get�����쳣");
		}			
		return result;
	}
	
	private static String post(String url, Map<String, Object> headers, String requestStr, Map<String,Object> params, String charset){
		
		String result = "";		
		if(!CommonUtil.isEmpty(charset)){
			charset = ENCODING;
		}
		
		try {
			//����post����ʵ��
			HttpPost httpPost = new HttpPost(url);
			//����ȫ�ֻ�������
			httpPost.setConfig(requestConfig);
			//���������������ͷ,���ش���
			if(!CommonUtil.isEmpty(headers)){
				for(Entry<String, Object> entry : headers.entrySet()){
					httpPost.addHeader(entry.getKey(),  entry.getValue().toString());
		        }
			}	
			
			//���ʹ�õ��Ǵ��ַ���ʵ��,ֱ��װ��ʵ��
			if(!CommonUtil.isEmpty(requestStr)){
				StringEntity strEntity = new StringEntity(requestStr, charset);
				httpPost.setEntity(strEntity);
			}
			
			//���ʹ�õ���Map��ֵ��,ת������,ƴ�ӱ���ʵ��
			if(!CommonUtil.isEmpty(params)){
				List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
                for(Map.Entry<String,Object> entry : params.entrySet()){
                    String value = entry.getValue().toString();
                    if(!CommonUtil.isEmpty(value)){
                        pairs.add(new BasicNameValuePair(entry.getKey(), value));
                    }
                }
                
                if(!CommonUtil.isEmpty(pairs)){
                	httpPost.setEntity(new UrlEncodedFormEntity(pairs, charset));
                }
			}
						
			CloseableHttpResponse response = httpClient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				httpPost.abort();
				ErrorHandler.stopRunning("��Ӧ״̬�쳣��"+statusCode);
		    }
			HttpEntity entity = response.getEntity();
			if (entity != null){
				if(charset == null){
					result = EntityUtils.toString(entity, ENCODING);
				}else{
					result = EntityUtils.toString(entity, charset);
				}
		    }				
	        EntityUtils.consume(entity);
	        response.close();
			
		} catch (Exception e) {
			ErrorHandler.stopRunning(e, "����http-post�����쳣");
		}
		return result;		
	}

	private static String post(String url, JSONObject jsonObject) {

		String result = "";
		// if(!CommonUtil.isEmpty(charset)){
		// charset = ENCODING;
		// }

		try {
			// ����post����ʵ��
			HttpPost httpPost = new HttpPost(url);
			// ����ȫ�ֻ�������
			httpPost.setConfig(requestConfig);
			// ���������������ͷ,���ش���
			// if(!CommonUtil.isEmpty(headers)){
			// for(Entry<String, Object> entry : headers.entrySet()){
			// httpPost.addHeader(entry.getKey(), entry.getValue().toString());
			// }
			// }

			// ���ʹ�õ��Ǵ��ַ���ʵ��,ֱ��װ��ʵ��
			// if(!CommonUtil.isEmpty(requestStr)){
			// StringEntity strEntity = new StringEntity(requestStr, charset);
			// httpPost.setEntity(strEntity);
			// }

			// ���ʹ�õ���Map��ֵ��,ת������,ƴ�ӱ���ʵ��
			// if(!CommonUtil.isEmpty(params)){
			// List<NameValuePair> pairs = new
			// ArrayList<NameValuePair>(params.size());
			// for(Map.Entry<String,Object> entry : params.entrySet()){
			// String value = entry.getValue().toString();
			// if(!CommonUtil.isEmpty(value)){
			// pairs.add(new BasicNameValuePair(entry.getKey(), value));
			// }
			// }

			// if(!CommonUtil.isEmpty(pairs)){
			// httpPost.setEntity(new UrlEncodedFormEntity(pairs, charset));
			// }
			// }

			CloseableHttpResponse response = httpClient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				httpPost.abort();
				ErrorHandler.stopRunning("��Ӧ״̬�쳣��" + statusCode);
			}
			HttpEntity entity = response.getEntity();
			// if (entity != null){
			// if(charset == null){
			// result = EntityUtils.toString(entity, ENCODING);
			// }else{
			// result = EntityUtils.toString(entity, charset);
			// }
			// }
			EntityUtils.consume(entity);
			response.close();

		} catch (Exception e) {
			ErrorHandler.stopRunning(e, "����http-post�����쳣");
		}
		return result;
	}

}
