
import javax.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 用于测试ServletServer，编译后的class文件放入resources/WEB-INF/classes/.
 * 注意这个没有包名
 * <br>
 * Created by muscaestar on 12/2/20
 *
 * @author muscaestar
 */
public class PrimitiveServlet implements Servlet {
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        System.out.println(this + " init called");
    }

    @Override
    public ServletConfig getServletConfig() {
        System.out.println(this + " getServletConfig called");
        return null;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        System.out.println(this + " service called");
        PrintWriter out = servletResponse.getWriter();
        out.println("message written by println");
        out.print("message written by print");

    }

    @Override
    public String getServletInfo() {
        System.out.println(this + "getServletInfo called");
        return null;
    }

    @Override
    public void destroy() {
        System.out.println(this + "destroy called");
    }
}
