package pbx;

import java.io.IOException;
import java.net.ServerSocket;

public class SocketServer {
   
   public static void main(String[] args){
      //
      try {
         ServerSocket socket = new ServerSocket(9001);
         socket.accept();
         
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }
}
