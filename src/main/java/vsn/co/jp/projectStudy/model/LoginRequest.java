package vsn.co.jp.projectStudy.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class LoginRequest implements RequestBase{

    @NotEmpty
    @Pattern(regexp="[a-zA-Z0-9]+")
    private String userId;

    @NotEmpty
    @Pattern(regexp="[a-zA-Z0-9]+")
    private String password;
    
    private Client client;
}
