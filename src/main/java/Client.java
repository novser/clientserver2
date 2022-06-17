import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client extends Thread {

    private final int timeout = 1000;

    @Override
    public void run() {
        InetSocketAddress socketAddress = new InetSocketAddress(Main.getIp(), Main.getPort());
        try (final SocketChannel socketChannel = SocketChannel.open();
             Scanner scanner = new Scanner(System.in)) {
            socketChannel.connect(socketAddress);
            final ByteBuffer inputBuffer = ByteBuffer.allocate(Main.getReservedBite());
            String msg;
            while (true) {
                System.out.println("Введите сообщение для обработки или \"end\" для выхода");
                msg = scanner.nextLine();
                if ("end".equals(msg)) break;
                socketChannel.write(ByteBuffer.wrap(msg.getBytes(StandardCharsets.UTF_8)));
                Thread.sleep(timeout);
                int bytesCount = socketChannel.read(inputBuffer);
                System.out.println("Результат: " + new String(inputBuffer.array(), 0, bytesCount, StandardCharsets.UTF_8).trim());
                inputBuffer.clear();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
