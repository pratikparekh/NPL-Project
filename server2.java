import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Vidish on 29-03-2017.
 */
public class server2 {
    static List<Website> websiteArrayList = new ArrayList<>();
    public static void main(String[] args) {
        receive();
    }
    public static void receive(){
        try {
            ServerSocket ss = new ServerSocket(6668);
            Socket s = ss.accept();
            DataInputStream dis = new DataInputStream(s.getInputStream());
            String str = dis.readUTF();
            ss.close();
            identify(str);
        }catch (Exception e)
        {e.printStackTrace();}
        receive();
    }
    public static void send(String ip) throws InterruptedException {
        try{
            Socket s=new Socket("localhost",6670);
            DataOutputStream dout=new DataOutputStream(s.getOutputStream());
            System.out.println("Sending IP: "+ ip +" to client");
            TimeUnit.SECONDS.sleep(3);
            dout.writeUTF(ip);
            dout.flush();
            dout.close();
            s.close();
        } catch(IOException e)
        {}
        receive();
    }

    private static void identify(String site) throws InterruptedException {
        if (site.contains(".org"))
            try {
                InetAddress ip = InetAddress.getByName(site);
                if(site.contains("www."))
                {
                    site = site.substring(4);
                }
                for (int i=0; i<websiteArrayList.size(); i++)
                {
                    if(websiteArrayList.get(i).getSite().equals(site))
                    {
                        System.out.println("FETCHING IP OF SITE FROM DATABASE:");
                        print(websiteArrayList.get(i).getIp().split("\\."));
                        System.out.println("3256");
                        send(websiteArrayList.get(i).getIp());
                        return;
                    }
                }
                System.out.println("FETCHING IP OF SITE:");
                String s[] = ip.getHostAddress().split("\\.");
                print(s);
                websiteArrayList.add(new Website(site,addDot(s)));
                send(addDot(s));
            } catch (UnknownHostException e) {
                send("Unknown Host. DNS address could not be found");
            }
    }

    public static String addDot(String[] s)
    {
        String a = "";
        for (int i = 0; i < s.length ; i++)
        {
            if (i == s.length - 1)
                a = a + s[i];
            else
                a = a + s[i] + ".";
        }
        return a;
    }
    public static void print(String[] s)
    {
        for (int j = 0 ; j < 4 ; j++)
        {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.print(s[j]);
            if(j!=3)
                System.out.print(".");
        }
        System.out.println();
    }

}
