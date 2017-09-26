package com.example.guoc.myapplication;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.guoc.myapplication.view.CornersImageView;

/**
 * Created by Chanven on 2016/6/22.
 */
public class ViewActivity extends AppCompatActivity {

    private ImageView mImageView;
    private LinearLayout mLyt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        ((CornersImageView) this.findViewById(R.id.iv_corner_imageview)).setImageResource(R.mipmap.victorique);
        mImageView = (ImageView) this.findViewById(R.id.iv_normal);
        mLyt = (LinearLayout) this.findViewById(R.id.lyt_bg);
//        mImageView.setImageBitmap(getCircleBitmap());
//        mLyt.setBackgroundDrawable(new BitmapDrawable(getCircleBitmap()));
        getCircleBitmap();
    }

    /**
     * 将方形图片剪切成圆图
     *
     * @param
     * @return
     */
    public void getCircleBitmap(/*Bitmap bitmap*/) {
//        int width;
//        final int height;
        final ViewTreeObserver observer = mLyt.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int width = mLyt.getMeasuredWidth();
                int height = mLyt.getMeasuredHeight();
                Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(output);

//        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
                Rect rect = new Rect(0, 0, width, height);
                RectF rectF = new RectF(rect);

                Paint paint = new Paint();
                paint.setAntiAlias(true);   // 防止边缘的锯齿
                paint.setFilterBitmap(true);  // 对位图进行滤波处理
                paint.setColor(Color.parseColor("#EB5E18"));

                canvas.drawOval(rectF, paint);  // 根据rectF 绘制一个椭圆/圆形
                canvas.drawRoundRect(rectF, 40, 40, paint);



                Rect rect2 = new Rect(5, 5, width-5, height-5);
                RectF rectF2 = new RectF(rect2);

                Paint paint2 = new Paint();
                paint2.setAntiAlias(true);   // 防止边缘的锯齿
                paint2.setFilterBitmap(true);  // 对位图进行滤波处理
                paint2.setColor(Color.parseColor("#E7E9D1"));

                canvas.drawOval(rectF2, paint2);  // 根据rectF 绘制一个椭圆/圆形
                canvas.drawRoundRect(rectF2, 40, 40, paint2);

                mLyt.setBackgroundDrawable(new BitmapDrawable(output));
            }
        });
        // 设置两张图片相交时的显示模式为 SRC_IN
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
//        canvas.drawBitmap(bitmap, rect, rectF, paint);

//        return output;
    }
}
