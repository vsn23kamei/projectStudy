package vsn.co.jp.projectStudy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vsn.co.jp.projectStudy.DBModel.AccessToken;
import vsn.co.jp.projectStudy.DBModel.AuthCodePkey;

@Repository
public interface AccessTokenRepository extends JpaRepository<AccessToken, AuthCodePkey> {

}
