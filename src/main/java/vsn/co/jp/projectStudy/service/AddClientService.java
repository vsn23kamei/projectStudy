package vsn.co.jp.projectStudy.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import vsn.co.jp.projectStudy.DBModel.ClientMaster;
import vsn.co.jp.projectStudy.model.Client;
import vsn.co.jp.projectStudy.model.LoginData;
import vsn.co.jp.projectStudy.model.RequestBase;
import vsn.co.jp.projectStudy.repository.ClientDetailRepository;
import vsn.co.jp.projectStudy.repository.ClientMasterRepository;
import vsn.co.jp.projectStudy.utility.ClientUtil;

@Service
@Transactional
public class AddClientService extends AbstractLoginService {

    protected final static Logger logger = LoggerFactory.getLogger(AddClientService.class);
    
    @Autowired
    ClientMasterRepository clientMaster;

    @Autowired
    ClientDetailRepository clientDetail;

    /**
     * 
     */
    @Override
    public String preExecute(RequestBase request, Model model,
            LoginData loginData, BindingResult valid) throws Exception {

        super.preExecute(request, model, loginData, valid);

        Client client = (Client) request;

        if (!StringUtils.isEmpty(client.getClientId()))
            return SUCCESS_STR;

        List<ClientMaster> masters = clientMaster
                .findByClientCodeIs(client.getClientCode());
        
        String[] codes = null;
        Object[] arguments = null;

        if (null != masters && masters.size() > 0) {
            FieldError error = new FieldError("clientData", "clientCode", client.getClientId(), false, codes,arguments,"Already in use");
            valid.addError(error);
            return FAILURE_STR;
        }

        return SUCCESS_STR;
    }

    /**
     * 
     */
    @Override
    public String mainExecute(RequestBase request, Model model,
            LoginData loginData, BindingResult valid) {
        Client client = (Client) request;

        if (StringUtils.isEmpty(client.getClientId())) {
            ClientUtil.addClient(client, clientMaster, clientDetail);
        } else {
            ClientUtil.updateClient(client, clientMaster, clientDetail);
        }
        
        model.addAttribute("clientData", client);

        return SUCCESS_STR;
    }

    /**
     * 
     */
    @Override
    public String postExecute(RequestBase request, Model model,
            LoginData loginData, BindingResult valid) {
        // NOP
        return SUCCESS_STR;
    }
}
