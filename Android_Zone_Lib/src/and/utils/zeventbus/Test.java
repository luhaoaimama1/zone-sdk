package and.utils.zeventbus;

/**
 * [2017] by Zone
 */

public class Test {


    public static void main(String[] args) {
        ZEventBus ga = new ZEventBus(new Test());
        ga.post(new RBQ("what?"));
    }
    public void gan(RBQ test){
        System.out.println("method:heihei---->" + test.name);
    }
    public static class RBQ{
        public String name;

        public RBQ(String name) {
            this.name = name;
        }
    }
}
