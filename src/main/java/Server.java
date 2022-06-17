import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class Server extends Thread {

    @Override
    public void run() {
        try {
            ServerSocketChannel serverChannel = ServerSocketChannel.open();
            serverChannel.bind(new InetSocketAddress(Main.getIp(), Main.getPort()));
            while (true) {
                SocketChannel socketChannel = serverChannel.accept();
                final ByteBuffer inputBuffer = ByteBuffer.allocate(Main.getReservedBite());
                while (socketChannel.isConnected()) {
                    int bytesCount = socketChannel.read(inputBuffer);
                    if (bytesCount == -1) break;
                    final String msg = new String(inputBuffer.array(), 0, bytesCount, StandardCharsets.UTF_8);
                    inputBuffer.clear();
                    socketChannel.write(ByteBuffer.wrap((msg.replaceAll(" ", ""))
                            .getBytes(StandardCharsets.UTF_8)));
                }
            }
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
        }
    }
}
