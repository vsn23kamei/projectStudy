package vsn.co.jp.projectStudy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vsn.co.jp.projectStudy.DBModel.SelectClientList;
import vsn.co.jp.projectStudy.repository.ClientListRepository;

@Service
public class ClientListService {

    @Autowired
    ClientListRepository repo;

    public List<SelectClientList> findAll() {
        return repo.findAll();
    }
}
