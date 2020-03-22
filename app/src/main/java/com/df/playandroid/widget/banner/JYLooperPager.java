package com.df.playandroid.widget.banner;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.df.playandroid.R;
import com.df.playandroid.home.response.BannerResponse;
import com.df.playandroid.utils.DeviceUtil;
import com.df.playandroid.widget.roundimage.RoundRectImageView;
import java.util.ArrayList;
import java.util.List;

/**
 * @作者 PeterWu
 * @时间 2019/11/24 14:24
 */
public class JYLooperPager extends LinearLayout {

    private JYViewPager mVp;
    private LinearLayout mPointContainer;
    private InnerAdapter mAdapter = null;
    private OnItemClickListener mItemClickListener = null;
    // 加载网络图片
    private List<BannerResponse.BannerData> mItems;
    private List<ImageView> mViewpagerViews;
    private boolean mIsSetData = false;

    public JYLooperPager(Context context) {
        this(context, null);
    }

    public JYLooperPager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public JYLooperPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 把布局加载出来
        LayoutInflater.from(context).inflate(R.layout.looper_pager, this, true);
        // 初始化，找到子控件
        init();
    }

    private void init() {
        mItems = new ArrayList<>();
        mViewpagerViews = new ArrayList<>();
        initView();
        initEvent();
    }

    private void initEvent() {

        mVp.setPageItemClickListener(new JYViewPager.OnPageItemClickListener() {
            @Override
            public void onPageItemClick(int position) {
                if (mItemClickListener != null && mAdapter != null) {
                    int realPosition = position % mItems.size();
                    mItemClickListener.onItemClick(realPosition);
                }
            }
        });

        mVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                // 切换的一个回调
            }

            @Override
            public void onPageSelected(int i) {
                if (mAdapter != null) {
                    // 切换停下来的回调
                    // 切换指示器焦点
                    updateIndicator();
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {
                // 切换状态的回调
            }
        });
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    // 网络
    public void setData(List<BannerResponse.BannerData> items) {
        if (!mIsSetData) {
            this.mItems = items;
            setupImageView();
            mAdapter = new InnerAdapter();
            mVp.setAdapter(mAdapter);
            mVp.setCurrentItem(mItems.size() * 100, false);
            // 根据数据个数，动态创建指示器原点
            updateIndicator();
            mIsSetData = true;
        }
    }
    // 创建ImageView
    private void setupImageView() {
        for (int i = 0; i < mItems.size(); i++) {
//            ImageView iv = new ImageView(getContext());
            RoundRectImageView iv = new RoundRectImageView(getContext());
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            mViewpagerViews.add(iv);
        }
    }

    public class InnerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            // 网络
            int realPosition = position % mItems.size();
            ImageView imageView = mViewpagerViews.get(realPosition);
            container.addView(imageView);
            Glide.with(container.getContext()).load(mItems.get(realPosition).getImagePath()).into(imageView);
            return imageView;
        }
    }

    private void updateIndicator() {
        if (mAdapter != null) {
            int count = mItems.size();
            mPointContainer.removeAllViews();
            for (int i = 0; i < count; i++) {
                View view = new View(getContext());
                // 设置背景
                if (mVp.getCurrentItem() % mItems.size() == i) {
                    view.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_point_selected));
                } else {
                    view.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_point_normal));
                }
                // 设置大小
                LayoutParams layoutParams = new LayoutParams(DeviceUtil.INSTANCE.dp2px(8f), DeviceUtil.INSTANCE.dp2px(8f));
                layoutParams.setMargins(DeviceUtil.INSTANCE.dp2px(5f), 0, DeviceUtil.INSTANCE.dp2px(5f), 0);
                view.setLayoutParams(layoutParams);
                // 添加view
                mPointContainer.addView(view);
            }
        }
    }

    private void initView() {
        mVp = findViewById(R.id.looper_pager_vp);
        mPointContainer = findViewById(R.id.looper_point_container);
    }
}