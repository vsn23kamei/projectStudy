package vsn.co.jp.projectStudy.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import vsn.co.jp.projectStudy.DBModel.ClientMaster;

@Repository
public interface ClientMasterRepository
        extends
            JpaRepository<ClientMaster, String> {

    @Query(value = "SELECT nextval('client_id')", nativeQuery = true)
    public int getNextId();

    @Query(value = "SELECT m.client_id AS client_id,d.client_name AS client_name,m.client_code AS client_code FROM client_master m , client_detail d WHERE m.CLIENT_ID = d.CLIENT_ID", nativeQuery = true)
    public List<Map<String, Object>> findAllCliant();

    @Query(value = "SELECT client_id, client_code FROM client_master WHERE client_code = :clientCode", nativeQuery = true)
    public List<ClientMaster> findByClientCodeIs(String clientCode);

    @Query(value = "SELECT m.client_id AS \"clientId\", m.client_code AS\"clientCode\", d.client_name AS \"clientName\" FROM client_master m , client_detail d WHERE m.client_id = d.client_id AND m.client_code = :clientCode", nativeQuery = true)
    public List<Map<String, Object>> findCliantNameByClientCodeIs(
            String clientCode);
}
