import java.io.*;
import java.net.*;
import java.util.HashMap;

public class ServerThread extends Thread{

    private Socket socketConnection = null;
    private HashMap<String, Pair <InetAddress, Integer>> users = null;
    private int userPort;

    public ServerThread(Socket socket, HashMap<String, Pair <InetAddress, Integer>> userList, int userPort){
        this.socketConnection = socket;
        this.users = userList;
        this.userPort = userPort;
    }

    public void run(){
        
        try{

            // Open I/O buffers
            PrintWriter outPrinter = new PrintWriter(this.socketConnection.getOutputStream(), true);
            BufferedReader inReader = new BufferedReader(new InputStreamReader(this.socketConnection.getInputStream()));
            
            // Read action code
            String code = inReader.readLine();
            
            // Action for code "00" (Register user)
            if (code.equals("00")){
                outPrinter.println("Bienvenido a Tchat. Introduzca su nombre de usuario: ");
                String usrname = inReader.readLine();
                Pair usrInfo = new Pair(socketConnection.getInetAddress(), userPort);
                this.users.put(usrname, usrInfo);
                outPrinter.println(userPort);
            }
            
            // Action for code "01" (Answer connection with user IP)
            if (code.equals("01")){
                outPrinter.println("Bienvenido a Tchat. Introduzca el nombre del usuario al que se quiere conectar: ");
                String usrname = inReader.readLine();
                String address_to_send = "-1";
                while(!this.users.containsKey(usrname)){
                    outPrinter.println(address_to_send);
                    usrname = inReader.readLine();
                }
                address_to_send = this.users.get(usrname).getLeft().getHostAddress();
                int port_to_send = this.users.get(usrname).getRight();
                outPrinter.println(address_to_send);
                outPrinter.println(Integer.toString(port_to_send));
            }

            System.out.println("Cierro la conexión con el cliente");
            // socketConnection.close();
            
            }catch(IOException e){
                System.err.println("Se ha producido un error en el servidor");
            }
    }
}