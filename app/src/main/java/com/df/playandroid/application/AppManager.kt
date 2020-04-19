package com.df.playandroid.application

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import com.df.playandroid.utils.LogUtil
import java.util.*
import kotlin.system.exitProcess

class AppManager private constructor(){
    //  activity管理栈
    private val activityStack: Stack<Activity> = Stack()

    companion object {
        val instance : AppManager by lazy { AppManager() }
    }

    /**
     * 入栈
     */
    fun addActivity(activity: Activity){
        activityStack.add(activity)
        LogUtil.info("AppManager stack size -----> ${activityStack.size}")
    }

    /**
     * 出栈
     */
    fun finishActivity(activity: Activity){
        activity.finish()
        activityStack.remove(activity)
        LogUtil.info("AppManager stack size -----> ${activityStack.size}")
    }

    /**
     * 当前activity,也就是栈的最后一位元素
     */
    fun currentActivity(): Activity{
        return activityStack.lastElement()
    }

    /**
     * 清理栈
     */
    fun finishAllActivity(){
        for(activity in activityStack){
            activity.finish()
        }
        activityStack.clear()
    }

    /**
     * 退出应用
     */
    fun exitApplication(context: Context){
        finishAllActivity()
        val activityManager:ActivityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        activityManager.killBackgroundProcesses(context.packageName)
        exitProcess(0)
    }
}