/**
 * 获取服务器IP地址
 * @author YL
 *
 */
package utils;

public class IpAdressUtils {
	private final static String url = "http://192.168.1.107:8080";
	// private final static String url = "http://192.168.43.38:8080";
	// private final static String url = "http://192.168.43.38:8080";

	public IpAdressUtils() {

	}

	public static String getURL() {

		return url;
	}
}
