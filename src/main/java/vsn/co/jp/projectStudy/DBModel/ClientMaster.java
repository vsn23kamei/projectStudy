package vsn.co.jp.projectStudy.DBModel;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "client_master")
@Data
public class ClientMaster {

    @Id
    private String client_id;

    private String client_code;

}
