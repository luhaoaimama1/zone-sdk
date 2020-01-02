package com.example.mylib_test.activity.animal

import android.view.View

import com.example.mylib_test.R
import com.example.mylib_test.activity.animal.utils.test.CustomAnim
import com.example.mylib_test.activity.animal.utils.test.Matrix3DAnimTest
import com.zone.lib.base.controller.activity.BaseFeatureActivity
import kotlinx.android.synthetic.main.a_animal_custom_ani.*

class CustomAniActivity : BaseFeatureActivity() {
    private val ani = Matrix3DAnimTest(this)

    override fun setContentView() {
        setContentView(R.layout.a_animal_custom_ani)
    }

    override fun initData() {
    }

    override fun setListener() {
    }

    override fun onClick(view: View?) {
        val ani = CustomAnim()
        when (view?.id) {

            R.id.x -> {
                ani.setX(100f)
                iv!!.startAnimation(ani)
            }
            R.id.y -> {
                ani.setY(500f)
                iv!!.startAnimation(ani)
            }
            R.id.z -> {
                ani.setZ(500f)
                iv!!.startAnimation(ani)
            }

            R.id.utils_xR -> {
                ani.setRotateX(30f)
                iv!!.startAnimation(ani)
            }
            R.id.utils_yR -> {
                ani.setRotateY(30f)
                iv!!.startAnimation(ani)
            }
            R.id.utils_zR -> {
                ani.setRotateZ(30f)
                iv!!.startAnimation(ani)
            }
        }
        view?.let {
            test2D(it)
            mx3DTest(it)
        }
    }


    fun test2D(view: View) {
        when (view.id) {
            R.id.zR -> iv!!.animate().rotation(30f).start()
            R.id.zYR -> iv!!.animate().rotationX(30f).start()
        }
    }

    fun mx3DTest(view: View) {

        when (view.id) {
            R.id.mx3D_tran -> {
                ani.setView(iv)
                ani.setTest(Matrix3DAnimTest.Test.compose)
                iv!!.startAnimation(ani)
            }
            R.id.mx3D_tran2 -> {
                ani.setView(iv)
                ani.setTest(Matrix3DAnimTest.Test.compose2)
                iv!!.startAnimation(ani)
            }
            R.id.mx3D_xR -> {
                ani.setView(iv)
                ani.setTest(Matrix3DAnimTest.Test.composeX)
                iv!!.startAnimation(ani)
            }
            R.id.mx3D_yR -> {
                ani.setView(iv)
                ani.setTest(Matrix3DAnimTest.Test.composeY)
                iv!!.startAnimation(ani)
            }
            R.id.mx3D_zR -> {
                ani.setView(iv)
                ani.setTest(Matrix3DAnimTest.Test.composeZ)
                iv!!.startAnimation(ani)
            }
            R.id.mx3D -> {
                ani.setView(iv)
                ani.setTest(Matrix3DAnimTest.Test.compose3)
                iv!!.startAnimation(ani)
            }
            R.id.layer -> {
                ani.setView(iv)
                ani.setTest(Matrix3DAnimTest.Test.layer)
                iv!!.startAnimation(ani)
            }
            R.id.layer2 -> {
                ani.setView(iv)
                ani.setTest(Matrix3DAnimTest.Test.layer2)
                iv!!.startAnimation(ani)
            }
            else -> {
            }
        }
    }
}
