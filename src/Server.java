
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


public class Server {
    private DataInputStream in;
    private DataOutputStream out;

    public static void main(String[] args) {
        Server server = new Server();
    }

    public Server() {

        // розетка сервера
        ServerSocket server = null; // иницилизация локальной перемннной, так что пишу нолик
        // hрозетка клиента, это некий поток, который будет подключаться к серверу
        Socket socket = null;

        try {
            // порт, который будет прослушивать наш сервер
            System.out.println("Сервер запущен...");
            server = new ServerSocket(18443);
            System.out.println("Ждем подключения...");
            socket = server.accept();
            System.out.println("Клиент подключился ");

            Scanner in =  new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            Scanner sc = new Scanner(System.in);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("Отправка сообщения из сервера");
                    while (true) {
                        String msg = sc.nextLine();
                        System.out.println("Сервер: " + msg);
                        out.println(msg);
                    }
                }
            }).start();

            while (true) {
                String message = in.nextLine();
                System.out.println("Клиент: " + message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
                server.close();
                System.out.println("Соединение закрыто");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}