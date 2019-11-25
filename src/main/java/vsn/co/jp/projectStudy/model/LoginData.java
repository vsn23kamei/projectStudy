package vsn.co.jp.projectStudy.model;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import lombok.Data;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Data
public class LoginData implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1009058320240600170L;

    private String clientCode;

    private String accessToken;

}
