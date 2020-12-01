package staticServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by muscaestar on 11/30/20
 *
 * @author muscaestar
 */
public class Request {

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

}
