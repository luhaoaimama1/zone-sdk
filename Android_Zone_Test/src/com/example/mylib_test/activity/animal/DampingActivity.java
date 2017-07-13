package com.example.mylib_test.activity.animal;
import android.widget.EditText;
import com.example.mylib_test.R;
import com.zone.lib.base.activity.BaseActivity;
import com.zone.lib.utils.activity_fragment_ui.ToastUtils;
import com.zone.lib.utils.data.check.EmptyCheck;
import butterknife.Bind;
import butterknife.ButterKnife;
import view.DampingView;


/**
 * Created by fuzhipeng on 16/7/29.
 */
public class DampingActivity extends BaseActivity implements DampingView.DownCallback{
    @Bind(R.id.maxValue)
    EditText maxValue;
    @Bind(R.id.dampingRadio)
    EditText dampingRadio;
    @Bind(R.id.dampingView)
    DampingView dampingView;

    @Override
    public void setContentView() {
        setContentView(R.layout.a_damping);
        ButterKnife.bind(this);
        dampingView.setDownCallback(this);
    }

    @Override
    public void findIDs() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void setListener() {

    }


    @Override
    public void down() {
        try {
            if(!EmptyCheck.isEmpty(maxValue.getText().toString())){
                float maxValueT =Float.parseFloat(maxValue.getText().toString());
                dampingView.setMaxValue(maxValueT);
            }
            if(!EmptyCheck.isEmpty(dampingRadio.getText().toString())){
                float dampingRadioT =Float.parseFloat(dampingRadio.getText().toString());
                dampingView.setDampingRadio(dampingRadioT);
            }
        } catch (NumberFormatException e) {
//            e.printStackTrace();
            ToastUtils.showShort(this,"请输入数字");
        }
    }
}
