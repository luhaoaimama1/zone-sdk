//package com.example.mylib_test.adapter
//
//import android.content.Context
//import android.graphics.Rect
//import android.view.View
//import android.view.ViewGroup
//import com.zone.adapter3kt.QuickAdapter
//import com.zone.adapter3kt.adapter.StickyAdapter
//import com.zone.adapter3kt.ViewStyleDefault
//import com.zone.adapter3kt.ViewStyleOBJ
//import com.zone.adapter3kt.adapter.OnItemClickListener
//import com.zone.adapter3kt.data.HFMode
//import com.zone.adapter3kt.divder.StandardDivder
//import zone.com.zadapter3.R
//import zone.com.zadapter3kt.adapter.LeftDelegates
//import zone.com.zadapter3kt.adapter.RightDelegates
//
///**
// * [2018/9/13] by Zone
// */
//class CommonAdapter(context: Context) : QuickAdapter<String>(context) {
//    init {
//        enableHistory(true)
//
//        registerDelegate(LeftDelegates())
//        registerDelegate(0, LeftDelegates())
//        registerDelegate(1, RightDelegates())
//
//        registerDelegate(3, R.layout.header_simple)
//        registerDelegate(4, R.layout.header_simple2)
//        registerDelegate(5, R.layout.header_simple3)
//
//        registerDelegate(6, R.layout.footer_simple)
//        registerDelegate(7, R.layout.footer_simple_img2)
//        registerDelegate(8, R.layout.footer_simple_img)
//        defineHeaderOrder(HFMode.ADD, 3, 4, 5)
//        defineFooterOrder(HFMode.ADD, 6, 7, 8)
//
//        registerEmpytDelegate(R.layout.empty)
//
//        setStyleExtra(object : ViewStyleDefault<String>() {
//            override fun generateViewStyleOBJ(item: String): ViewStyleOBJ? {
//                var viewStyle = when (item) {
//                    "header1" -> 3
//                    "header2" -> 4
//                    "header3" -> 5
//                    "footer1" -> 6
//                    "footer2" -> 7
//                    "footer3" -> 8
//                    else -> -1
//                }
//                if (item.contains("insert one")) {
//                    val index = item.substring("insert one item!+".length, item.length).toInt()
//                    viewStyle = if (index % 3 == 0) 1 else 0
//                }
//                return ViewStyleOBJ().viewStyle(viewStyle)
//            }
//
//            override fun getItemViewType(position: Int, itemConfig: ViewStyleOBJ) {
//            }
//        })
//    }
//}
