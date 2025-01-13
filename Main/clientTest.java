import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class clientTest {
	public clientTest() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws UnknownHostException, IOException {
		Socket socket = new Socket("localhost", 21312);
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter whether landing or departure is scheduled (ld or dp): ");
		String uI = scanner.nextLine();
		scanner.close();
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		out.println(uI);
		out.close();

	}

}
