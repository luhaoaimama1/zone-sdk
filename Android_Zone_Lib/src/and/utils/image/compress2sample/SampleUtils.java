package and.utils.image.compress2sample;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import and.LogUtil;

/**
 * Created by Administrator on 2016/4/7.
 */
public class SampleUtils {
    private final Context context;
    private String filePath;
    private Integer resourceId;
    private Integer targetWidth;
    private Integer targetHeight;
    private Bitmap.Config config= Bitmap.Config.RGB_565;
    private BitmapFactory.Options opts;
    private int inSampleSize=1;

    private static final int FitCenter=1;
    private static final int CenterCrop=2;

    private int simpleType=FitCenter;
    private boolean configChange;

    public SampleUtils(Context context) {
        this.context = context;
    }

    public static SampleUtils with(Context context) {
        return new SampleUtils(context);
    }
    public SampleUtils load(String filePath){
        this.filePath=filePath;
        return this;
    }
    public SampleUtils load(int resourceId){
        this.resourceId=resourceId;
        return this;
    }
    public SampleUtils override(int targetWidth,int targetHeight){
        this.targetWidth=targetWidth;
        this.targetHeight=targetHeight;
        return this;
    }

    public SampleUtils overrideW(int targetWidth){
        this.targetWidth=targetWidth;
        return this;
    }

    public SampleUtils overrideH(int targetHeight){
        this.targetHeight=targetHeight;
        return this;
    }


    /**
     * 默认 FitCenter
     *采样值：宽高缩放比 大的那个 既图像显示全
     */
    public SampleUtils FitCenter(){
        simpleType=FitCenter;
        return this;
    }
    /**
     * 默认 FitCenter
     *采样值：宽高缩放比 小的那个 既图像显示不全
     */
    public SampleUtils CenterCrop(){
        simpleType=CenterCrop;
        return this;
    }
    public SampleUtils config(Bitmap.Config config){
        configChange=true;
        this.config=config;
        return this;
    }

    public Bitmap bitmap(){
        Bitmap result = null;
        if(targetWidth==null&&targetHeight==null)
            result=decodeRawOps();
        else
            result=decodeOps();
        return result;
    }
    private  Bitmap decodeRawOps(){
        Bitmap result = null;
        if (configChange) {
            BitmapFactory.Options potions = new BitmapFactory.Options();
            potions.inPreferredConfig=config;
            if(filePath!=null)
                result=BitmapFactory.decodeFile(filePath,potions);
            else if(resourceId!=null)
                result=BitmapFactory.decodeResource(context.getResources(), resourceId,potions);
        }else{
            if(filePath!=null)
                result=BitmapFactory.decodeFile(filePath);
            else if(resourceId!=null)
                result=BitmapFactory.decodeResource(context.getResources(), resourceId);
        }
        return result;
    }
    private  Bitmap decodeOps(){
        calculateInSampleSize();
        opts.inSampleSize=inSampleSize;
        opts.inJustDecodeBounds = false;
        //另外，为了节约内存我们还可以使用下面的几个字段：
        opts.inPreferredConfig =config;// 默认是Bitmap.Config.ARGB_8888
        opts.inPurgeable = true;//true 内存不足可收回 再次用的时候 重新解码   false则不可收回
        opts.inInputShareable = true;//设置是否深拷贝，与inPurgeable结合使用，inPurgeable为false时，该参数无意义。
        if (filePath!=null)
            return BitmapFactory.decodeFile(filePath, opts);
        else if(resourceId!=null)
            return BitmapFactory.decodeResource(context.getResources(), resourceId, opts);
        return null;
    }
    public BitmapFactory.Options justDecodeBounds(){
        opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        if (filePath!=null)
            BitmapFactory.decodeFile(filePath, opts);
        else  if(resourceId!=null)
            BitmapFactory.decodeResource(context.getResources(), resourceId, opts);
        return opts;
    }
    public int calculateInSampleSize(){
        if(opts==null)
            justDecodeBounds();
        if (targetWidth == null&&targetHeight!= null) {
            inSampleSize = opts.outHeight / targetHeight;
            LogUtil.d("only targetHeight 的缩放比：" + inSampleSize);
        }else if (targetHeight == null&&targetWidth!= null) {
            inSampleSize = opts.outWidth / targetWidth;
            LogUtil.d("only targetWidth 的缩放比：" + inSampleSize);
        }else if(targetHeight != null&&targetWidth != null){
            float h_scale = opts.outHeight / targetHeight;
            float w_scale = opts.outWidth / targetWidth;
            LogUtil.d("横向缩放比：h_scale:" + h_scale+"\t 纵向缩放比：w_scale" + w_scale);
            if (simpleType==FitCenter) {
                inSampleSize = (int) ((h_scale > w_scale) ? h_scale : w_scale);
            }else if(simpleType==CenterCrop) {
                inSampleSize = (int) ((h_scale > w_scale) ? w_scale : h_scale);
            }
            LogUtil.d("mode:"+(simpleType==FitCenter?"FitCenter":"CenterCrop")+"的缩放比：simpleScale" + inSampleSize);
        }else{
            return inSampleSize;
        }
        if (inSampleSize <= 1)
            // 不缩放 即原图大小
            inSampleSize = 1;
        else {
            for (int i = 0; i < 4; i++) {
                if (inSampleSize < Math.pow(2, i)) {
                    inSampleSize =(int) Math.pow(2, i-1);
                    break;
                }else if(inSampleSize == Math.pow(2, i)){
                    inSampleSize=(int) Math.pow(2, i);
                    break;
                }
            }
        }
        LogUtil.d("最终缩放比：" + inSampleSize);
        return inSampleSize;
    }
}
