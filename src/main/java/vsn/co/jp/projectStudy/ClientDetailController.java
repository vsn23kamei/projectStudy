package vsn.co.jp.projectStudy;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import vsn.co.jp.projectStudy.model.Client;
import vsn.co.jp.projectStudy.model.LoginData;
import vsn.co.jp.projectStudy.service.AddClientService;
import vsn.co.jp.projectStudy.service.ClientDetailService;

/**
 * <p>
 * クライアント登録画面コントローラ
 * </p>
 * 
 */
@Controller
@RequestMapping("clientDetail/")
public class ClientDetailController {

    @Autowired
    AddClientService addService;

    @Autowired
    ClientDetailService detailService;

    @Autowired
    LoginData loginData;

    @RequestMapping("detaiil/{clientId}")
    public String clientDetail(@PathVariable String clientId, Model model) {

        Client client = new Client();
        client.setClientId(clientId);

        String returnURI = detailService.execute(client, model, loginData,
                null);

        return returnURI;
    }

    @RequestMapping("new/")
    public String newClient(Model model) {

        model.addAttribute("clientData", new Client());

        return "clientDetail";
    }

    @RequestMapping("addClient/")
    public String addClient(@Valid @ModelAttribute("clientData") Client client,
            BindingResult valid, Model model) {

        if (valid.hasErrors())
            return "clientDetail";

        addService.execute(client, model, loginData, valid);

        if (valid.hasErrors())
            return "clientDetail";

        return "redirect:/clientDetail/detaiil/"
                + ((Client) model.getAttribute("clientData")).getClientId();
    }

}
