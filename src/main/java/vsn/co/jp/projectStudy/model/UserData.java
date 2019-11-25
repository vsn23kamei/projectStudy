package vsn.co.jp.projectStudy.model;

import java.sql.Timestamp;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class UserData implements RequestBase {

    @NotEmpty
    @Pattern(regexp="[a-zA-Z0-9]+")
    private String userId;

    @NotEmpty
    @Pattern(regexp="[a-zA-Z0-9]+")
    private String password;

    @NotEmpty
    private String userName;

    @NotEmpty
    private String address;

    @NotEmpty
    @Pattern(regexp="[0-9]+")
    private String telNumber;

    @NotEmpty
    @Pattern(regexp="[a-zA-Z0-9]+@[a-zA-Z0-9]+")
    private String mailAddress;

    private Timestamp add_timestamp;

    private Timestamp last_login_time;

    private Client client;
}
