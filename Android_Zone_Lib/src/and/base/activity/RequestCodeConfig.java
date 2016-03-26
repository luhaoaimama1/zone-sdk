package and.base.activity;

//1000以后是我用的　想用每次+1
public class RequestCodeConfig {
    //可以通过设置他  防止和别的库 请求码相同
    public static int START_CODE = 1000;
    public static final int Feature_Pic__REQUESTCODE_CAMERA = 0 ;
    public static final int Feature_Pic__REQUESTCODE_PHOTOS =  1;
    public static final int Feature_SystemClip__REQUESTCODE_Clip = 2;

    public static final int Reresh_RequestCode = 3;
    public static final int Reresh_Response = 4;

    public static int getRequestCode(int requestCodeConfig_Code){
        return requestCodeConfig_Code+START_CODE;
    }
    public static int getSwitchRequestCode(int requestCodeConfig_Code){
        return requestCodeConfig_Code-requestCodeConfig_Code;
    }
}
