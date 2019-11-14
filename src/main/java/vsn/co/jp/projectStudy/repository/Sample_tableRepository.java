package vsn.co.jp.projectStudy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vsn.co.jp.projectStudy.DBModel.SampleTable;

@Repository
public interface Sample_tableRepository extends JpaRepository<SampleTable, Integer>{

}
