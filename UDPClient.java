
import java.net.*;
import java.io.*;
import java.util.*;
public class UDPClient {
    private DatagramSocket socket;
    private InetAddress address;

    private byte[] buf;

    public UDPClient() throws IOException {
        socket = new DatagramSocket();
        address = InetAddress.getByName("localhost");
    }

    public String sendEcho(String msg) throws IOException {
        buf = msg.getBytes();
        DatagramPacket packet
                = new DatagramPacket(buf, buf.length, address, 4445);
        socket.send(packet);
        packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);
        String received = new String(
                packet.getData(), 0, packet.getLength());

        DatagramPacket recvPacket = new DatagramPacket(buf, buf.length);//создание дейтаграммы для получения данных
        socket.receive(recvPacket);//получение дейтаграммы
        String otvet = new String(recvPacket.getData()).trim();//извлечение
//данных
        System.out.println("UDPClient: Server otvet: " + otvet);

        return received;
    }

    public void close() {
        socket.close();
    }

    public static void main(String[] args) {
        try {
            UDPClient client = new UDPClient();
            System.out.println("Введите x y z:");
            Scanner scan= new Scanner(System.in);//создание объекта client
            client.sendEcho(scan.nextLine());//вызов метода объекта client
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }

    }

}