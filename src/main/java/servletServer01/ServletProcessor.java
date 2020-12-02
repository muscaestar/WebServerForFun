package servletServer01;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;

/**
 * Created by muscaestar on 12/2/20
 *
 * @author muscaestar
 */
public class ServletProcessor {

    private static ServletProcessor instance;

    public static ServletProcessor getInstance() {
        if (instance == null) {
            instance = new ServletProcessor();
        }
        return instance;
    }

    private ServletProcessor() {
    }

    public void process(Request request, Response response) {
        // 得到指定路径下的类加载器
        URL[] urls = new URL[1];
        String repository = null;
        try {
            repository = new URL("file", null, "target/classes/WEB-INF/classes/").toString();
            URLStreamHandler nullStreamHandler = null;
            urls[0] = new URL(null, repository, nullStreamHandler);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            response.sendServerErrorResponse("Servlet process error");
            return;
        }
        URLClassLoader classLoader = new URLClassLoader(urls);

        // 依据名字加载servlet类
        String uri = request.getUri();
        String servletName = uri.substring(uri.lastIndexOf("/") + 1);
        Class servletClass = null;
        Servlet servlet = null;
        RequestFacade requestFacade = new RequestFacade(request);
        ResponseFacade responseFacade = new ResponseFacade(response);
        try {
            servletClass = classLoader.loadClass(servletName);
            servlet = (Servlet) servletClass.newInstance();
            // 调用service方法
            servlet.service(requestFacade, responseFacade);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | ServletException | IOException e) {
            e.printStackTrace();
            response.sendServerErrorResponse("Load Servlet error");
            return;
        }
    }
}
