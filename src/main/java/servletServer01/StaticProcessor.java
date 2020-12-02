package servletServer01;

/**
 * Created by muscaestar on 12/2/20
 *
 * @author muscaestar
 */
public class StaticProcessor {
    public static StaticProcessor instance;

    public static StaticProcessor getInstance() {
        if (instance == null) {
            instance = new StaticProcessor();
        }
        return instance;
    }

    private StaticProcessor() {
    }

    public void process(Request request, Response response) {
        response.sendStatic();
    }
}
