package utils;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Picasso封装的工具类 
 * @author LongGe
 *
 */
public class PicassoUtils {

	/**
	 * 加载默认的图片
	 * 
	 * @param context
	 *            上下文
	 * @param url
	 *            图片地址url
	 * @param imageView
	 *            要显示的view
	 */
	public static void loadDefaultIMG(Context context, String url, ImageView imageView) {
		Picasso.with(context).load(url).into(imageView);
	}

	/**
	 * 加载图片并且指定大小
	 * 
	 * @param context
	 *            上下文
	 * @param url
	 *            图片地址url
	 * @param imageView
	 *            将图片显示的view
	 * @param width
	 *            图片的宽
	 * @param height
	 *            图片的高
	 */
	public static void loadIMGSize(Context context, String url, ImageView imageView, int width, int height) {
		Picasso.with(context).load(url).resize(width, height).centerCrop().into(imageView);
	}

	/**
	 * 加载错误的图片和占用时的图片
	 * 
	 * @param context
	 *            上下文
	 * @param url
	 *            图片地址url
	 * @param imageView
	 *            将图片显示的view
	 * @param placeholderIMG
	 *            占位的图片
	 * @param errorIMG
	 *            加载 错误时的图片
	 */
	public static void loadIMGError(Context context, String url, ImageView imageView, int placeholderIMG,
			int errorIMG) {
		Picasso.with(context).load(url).placeholder(placeholderIMG).error(errorIMG).into(imageView);
	}

	/**
	 * 图片裁剪
	 * @param context
	 * @param url
	 * @param imageView
	 */
	public static void loadIMGCrop(Context context, String url, ImageView imageView) {
		Picasso.with(context).load(url).transform(new CropSquareTransformation()).into(imageView);
	}

}
/**
 * 裁剪类
 * @author LongGe
 *
 */
class CropSquareTransformation implements Transformation {
	@Override
	public Bitmap transform(Bitmap source) {
		int size = Math.min(source.getWidth(), source.getHeight());
		int x = (source.getWidth() - size) / 2;
		int y = (source.getHeight() - size) / 2;
		Bitmap result = Bitmap.createBitmap(source, x, y, size, size);
		if (result != source) {
			source.recycle();
		}
		return result;
	}

	@Override
	public String key() {
		return "LongGe";
	}
}
