package vsn.co.jp.projectStudy.model;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client implements RequestBase {

    private String clientId;

    @NotEmpty
    private String clientName;

    @NotEmpty
    private String clientCode;

    private boolean openFlag;

    private boolean userNameFlg;

    private boolean addressFlg;

    private boolean telNumberFlg;

    private boolean mailAddressFlg;

    private boolean addTimeStampFlg;

    private boolean lastLoginTimeFlg;

    @NotEmpty
    private String redirectUrl;

    private String memo;
}
