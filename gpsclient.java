import java.net.*;
import java.io.*;

public class gpsclient{

	static int timeouts = 0;
  static int reboots = 0;

	public static void main(String[] args)   throws Exception {
		// TODO Auto-generated method stub

   while(true){

        System.out.println(" Reboot :" + reboots);
        timeouts = 0;
        if(reboots > 0){
          System.out.println("Time to restart the server");
          Thread.sleep(10000);
        }

	     String msgin = "timeout" , msgout = "Login request";

       Socket s = new Socket("localhost",4999);

       s.setSoTimeout(3000);

       DataInputStream din = new DataInputStream(s.getInputStream());
       DataOutputStream dout = new DataOutputStream(s.getOutputStream());

       BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	   dout.writeUTF(msgout);

     try{
      msgin = din.readUTF();
     }
     catch (SocketTimeoutException e) {
    	 timeouts++;
       System.out.println("Current timeout value is :" + timeouts);
       if(timeouts > 3)
          break;
     }
     catch(Exception e){
       System.out.println("some other exception occured");
     }
	   System.out.println("Received login response from server :" + msgin);

	   Thread.sleep(12000);



	   Socket s2 = new Socket("localhost",5000);
     s2.setSoTimeout(4000);

	   Thread gps = new Thread(new Runnable(){
	   DataInputStream din2 = new DataInputStream(s2.getInputStream());
	   DataOutputStream dout2 = new DataOutputStream(s2.getOutputStream());
		@Override
		public void run() {
			// TODO Auto-generated method stub
		  while(true){
			try {
				dout2.writeUTF("gps request");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				String msgingpsresp = din2.readUTF();
				System.out.println("from server :" + msgingpsresp);
			} catch (SocketTimeoutException e) {
				// TODO Auto-generated catch block
				timeouts++;
				System.out.println("Current timeout value is :" + timeouts);
        if(timeouts >= 3)
          break;
				//e.printStackTrace();
			}
			catch(IOException e){
				e.printStackTrace();
			}
			catch(Exception e){
				System.out.println("Some other exception occured");
			}

			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  }

		}


	   });

	   gps.start();

     gps.join();

     reboots++;

   }

	}

}
