package vsn.co.jp.projectStudy.DBModel;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "user_detail")
@Data
public class UserDetail {

    @Id
    private String user_id;

    private String user_name;

    private String address;

    private String tel_number;

    private String mail_address;

    private Timestamp add_timestamp;

    private Timestamp last_login_time;

    private Timestamp last_modified;
}
