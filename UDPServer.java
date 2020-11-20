import java.net.*;
import java.io.*;
import java.util.Arrays;

public class UDPServer  {

    private void processLine(String[] strings) {

           Integer[] numbers = new Integer[strings.length];
        for(int i = 0;i < strings.length;i++)
        {
            try
            {
                numbers[i] = Integer.parseInt(strings[i]);
            }
            catch (NumberFormatException nfe)
            {
                numbers[i] = null;
            }

            System.out.println("numbers ["+i+"]: "+numbers[i]);
        }

        func(numbers);
    }
    private double f=0;
    private float func(Integer[] arr)
    {
        double x = arr[0];
        double y=arr[1];
        double z=arr[2];
        f=Math.abs(Math.pow(x,y/x)-Math.sqrt(y/x))+(y-x)*(Math.cos(y)-Math.exp(z/(y-x)))/(1+Math.pow((y-x),2));
        return (float) f;
    }

    private DatagramSocket socket;
    private boolean running;
    private byte[]buf = new byte[256];

    public UDPServer() throws IOException{
        socket = new DatagramSocket(4445);
    }

    public void runServer() throws IOException {
        running = true;

        while (running) {
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);

            InetAddress address = packet.getAddress();
            int port = packet.getPort();
            packet = new DatagramPacket(buf, buf.length, address, port);
            String received = new String(packet.getData(), 0, packet.getLength());
            System.out.println("server is working");

            String strArr[] = received.split(" ");
            processLine(strArr);
            String otvet=Double.toString(f);
            byte[] otvetik = new byte[256];
            otvetik = otvet.getBytes();

            DatagramPacket otv = new DatagramPacket(otvetik, otvetik.length, address, port);
            socket.send(otv);


            if (received.equals("end")) {
                running = false;
                continue;
            }
            socket.send(otv);
        }
        socket.close();
    }

    public static void main(String[] args) {
        try {
            UDPServer server = new UDPServer();//создание объекта server
            server.runServer();//вызов метода объекта server
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }

    }
}