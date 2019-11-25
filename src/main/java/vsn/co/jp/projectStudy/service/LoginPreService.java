package vsn.co.jp.projectStudy.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import vsn.co.jp.projectStudy.model.Client;
import vsn.co.jp.projectStudy.model.LoginData;
import vsn.co.jp.projectStudy.model.RequestBase;
import vsn.co.jp.projectStudy.model.UserData;
import vsn.co.jp.projectStudy.repository.ClientMasterRepository;

@Service
public class LoginPreService extends AbstractService {

    @Autowired
    ClientMasterRepository clientMaster;
    
    @Override
    public String preExecute(RequestBase request, Model model,
            LoginData loginData, BindingResult valid) throws Exception {
        // NOP
        return SUCCESS_STR;
    }

    @Override
    public String mainExecute(RequestBase request, Model model,
            LoginData loginData, BindingResult valid) throws Exception {
        Client client = (Client)request;
        UserData userData = new UserData();
        
        List<Map<String, Object>> clientNameMap = clientMaster.findCliantNameByClientCodeIs(client.getClientCode());
        
        if (null == clientNameMap || clientNameMap.size() == 0) {
            return "error";
        }
        
        BeanUtils.populate(client, clientNameMap.get(0));
        
        userData.setClient(client);
        
        model.addAttribute("userData", userData);
        
        return "login";
    }

    @Override
    public String postExecute(RequestBase request, Model model,
            LoginData loginData, BindingResult valid) throws Exception {
        // NOP
        return SUCCESS_STR;
    }

}
