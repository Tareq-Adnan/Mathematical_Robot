package classes;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Server extends Thread {
    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(12);
        Socket socket = null;
        int c = 0;
        while (true) {
            if (c > 10) {
                socket.close();
                serverSocket.close();
                System.exit(0);
                break;
            } else {
                c++;
                socket = serverSocket.accept();
                DataInputStream in = new DataInputStream(socket.getInputStream());
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                reading(socket, c,in,dos);
                System.out.println("User "+c);
            }
        }
    }

    private static void reading(Socket socket, int c,DataInputStream in,DataOutputStream dos) {
        Runnable r1 = () -> {

            while (true) {
                try {
                    String msg = in.readUTF();
                    String[] a = msg.split(",");
                    int s = a.length;

                    if(msg.equals("hi") || msg.equals("Hi")){
                        dos.writeUTF("Hi! I am Baymath\nHow can I help you?");
                    }
                    else if(msg.equals("How are you?") || msg.equals("how are you?")){
                        dos.writeUTF("I am Always Fine.\n How can I help you?");
                    }
                    else if (msg.equals("What do you can?") || msg.equals("what do you can?")){
                        dos.writeUTF("I can Do Mathematical Calculations.\nFollow the given Format (operator,value1,value2)");
                    }
                    else if (msg.equals("bye")) {

                       dos.writeUTF("bye");
                    }
                    else if(msg.equals("exit")){
                        socket.close();
                        System.exit(0);
                    }
                    else {
                        MathLab(a, s, dos);
                    }
                } catch (IOException e) {
                }
            }
        };
        new Thread(r1).start();
    }

    private static void MathLab(String[] a, int s, DataOutputStream dos) throws IOException {
        String[] operators = {"+", "-", "*", "/", "%", "root", "abs", "exp", "log", "log10", "pow", "sin", "cos", "tan", "deg", "rad"};

        if (a[0].equals("+")) {
            dos.writeUTF(String.valueOf(Integer.parseInt(a[1]) + Integer.parseInt(a[2])));
        } else if (a[0].equals("-")) {
            dos.writeUTF(String.valueOf(Integer.parseInt(a[1]) - Integer.parseInt(a[2])));
        } else if (a[0].equals("/")) {
            dos.writeUTF(String.valueOf((Double.parseDouble(a[1]) / Integer.parseInt(a[2]))));
        } else if (a[0].equals("*")) {
            dos.writeUTF(String.valueOf(Integer.parseInt(a[1]) * Integer.parseInt(a[2])));
        } else if (a[0].equals("%")) {
            dos.writeUTF(String.valueOf(Integer.parseInt(a[1]) % Integer.parseInt(a[2])));
        } else if (a[0].equals("root")) {
            dos.writeUTF(String.valueOf((Math.sqrt(Integer.parseInt(a[1])))));
        } else if (a[0].equals("abs")) {
            dos.writeUTF(String.valueOf((Math.abs(Integer.parseInt(a[1])))));
        } else if (a[0].equals("exp")) {
            dos.writeUTF(String.valueOf((Math.exp(Integer.parseInt(a[1])))));
        } else if (a[0].equals("log")) {
            dos.writeUTF(String.valueOf((Math.log(Double.parseDouble(a[1])))));
        } else if (a[0].equals("pow")) {
            dos.writeUTF(String.valueOf((Math.pow(Double.parseDouble(a[1]), Double.parseDouble(a[2])))));
        } else if (a[0].equals("log10")) {
            dos.writeUTF(String.valueOf((Math.log10(Double.parseDouble(a[1])))));
        } else if (a[0].equals("sin")) {
            dos.writeUTF(String.valueOf((Math.sin(Double.parseDouble(a[1])))));
        } else if (a[0].equals("cos")) {
            dos.writeUTF(String.valueOf((Math.cos(Double.parseDouble(a[1])))));
        } else if (a[0].equals("tan")) {
            dos.writeUTF(String.valueOf((Math.tan(Double.parseDouble(a[1])))));
        } else if (a[0].equals("deg")) {
            dos.writeUTF(String.valueOf((Math.toDegrees(Double.parseDouble(a[1])))));
        } else if (a[0].equals("rad")) {
            dos.writeUTF(String.valueOf((Math.toRadians(Double.parseDouble(a[1])))));
        } else {
            dos.writeUTF("Sorry! I can't understand!");
        }

    }
}