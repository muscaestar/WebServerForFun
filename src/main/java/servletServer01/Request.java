package servletServer01;

import javax.servlet.*;
import java.io.*;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

/**
 * Created by muscaestar on 11/30/20
 *
 * @author muscaestar
 */
public class Request implements ServletRequest {

    private String uri;
    private InputStream inputStream;

    public Request() {
    }

    public Request(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public boolean parse() {
        InputStreamReader reader = new InputStreamReader(this.inputStream);
        StringBuilder sb = new StringBuilder();
        char[] chars = new char[1024];
        try {
//            int len;
//            while ((len = reader.read(chars)) > 0) {
//                sb.append(chars, 0, len);
//                System.out.println("reading");
//            }
            // 无法识别inputstream的结束，为了方便这里只读1024字节
            int len = reader.read(chars);
            sb.append(chars, 0, len);

            String reqMsg = sb.toString();
            System.out.println("reqMsg: " + reqMsg);
            parseUri(reqMsg);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isServlet() {
        if (this.uri == null) {
            return false;
        }
        return this.uri.startsWith("/servlet");
    }

    private void parseUri(String msg) throws IOException {
        int firstSp = msg.indexOf(" ");
        int secondSp;
        if (firstSp != -1) {
            secondSp = msg.indexOf(" ", firstSp + 1);
            if (secondSp != -1) {
                this.uri = msg.substring(firstSp + 1, secondSp);
                return;
            }
        }
        throw new IOException("Error in parse Uri");
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @Override
    public Object getAttribute(String s) {
        return null;
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        return null;
    }

    @Override
    public String getCharacterEncoding() {
        return null;
    }

    @Override
    public void setCharacterEncoding(String s) throws UnsupportedEncodingException {

    }

    @Override
    public int getContentLength() {
        return 0;
    }

    @Override
    public long getContentLengthLong() {
        return 0;
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return null;
    }

    @Override
    public String getParameter(String s) {
        return null;
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return null;
    }

    @Override
    public String[] getParameterValues(String s) {
        return new String[0];
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return null;
    }

    @Override
    public String getProtocol() {
        return null;
    }

    @Override
    public String getScheme() {
        return null;
    }

    @Override
    public String getServerName() {
        return null;
    }

    @Override
    public int getServerPort() {
        return 0;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return null;
    }

    @Override
    public String getRemoteAddr() {
        return null;
    }

    @Override
    public String getRemoteHost() {
        return null;
    }

    @Override
    public void setAttribute(String s, Object o) {

    }

    @Override
    public void removeAttribute(String s) {

    }

    @Override
    public Locale getLocale() {
        return null;
    }

    @Override
    public Enumeration<Locale> getLocales() {
        return null;
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public RequestDispatcher getRequestDispatcher(String s) {
        return null;
    }

    @Override
    public String getRealPath(String s) {
        return null;
    }

    @Override
    public int getRemotePort() {
        return 0;
    }

    @Override
    public String getLocalName() {
        return null;
    }

    @Override
    public String getLocalAddr() {
        return null;
    }

    @Override
    public int getLocalPort() {
        return 0;
    }

    @Override
    public ServletContext getServletContext() {
        return null;
    }

    @Override
    public AsyncContext startAsync() throws IllegalStateException {
        return null;
    }

    @Override
    public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) throws IllegalStateException {
        return null;
    }

    @Override
    public boolean isAsyncStarted() {
        return false;
    }

    @Override
    public boolean isAsyncSupported() {
        return false;
    }

    @Override
    public AsyncContext getAsyncContext() {
        return null;
    }

    @Override
    public DispatcherType getDispatcherType() {
        return null;
    }
}
