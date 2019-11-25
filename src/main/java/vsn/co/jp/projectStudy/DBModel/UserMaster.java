package vsn.co.jp.projectStudy.DBModel;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "user_master")
@Data
public class UserMaster {

    @Id
    private String user_id;

    private String password;
}
