package servletServer01;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by muscaestar on 12/2/20
 *
 * @author muscaestar
 */
public class HttpServer {
    public static void main(String[] args) {
        HttpServer httpServer = new HttpServer();
        httpServer.await();
    }

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
                    boolean parseSuccess = request.parse();
                    System.out.println("Parsed URI: " + request.getUri());
                    Response response;
                    if (parseSuccess) {
                        response = new Response(request, os);
                    } else {
                        response = new Response(os);
                    }
                    if (request.isServlet()) {
                        ServletProcessor servletProcessor = ServletProcessor.getInstance();
                        servletProcessor.process(request, response);
                    } else {
                        StaticProcessor staticProcessor = StaticProcessor.getInstance();
                        staticProcessor.process(request, response);
                    }
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
