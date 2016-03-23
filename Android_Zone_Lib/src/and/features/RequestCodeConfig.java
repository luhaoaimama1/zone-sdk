package and.features;

//1000以后是我用的　想用每次+1
public class RequestCodeConfig {
    //可以通过设置他  防止和别的库 请求码相同
    public static int START_CODE = 1000;
    public static int Feature_Pic__REQUESTCODE_CAMERA = START_CODE ;
    public static int Feature_Pic__REQUESTCODE_PHOTOS = START_CODE + 1;
    public static int Feature_SystemClip__REQUESTCODE_Clip = START_CODE+2;
}
