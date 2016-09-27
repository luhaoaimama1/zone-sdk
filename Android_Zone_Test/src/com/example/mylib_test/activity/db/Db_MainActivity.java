package com.example.mylib_test.activity.db;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.mylib_test.R;

import butterknife.Bind;
import butterknife.ButterKnife;

import com.zone.view.FlowLayout;

public class Db_MainActivity extends Activity implements OnClickListener {

    @Bind(R.id.db_addColumn)
    Button dbAddColumn;
    @Bind(R.id.db_update_Column)
    Button dbUpdateColumn;
    @Bind(R.id.db_queryColumn)
    Button dbQueryColumn;
    @Bind(R.id.db_create)
    Button dbCreate;
    @Bind(R.id.db_Tabledelete)
    Button dbTabledelete;
    @Bind(R.id.db_add)
    Button dbAdd;
    @Bind(R.id.db_addOrUpdateEntity)
    Button dbAddOrUpdateEntity;
    @Bind(R.id.db_updateByC)
    Button dbUpdateByC;
    @Bind(R.id.db_update)
    Button dbUpdate;
    @Bind(R.id.db_deleteAll)
    Button dbDeleteAll;
    @Bind(R.id.db_deleteByC)
    Button dbDeleteByC;
    @Bind(R.id.db_delete)
    Button dbDelete;
    @Bind(R.id.db_query)
    Button dbQuery;
    @Bind(R.id.db_queryInner)
    Button dbQueryInner;
    @Bind(R.id.db_queryByC)
    Button dbQueryByC;
    @Bind(R.id.tran_add_fail)
    Button tranAddFail;
    @Bind(R.id.tran_add_success)
    Button tranAddSuccess;
    @Bind(R.id.flowLayoutZone1)
    FlowLayout flowLayoutZone1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_dbtest);
        ButterKnife.bind(this);

    }

    @Override
    public void onClick(View v) {
        onClickTable(v);
        onClickAdd(v);
        onClickQuery(v);
        onClickUpdate(v);
        onClickDelete(v);
    }

    private void onClickDelete(View v) {
        switch (v.getId()) {
            case R.id.db_delete:
                break;
            case R.id.db_deleteAll:
                break;
            case R.id.db_deleteByC:
                break;

            default:
                break;
        }
    }

    private void onClickUpdate(View v) {
        switch (v.getId()) {
            case R.id.db_update:
                break;
            case R.id.db_updateByC:
                break;

            default:
                break;
        }
    }

    private void onClickQuery(View v) {
        switch (v.getId()) {
            case R.id.db_query:
                break;
            case R.id.db_queryInner:
                break;
            case R.id.db_queryByC:
                break;

            default:
                break;
        }
    }

    private void onClickAdd(View v) {
        switch (v.getId()) {
            case R.id.db_add:
                break;
            case R.id.db_addColumn:
                break;
            case R.id.db_addOrUpdateEntity:
                break;
            case R.id.tran_add_fail:

                break;
            case R.id.tran_add_success:
                break;
            default:
                break;
        }
    }

    private void onClickTable(View v) {
        switch (v.getId()) {
            case R.id.db_create:
                break;
            case R.id.db_Tabledelete:
                break;
            case R.id.db_queryColumn:
                break;

            default:
                break;
        }
    }

}
