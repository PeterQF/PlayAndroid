package com.df.playandroid.base.helper

import android.app.Activity
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType

/**
 * 作者：PeterWu
 * 时间：2020/5/9
 * 描述：
 */
object PictureSelectorHelper {

    /**
     * 打开相册
     */
    fun openGallery(activity: Activity) {
        PictureSelector
            .create(activity)
            .openGallery(PictureMimeType.ofImage())
            .loadImageEngine(JYImageEngine())
            .isPageStrategy(true)
            .selectionMode(PictureConfig.SINGLE)
            .isCamera(true)
            .minSelectNum(1)
            .maxSelectNum(1)
            .previewImage(true)
            .enableCrop(true)
            .scaleEnabled(true)
            .isDragFrame(true)
            .compress(true)
            .minimumCompressSize(1024)
            .forResult(PictureConfig.CHOOSE_REQUEST)

    }
}