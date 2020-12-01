package staticServer;

/**
 * Created by muscaestar on 11/30/20
 *
 * @author muscaestar
 */
public class Main {
    public static void main(String[] args) {

        HttpServer httpServer = new HttpServer();
        httpServer.await();

    }
}
