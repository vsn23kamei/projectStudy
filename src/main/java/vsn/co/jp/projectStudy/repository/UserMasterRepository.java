package vsn.co.jp.projectStudy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import vsn.co.jp.projectStudy.DBModel.UserMaster;

public interface UserMasterRepository
        extends
            JpaRepository<UserMaster, String> {
    
    @Query(value = "SELECT user_id FROM user_master WHERE user_id = :id AND password = :password", nativeQuery = true)
    public List<String> authByIdPassIs(String id, String password);


}
