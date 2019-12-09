package vsn.co.jp.projectStudy.model;

import lombok.Data;

@Data
public class AuthRequest implements RequestBase {

    private String client_code;

    private String auth_code;
}
