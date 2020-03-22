package com.df.playandroid.widget.roundimage;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatImageView;


/**
 * @包名 com.example.peterwu.himalayan.views
 * @描述 自定义view，圆型imageView
 * @作者 PeterWu
 * @时间 2019/3/14 17:02
 */
public class RoundImageView extends AppCompatImageView {

    private Path path;

    public RoundImageView(Context context) {
        super(context);
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (path == null) {
            path = new Path();
            path.addCircle(getWidth() / 2, getHeight() / 2, Math.min(getWidth(), getHeight()) / 2, Path.Direction.CW);
        }
        canvas.save();
        canvas.clipPath(path);
        super.onDraw(canvas);
        canvas.restore();
    }
}