import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

/**
* Created by Vidish on 28-03-2017.
*/
public class mainserver {

public static void main(String[] args) {
receive();
}

public static void receive()
{
try {
ServerSocket ss = new ServerSocket(6666);
Socket s = ss.accept();
DataInputStream dis = new DataInputStream(s.getInputStream());
String str = dis.readUTF();
ss.close();
identify(str);
}catch (Exception e)
{e.printStackTrace();}
receive();
}
public static void identify(String site) throws InterruptedException {
if(site.contains(".com"))
{
System.out.println("Forwarding to SERVER 1");
TimeUnit.SECONDS.sleep(3);
forward(site,6667);
}
else if(site.contains(".org"))
{
System.out.println("Forwarding to SERVER 2");
TimeUnit.SECONDS.sleep(3);
forward(site,6668);
}
else if(site.contains(".in"))
{
System.out.println("Forwarding to SERVER 3");
TimeUnit.SECONDS.sleep(3);
forward(site,6669);
}
else
{
System.out.println("Forwarding to Miscellaneous Server");
TimeUnit.SECONDS.sleep(3);
forward(site,6660);
}
}

private static void forward(String site,int port) {
try{
Socket s = new Socket("localhost",port);
DataOutputStream dout = new DataOutputStream(s.getOutputStream());
dout.writeUTF(site);
dout.flush();
dout.close();
s.close();
} catch(IOException e)
{}
}
}