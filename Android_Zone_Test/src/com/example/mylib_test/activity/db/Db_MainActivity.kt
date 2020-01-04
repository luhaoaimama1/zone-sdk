package com.example.mylib_test.activity.db

import android.view.View
import android.view.View.OnClickListener
import com.example.mylib_test.R

import com.zone.lib.base.controller.activity.BaseFeatureActivity

class Db_MainActivity : BaseFeatureActivity(), OnClickListener {

    override fun setContentView() {
        setContentView(R.layout.a_dbtest)
    }

    override fun initData() {
    }

    override fun setListener() {
    }

    override fun onClick(v: View?) {
        onClickTable(v)
        onClickAdd(v)
        onClickQuery(v)
        onClickUpdate(v)
        onClickDelete(v)
    }

    private fun onClickDelete(v: View?) {
        when (v?.id) {
            R.id.db_delete -> {
            }
            R.id.db_deleteAll -> {
            }
            R.id.db_deleteByC -> {
            }

            else -> {
            }
        }
    }

    private fun onClickUpdate(v: View?) {
        when (v?.id) {
            R.id.db_update -> {
            }
            R.id.db_updateByC -> {
            }

            else -> {
            }
        }
    }

    private fun onClickQuery(v: View?) {
        when (v?.id) {
            R.id.db_query -> {
            }
            R.id.db_queryInner -> {
            }
            R.id.db_queryByC -> {
            }

            else -> {
            }
        }
    }

    private fun onClickAdd(v: View?) {
        when (v?.id) {
            R.id.db_add -> {
            }
            R.id.db_addColumn -> {
            }
            R.id.db_addOrUpdateEntity -> {
            }
            R.id.tran_add_fail -> {
            }
            R.id.tran_add_success -> {
            }
            else -> {
            }
        }
    }

    private fun onClickTable(v: View?) {
        when (v?.id) {
            R.id.db_create -> {
            }
            R.id.db_Tabledelete -> {
            }
            R.id.db_queryColumn -> {
            }

            else -> {
            }
        }
    }

}
