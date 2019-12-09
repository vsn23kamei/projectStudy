package vsn.co.jp.projectStudy.DBModel;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "auth_code")
@IdClass(AuthCodePkey.class)
@Data
public class AuthCode {

    @Id
    private String user_id;

    @Id
    private String client_id;

    private String auth_code;

    private Timestamp valid_datetime;

    private Timestamp last_modified;
}
