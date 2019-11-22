package vsn.co.jp.projectStudy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vsn.co.jp.projectStudy.DBModel.ClientDetail;

@Repository
public interface ClientDetailRepository extends JpaRepository<ClientDetail, String>{

}
