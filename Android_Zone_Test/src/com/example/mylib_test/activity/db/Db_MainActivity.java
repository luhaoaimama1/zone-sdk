package com.example.mylib_test.activity.db;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.mylib_test.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.zone.view.FlowLayout;

public class Db_MainActivity extends Activity implements OnClickListener {

    @BindView(R.id.db_addColumn)
    Button dbAddColumn;
    @BindView(R.id.db_update_Column)
    Button dbUpdateColumn;
    @BindView(R.id.db_queryColumn)
    Button dbQueryColumn;
    @BindView(R.id.db_create)
    Button dbCreate;
    @BindView(R.id.db_Tabledelete)
    Button dbTabledelete;
    @BindView(R.id.db_add)
    Button dbAdd;
    @BindView(R.id.db_addOrUpdateEntity)
    Button dbAddOrUpdateEntity;
    @BindView(R.id.db_updateByC)
    Button dbUpdateByC;
    @BindView(R.id.db_update)
    Button dbUpdate;
    @BindView(R.id.db_deleteAll)
    Button dbDeleteAll;
    @BindView(R.id.db_deleteByC)
    Button dbDeleteByC;
    @BindView(R.id.db_delete)
    Button dbDelete;
    @BindView(R.id.db_query)
    Button dbQuery;
    @BindView(R.id.db_queryInner)
    Button dbQueryInner;
    @BindView(R.id.db_queryByC)
    Button dbQueryByC;
    @BindView(R.id.tran_add_fail)
    Button tranAddFail;
    @BindView(R.id.tran_add_success)
    Button tranAddSuccess;
    @BindView(R.id.flowLayoutZone1)
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
