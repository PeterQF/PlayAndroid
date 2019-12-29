package com.df.playandroid.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.df.playandroid.R;
import com.df.playandroid.utils.LogUtil;
import com.qmuiteam.qmui.util.QMUIViewOffsetHelper;

public class DropLayout extends RelativeLayout {

    private ImageView mIv;
    private RelativeLayout mAkiaLayout;
    private int touchSlop;
    private float touchDownX = 0;
    private float touchDownY = 0;
    private float lastTouchX = 0;
    private float lastTouchY = 0;
    private boolean isDragging;
    private boolean isTouchDownInGlobalBtn = false;
    private QMUIViewOffsetHelper globalBtnOffsetHelper;

    public DropLayout(Context context) {
        this(context, null);
    }

    public DropLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DropLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.drop_layout, this, true);
        initView();
    }

    private void initView() {
        mAkiaLayout = findViewById(R.id.akia_rl);
        mIv = findViewById(R.id.image);
        globalBtnOffsetHelper = new QMUIViewOffsetHelper(mAkiaLayout);
        touchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        mIv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Aika啊", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        globalBtnOffsetHelper.onViewLayout();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        float x = event.getX(), y = event.getY();
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            isTouchDownInGlobalBtn = isDownInGlobalBtn(x, y);
            touchDownX = lastTouchX = x;
            touchDownY = lastTouchY = y;
        } else if (action == MotionEvent.ACTION_MOVE) {
            if (!isDragging && isTouchDownInGlobalBtn) {
                int dx = (int) (x - touchDownX);
                int dy = (int) (y - touchDownY);
                if (Math.sqrt(dx * dx + dy * dy) > touchSlop) {
                    isDragging = true;
                }
            }

            if (isDragging) {
                int dx = (int) (x - lastTouchX);
                int dy = (int) (y - lastTouchY);
                int gx = mAkiaLayout.getLeft();
                int gy = mAkiaLayout.getTop();
                int gw = mAkiaLayout.getWidth(), w = getWidth();
                int gh = mAkiaLayout.getHeight(), h = getHeight();

                if (gx + dx < 0) {
                    dx = -gx;
                } else if (gx + dx + gw > w) {
                    dx = w - gw - gx;
                }

                if (gy + dy < 0) {
                    dy = -gy;
                } else if (gy + dy + gh > h) {
                    dy = h - gh - gy;
                }

                globalBtnOffsetHelper.setLeftAndRightOffset(
                        globalBtnOffsetHelper.getLeftAndRightOffset() + dx);
                globalBtnOffsetHelper.setTopAndBottomOffset(
                        globalBtnOffsetHelper.getTopAndBottomOffset() + dy);

            }
            lastTouchX = x;
            lastTouchY = y;
        } else if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            isDragging = false;
            isTouchDownInGlobalBtn = false;
        }
        return isDragging;
    }

    private boolean isDownInGlobalBtn(float x, float y) {
        return mAkiaLayout.getLeft() < x && mAkiaLayout.getRight() > x &&
                mAkiaLayout.getTop() < y && mAkiaLayout.getBottom() > y;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX(), y = event.getY();
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            LogUtil.INSTANCE.info("drop layout onTouch ---> down");
            isTouchDownInGlobalBtn = isDownInGlobalBtn(x, y);
            touchDownX = lastTouchX = x;
            touchDownY = lastTouchY = y;
        } else if (action == MotionEvent.ACTION_MOVE) {
            LogUtil.INSTANCE.info("drop layout onTouch ---> move");
            if (!isDragging && isTouchDownInGlobalBtn) {
                int dx = (int) (x - touchDownX);
                int dy = (int) (y - touchDownY);
                if (Math.sqrt(dx * dx + dy * dy) > touchSlop) {
                    isDragging = true;
                }
            }

            if (isDragging) {
                int dx = (int) (x - lastTouchX);
                int dy = (int) (y - lastTouchY);
                int gx = mAkiaLayout.getLeft();
                int gy = mAkiaLayout.getTop();
                int gw = mAkiaLayout.getWidth(), w = getWidth();
                int gh = mAkiaLayout.getHeight(), h = getHeight();

                if(gx + dx < 0){
                    dx = -gx;
                }else if(gx + dx + gw > w){
                    dx = w - gw - gx;
                }

                if(gy + dy < 0){
                    dy = - gy;
                }else if(gy + dy + gh > h){
                    dy = h - gh - gy;
                }
                globalBtnOffsetHelper.setLeftAndRightOffset(
                        globalBtnOffsetHelper.getLeftAndRightOffset() + dx);
                globalBtnOffsetHelper.setTopAndBottomOffset(
                        globalBtnOffsetHelper.getTopAndBottomOffset() + dy);

            }
            lastTouchX = x;
            lastTouchY = y;
        } else if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            LogUtil.INSTANCE.info("drop layout onTouch ---> up");
            isDragging = false;
            isTouchDownInGlobalBtn = false;
        }
        return isDragging || super.onTouchEvent(event);
    }
}
