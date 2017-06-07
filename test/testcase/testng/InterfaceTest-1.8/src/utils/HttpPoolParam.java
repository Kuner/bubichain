package utils;

public class HttpPoolParam {

	// �����ַ
	private String proxyAddress;
	// ����˿ں�
	private int proxyPort;
	// ���ӳ�ʱʱ��
	private int soTime = 5000;
	// ��Ӧ��ʱʱ��
	private int connectioneTime = 6000;
	// ÿ��HttpPool��connManager�ܵ�socket������
	private int connManagerMaxTotal = 200;
	// ����ÿ��HttpPool��connManagerÿ��Ŀ���ַ�ܹ�ʹ�õ�socket������
	private int connManagerMaxPerRoute = 50;
	// �Ƿ񱣴�cookie
	private boolean cookie = true;
	// ���Դ���
	private int reTryTimes = 3;

	public String getProxyAddress() {
		return proxyAddress;
	}

	public void setProxyAddress(String proxyAddress) {
		this.proxyAddress = proxyAddress;
	}

	public int getProxyPort() {
		return proxyPort;
	}

	public void setProxyPort(int proxyPort) {
		this.proxyPort = proxyPort;
	}

	public int getSoTime() {
		return soTime;
	}

	public void setSoTime(int soTime) {
		this.soTime = soTime;
	}

	public int getConnectioneTime() {
		return connectioneTime;
	}

	public void setConnectioneTime(int connectioneTime) {
		this.connectioneTime = connectioneTime;
	}

	public int getConnManagerMaxTotal() {
		return connManagerMaxTotal;
	}

	public void setConnManagerMaxTotal(int connManagerMaxTotal) {
		this.connManagerMaxTotal = connManagerMaxTotal;
	}

	public int getConnManagerMaxPerRoute() {
		return connManagerMaxPerRoute;
	}

	public void setConnManagerMaxPerRoute(int connManagerMaxPerRoute) {
		this.connManagerMaxPerRoute = connManagerMaxPerRoute;
	}

	public boolean isCookie() {
		return cookie;
	}

	public void setCookie(boolean cookie) {
		this.cookie = cookie;
	}

	public HttpPoolParam(int time) {
		this.soTime = time;
		this.connectioneTime = time;
	}

	public HttpPoolParam(int soTime, int connectioneTime, int reTryTimes) {
		this.soTime = soTime;
		this.connectioneTime = connectioneTime;
		this.reTryTimes = reTryTimes;
	}

	public int getReTryTimes() {
		return reTryTimes;
	}

	public void setReTryTimes(int reTryTimes) {
		this.reTryTimes = reTryTimes;
	}

}
