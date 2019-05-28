package 并行模式与算法.探讨单例模式;

/**
 * @author WangXu
 * @date 2019/5/28 0028 - 15:42
 */
public class StaticSingleton {
    private StaticSingleton() {
        System.out.println("StaticSingleton is create");
    }

    private static class SingletonHolder {
        private static StaticSingleton instance = new StaticSingleton();
    }

    public static StaticSingleton getInstance() {
        return SingletonHolder.instance;
    }
    //这样调用getInstance方法的时候才会触发构造函数
    //而且调用该方法触发的是内部类调用，无法在外部访问并初始化它
}
