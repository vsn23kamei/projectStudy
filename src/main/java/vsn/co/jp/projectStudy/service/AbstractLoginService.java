package vsn.co.jp.projectStudy.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;

import com.fasterxml.jackson.databind.ObjectMapper;

import vsn.co.jp.projectStudy.model.AuthorizationResult;
import vsn.co.jp.projectStudy.model.LoginData;
import vsn.co.jp.projectStudy.model.RequestBase;

public abstract class AbstractLoginService extends AbstractService {

    String AuthUrlString = "http://localhost:8080/authorization/authCode/";
    
    String AccessTokenUrlString = "http://localhost:8080/authorization/accessToken/onlyAuth/";
    /**
     * 
     */
    @Override
    public String preExecute(RequestBase request, Model model,
            LoginData loginData, BindingResult valid) throws Exception {

        
        if (StringUtils.isEmpty(loginData.getAccessToken())) {
            String authCode = (String)model.getAttribute("authCode");
            if (StringUtils.isEmpty(authCode)) {
                model.addAttribute("message", "ログインしてください。");
                return "redirect:/user/login/clientManage";
            }
            
            String authResult = postToJson(AuthUrlString, "{\"client_code\" : \"clientManage\", \"auth_code\" : \"" + authCode + "\" }");
            ObjectMapper mapper = new ObjectMapper();
            AuthorizationResult result = mapper.readValue(authResult, AuthorizationResult.class);
            if(!result.getState().equals("success")) {
                return "redirect:/user/login/clientManage";
            }
            
            loginData.setAccessToken(result.getCode());
        } else {
            String accessToken = loginData.getAccessToken();
            String accessTokenResult = postToJson(AccessTokenUrlString, "{\"client_code\" : \"clientManage\", \"accessToken\" : \"" + accessToken + "\" }");
            ObjectMapper mapper = new ObjectMapper();
            AuthorizationResult result = mapper.readValue(accessTokenResult, AuthorizationResult.class);
            if(!result.getState().equals("success")) {
                return "redirect:/user/login/clientManage";
            }
        }

        return SUCCESS_STR;
    }
    
    private String postToJson(String urlStr, String json) throws IOException {
        HttpURLConnection con = null;
        StringBuffer result = new StringBuffer();
        URL url = new URL(urlStr);
        
        con = (HttpURLConnection) url.openConnection();
        // HTTPリクエストコード
        con.setDoOutput(true);
        con.setRequestMethod("POST");
        con.setRequestProperty("Accept-Language", "jp");
        // データがJSONであること、エンコードを指定する
        con.setRequestProperty("Content-Type", "application/JSON; charset=utf-8");
        // POSTデータの長さを設定
        con.setRequestProperty("Content-Length", String.valueOf(json.length()));
        
        // リクエストのbodyにJSON文字列を書き込む
        OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());
        out.write(json);
        out.flush();
        con.connect();
        
        // HTTPレスポンスコード
        final int status = con.getResponseCode();
        if (status == HttpURLConnection.HTTP_OK) {
            // 通信に成功した
            // テキストを取得する
            final InputStream in = con.getInputStream();
            String encoding = con.getContentEncoding();
            if (null == encoding) {
                encoding = "UTF-8";
            }
            final InputStreamReader inReader = new InputStreamReader(in, encoding);
            final BufferedReader bufReader = new BufferedReader(inReader);
            String line = null;
            // 1行ずつテキストを読み込む
            while ((line = bufReader.readLine()) != null) {
                result.append(line);
            }
            bufReader.close();
            inReader.close();
            in.close();
        } else {
            // 通信が失敗した場合のレスポンスコードを表示
            return String.valueOf(status);
        }
        
        return result.toString();
    }
}
