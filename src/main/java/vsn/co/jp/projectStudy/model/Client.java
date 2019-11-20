package vsn.co.jp.projectStudy.model;

import lombok.Data;

@Data
public class Client {

    private String clientName;
    
    private boolean openFlag;
    
    private boolean userNameFlg;
    
    private boolean addressFlg;
    
    private boolean telNumberFlg;
    
    private boolean mailAddressFlg;
    
    private boolean addTimeStampFlg;
    
    private boolean lastLoginTimeFlg;
    
    private String redirectUrl;
    
    private String memo;
}
