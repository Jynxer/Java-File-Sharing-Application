import java.io.*;
import java.net.*;
import java.util.*;

public class FileShareMain {

	public static void main(String[] args) throws Exception {
		
		boolean stop = false;
		
		Scanner scan= new Scanner(System.in);
		
		do {
			System.out.println("Are you sending (s) or receiving (r) a file?");
			String response = scan.nextLine();
			
			if (response.equals("s")) {
				send();
				stop = true;
			} else if (response.contentEquals("r")) {
				receive();
				stop = true;
			} else {
				System.out.println("Please choose either 'r' or 's'.");
			}
		} while (stop == false);
	}
	
	public static boolean send() throws Exception {
		
		ServerSocket servSock = new ServerSocket(0202);
		System.out.println("Server has started");
		InetAddress inetAddress = InetAddress.getLocalHost();
        System.out.println("Host IP Address: " + inetAddress.getHostAddress());
		System.out.println("Server waiting for client request");
		Socket sock = servSock.accept();
		
		try {
			
			Scanner scan= new Scanner(System.in);
			System.out.println("Type the path of file to be uploaded below:");
			String path = scan.nextLine();
			
			File file = new File(path);
			FileInputStream fis = new FileInputStream(file);
			long fileLength = file.length();
			int fileLengthInt = (int)fileLength;
			//System.out.println(fileLengthInt);
			byte b[] = new byte[fileLengthInt];
			fis.read(b, 0, b.length);
			
			DataOutputStream dos = new DataOutputStream(sock.getOutputStream());
			dos.writeInt(fileLengthInt);
			
			OutputStream os = sock.getOutputStream();
			os.write(b, 0, b.length);
			
			System.out.print("Press enter once file has been transferred.");
			String temp = scan.nextLine();
			
			return(true);
			
		} catch(Exception e) {
			
			System.out.println("File does not exist.");
			return(false);
			
		}
	}
	
	public static boolean receive() throws Exception {
		
		try {
			
			Scanner scan= new Scanner(System.in);
			
			System.out.println("Enter the IP of sender: (Type 'localhost' for local transfers)");
			String ip = scan.nextLine();
			
			Socket sock = new Socket(ip, 0202);
			InputStream is = sock.getInputStream();
			System.out.println("Connection made.");
			
			System.out.print("Press enter once sender has chosen the file to be sent.");
			String temp = scan.nextLine();
			
			System.out.println("Type the path of file to be downloaded below: (include chosen file name at end)");
			String path = scan.nextLine();
			
			DataInputStream dis = new DataInputStream(sock.getInputStream());
			int fileLengthInt = dis.readInt();
			
			File file = new File(path);
			FileOutputStream fos = new FileOutputStream(file);
			byte b[] = new byte[fileLengthInt];
			is.read(b, 0, b.length);
			fos.write(b, 0, b.length);
			
			return(true);
			
		} catch(Exception e) {
			
			System.out.println("Directory does not exist.");
			return(false);
			
		}
	}
	
}
