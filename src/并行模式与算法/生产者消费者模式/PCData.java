package 并行模式与算法.生产者消费者模式;

/**
 * @author WangXu
 * @date 2019/5/28 0028 - 16:02
 */
public class PCData {
    private final int intData;

    public PCData(int data) {
        intData = data;
    }

    public int getData() {
        return intData;
    }

    @Override
    public String toString() {
        return "PCData{" +
                "intData=" + intData +
                '}';
    }
}
