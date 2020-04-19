package com.df.playandroid.utils

import java.io.Closeable
import java.io.IOException

/**
 * 作者：PeterWu
 * 时间：2020/4/19
 * 描述：
 */
object IOUtils {

    fun closeIO(vararg closeables: Closeable?) {
        for (closeable in closeables) {
            if (closeable != null) {
                try {
                    closeable.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }
}