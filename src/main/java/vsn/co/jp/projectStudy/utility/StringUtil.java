package vsn.co.jp.projectStudy.utility;

public class StringUtil {

    public static final String CLIENT_HEAD = "C";
    
    public static String createClientId(int count) {
        
        StringBuffer sb = new StringBuffer(CLIENT_HEAD);
        
        sb.append(String.format("%05d", count));
        
        return sb.toString();
    }
    
}
