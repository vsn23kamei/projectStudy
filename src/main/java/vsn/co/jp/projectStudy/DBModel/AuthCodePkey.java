package vsn.co.jp.projectStudy.DBModel;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class AuthCodePkey implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String user_id;

    private String client_id;

}
