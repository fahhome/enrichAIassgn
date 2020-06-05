import java.net.*;
import java.io.*;
public class gpsserver {

	static ServerSocket ss ;
	static Socket s ;
	static DataInputStream din;
	static DataOutputStream dout;

	static ServerSocket ss2 ;
	static Socket s2 ;
	static DataInputStream din2;
	static DataOutputStream dout2;


	static ServerSocket ss3 ;
	static Socket s3 ;
	static DataInputStream din3;
	static DataOutputStream dout3;

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

        //  Thread.sleep(10000); //for simulating timeout

          dout.writeUTF(msgout);
          dout.flush();
          s.close();
         // String msgin2 = "" , msgout2 = "" , msgin3 = "" , msgout3="";

          ss2 = new ServerSocket(5000);
          s2 = ss2.accept();
          din2 = new DataInputStream(s2.getInputStream());
          dout2 = new DataOutputStream(s2.getOutputStream());

          Thread gps =  new Thread(new Runnable(){

	     		@Override
		    	public void run() {
				// TODO Auto-generated method stub
		    	while(true){
			   	String msgin2 = null;
				   try {
					 msgin2 = din2.readUTF();
					// System.out.println("from client :" + msgin2);
				  } catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				 }

		          System.out.println("from client : " + msgin2);

		         String  msgout2 = "gps response";

		          try {
		        //	Thread.sleep(8000);  //for simulating timeout
					dout2.writeUTF(msgout2);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();}
			//	} catch (InterruptedException e) {
					// TODO Auto-generated catch block
			//		e.printStackTrace();
				//}

		          try {
					dout2.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			}

          });

          gps.start();

     }

}
