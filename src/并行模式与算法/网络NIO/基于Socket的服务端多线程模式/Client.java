package 并行模式与算法.网络NIO.基于Socket的服务端多线程模式;

import 并行模式与算法.网络NIO.NIOMultiThreadEchoServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author WangXu
 * @date 2019/5/30 0030 - 9:02
 */
public class Client {
    public static void main(String[] args) throws IOException {
        Socket client = null;
        PrintWriter writer = null;
        BufferedReader reader = null;
        try {
            client = new Socket();
            client.connect(new InetSocketAddress("localhost", NIOMultiThreadEchoServer.port));
            writer = new PrintWriter(client.getOutputStream(), true);
            writer.println("Hellow!");
            writer.flush();

            reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            System.out.println("from server:" + reader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (writer != null) {
                writer.close();
            }
            if (reader != null) {
                reader.close();
            }
            if (client != null) {
                client.close();
            }
        }
    }
}
