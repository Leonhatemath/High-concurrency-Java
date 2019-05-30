package 并行模式与算法.网络NIO;

import javax.naming.InitialContext;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;

/**
 * @author WangXu
 * @date 2019/5/30 0030 - 11:25
 */
public class NIOClient {
    public static void main(String[] args) throws IOException {
        String ip = "localhost";
        int port = NIOMultiThreadEchoServer.port;
        for (int i = 0; i < 10; i++) {
            NIOClient nioClient = new NIOClient();
            nioClient.init(ip, port);
            nioClient.working();
        }
    }
    private Selector selector;

    public void init(String ip, int port) throws IOException {
        SocketChannel channel = SocketChannel.open();
        channel.configureBlocking(false);
        this.selector = SelectorProvider.provider().openSelector();
        channel.connect(new InetSocketAddress(ip, port));
        channel.register(selector, SelectionKey.OP_CONNECT);
    }

    public void working() throws IOException {
        while (true) {
            if (!selector.isOpen()) {
                break;
            }
            selector.select();
            Iterator<SelectionKey> ite = this.selector.selectedKeys().iterator();
            while (ite.hasNext()) {
                SelectionKey key = ite.next();
                ite.remove();
                if (key.isConnectable()) {
                    connect(key);
                } else if (key.isReadable()) {
                    read(key);
                }
            }
        }
    }

    public void connect(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        if (channel.isConnectionPending()) {
            channel.finishConnect();
        }
        channel.configureBlocking(false);
        channel.write(ByteBuffer.wrap(new String("hellow server!\r\n").getBytes()));
        channel.register(this.selector, SelectionKey.OP_READ);
    }

    public void read(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(100);
        channel.read(buffer);
        byte[] data = buffer.array();
        String msg = new String(data).trim();
        System.out.println("客户端收到信息:" + msg);
        channel.close();
        key.selector().close();
    }
}
