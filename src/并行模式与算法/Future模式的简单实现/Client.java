package 并行模式与算法.Future模式的简单实现;

/**
 * @author WangXu
 * @date 2019/5/30 0030 - 16:25
 */
public class Client {
    public static void main(String[] args) {
        Client client = new Client();
        Data data = client.request("name");
        System.out.println("请求完毕！");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("真实数据是：" + data.getResult());

    }
    public Data request(final String queryStr) {
        final FutureData future = new FutureData();
        new Thread() {//开启请求构造Future
            public void run() {
                RealData realData = new RealData(queryStr);
                future.setRealData(realData);
            }
        }.start();
        return future;
    }
}
