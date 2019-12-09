package vsn.co.jp.projectStudy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import vsn.co.jp.projectStudy.model.LoginData;
import vsn.co.jp.projectStudy.service.ClientListService;

/**
 * <p>クライアント一覧コントローラ</p>
 *
 */
@Controller
public class ClientListController {

    /**
     * クライアント一覧サービスクラス
     */
    @Autowired
    ClientListService service;

    @Autowired
    LoginData loginData;
    
    @RequestMapping("clientList/{authCode}")
    public String clientListCoontrol (@PathVariable String authCode, Model model) {
        
        if (!StringUtils.isEmpty(authCode)) {
            model.addAttribute("authCode", authCode);
        }
        
        service.execute(null, model, loginData, null);
        
        return "clientList";
    }
}
