package com.example.mylib_test.activity.study

import android.view.View
import com.zone.lib.base.activity.BaseActivity
import android.graphics.pdf.PdfRenderer
import android.os.ParcelFileDescriptor
import android.os.Build
import android.annotation.TargetApi
import android.content.Intent
import java.io.File
import java.io.IOException
import android.view.ViewGroup
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.example.mylib_test.R
import kotlinx.android.synthetic.main.a_study.*


/**
 *[2018/11/14] by Zone
 */
class PDFActivity : BaseActivity() {

    override fun setContentView() {
        setContentView(R.layout.a_study)

        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.setType("*/*");//设置类型，我这里是任意类型，任意后缀的可以这样写。
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        startActivityForResult(intent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        if (resultCode == RESULT_OK && requestCode == 1) {
            data?.data?.let {
                try {
                    openRender(Uri2PathUtil.getRealPathFromUri(this, it))
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }
    }

    override fun findIDs() {
    }

    override fun initData() {
    }

    override fun setListener() {
    }

    var mDescriptor: ParcelFileDescriptor? = null
    var mRenderer: PdfRenderer? = null

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Throws(IOException::class)
    private fun openRender(path: String) {
        //初始化PdfRender
        val open = System.currentTimeMillis()

        mDescriptor = ParcelFileDescriptor.open(File(path), ParcelFileDescriptor.MODE_READ_ONLY)
        if (mDescriptor != null) {
            mRenderer = PdfRenderer(mDescriptor)
        }
        val over = System.currentTimeMillis() - open
        Log.e("PDFTimer", "初始化时间：$over")
        //初始化ViewPager的适配器并绑定
        val adapter = MyPagerAdapter()
        vpPdf.adapter = adapter
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun closeRenderer() {
        mRenderer?.close()
        mDescriptor?.close()
    }

    override fun onDestroy() {
        //销毁页面的时候释放资源,习惯
        try {
            closeRenderer()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        super.onDestroy()
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    inner class MyPagerAdapter() : PagerAdapter() {

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val view = ImageView(container.context)
            view.setLayoutParams(ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
            view.id = position
            if (count <= position) {
                return view
            }

            val open = System.currentTimeMillis()
            val currentPage = mRenderer?.openPage(position)
            val over = System.currentTimeMillis() - open
            Log.e("PDFTimer", "打开时间：$over")
            val bitmap = Bitmap.createBitmap(1080, 1760, Bitmap.Config
                    .ARGB_8888)
            currentPage?.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
            view.setImageBitmap(bitmap)
            //关闭当前Page对象
            currentPage?.close()
            container.addView(view)
            return view
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
//            super.destroyItem(container, position, `object`)
            container.removeView(container.findViewById(position))
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean = view === `object`


        override fun getCount(): Int {
            return mRenderer?.pageCount ?: 0
        }
    }

}