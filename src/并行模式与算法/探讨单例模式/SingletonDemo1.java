package 并行模式与算法.探讨单例模式;

/**
 * @author WangXu
 * @date 2019/5/28 0028 - 15:36
 */
public class SingletonDemo1 {
    public static int STATUS=1;
    private SingletonDemo1() {
        System.out.println("SingleDemo1 is create");
    }

    private static SingletonDemo1 instance = new SingletonDemo1();

    public static SingletonDemo1 getInstance() {
        return instance;
    }
    //在任何地方访问这个STATUS的时候，都会访问该类的静态对象instance，那么就会触发构造方法
}
