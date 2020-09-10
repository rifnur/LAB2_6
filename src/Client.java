import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {

        Socket socket = null;

        try {
            socket = new Socket("localhost", 18443);
            Scanner in =  new Scanner(socket.getInputStream());

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            Scanner sc = new Scanner(System.in);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        String msg = in.nextLine();
                        System.out.println("Сервер: " + msg);
                    }
                }
            }).start();
            System.out.println("Напиши сообщение...");
            while (true) {

                String msg = sc.nextLine();
                System.out.println("Клиент: " +msg);
                out.println(msg);
                //System.out.println("Клиент:" +msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
