package com.df.playandroid.widget.arcview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.widget.ImageView

/**
 * 作者：PeterWu
 * 时间：2020/2/3
 * 描述：
 */
class ArcView : ImageView {

    private var mWidth = 0
    private var mHeight = 0
    private var mArcHeight = 120 // 圆弧高度
    private lateinit var mPaint: Paint // 画笔
    private lateinit var mRect: Rect
    private lateinit var mPath: Path

    constructor(context: Context): this(context, null)

    constructor(context: Context, attrs: AttributeSet?): this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int): super(context, attrs, defStyleAttr) {
        initView()
    }

    private fun initView() {
        mPaint = Paint()
        mPaint.isAntiAlias = true
        mPaint.flags = Paint.ANTI_ALIAS_FLAG
        mPaint.style = Paint.Style.FILL
        mPath = Path()
        mRect = Rect(0, 0, 0, 0)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        // 绘制矩形
        mRect.set(0, 0, mWidth, mHeight - mArcHeight)
        canvas?.drawRect(mRect, mPaint)

        // 绘制路径
        mPath.moveTo(0.toFloat(), (mHeight - mArcHeight).toFloat())
        mPath.quadTo((mWidth / 2).toFloat(), mHeight.toFloat(), mWidth.toFloat(),
            (mHeight - mArcHeight).toFloat()
        )
        canvas?.drawPath(mPath, mPaint)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)

        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize
        }

        setMeasuredDimension(mWidth, mHeight)
    }
}