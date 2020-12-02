package servletServer01;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by muscaestar on 11/30/20
 *
 * @author muscaestar
 */
public class Response implements ServletResponse {

    public static final int BUFFER_SIZE = 1024;
    public static final String EMPTY_LINE = "\r\n";
    public static final String MSG_FILE_NOT_FOUND = "HTTP/1.1 404 File Not Found\r\n" +
            "Server: Static Server\r\n" +
            "Content-Type: text/html\r\n" +
            "Content-Length: 23\r\n" +
            EMPTY_LINE +
            "<h1>File Not Found</h1>";

    private OutputStream outputStream;
    private Request request;
    private HashMap<String, String> head = new HashMap<>();

    public Response() {
    }

    public Response(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public Response(Request request, OutputStream os) {
        this.outputStream = os;
        this.request = request;
    }

    public void sendStatic() {
        if (request == null) {
            sendServerErrorResponse("Request parsing error");
            return;
        }
        String uri = request.getUri();
        File file = new File("target/classes" + uri);
        System.out.println(file.getAbsolutePath());
        try {
            if (file.exists()) {
                sendOKResponse(file);
            } else {
                sendFileNotFoundResponse();
            }
        } catch (IOException e) {
            e.printStackTrace();
            sendServerErrorResponse(e.getMessage());
        }
    }

    private void sendOKResponse(File file) throws IOException {
        byte[] bytes = new byte[BUFFER_SIZE];
        try (FileInputStream fis = new FileInputStream(file)) {
            if (file.exists()) {
                // 写入流，响应行，响应头
                String startLine = "HTTP/1.1 200 OK\r\n";
                writeResponseHeads(startLine, file);
                // 读取文件数据
                int ch = fis.read(bytes, 0, BUFFER_SIZE);
                while (ch != -1) {
                    // 写入流，响应实体
                    this.outputStream.write(bytes, 0, ch);
                    // 读取文件数据
                    ch = fis.read(bytes, 0, BUFFER_SIZE);
                }
            } else {
                throw new IOException("File not exist");
            }
        }

    }

    private void writeResponseHeads(String startLine, File file) throws IOException {
        try {
            long length = Files.size(Paths.get(file.getAbsolutePath()));
            String type = parseType(file.getName());
            this.head.put("Server", "Static Server");
            this.head.put("Content-Type", type);
            this.head.put("Content-Length", String.valueOf(length));

            StringBuilder sb = new StringBuilder();
            sb.append(startLine);
            this.head.forEach((k,v) -> sb.append(k).append(": ").append(v).append("\r\n"));
            sb.append(EMPTY_LINE);
            this.outputStream.write(sb.toString().getBytes());
        } catch (IOException ex) {
            throw new IOException("Error when write response heads" + ex.getMessage());
        }
    }

    private String parseType(String fileName) {
        String type = fileName.substring(fileName.lastIndexOf(".")+1);
        String res;
        switch (type.toUpperCase()) {
            case "HTML":
                res = "text/html";
                break;
            case "PNG":
                res = "image/png";
                break;
            default:  // "TXT"
                res = "text/plain";
        }
        return res;
    }

    private void sendFileNotFoundResponse() throws IOException {
        try {
            this.outputStream.write(MSG_FILE_NOT_FOUND.getBytes());
        } catch (IOException ex) {
            throw new IOException("Error when write file not found response" + ex.getMessage());
        }
    }

    public void sendServerErrorResponse(String msg) {
        String responseBody = "<h1>Server Error</h1><p>"+ msg +"</p>";
        String responseMsg = "HTTP/1.1 500 Server Error\r\n" +
                "Server: Static Server\r\n" +
                "Content-Type: text/html\r\n" +
                "Content-Length: "+ responseBody.length() +"\r\n" +
                EMPTY_LINE + responseBody;
        try {
            this.outputStream.write(responseMsg.getBytes());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public String getCharacterEncoding() {
        return null;
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return null;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        PrintWriter writer = new PrintWriter(outputStream, true);
        return writer;
    }

    @Override
    public void setCharacterEncoding(String s) {

    }

    @Override
    public void setContentLength(int i) {

    }

    @Override
    public void setContentLengthLong(long l) {

    }

    @Override
    public void setContentType(String s) {

    }

    @Override
    public void setBufferSize(int i) {

    }

    @Override
    public int getBufferSize() {
        return 0;
    }

    @Override
    public void flushBuffer() throws IOException {

    }

    @Override
    public void resetBuffer() {

    }

    @Override
    public boolean isCommitted() {
        return false;
    }

    @Override
    public void reset() {

    }

    @Override
    public void setLocale(Locale locale) {

    }

    @Override
    public Locale getLocale() {
        return null;
    }
}
