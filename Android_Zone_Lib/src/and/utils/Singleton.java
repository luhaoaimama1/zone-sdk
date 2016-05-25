package and.utils;

/**
 * Singleton helper class for lazily initialization.
 */
public class Singleton {
    private static volatile Singleton instance;

    protected Singleton() {
    };

    public final Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null)
                    instance = new Singleton();
            }
        }
        return instance;
    }
}
