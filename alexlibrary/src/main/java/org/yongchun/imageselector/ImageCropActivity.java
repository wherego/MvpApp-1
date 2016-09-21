package org.yongchun.imageselector;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.opengl.GLES10;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import org.alex.util.LogUtil;

import org.alex.alexlibrary.R;
import org.isseiaoki.simplecropview.CropImageView;
import org.yongchun.imageselector.utils.CropUtil;
import org.yongchun.imageselector.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * Created by dee on 15/11/26.
 * @link  https://github.com/weileng11/ImageSelector-master
 */
@SuppressWarnings("all")
public class ImageCropActivity extends AppCompatActivity implements View.OnClickListener {
    //yongchun_imageselector_
    public static final String EXTRA_PATH = "extraPath";
    public static final String OUTPUT_PATH = "outputPath";
    public static final int REQUEST_CROP = 69;
    /**图片 默认 尺寸，单位 - 像素*/
    private static final int SIZE_DEFAULT = 800;
    /**图片 最大 尺寸，单位 - 像素*/
    private static final int SIZE_LIMIT = 1080;

    private Toolbar toolbar;
    private TextView doneText;
    private CropImageView cropImageView;
    private Uri sourceUri;
    private Uri saveUri;
    private final Handler handler = new Handler();
    public static void startCrop(Activity activity, String path) {
        Intent intent = new Intent(activity, ImageCropActivity.class);
        intent.putExtra(EXTRA_PATH, path);
        activity.startActivityForResult(intent, REQUEST_CROP);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yongchun_imageselector_activity_image_crop);
        String path = getIntent().getStringExtra(EXTRA_PATH);
        sourceUri = Uri.fromFile(new File(path));
        initView();
        registerListener();
    }

    public void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.yongchun_imageselector_ic_back);

        findViewById(R.id.iv_rotate_left).setOnClickListener(this);
        findViewById(R.id.iv_rotate_right).setOnClickListener(this);
        doneText = (TextView) findViewById(R.id.done_text);
        cropImageView = (CropImageView) findViewById(R.id.cropImageView);
        cropImageView.setHandleSizeInDp(10);
        int exifRotation = CropUtil.getExifRotation(CropUtil.getFromMediaUri(this, getContentResolver(), sourceUri));
        InputStream is = null;
        try {
            int sampleSize = calculateBitmapSampleSize(sourceUri);
            is = getContentResolver().openInputStream(sourceUri);
            BitmapFactory.Options option = new BitmapFactory.Options();
            option.inSampleSize = sampleSize;
            Bitmap sizeBitmap = BitmapFactory.decodeStream(is, null, option);
            Matrix matrix = getRotateMatrix(sizeBitmap, exifRotation % 360);
            Bitmap rotated = Bitmap.createBitmap(sizeBitmap, 0, 0, sizeBitmap.getWidth(), sizeBitmap.getHeight(), matrix, true);

            LogUtil.e(""+rotated.getWidth()+"  "+rotated.getHeight());
            cropImageView.setImageBitmap(rotated);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        } finally {
            CropUtil.closeSilently(is);
        }
    }



    public void registerListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        doneText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog.show(
                        ImageCropActivity.this, null, getString(R.string.save_ing), true, false);
                saveUri = Uri.fromFile(FileUtils.createCropFile(ImageCropActivity.this));
                saveOutput(cropImageView.getCroppedBitmap());
            }
        });
    }

    public Matrix getRotateMatrix(Bitmap bitmap, int rotation) {
        Matrix matrix = new Matrix();
        if (bitmap != null && rotation != 0) {
            int cx = bitmap.getWidth() / 2;
            int cy = bitmap.getHeight() / 2;
            matrix.preTranslate(-cx, -cy);
            matrix.postRotate(rotation);
            matrix.postTranslate(bitmap.getWidth() / 2, bitmap.getHeight() / 2);
        }
        return matrix;
    }

    private int calculateBitmapSampleSize(Uri bitmapUri) throws IOException {
        InputStream is = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        try {
            is = getContentResolver().openInputStream(bitmapUri);
            BitmapFactory.decodeStream(is, null, options); // Just get image size
        } finally {
            CropUtil.closeSilently(is);
        }

        int maxSize = getMaxImageSize();
        int sampleSize = 1;
        while (options.outHeight / sampleSize > maxSize || options.outWidth / sampleSize > maxSize) {
            sampleSize = sampleSize << 1;
        }
        return sampleSize;
    }

    private int getMaxImageSize() {
        int textureLimit = getMaxTextureSize();
        if (textureLimit == 0) {
            return SIZE_DEFAULT;
        } else {
            return Math.min(textureLimit, SIZE_LIMIT);
        }
    }

    private int getMaxTextureSize() {
        int[] maxSize = new int[1];
        GLES10.glGetIntegerv(GLES10.GL_MAX_TEXTURE_SIZE, maxSize, 0);
        return maxSize[0];
    }

    private void saveOutput(Bitmap croppedImage) {
        if (saveUri != null) {
            OutputStream outputStream = null;
            try {
                outputStream = getContentResolver().openOutputStream(saveUri);
                if (outputStream != null) {
                    croppedImage.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                CropUtil.closeSilently(outputStream);
            }
            setResult(RESULT_OK, new Intent().putExtra(OUTPUT_PATH, saveUri.getPath()));
        }
        final Bitmap b = croppedImage;
        handler.post(new Runnable() {
            public void run() {
                b.recycle();
            }
        });
        finish();
    }

    @Override
    public void onClick(View view) {
        if(R.id.iv_rotate_left == view.getId()){
            cropImageView.rotateImage(CropImageView.RotateDegrees.ROTATE_M90D);
        }else if(R.id.iv_rotate_right == view.getId()){
            cropImageView.rotateImage(CropImageView.RotateDegrees.ROTATE_90D);
        }
    }
}
