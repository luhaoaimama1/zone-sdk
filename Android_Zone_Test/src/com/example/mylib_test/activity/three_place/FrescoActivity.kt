package com.example.mylib_test.activity.three_place

import android.graphics.Rect
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mylib_test.R
import com.example.mylib_test.activity.db.entity.MenuEntity
import com.zone.lib.base.controller.activity.BaseFeatureActivity
import com.example.mylib_test.adapter.delegates.FrescoDeletates
import com.example.mylib_test.adapter.delegates.FrescoProcessorDealDeletates
import com.example.mylib_test.adapter.delegates.FrescoProcessorDeletates
import com.example.mylib_test.adapter.delegates.FrescoSuperscriptDeletates
import com.zone.adapter3kt.QuickAdapter
import com.zone.adapter3kt.ViewStyleDefault
import com.zone.adapter3kt.ViewStyleOBJ
import kotlinx.android.synthetic.main.a_fresco.*

/**
 *[2018/7/8] by Zone
 */
class FrescoActivity : BaseFeatureActivity() {

    inner class Entity(var introduce: String, var uri: String);

    private var mDatas = ArrayList<Entity>()

    init {
        mDatas.add(Entity("http", "http://ww4.sinaimg.cn/mw1024/005PquKVgw1ezrgh5ppeyj30ku0kujvv.jpg"))
        mDatas.add(Entity("File", "http://ww4.sinaimg.cn/mw1024/005PquKVgw1ezrgh5ppeyj30ku0kujvv.jpg"))
        mDatas.add(Entity("Resource", "http://ww4.sinaimg.cn/mw1024/005PquKVgw1ezrgh5ppeyj30ku0kujvv.jpg"))
        mDatas.add(Entity("Uri", "http://ww4.sinaimg.cn/mw1024/005PquKVgw1ezrgh5ppeyj30ku0kujvv.jpg"))
        mDatas.add(Entity("Gif", "http://ww4.sinaimg.cn/mw1024/005PquKVgw1ezrgh5ppeyj30ku0kujvv.jpg"))
        mDatas.add(Entity("Mp4", "http://ww4.sinaimg.cn/mw1024/005PquKVgw1ezrgh5ppeyj30ku0kujvv.jpg"))
        mDatas.add(Entity("Error", "http://ww4.sinaimg.cn/mw1024/005PquKVgw1ezrgh5ppeyj30ku0kujvv.jpg"))
        mDatas.add(Entity("角标", "http://ww4.sinaimg.cn/mw1024/005PquKVgw1ezrgh5ppeyj30ku0kujvv.jpg"))
        mDatas.add(Entity("进度", "http://ww4.sinaimg.cn/mw1024/005PquKVgw1ezrgh5ppeyj30ku0kujvv.jpg"))
        mDatas.add(Entity("处理", "http://ww4.sinaimg.cn/mw1024/005PquKVgw1ezrgh5ppeyj30ku0kujvv.jpg"))
    }

    override fun setContentView() {
        setContentView(R.layout.a_fresco)
    }


    override fun initData() {
        rv!!.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rv!!.itemAnimator = DefaultItemAnimator()
        rv.adapter =   QuickAdapter<Entity>(this@FrescoActivity).apply {
            registerDelegate(FrescoDeletates())
            registerDelegate(1, FrescoSuperscriptDeletates())
            registerDelegate(2, FrescoProcessorDeletates())
            registerDelegate(3, FrescoProcessorDealDeletates())
            setStyleExtra(object : ViewStyleDefault<Entity>() {
                override fun generateViewStyleOBJ(item: Entity): ViewStyleOBJ? {

                    var bottom = 10
                    val viewStyle = when (item.introduce) {
                        "角标" -> 1
                        "进度" -> 2
                        "处理" -> {
                            bottom = 0
                            3
                        }
                        else -> -1
                    }

                    return ViewStyleOBJ().viewStyle(viewStyle)
                            .divderRect(Rect(10, 0, 10, bottom))

                }

                override fun getItemViewType(position: Int, itemConfig: ViewStyleOBJ) {
                }
            })
            add(mDatas)
        }

    }

    override fun setListener() {
    }
}