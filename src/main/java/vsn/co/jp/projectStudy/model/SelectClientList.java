package vsn.co.jp.projectStudy.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class SelectClientList {

    @Id
    private String client_id;
    
    private String client_name;
    
    private String client_code;
}
