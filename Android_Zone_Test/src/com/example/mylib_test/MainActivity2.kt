package com.example.mylib_test

import com.example.mylib_test.activity.db.entity.MenuEntity
import com.example.mylib_test.adapter.delegates.MenuEntityDeletates
import android.app.AlertDialog
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.zone.adapter3kt.QuickAdapter
import com.zone.adapter3kt.ViewStyleDefault
import com.zone.adapter3kt.ViewStyleOBJ
import com.zone.adapter3kt.adapter.OnItemClickListener
import com.zone.adapter3kt.data.HFMode
import com.zone.lib.base.controller.activity.BaseFeatureActivity
import com.zone.lib.LogZSDK
import com.zone.lib.ZLogger
import com.zone.lib.base.controller.activity.controller.SwipeBackActivityController
import kotlinx.android.synthetic.main.a_menu.*

class MainActivity2 : BaseFeatureActivity() {

    private var positonId = -1
    private var alert: AlertDialog? = null
    private var adapter2: QuickAdapter<MenuEntity>? = null

    companion object {
        //还原最开始的log配置  如果某次配置一次特殊的 打印完后的记得还原配置
        @JvmStatic
        fun initLogger() {
            ZLogger.logLevelList.clear()
            ZLogger.mayLoggerList.clear()
            ZLogger.mayLoggerList.addAll(listOf<ZLogger>(LogApp, LogZSDK))
        }
    }

    override fun initDefaultConifg() {
        unRegisterPrestener(SwipeBackActivityController::class.java)
    }

    override fun setContentView() {
        setContentView(R.layout.a_menu)
    }

    override fun onStoragePermit() {
        super.onStoragePermit()
        FileManager.firstInit(this)
    }

    override fun onStorageDeniedMustPermit() {
        super.onStorageDeniedMustPermit()
        finish()
    }

    override fun initData() {
        permissionCheckStorageMustPermit()
        initLogger()
        createDialog()
        listView1.layoutManager = LinearLayoutManager(this)
        val colorArry = intArrayOf(Color.WHITE, Color.GREEN, Color.YELLOW, Color.CYAN)
        adapter2 = QuickAdapter<MenuEntity>(this).apply {
            enableLoadMore = true
            registerDelegate(MenuEntityDeletates(this@MainActivity2, colorArry, MainMenu.menu))
            registerDelegate(1, R.layout.header_simple)
            registerDelegate(10, R.layout.footer_simple)
            defineHeaderOrder(HFMode.ADD, 1)
            defineFooterOrder(HFMode.ADD, 10)

            onItemClickListener = object : OnItemClickListener {
                override fun onItemClick(parent: ViewGroup, view: View, position: Int) {
                    positonId = position
                    alert!!.show()
                }
            }
            setStyleExtra(object : ViewStyleDefault<MenuEntity>() {
                override fun generateViewStyleOBJ(item: MenuEntity): ViewStyleOBJ? {
                    val viewStyle = when {
                        item.info.contains("header") -> 1
                        item.info.contains("footer") -> 10
                        else -> -1
                    }
                    return ViewStyleOBJ().viewStyle(viewStyle)
                }

                override fun getItemViewType(position: Int, itemConfig: ViewStyleOBJ) {
                }
            })

            add(MenuEntity("header", String::class.java))
            add(MainMenu.menu)
            add(MenuEntity("footer", String::class.java))
        }
        listView1.adapter=adapter2

        //通过加载XML动画设置文件来创建一个Animation对象；
        //		Animation animation= AnimationUtils.loadAnimation(this, R.anim.scale_in);   //得到一个LayoutAnimationController对象；
        //		LayoutAnimationController controller = new LayoutAnimationController(animation);   //设置控件显示的顺序；
        //		controller.setOrder(LayoutAnimationController.ORDER_RANDOM);   //设置控件显示间隔时间；
        //		controller.setDelay(0.3F);   //为ListView设置LayoutAnimationController属性；
        //		listView1.setLayoutAnimation(controller);
        //		listView1.startLayoutAnimation();


        //		CustomLayoutAnimationController controller2 = new CustomLayoutAnimationController(animation);   //设置控件显示的顺序；
        //		controller2.setOrder(LayoutAnimationController.ORDER_REVERSE);   //设置控件显示间隔时间；
        //		controller2.setDelay(2F);   //为ListView设置LayoutAnimationController属性；
        //		listView1.setLayoutAnimation(controller2);
        //		listView1.startLayoutAnimation();
    }

    override fun setListener() {
    }

    private fun createDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you sure you want to remove?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->
                    if (positonId != -1) {
                        MainMenu.menu.removeAt(positonId)
                        positonId = -1
                        adapter2!!.notifyDataSetChanged()
                    }
                }
                .setNegativeButton("No") { dialog, id -> dialog.cancel() }
        alert = builder.create()
    }
}
