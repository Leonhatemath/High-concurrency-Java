package 并行模式与算法.网络NIO;

import 并行模式与算法.网络NIO.基于Socket的服务端多线程模式.MultiThreadEchoServer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.channels.spi.*;
import java.nio.channels.spi.SelectorProvider;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author WangXu
 * @date 2019/5/30 0030 - 9:33
 */
public class NIOMultiThreadEchoServer {
    public static void main(String[] args) {
        NIOMultiThreadEchoServer NIOEchoServer = new NIOMultiThreadEchoServer();
        try {
            NIOEchoServer.startServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static final int port = 8099;
    private Selector selector;
    private ExecutorService tp = Executors.newCachedThreadPool();
    public static Map<Socket, Long> time_stat = new HashMap<>(10240);

    private void startServer() throws IOException {
        selector = SelectorProvider.provider().openSelector();
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);//设置为非阻塞模式

//        InetSocketAddress isa = new InetSocketAddress(InetAddress.getLocalHost(), port);
        InetSocketAddress isa = new InetSocketAddress(port);
        ssc.socket().bind(isa);
        //注册感兴趣的事件为accept
        SelectionKey acceptKey = ssc.register(selector, SelectionKey.OP_ACCEPT);

        for (; ; ) {
            selector.select();//没有数据就等待，有数据就返回就绪的SelectionKey的数量
            Set readyKeys = selector.selectedKeys();
            Iterator i = readyKeys.iterator();
            long e = 0;
            while (i.hasNext()) {
                SelectionKey sk = (SelectionKey) i.next();
                i.remove();

                if (sk.isAcceptable()) {
                    doAccept(sk);//客户端的接收
                } else if (sk.isValid() && sk.isReadable()) {
                    if (!time_stat.containsKey(((SocketChannel) sk.channel()).socket())) {
                        time_stat.put((((SocketChannel) sk.channel()).socket()), System.currentTimeMillis());
                    }
                    doRead(sk);//读取数据
                } else if (sk.isValid() && sk.isWritable()) {
                    doWrite(sk);//写数据
                    e = System.currentTimeMillis();
                    long b = time_stat.remove(((SocketChannel) sk.channel()).socket());
                    System.out.println("spend:" + (e - b) + "ms");

                }
            }
        }
    }

    private void doAccept(SelectionKey sk) {
        ServerSocketChannel server = (ServerSocketChannel) sk.channel();
        SocketChannel clientChannel;
        try {
            clientChannel = server.accept();//接收Server的channel(也就是输入的sk的channel)
            clientChannel.configureBlocking(false);

            //将接收的channel注册到Selector上，注册其读
            SelectionKey clientKey = clientChannel.register(selector, SelectionKey.OP_READ);
            //为这个SelectionKey分配一个EchoClient,通知客户端，你可以读了
            EchoClient echoClient = new EchoClient();
            clientKey.attach(echoClient);

            InetAddress clientAddress = clientChannel.socket().getInetAddress();
            System.out.println("Accepted connected from" + clientAddress.getHostAddress() + ".");
        } catch (IOException e) {
            System.out.println("Failed to accept new client.");
            e.printStackTrace();
        }
    }

    private void doRead(SelectionKey sk) throws IOException {
        SocketChannel channel = (SocketChannel) sk.channel();
        ByteBuffer bb = ByteBuffer.allocate(8192);
        int len;

        try {
            len = channel.read(bb);
            if (len < 0) {
                sk.cancel();
                return;
            }
        } catch (IOException e) {
            System.out.println("Failed to read from client.");
            e.printStackTrace();
            sk.cancel();
            return;
        }

        bb.flip();
        tp.execute(new HandleMsg(sk, bb));
    }

    public void doWrite(SelectionKey sk) throws IOException {
        SocketChannel channel = (SocketChannel) sk.channel();
        EchoClient echoClient = (EchoClient) sk.attachment();
        LinkedList<ByteBuffer> outq = echoClient.getOutputQueue();

        ByteBuffer bb = outq.getLast();
        try {
            int len = channel.write(bb);
            if (len == -1) {
                //disconnect(sk)，没有这个函数了
                sk.cancel();
                //channel.close()效果与上面的一样，不知道两个函数的具体区别
                return;
            }

            if (bb.remaining() == 0) {
                outq.removeLast();//这个移除也挺重要的
            }
        } catch (IOException e) {
            System.out.println("Failed to write to client.");
            e.printStackTrace();
            sk.cancel();
        }

        if (outq.size() == 0) {
            //写完了就移除写的兴趣，注册读的兴趣
            sk.interestOps(SelectionKey.OP_READ);
        }
    }

    class HandleMsg implements Runnable {
        SelectionKey sk;
        ByteBuffer bb;

        public HandleMsg(SelectionKey sk, ByteBuffer bb) {
            this.sk = sk;
            this.bb = bb;
        }

        @Override
        public void run() {
            EchoClient echoClient = (EchoClient) sk.attachment();
            //将收到的数据压入到EchoClientde的队列中
            echoClient.enqueue(bb);
            sk.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
            selector.wakeup();//让selector立即返回
        }
    }

    class EchoClient {
        private LinkedList<ByteBuffer> outq;

        EchoClient() {
            outq = new LinkedList<>();
        }

        public LinkedList<ByteBuffer> getOutputQueue() {
            return outq;
        }
        public void enqueue(ByteBuffer bb) {
            outq.addFirst(bb);
        }
    }
}
