package com.example.mylib_test.activity.file

import java.io.ByteArrayInputStream
import java.io.File
import java.io.InputStream
import java.io.UnsupportedEncodingException

import com.example.mylib_test.R

import com.zone.lib.utils.activity_fragment_ui.ToastUtils
import com.zone.lib.utils.data.file2io2data.FileUtils
import com.zone.lib.utils.data.file2io2data.SDCardUtils
import com.zone.lib.utils.data.file2io2data.IOUtils
import android.content.Intent
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import com.example.mylib_test.FileManager
import com.example.mylib_test.LogApp
import com.example.mylib_test.SP2
import com.zone.lib.base.controller.activity.BaseFeatureActivity

class FileTestActivity : BaseFeatureActivity(), OnClickListener {
    override fun setContentView() {
        setContentView(R.layout.a_filetest)
    }

    override fun initData() {
    }

    override fun setListener() {
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.create -> createFile()
            R.id.delete -> deleteFile()
            R.id.readFile -> readFile()
            R.id.copyFileto -> copyFile()
            //测试 用完1000mb SD是否够用
            R.id.sd -> SDCardUtils.IsSDspaceEnough(this, "1000mb")
            R.id.self -> startActivity(Intent(this, Activity1::class.java))
            R.id.sptest -> sptest()
            else -> {
            }
        }
    }

    private fun copyFile() {
        //把数据复制到另一个路径 的文件aabb.txt
        val inFile = File(FileManager.MyFolder, "bbcc.txt")
        val outFile = File(FileManager.MyFolder, "aabb.txt")
        ToastUtils.showLong(this, if (IOUtils.write(outFile, inFile, false) == true) "复制成功" else "复制失败")
    }

    private fun readFile() {
        //得到bbcc.txt这个文件 读取文件
        val file = File(FileManager.MyFolder, "bbcc.txt")
        val text = "我非常好,你说呢！"
        var ins: InputStream? = null
        try {
            ins = ByteArrayInputStream(text.toByteArray(charset("GBK")))
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }

        IOUtils.write(file, ins)
        Log.e("xihuan", IOUtils.read(file, "GBK"))
    }

    private fun createFile() {
        //创建目录测试
        val file2 = FileUtils.getFile(FileManager.MyFolder,  "mma", "heihei", "bbcc.txt")
        Log.e("xihuan", file2.absolutePath)
    }

    private fun deleteFile() {
        if (FileUtils.getFile(FileManager.MyFolder,  "mma", "heihei").delete())
        //删除文件测试
            ToastUtils.showLong(this, "删除成功")
        else
            ToastUtils.showLong(this, "删除失败")
    }

    private fun sptest() {

        LogApp.d(
                "不从缓存from cache：int:${SP2.get("int", -1)} "
        )
        SP2.put("int", 1)
        SP2.put("long", 10L)
        SP2.put("float", 1.0F)
        SP2.put("string", "happy?")
        SP2.put("boolean", true)

        LogApp.d(
                "from cache：int${SP2.get("int", -1)} \n" +
                        "from cache：long${SP2.get("long", -1L)} \n" +
                        "from cache：float${SP2.get("float", -1.0)} \n" +
                        "from cache：string${SP2.get("string", "empty")} \n" +
                        "from cache：boolean${SP2.get("boolean", false)} \n" +
                        ""
        )
    }

}
