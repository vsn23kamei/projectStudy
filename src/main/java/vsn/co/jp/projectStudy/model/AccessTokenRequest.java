package vsn.co.jp.projectStudy.model;

import lombok.Data;

@Data
public class AccessTokenRequest implements RequestBase {

    private String client_code;

    private String accessToken;
}
