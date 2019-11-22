package vsn.co.jp.projectStudy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import vsn.co.jp.projectStudy.DBModel.ClientDetail;
import vsn.co.jp.projectStudy.DBModel.ClientMaster;
import vsn.co.jp.projectStudy.model.Client;
import vsn.co.jp.projectStudy.model.LoginData;
import vsn.co.jp.projectStudy.model.RequestBase;
import vsn.co.jp.projectStudy.repository.ClientDetailRepository;
import vsn.co.jp.projectStudy.repository.ClientMasterRepository;
import vsn.co.jp.projectStudy.utility.ClientUtil;

@Service
public class ClientDetailService extends AbstractLoginService {

    @Autowired
    ClientMasterRepository clientMaster;

    @Autowired
    ClientDetailRepository clientDetail;

    @Override
    public String mainExecute(RequestBase request, Model model,
            LoginData loginData, BindingResult valid) {
        Client client = (Client) request;

        ClientMaster master = clientMaster.getOne(client.getClientId());
        ClientDetail detail = clientDetail.getOne(client.getClientId());

        if (null == master) {
            return "redirect:/clientList";
        }

        model.addAttribute("clientData", client);
        
        try {
            client = ClientUtil.createResponseClientData(master, detail);
        } catch (Exception e) {
            return "redirect:/clientList";
        }
        
        model.addAttribute("clientData", client);

        return "clientDetail";
    }

    @Override
    public String postExecute(RequestBase request, Model model,
            LoginData loginData, BindingResult valid) {
        // NOP
        return SUCCESS_STR;
    }

}
