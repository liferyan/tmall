package com.liferyan.tmall.web.util;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferInt;
import java.awt.image.DirectColorModel;
import java.awt.image.PixelGrabber;
import java.awt.image.Raster;
import java.awt.image.RenderedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Created by Ryan on 2017/4/27.
 * 图片格式转换工具 转换为jpg
 */
public class ImageUtil {

  public static BufferedImage change2jpg(File f) {
    try {
      Image i = Toolkit.getDefaultToolkit().createImage(f.getAbsolutePath());
      PixelGrabber pg = new PixelGrabber(i, 0, 0, -1, -1, true);
      pg.grabPixels();
      int width = pg.getWidth(), height = pg.getHeight();
      final int[] RGB_MASKS = {0xFF0000, 0xFF00, 0xFF};
      final ColorModel RGB_OPAQUE = new DirectColorModel(32, RGB_MASKS[0], RGB_MASKS[1],
          RGB_MASKS[2]);
      DataBuffer buffer = new DataBufferInt((int[]) pg.getPixels(), pg.getWidth() * pg.getHeight());
      WritableRaster raster = Raster
          .createPackedRaster(buffer, width, height, width, RGB_MASKS, null);
      return new BufferedImage(RGB_OPAQUE, raster, false, null);
    } catch (InterruptedException e) {
      e.printStackTrace();
      return null;
    }
  }

  public static void resizeImage(File srcFile, int width, int height, File destFile) {
    try {
      Image img = ImageIO.read(srcFile);
      img = resizeImage(img, width, height);
      if (img != null) {
        ImageIO.write((RenderedImage) img, "jpg", destFile);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static Image resizeImage(Image srcImage, int width, int height) {
    try {
      BufferedImage buffImg;
      buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
      buffImg.getGraphics()
          .drawImage(srcImage.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
      return buffImg;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

}
