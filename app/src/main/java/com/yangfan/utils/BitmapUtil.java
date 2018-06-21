package com.yangfan.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.os.Environment;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by yangfan on 2016/6/27.
 */
public class BitmapUtil {
    public static final String path = Environment.getExternalStorageDirectory().toString()
            + "/DCIM/Camera/";

    /**
     * 路径获得bitmap
     * @param imgPath
     * @return
     */
    public static Bitmap getBitmap(String imgPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = false;
        newOpts.inPurgeable = true;
        newOpts.inInputShareable = true;
        newOpts.inSampleSize = 1;
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
        return BitmapFactory.decodeFile(imgPath, newOpts);
    }

    public static Bitmap getSmallBitmap(String filePath, int width, int height, int quality) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        options.inSampleSize = calculateInSampleSize(options, width, height);
        options.inJustDecodeBounds = false;
        Bitmap srcBitmap = BitmapFactory.decodeFile(filePath, options);
        if(srcBitmap == null) {
            return null;
        } else {
            int degree = readPictureDegree(filePath);
            Bitmap dstBitmap = rotateBitmap(srcBitmap, degree);
            if(dstBitmap != srcBitmap) {
                srcBitmap.recycle();
                srcBitmap = null;
            }

            if(quality > 0 && quality <= 100) {
                ByteArrayOutputStream baos = null;

                try {
                    baos = new ByteArrayOutputStream();
                    dstBitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
                    ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
                    dstBitmap = BitmapFactory.decodeStream(isBm, (Rect)null, (BitmapFactory.Options)null);
                } finally {
                    try {
                        if(baos != null) {
                            baos.close();
                        }
                    } catch (IOException var15) {
                        var15.printStackTrace();
                    }

                }

                return dstBitmap;
            } else {
                return dstBitmap;
            }
        }
    }

    /**
     * 读取本地图片限制最大文件大小
     * @param filePath
     * @param width
     * @param height
     * @param maxSize
     * @return
     */
    public static Bitmap getSmallBitmap2(String filePath, int width, int height, int maxSize) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        options.inSampleSize = calculateInSampleSize(options, width, height);
        options.inJustDecodeBounds = false;
        Bitmap srcBitmap = BitmapFactory.decodeFile(filePath, options);
        if(srcBitmap == null) {
            return null;
        } else {
            int degree = readPictureDegree(filePath);
            Bitmap dstBitmap = rotateBitmap(srcBitmap, degree);
            if(dstBitmap != srcBitmap) {
                srcBitmap.recycle();
                srcBitmap = null;
            }

            if(dstBitmap != null) {
                int quality = 100;
                ByteArrayOutputStream baos = null;

                try {
                    baos = new ByteArrayOutputStream();
                    dstBitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);

                    while(baos.toByteArray().length / 1024 > maxSize && quality > 0) {
                        baos.reset();
                        quality -= 5;
                        dstBitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
                    }

                    ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
                    dstBitmap = BitmapFactory.decodeStream(isBm, (Rect)null, (BitmapFactory.Options)null);
                    return dstBitmap;
                } finally {
                    try {
                        if(baos != null) {
                            baos.close();
                        }
                    } catch (IOException var16) {
                        var16.printStackTrace();
                    }

                }
            } else {
                return dstBitmap;
            }
        }
    }

    /**
     * 计算出图片的inSampleSize
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;
        if(height > reqHeight || width > reqWidth) {
            int heightRatio = Math.round((float)height / (float)reqHeight);
            int widthRatio = Math.round((float)width / (float)reqWidth);
            inSampleSize = heightRatio > widthRatio?widthRatio:heightRatio;
        }

        return inSampleSize;
    }



    public static void compressAndGenImage(Bitmap image, String outPath, int maxSize) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        int options = 100;
        image.compress(Bitmap.CompressFormat.JPEG, options, os);

        while(os.toByteArray().length / 1024 > maxSize && options > 0) {
            os.reset();
            options -= 10;
            image.compress(Bitmap.CompressFormat.JPEG, options, os);
        }

        FileOutputStream fos = new FileOutputStream(outPath);
        fos.write(os.toByteArray());
        fos.flush();
        fos.close();
        if(image != null && !image.isRecycled()) {
            image.recycle();
            image = null;
        }

        System.gc();
    }

    /**
     * 更改输出路径压缩
     * @param imgPath
     * @param outPath
     * @param maxSize
     * @param needsDelete
     * @throws IOException
     */
    public static void compressAndGenImage(String imgPath, String outPath, int maxSize, boolean needsDelete) throws IOException {
        compressAndGenImage(getBitmap(imgPath), outPath, maxSize);
        if(needsDelete) {
            File file = new File(imgPath);
            if(file.exists()) {
                file.delete();
            }
        }

    }

    /**
     * 缩放bitmap  尺寸压缩,读写速度快,图片糊
     * @param bmp
     * @param maxWidth 最大宽度
     * @param maxHeight 最大高度
     * @return
     */
    public static Bitmap getScaleBitmap(Bitmap bmp, int maxWidth, int maxHeight) {
        if (bmp != null) {
            Matrix matrix = new Matrix();
            int oldWidth = bmp.getWidth();
            int oldHeight = bmp.getHeight();
            float scaleWidth = maxWidth * 1.0f / oldWidth;
            float scaleHeight = maxHeight * 1.0f / oldHeight;
            float scale = Math.min(scaleWidth, scaleHeight);
            if (scale >= 1) {
                return Bitmap.createBitmap(bmp);
            } else {
                matrix.postScale(scale, scale);
                // 得到新的图片
                bmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix,
                        true);
            }
        }
        return bmp;
    }
    /**
     * 质量压缩方法  图片清晰 大小减少  读写速度慢(原图速度)
     *
     * @param image
     * @return
     */
    public static Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 90;

        while (baos.toByteArray().length / 1024 > 30) { // 循环判断如果压缩后图片是否大于300kb,大于继续压缩
            baos.reset(); // 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;// 每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    /**
     * 质量尺寸双重压缩,尺寸可调大,速度块,图片清晰
     * @param image
     * @param pixelW
     * @param pixelH
     * @return
     */
    public static Bitmap ratio(Bitmap image, float pixelW, float pixelH)throws Exception {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, os);
        if( os.toByteArray().length / 1024>300) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
            os.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, 50, os);//这里压缩50%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap bitmap = BitmapFactory.decodeStream(is, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        float hh = pixelH;// 设置高度为240f时，可以明显看到图片缩小了
        float ww = pixelW;// 设置宽度为120f，可以明显看到图片缩小了
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0) be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        is = new ByteArrayInputStream(os.toByteArray());
        bitmap = BitmapFactory.decodeStream(is, null, newOpts);
        //压缩好比例大小后再进行质量压缩
//      return compress(bitmap, maxSize); // 这里再进行质量压缩的意义不大，反而耗资源，删除
        return bitmap;
    }
    /**
     * 读取图片旋转角度
     * @param path
     * @return
     */
    public static int readPictureDegree(String path) {
        int degree  = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /*
     * 旋转图片
     * @param angle
     * @param bitmap
     * @return Bitmap
     */
    private static Bitmap rotateBitmap(Bitmap bitmap, int rotate) {
        if(bitmap == null) {
            return null;
        } else {
            int w = bitmap.getWidth();
            int h = bitmap.getHeight();
            Matrix mtx = new Matrix();
            mtx.postRotate((float)rotate);
            return Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, true);
        }
    }

    /** 保存方法 */
    public static  boolean saveMyBitmap(Bitmap bmp, String bitName) throws IOException {
        File dirFile = new File(path);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        File f = new File(path + bitName + ".png");
        boolean flag = false;
        f.createNewFile();
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            flag = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }


}
