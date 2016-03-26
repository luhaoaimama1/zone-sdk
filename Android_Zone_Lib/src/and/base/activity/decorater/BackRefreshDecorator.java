package and.base.activity.decorater;

import android.content.Intent;

import and.base.activity.RequestCodeConfig;

/**
 * Created by Administrator on 2016/3/26.
 */
public class BackRefreshDecorator extends BaseDecorater {
    public static int Reresh_RequestCode = RequestCodeConfig.getRequestCode(RequestCodeConfig.Reresh_RequestCode);
    public static int Reresh_Response= RequestCodeConfig.getRequestCode(RequestCodeConfig.Reresh_Response);
    //带有可返回刷新 跳转
    public  void startActivityWithRefresh(Intent intent){
        startActivityForResult(intent, Reresh_RequestCode);
    }
    //有intent数据返回的刷新
    public void finishWithBackRefresh(Intent data){
        setResult(Reresh_Response, data);
        finish();
    }
    //没有intent数据返回的刷新
    public void finishWithBackRefresh(){
        setResult(Reresh_Response);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //resultCode==0那么就是默认返回的即直接finish　的不管
        if(requestCode== Reresh_RequestCode &&resultCode==Reresh_Response)
            backRefresh();
        super.onActivityResult(requestCode, resultCode, data);
    }
    public void  backRefresh(){

    };
}
