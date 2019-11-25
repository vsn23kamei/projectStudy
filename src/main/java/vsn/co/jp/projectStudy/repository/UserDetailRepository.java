package vsn.co.jp.projectStudy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vsn.co.jp.projectStudy.DBModel.UserDetail;

public interface UserDetailRepository
        extends
            JpaRepository<UserDetail, String> {

}
