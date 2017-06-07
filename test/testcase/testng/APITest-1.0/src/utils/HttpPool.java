package utils;

import java.io.IOException;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;

import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import com.google.gson.JsonObject;


import base.ErrorHandler;
import base.TestBase;

public class HttpPool extends TestBase {

	private RequestConfig requestConfig = null;
	private static int maxTotal = 200;
	private static int maxPerRoute = 50;
	private static int reTryTimes = 3;
	private static final String ENCODING = "utf-8";

	public HttpPool() {
		requestConfig = RequestConfig.custom()
				.setConnectionRequestTimeout(5000)
				.setConnectTimeout(6000)
				.setSocketTimeout(5000).build();
	}
	
	public static CloseableHttpClient getClientFromHttpPool() {
		ConnectionSocketFactory plainsf = PlainConnectionSocketFactory
				.getSocketFactory();
		LayeredConnectionSocketFactory sslsf = SSLConnectionSocketFactory
				.getSocketFactory();
		Registry<ConnectionSocketFactory> registry = RegistryBuilder
				.<ConnectionSocketFactory> create().register("http", plainsf)
				.register("https", sslsf).build();
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(
				registry);
		// �̳߳����������
		cm.setMaxTotal(maxTotal);
		// ÿ��վ�����������
		cm.setDefaultMaxPerRoute(maxPerRoute);

		HttpRequestRetryHandler httpRequestRetryHandler = new HttpRequestRetryHandler() {
			@Override
			public boolean retryRequest(IOException exception,
					int executionCount, HttpContext context) {
				if (executionCount >= reTryTimes) {// ����Ѿ�������n�Σ��ͷ���
					return false;
				}
				if (exception instanceof NoHttpResponseException) {// ������������������ӣ���ô������
					return true;
				}
				if (exception instanceof SSLHandshakeException) {// ��Ҫ����SSL�����쳣
					return false;
				}

				if (exception instanceof UnknownHostException) {// Ŀ����������ɴ�
					return false;
				}
				if (exception instanceof ConnectTimeoutException) {// ���ӱ��ܾ�
					return false;
				}
				if (exception instanceof SSLException) {// ssl�����쳣
					return false;
				}

				HttpClientContext clientContext = HttpClientContext
						.adapt(context);
				HttpRequest request = clientContext.getRequest();
				// ����������ݵȵģ����ٴγ���
				if (!(request instanceof HttpEntityEnclosingRequest)) {
					return true;
				}
				return false;
			}
		};

		return HttpClients.custom().setConnectionManager(cm)
				.setRetryHandler(httpRequestRetryHandler).build();
	}

	
	public static SSLContext createIgnoreVerifySSL() {  
	    SSLContext sc = null;
		try {
			sc = SSLContext.getInstance("SSLv3");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	  
	    // ʵ��һ��X509TrustManager�ӿڣ������ƹ���֤�������޸�����ķ���  
	    X509TrustManager trustManager = new X509TrustManager() {  
	        @Override  
	        public void checkClientTrusted(  
	                java.security.cert.X509Certificate[] paramArrayOfX509Certificate,  
	                String paramString) throws CertificateException {  
	        }  
	  
	        @Override  
	        public void checkServerTrusted(  
	                java.security.cert.X509Certificate[] paramArrayOfX509Certificate,  
	                String paramString) throws CertificateException {  
	        }  
	  
	        @Override  
	        public java.security.cert.X509Certificate[] getAcceptedIssuers() {  
	            return null;  
	        }  
	    };  
	  
	    try {
			sc.init(null, new TrustManager[] { trustManager }, null);
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	    return sc;  
	} 
	
	public static String getapi(String url) {
		
		
		 //�����ƹ���֤�ķ�ʽ����https����  
	    SSLContext sslcontext = createIgnoreVerifySSL();  
	      
	       // ����Э��http��https��Ӧ�Ĵ���socket���ӹ����Ķ���  
	       Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()  
	           .register("http", PlainConnectionSocketFactory.INSTANCE)  
	           .register("https", new SSLConnectionSocketFactory(sslcontext))  
	           .build();  
	       PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);  
	       HttpClients.custom().setConnectionManager(connManager);  
	  
	       //�����Զ����httpclient����  
	    CloseableHttpClient client = HttpClients.custom().setConnectionManager(connManager).build();  
			
	    String result = "";
		String charset = "utf-8";
		HttpGet httpGet = new HttpGet(url);
		
		try {
			CloseableHttpResponse response = client.execute(
					httpGet);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				httpGet.abort();
				ErrorHandler.stopRunning("��Ӧ״̬�쳣��" + statusCode);
			}
			HttpEntity entity = response.getEntity();
			result = EntityUtils.toString(entity, charset);
			EntityUtils.consume(entity);
			response.close();
		} catch (Exception e) {
			ErrorHandler.stopRunning(e, "����http-get�����쳣");
		}
		return result;
	}

	 

	public static String postapi(String url, Object jsonObject)  {
		
		//CloseableHttpClient client = getClientFromHttpPool();
		
		 //�����ƹ���֤�ķ�ʽ����https����  
	    SSLContext sslcontext = createIgnoreVerifySSL();  
	      
	       // ����Э��http��https��Ӧ�Ĵ���socket���ӹ����Ķ���  
	       Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()  
	           .register("http", PlainConnectionSocketFactory.INSTANCE)  
	           .register("https", new SSLConnectionSocketFactory(sslcontext))  
	           .build();  
	       PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);  
	       HttpClients.custom().setConnectionManager(connManager);  
	  
	       //�����Զ����httpclient����  
	    CloseableHttpClient client = HttpClients.custom().setConnectionManager(connManager).build();  
	    
	    
	    HttpPost httpPost = new HttpPost(url);
	    String respContent = null;
		if(jsonObject!=null)
		{
			StringEntity entity = new StringEntity(jsonObject.toString(), "utf-8");// ���������������
			entity.setContentEncoding("UTF-8");
			entity.setContentType("application/x-www-form-urlencoded");
			httpPost.setEntity(entity);
		}	
		else
		{
			StringEntity entity = new StringEntity("", "utf-8");// ���������������
			entity.setContentEncoding("UTF-8");
			entity.setContentType("application/x-www-form-urlencoded");
			httpPost.setEntity(entity);					
		}

		HttpResponse resp;
		try {
			resp = client.execute(httpPost);
			if (resp.getStatusLine().getStatusCode() == 200) {
				HttpEntity he = resp.getEntity();
				respContent = EntityUtils.toString(he, "UTF-8");
			}
		} catch (Exception e) {
			ErrorHandler.stopRunning(e, "����http-post�����쳣");
		}
		return respContent;
		
	}

	
}