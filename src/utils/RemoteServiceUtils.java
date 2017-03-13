package utils;
/*
 * 链接远程服务器,接收服务器返回的的json字符串
 * @author YL
 * @datatime 2017-2-19
 */
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.util.Log;

public class RemoteServiceUtils {

	@SuppressWarnings("finally")
	public static String loginRemoteService(String url) {
		String result = null;
		try {

			HttpClient httpclient = new DefaultHttpClient();

			Log.d("远程URL", url);
			HttpPost request = new HttpPost(url);

			request.addHeader("Accept", "text/json");
			// 获取响应的结果
			HttpResponse response = httpclient.execute(request);
			// 获取HttpEntity
			HttpEntity entity = response.getEntity();
			// 获取响应的结果信息
			String json = EntityUtils.toString(entity, "UTF-8");

			if (json != null) {
				result = json;

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return result;
		}
	}
}