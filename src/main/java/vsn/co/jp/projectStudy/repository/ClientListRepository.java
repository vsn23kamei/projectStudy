package vsn.co.jp.projectStudy.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import vsn.co.jp.projectStudy.DBModel.SelectClientList;

@Repository
@Mapper
public interface ClientListRepository {
    
    @Select("SELECT m.client_id ,d.client_name ,m.client_code FROM client_master m , client_detail d WHERE m.CLIENT_ID = d.CLIENT_ID")
    public List<SelectClientList> findAll();

}
