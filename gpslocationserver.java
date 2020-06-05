import java.net.*;
import java.io.*;
public class gpslocationserver {

	static ServerSocket ss ;
	static Socket s ;
	static DataInputStream din;
	static DataOutputStream dout;

	static ServerSocket ss2 ;
	static Socket s2 ;
	static DataInputStream din2;
	static DataOutputStream dout2;

  public static void appendStrToFile(String fileName,
                                      String str)
   {
       try {
           BufferedWriter out = new BufferedWriter(
                  new FileWriter(fileName, true));
           out.write(str);
           out.close();
       }
       catch (IOException e) {
           System.out.println("exception occoured" + e);
       }
   }

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub

		      String msgin = "" , msgout = "";
          ss = new ServerSocket(4999);
          s = ss.accept();
          din = new DataInputStream(s.getInputStream());
          dout = new DataOutputStream(s.getOutputStream());
          BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
          msgin = din.readUTF();
          System.out.println("login request from client : " + msgin);
          msgout = "login response";
          dout.writeUTF(msgout);
          dout.flush();
          s.close();
          ss2 = new ServerSocket(5000);
          s2 = ss2.accept();
          din2 = new DataInputStream(s2.getInputStream());
          Thread t = new Thread(new Runnable(){
            public void run(){
               while(true){
                 try{
									   byte[] messagein = new byte[25];
										 din.readFully(messagein, 0, 25);
										   System.out.println(toHexString(messagein));
										   for(int i = 4; i<=9 ; i++){
											      int val = Byte.toUnsignedInt(byte[i]);
											      appendStrToFile("gpstracker.txt"," " + val + " ");
										     }
                      appendStrToFile("gpstracker.txt",'\n'  + "Latitude is:" + '\n');
											byte[] bytes = new byte[4];
											int j = 0;
											for(int i= 10 ; i<=13;i++)
											    bytes[j++] = messagein[i] ;

											double result = ByteBuffer.wrap(bytes).getInt()/1800000;
											appendStrToFile("gpstracker.txt"," " + result);
											appendStrToFile("gpstracker.txt",'\n'  + "longitude is:" + '\n');

											j = 0;
											for(int i= 14 ; i<=17;i++)
													bytes[j++] = messagein[i] ;

											result = ByteBuffer.wrap(bytes).getInt()/1800000;
                      appendStrToFile("gpstracker.txt"," " + result);

                   }catch(Exception e){
                      System.out.println("Exception occured");
                   }
               }
            }
          });
          t.start();
     }
}
