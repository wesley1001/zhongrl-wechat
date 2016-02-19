package cn.xn.wechat.web.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.swetake.util.Qrcode;

/**
 * 作用：QRCODE方式生成二维码 作者：rod zhong
 * */
public class QRCodeUtil {

	private int LEVEL = 7;

	private int WIDTH = 140;

	private int HEIGHT = 140;

	private String PATH = "/";

	private int PIXOFF = 2;

	// 构造方法
	public QRCodeUtil(String path) {

		this.PATH = path;

	}

	/**
	 * 生成二维码
	 * */
	public void encodeQRCode(String content) {

		try {

			Qrcode qrcodeHandle = new Qrcode();

			qrcodeHandle.setQrcodeErrorCorrect('M');

			qrcodeHandle.setQrcodeEncodeMode('B');

			qrcodeHandle.setQrcodeVersion(this.LEVEL);

			byte[] contentBytes = content.getBytes("UTF-8");

			BufferedImage bufImg = new BufferedImage(WIDTH, HEIGHT,
					BufferedImage.TYPE_INT_RGB);

			Graphics2D gs = bufImg.createGraphics();

			gs.setBackground(Color.WHITE);

			gs.clearRect(0, 0, WIDTH, HEIGHT);

			gs.setColor(Color.BLACK);

			int pixoff = this.PIXOFF;

			if (contentBytes.length > 0 && contentBytes.length < 120) {

				boolean[][] codeOut = qrcodeHandle.calQrcode(contentBytes);

				for (int i = 0; i < codeOut.length; i++) {
					for (int j = 0; j < codeOut.length; j++) {
						if (codeOut[j][i]) {

							gs.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);
						}
					}
				}
			} else {

				System.err.println("生成二维码的内容小于2个字符或者大于120个字符");

			}

			gs.dispose();

			File file = new File(PATH);

			ImageIO.write(bufImg, "png", file);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}