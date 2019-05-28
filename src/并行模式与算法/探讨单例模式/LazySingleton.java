package 并行模式与算法.探讨单例模式;

/**
 * @author WangXu
 * @date 2019/5/28 0028 - 15:39
 */
public class LazySingleton {
    private LazySingleton() {
        System.out.println("LazySingleton is create");
    }
    private static LazySingleton instance = null;

    public static synchronized LazySingleton getInstance() {
        if (instance == null) {
            instance = new LazySingleton();
        }
        return instance;
    }
    //明确了，只有在调用getInstance的时候才会触发构造函数，
    //但是为了防止对象被多次创建而加了锁
}
