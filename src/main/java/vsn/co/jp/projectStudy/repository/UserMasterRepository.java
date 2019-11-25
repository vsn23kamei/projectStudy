package vsn.co.jp.projectStudy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vsn.co.jp.projectStudy.DBModel.UserMaster;

public interface UserMasterRepository
        extends
            JpaRepository<UserMaster, String> {

}
