package vsn.co.jp.projectStudy.model;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class GetDataResult implements JsonModel {

    private String code;

    private String state;

    private String error;

    private String user_id;

    private String user_name;

    private String address;

    private String tel_number;

    private String mail_address;

    private Timestamp add_timestamp;

    private Timestamp last_login_time;

}
