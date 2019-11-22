package vsn.co.jp.projectStudy.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import vsn.co.jp.projectStudy.model.LoginData;
import vsn.co.jp.projectStudy.model.RequestBase;
import vsn.co.jp.projectStudy.model.SelectClientList;
import vsn.co.jp.projectStudy.repository.ClientMasterRepository;

@Service
public class ClientListService  extends AbstractLoginService{

    @Autowired
    ClientMasterRepository repo;
    
    @Override
    public String mainExecute(RequestBase request, Model model,
            LoginData loginData, BindingResult valid) throws IllegalAccessException, InvocationTargetException {
        
        List<Map<String, Object>> clientList = repo.findAllCliant();
        
        List<SelectClientList> responseClientList = new ArrayList<SelectClientList>();
        
        for (Map<String, Object> client:clientList) {
            SelectClientList responseClient = new SelectClientList();
            BeanUtils.populate(responseClient, client);
            responseClientList.add(responseClient);
        }
        
        model.addAttribute("clientList", responseClientList);
        
        return SUCCESS_STR;
    }

    @Override
    public String postExecute(RequestBase request, Model model,
            LoginData loginData, BindingResult valid) {
        // NOP
        return SUCCESS_STR;
    }
}
