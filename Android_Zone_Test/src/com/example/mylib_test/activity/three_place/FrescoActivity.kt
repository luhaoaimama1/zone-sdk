package com.example.mylib_test.activity.three_place

import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.ButterKnife
import com.example.mylib_test.R
import com.example.mylib_test.delegates.FrescoDeletates
import com.example.mylib_test.delegates.FrescoProcessorDeletates
import com.example.mylib_test.delegates.FrescoSuperscriptDeletates
import com.zone.adapter3.QuickRcvAdapter
import com.zone.adapter3.base.IAdapter
import com.zone.lib.base.activity.BaseActivity
import kotlinx.android.synthetic.main.a_fresco.*

/**
 *[2018/7/8] by Zone
 */
class FrescoActivity : BaseActivity() {

    inner class Entity(var introduce: String, var uri: String);

    private var mDatas = ArrayList<Entity>()
    private var muliAdapter: IAdapter<Entity>? = null

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
    }

    override fun setContentView() {
        setContentView(R.layout.a_fresco)
        ButterKnife.bind(this)
    }

    override fun findIDs() {
    }

    override fun initData() {
        rv!!.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rv!!.itemAnimator = DefaultItemAnimator()
        muliAdapter = object : QuickRcvAdapter<Entity>(this, mDatas), IAdapter<Entity> {
            override fun getItemViewType2(dataPosition: Int): Int {
                when (mDatas.get(dataPosition).introduce) {
                    "角标" -> return 1
                    "进度" -> return 2
                    else -> return 0
                }
            }
        }

        muliAdapter!!.addViewHolder(FrescoDeletates())
                .addViewHolder(1, FrescoSuperscriptDeletates())
                .addViewHolder(2, FrescoProcessorDeletates())
                .relatedList(rv)
                .addItemDecoration(10)

    }

    override fun setListener() {
    }
}