package vsn.co.jp.projectStudy.DBModel;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "client_detail")
@Data
public class ClientDetail {

    @Id
    private String client_id;

    private String client_name;

    private boolean open_flg;

    private boolean user_name_flg;

    private boolean address_flg;

    private boolean tel_number_flg;

    private boolean mail_address_flg;

    private boolean add_timestamp_flg;

    private boolean last_login_time_flg;

    private Timestamp add_timestamp;

    private String memo;

    private String redirect_address;
}
