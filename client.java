import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.util.Scanner;

import static java.awt.Desktop.getDesktop;

/**
 * Created by Vidish on 28-03-2017.
 */
public class client {

    static String message;
    public static void main(String[] args) {
        send();
    }
    public static void send()
    {
        try{
            Socket s=new Socket("localhost",6666);
            Scanner sc = new Scanner(System.in);
            DataOutputStream dout=new DataOutputStream(s.getOutputStream());
            System.out.println("Enter a website name: ");
            message = sc.next();
            dout.writeUTF(message);
            dout.flush();
            dout.close();
            s.close();
        } catch(IOException e)
        {}
        receive();
    }

    public static void receive() {
        String str="";
        try {
            ServerSocket ss = new ServerSocket(6670);
            Socket s = ss.accept();
            DataInputStream dis = new DataInputStream(s.getInputStream());
            str = dis.readUTF();
            ss.close();
            System.out.println("IP : " + str);
        }catch (Exception e)
        {e.printStackTrace();}
        if (!str.contains("not")) {
            int n;
            do {
                System.out.println("Do you want to open the IP " + str + "?");
                System.out.println("5. Yes");
                System.out.println("6. No");
                n = new Scanner(System.in).nextInt();
                switch (n) {
                    case 5:
                        if (Desktop.isDesktopSupported()) {
                            try {
                                if (message.contains("www."))
                                    getDesktop().browse(new URI(message));
                                else
                                    getDesktop().browse(new URI("www." + message));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case 6:
                        break;
                    default:
                        System.out.println("Wrong Input");
                }
            } while (n != 5 && n != 6);
        }
        send();
    }
}
