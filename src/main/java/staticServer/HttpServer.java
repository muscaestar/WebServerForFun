package staticServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by muscaestar on 11/30/20
 *
 * @author muscaestar
 */
public class HttpServer {
    public void await() {
        try (ServerSocket serverSocket =
                     new ServerSocket(8080, 1, InetAddress.getByName("127.0.0.1"))) {
            System.out.println(serverSocket.getInetAddress().toString());
            boolean flag = true;
            while (flag) {
                try (Socket socket = serverSocket.accept()) {
                    System.out.println("Create One Socket " + socket);
                    InputStream is = socket.getInputStream();
                    OutputStream os = socket.getOutputStream();
                    Request request = new Request(is);
                    boolean parseReq = request.parse();
                    System.out.println("Parsed URI: " + request.getUri());
                    Response response;
                    if (parseReq) {
                        response = new Response(request, os);
                    } else {
                        response = new Response(os);
                    }
                    response.send();
                    System.out.println("Close One Socket " + socket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
