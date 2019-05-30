package 并行模式与算法.Future模式的简单实现;

import java.security.PublicKey;

/**
 * @author WangXu
 * @date 2019/5/30 0030 - 16:21
 */
public class RealData implements Data {
    protected final String result;

    public RealData(String para) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 10; i++) {
            sb.append(para);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        result = sb.toString();
    }

    public String getResult() {
        return result;
    }
}
