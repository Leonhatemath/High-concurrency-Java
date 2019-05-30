package 并行模式与算法.AIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author WangXu
 * @date 2019/5/30 0030 - 15:21
 */
public class AIOEchoServer {
    public static void main(String[] args) throws IOException, InterruptedException, TimeoutException, ExecutionException {
        new AIOEchoServer().start();
        while (true) {
            Thread.sleep(1000);
        }
    }

    public final static int PORT = 8099;
    private AsynchronousServerSocketChannel server;

    public AIOEchoServer() throws IOException {
        server = AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(PORT));
    }

    public void start() throws InterruptedException,ExecutionException,TimeoutException{
        System.out.println("Server listen on:" + PORT);
        server.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {
            final ByteBuffer buffer = ByteBuffer.allocate(1024);

            @Override
            public void failed(Throwable exc, Object attachment) {
                System.out.println("failed:" + exc);
            }

            public void completed(AsynchronousSocketChannel result, Object attachmetn) {
                System.out.println(Thread.currentThread().getName());
                Future<Integer> writeResult = null;
                try {
                    buffer.clear();
                    result.read(buffer).get(100, TimeUnit.SECONDS);
                    System.out.println(new String(buffer.array()));
                    buffer.flip();
                    writeResult = result.write(buffer);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        server.accept(null, this);
                        writeResult.get();
                        result.close();
                    } catch (Exception e) {
                        System.out.println(e.toString());
                    }
                }

            }
        });
    }
}
