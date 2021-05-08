package com.doctorappoinmentdemo.utils

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.Window
import com.doctorappoinmentdemo.R


class AppProgressDialog(mContext: Context) {
    public val mProgressDialog: Dialog?

    init {
        mProgressDialog = Dialog(mContext)
    }

    fun show() {
        try {
            if (mProgressDialog!!.isShowing)
                return
            mProgressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            mProgressDialog.setContentView(R.layout.layout_progress_bar)
            // ((TextView) mProgressDialog.findViewById(R.id.title)).setText(msg);
            mProgressDialog.setCancelable(false)
            if (mProgressDialog.window != null)
                mProgressDialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
              mProgressDialog!!.show()

        } catch (e: Exception) {
            e.printStackTrace()
            Log.v("dip","progress error :"+e.message)
        }

    }

    fun hide() {
        if (mProgressDialog != null && mProgressDialog.isShowing) {
            mProgressDialog.dismiss()
        }
    }

}