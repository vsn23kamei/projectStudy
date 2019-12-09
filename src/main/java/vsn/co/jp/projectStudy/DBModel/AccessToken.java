package vsn.co.jp.projectStudy.DBModel;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "access_token")
@IdClass(AuthCodePkey.class)
@Data
public class AccessToken {

    @Id
    private String user_id;

    @Id
    private String client_id;

    private String access_token;

    private Timestamp valid_datetime;

    private Timestamp last_modified;
}
