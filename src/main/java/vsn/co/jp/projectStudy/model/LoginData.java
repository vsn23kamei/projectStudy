package vsn.co.jp.projectStudy.model;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Data
public class LoginData {

    private String clientCode;
    
    private String accessToken;
    
    private UserData userData;
}
