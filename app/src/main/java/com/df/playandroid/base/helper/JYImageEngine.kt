package com.df.playandroid.base.helper

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.luck.picture.lib.engine.ImageEngine
import com.luck.picture.lib.listener.OnImageCompleteCallback
import com.luck.picture.lib.widget.longimage.SubsamplingScaleImageView
import android.graphics.PointF
import com.luck.picture.lib.widget.longimage.ImageViewState
import com.luck.picture.lib.widget.longimage.ImageSource
import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.Nullable
import com.bumptech.glide.request.target.ImageViewTarget
import com.luck.picture.lib.tools.MediaUtils
import com.bumptech.glide.request.RequestOptions
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.df.playandroid.R


/**
 * 作者：PeterWu
 * 时间：2020/5/9
 * 描述：
 */
class JYImageEngine : ImageEngine {

    /**
     * 加载图片
     */
    override fun loadImage(context: Context, url: String, imageView: ImageView) {
        Glide
            .with(context)
            .load(url)
            .into(imageView)
    }

    /**
     * 加载网络图片适配长图方案
     * 注意：此方法只有加载网络图片才会回调
     */
    override fun loadImage(
        context: Context,
        url: String,
        imageView: ImageView,
        longImageView: SubsamplingScaleImageView?,
        callback: OnImageCompleteCallback?
    ) {
        Glide.with(context)
            .asBitmap()
            .load(url)
            .into(object : ImageViewTarget<Bitmap>(imageView) {
                override fun onLoadStarted(@Nullable placeholder: Drawable?) {
                    super.onLoadStarted(placeholder)
                    callback?.onShowLoading()
                }

                override fun onLoadFailed(@Nullable errorDrawable: Drawable?) {
                    super.onLoadFailed(errorDrawable)
                    callback?.onHideLoading()
                }

                override fun setResource(@Nullable resource: Bitmap?) {
                    callback?.onHideLoading()
                    if (resource != null) {
                        val eqLongImage = MediaUtils.isLongImg(
                            resource.width,
                            resource.height
                        )
                        longImageView?.visibility = if (eqLongImage) View.VISIBLE else View.GONE
                        imageView.visibility = if (eqLongImage) View.GONE else View.VISIBLE
                        if (eqLongImage) {
                            // 加载长图
                            longImageView?.run {
                                isQuickScaleEnabled = true
                                isZoomEnabled = true
                                isPanEnabled = true
                                setDoubleTapZoomDuration(100)
                                setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CENTER_CROP)
                                setDoubleTapZoomDpi(SubsamplingScaleImageView.ZOOM_FOCUS_CENTER)
                                setImage(
                                    ImageSource.bitmap(resource),
                                    ImageViewState(0f, PointF(0f, 0f), 0)
                                )
                            }
                        } else {
                            // 普通图片
                            imageView.setImageBitmap(resource)
                        }
                    }
                }
            })
    }

    /**
     * 加载网络图片适配长图方案
     * # 注意：此方法只有加载网络图片才会回调
     * 已废弃
     */
    override fun loadImage(
        context: Context,
        url: String,
        imageView: ImageView,
        longImageView: SubsamplingScaleImageView?
    ) {
        Glide.with(context)
            .asBitmap()
            .load(url)
            .into(object : ImageViewTarget<Bitmap>(imageView) {
                override fun setResource(resource: Bitmap?) {
                    if (resource != null) {
                        val eqLongImage = MediaUtils.isLongImg(
                            resource.width,
                            resource.height
                        )
                        longImageView?.visibility = if (eqLongImage) View.VISIBLE else View.GONE
                        imageView.visibility = if (eqLongImage) View.GONE else View.VISIBLE
                        if (eqLongImage) {
                            // 加载长图
                            longImageView?.run {
                                isQuickScaleEnabled = true
                                isZoomEnabled = true
                                isPanEnabled = true
                                setDoubleTapZoomDuration(100)
                                setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CENTER_CROP)
                                setDoubleTapZoomDpi(SubsamplingScaleImageView.ZOOM_FOCUS_CENTER)
                            }

                            longImageView?.setImage(
                                ImageSource.bitmap(resource),
                                ImageViewState(0f, PointF(0f, 0f), 0)
                            )
                        } else {
                            // 普通图片
                            imageView.setImageBitmap(resource)
                        }
                    }
                }
            })
    }

    /**
     * 加载gif
     */
    override fun loadAsGifImage(context: Context, url: String, imageView: ImageView) {
        Glide.with(context)
            .asGif()
            .load(url)
            .into(imageView)
    }

    /**
     * 加载图片列表图片
     */
    override fun loadGridImage(context: Context, url: String, imageView: ImageView) {
        Glide.with(context)
            .load(url)
            .override(200, 200)
            .centerCrop()
            .apply(RequestOptions().placeholder(R.drawable.picture_image_placeholder))
            .into(imageView)
    }

    /**
     * 加载相册目录
     */
    override fun loadFolderImage(context: Context, url: String, imageView: ImageView) {
        Glide.with(context)
            .asBitmap()
            .load(url)
            .override(180, 180)
            .centerCrop()
            .sizeMultiplier(0.5f)
            .apply(RequestOptions().placeholder(R.drawable.picture_image_placeholder))
            .into(object : BitmapImageViewTarget(imageView) {
                override fun setResource(resource: Bitmap?) {
                    val circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.resources, resource)
                    circularBitmapDrawable.cornerRadius = 8f
                    imageView.setImageDrawable(circularBitmapDrawable)
                }
            })
    }
}