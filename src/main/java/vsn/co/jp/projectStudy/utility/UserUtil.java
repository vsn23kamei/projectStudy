package vsn.co.jp.projectStudy.utility;

import java.sql.Timestamp;

import org.apache.commons.codec.digest.DigestUtils;

import vsn.co.jp.projectStudy.DBModel.UserDetail;
import vsn.co.jp.projectStudy.DBModel.UserMaster;
import vsn.co.jp.projectStudy.model.UserData;
import vsn.co.jp.projectStudy.repository.UserDetailRepository;
import vsn.co.jp.projectStudy.repository.UserMasterRepository;

public class UserUtil {

    public static void saveOrUpdateUser(UserData userData,
            UserMasterRepository masterRep, UserDetailRepository detailRep) {

        UserMaster master = new UserMaster();

        master.setUser_id(userData.getUserId());
        master.setPassword(DigestUtils.md5Hex(userData.getPassword()));

        masterRep.save(master);

        UserDetail detail = new UserDetail();

        detail.setUser_id(userData.getUserId());
        detail.setUser_name(userData.getUserName());
        detail.setAddress(userData.getAddress());
        detail.setTel_number(userData.getTelNumber());
        detail.setMail_address(userData.getMailAddress());
        detail.setAdd_timestamp(userData.getAdd_timestamp());
        detail.setLast_login_time(userData.getLast_login_time());
        detail.setLast_modified(new Timestamp(System.currentTimeMillis()));

        detailRep.save(detail);
    }

}
