package vsn.co.jp.projectStudy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import vsn.co.jp.projectStudy.DBModel.AuthCode;
import vsn.co.jp.projectStudy.DBModel.AuthCodePkey;

@Repository
public interface AuthCodeRepository extends JpaRepository<AuthCode, AuthCodePkey> {

    @Query(value = "SELECT auth_code FROM auth_code d WHERE client_id = :client_id and user_id = :user_id", nativeQuery = true)
    public List<String> findAuthCode(String client_id, String user_id);
}
