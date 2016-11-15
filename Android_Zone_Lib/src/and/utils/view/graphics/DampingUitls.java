package and.utils.view.graphics;

/**
 * Created by fuzhipeng on 2016/11/15.
 */
//阻尼工具类
public class DampingUitls {

    /**
     * e = 2.718281828459
     * 公式 y=-e^{-x}+1 。x>0的部分 y的值是0-1；
     * x:[0-18] y:[0-1]，那么18*x：[0-1] y:[0-1]
     * 因为下拉一般会给个最大值 maxLength， 超过这个值 就拉不动了,那么
     * 最后的映射就是 18*x/maxLength. -> x[0-maxLength], y[0-1]
     * 最后的公式  y=-e^{-x*17/maxLength}+1 -> x[0-maxLength], y[0-1]
     * @param overflowY 可以是正负值
     * @param maxLength 距离 正的值
     * @param dampingRadio [0-1]  更改曲线的~
     * @return [-1,1] 的值；
     */
    public static float damping(float overflowY, float maxLength,float dampingRadio) {
        float signum = Math.signum(overflowY);
        //主要让x一直是>=0 的部分
        double x = Math.abs(overflowY);
        //0-1， 指数：1-e;
        double eReplace = MathUtils.linear(dampingRadio,0,1,1, Math.E);
//        double dampingY = -Math.exp(-x *18/maxLength) + 1;
        double dampingY = -Math.pow(eReplace,-x *18/maxLength) + 1;
        return (float) (signum * dampingY);
    }

    public static void main(String[] args) {

        for (int i = -100; i <= 100; i++) {
            float value = DampingUitls.damping(i, 1,0.5F);
            if (value!= 1&&value!=-1)
                System.out.println("x:" + i +"\t y:"+value+ "\t value:" +value* 300);

        }
    }

}
