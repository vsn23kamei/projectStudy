package vsn.co.jp.projectStudy.model;

import lombok.Data;

@Data
public class AuthorizationResult implements JsonModel{

    private String code;

    private String state;

    private String error;
}
