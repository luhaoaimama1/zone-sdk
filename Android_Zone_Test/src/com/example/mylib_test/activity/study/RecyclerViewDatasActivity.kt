package com.example.mylib_test.activity.study

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.*
import com.example.mylib_test.LogApp
import com.example.mylib_test.R
import com.zone.lib.base.controller.activity.BaseFeatureActivity
import kotlinx.android.synthetic.main.a_rvdatas.*

//   PagerSnapHelper().attachToRecyclerView(recyclerView)
//  LinearSnapHelper().attachToRecyclerView(recyclerView)
class RecyclerViewDatasActivity : BaseFeatureActivity() {
    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView)
    private val list = ArrayList<String>()
    private var lastSelectedPosition = -1
    override fun setContentView() {
        for (i in 0..100) {
            list.add("图片_$i")
        }
        setContentView(R.layout.a_rvdatas)
        val linearLayoutManagerV2 = PagerSnapLinearLayoutManager(this)
        linearLayoutManagerV2.viewPagerListener = object : PagerSnapLinearLayoutManager.OnViewPagerListener {
            override fun onInitComplete() {
                LogApp.d("onInitComplete")
            }

            override fun onPageSelected(position: Int, isBottom: Boolean) {
                lastSelectedPosition = position
                LogApp.d("selectedPosition:$position")
            }
        }
        linearLayoutManagerV2.findSnapView = object : IFindSnapView {
            override fun findValidSnapView(findTargetSnapPosition: Int, isFling: Boolean): Boolean {
                return if (findTargetSnapPosition % 4 != 1) {
                    true
                } else {
                    LogApp.d("过滤掉：$findTargetSnapPosition ,是否是fling：$isFling")
                    false
                }
            }
        }
        recyclerView.layoutManager = linearLayoutManagerV2
        recyclerView.adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
                return if (viewType == 1) {
                    val inflate = LayoutInflater.from(parent.context).inflate(R.layout.item_rv_textview_mp, parent, false)
                    Holder(inflate)
                } else {
                    val inflate = LayoutInflater.from(parent.context).inflate(R.layout.item_hide, parent, false)
                    Holder(inflate)
                }
            }

            override fun getItemCount(): Int = list.size
            override fun getItemViewType(position: Int): Int {
                return if (position % 4 != 1) 1
                else 0
            }

            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                if (holder.itemView is TextView) {
                    (holder.itemView as TextView).text = list[position]
                } else {
                    holder.itemView.visibility = View.GONE
                }
            }
        }

        tv.setOnClickListener {
            //有动画
            val findValidPosi = linearLayoutManagerV2.pagerSnapHelper.nextValidPosi(lastSelectedPosition, true)
            if (findValidPosi != RecyclerView.NO_POSITION) {
                recyclerView.smoothScrollToPosition(findValidPosi)
            }

            //无动画
//            recyclerView.scrollToPosition(lastSelectedPosition);
        }

        tvSmoothScroll19.setOnClickListener {
            recyclerView.smoothScrollToPosition(19)
        }
        tvScroll36.setOnClickListener {
            recyclerView.scrollToPosition(36)
            linearLayoutManagerV2.scrollToPosition(36)
        }

        tvScroll27Offset.setOnClickListener {
            linearLayoutManagerV2.scrollToPositionWithOffset(27, 500)
        }
    }

    override fun initData() {}

    override fun setListener() {}
}