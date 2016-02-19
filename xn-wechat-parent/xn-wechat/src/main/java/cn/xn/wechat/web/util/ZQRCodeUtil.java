package cn.xn.wechat.web.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

/**
 * 作用：二维码助手类 作者： rod zhong
 * */
public class ZQRCodeUtil {

	private int WIDTH = 200;// 生成的二维码的宽度

	private int HEIGHT = 200;// 生成的二维码的高度

	private String PATH = "";// 生成的二维码的路径

	private static final int BLACK = 0xFF000000;// 黑色背景常量

	private static final int WHITE = 0xFFFFFFFF;// 白色背景常量

	/**
	 * 带参数的构造方法(参数为生成的二维码的高度或者宽度和路径)
	 * */
	public ZQRCodeUtil(int width, int height, String path) {

		this.WIDTH = width;

		this.HEIGHT = height;

		this.PATH = path;

	}

	/**
	 * 带路径的参数
	 * */
	public ZQRCodeUtil(String path) {
		this.PATH = path;
	}

	/**
	 * 作用：生成二维码 作者：rod zhong 
	 * */
	public void createQRCode(String content) {

		try {

			MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

			Map hints = new HashMap();

			hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

			BitMatrix bitMatrix = multiFormatWriter.encode(content,
					BarcodeFormat.QR_CODE, WIDTH, HEIGHT, hints);

			File file = new File(PATH);

			this.writeToFile(bitMatrix, "jpg", file);

		} catch (Exception e) {

			e.printStackTrace();

		}
	}

	/**
	 * 将BitMatrix的二维码对象转换为图片流对象
	 * */
	private BufferedImage toBufferedImage(BitMatrix matrix) {

		int width = matrix.getWidth();

		int height = matrix.getHeight();

		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);

		for (int x = 0; x < width; x++) {

			for (int y = 0; y < height; y++) {

				image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);

			}
		}

		return image;
	}

	/**
	 * 将BitMatrix的二维码对象写入文件
	 * */
	private void writeToFile(BitMatrix matrix, String format, File file)
			throws IOException {

		BufferedImage image = toBufferedImage(matrix);

		if (!ImageIO.write(image, format, file)) {

			throw new IOException("Could not write an image of format "
					+ format + " to " + file);

		}
	}

	/**
	 * 将二维码对象写入一个流
	 * */
	private void writeToStream(BitMatrix matrix, String format,
			OutputStream stream) throws IOException {

		BufferedImage image = toBufferedImage(matrix);

		if (!ImageIO.write(image, format, stream)) {

			throw new IOException("Could not write an image of format "
					+ format);

		}

	}

}
