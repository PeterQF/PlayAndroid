package com.df.playandroid.widget.banner

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.viewpager.widget.ViewPager

/**
 * @描述
 * @作者 PeterWu
 * @时间 2019/11/23 15:39
 */
class JYViewPager : ViewPager, View.OnTouchListener {

    private var mItemListener: OnPageItemClickListener? = null
    private var mDelayTime = 3000L
    private var isClick = false
    private var downX: Float = 0f
    private var downY: Float = 0f
    private var downTime = 0L

    constructor(context: Context, attrs: AttributeSet?): super(context, attrs) {
        setOnTouchListener(this)
        pageMargin = 40
    }

    constructor(context: Context): this(context, null)

    fun setDelayTime(time: Long) {
        this.mDelayTime = time
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        startLooper()
    }

    private val mTask = object : Runnable{
        override fun run() {
            var currentItem = currentItem
            currentItem++
            setCurrentItem(currentItem)
            postDelayed(this, mDelayTime)
        }
    }

    private fun startLooper() {
        stopLooper()
        postDelayed(mTask, mDelayTime)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stopLooper()
    }

    private fun stopLooper() {
        removeCallbacks(mTask)
    }

    override fun onTouch(view: View?, event: MotionEvent?): Boolean {
        when(event?.action) {
            MotionEvent.ACTION_DOWN -> {
                downX = event.x
                downY = event.y
                downTime = System.currentTimeMillis()
                stopLooper()
            }
            MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {
                val dx = Math.abs(event.x - downX)
                val dy = Math.abs(event.y - downY)
                val dTime = System.currentTimeMillis() - downTime
                isClick = dx <= 5 && dy <= 5 && dTime <= 1000
                if (isClick) {
                    mItemListener?.onPageItemClick(currentItem)
                }
                startLooper()
            }
        }
        return false
    }

    fun setPageItemClickListener(listener: OnPageItemClickListener) {
        this.mItemListener = listener
    }

    interface OnPageItemClickListener {
        fun onPageItemClick(position: Int)
    }
}