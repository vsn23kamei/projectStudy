package vsn.co.jp.projectStudy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
    
    @RequestMapping("clientList")
    public String clientListCoontrol (Model mode) {
        
        return "clientList";
    }
}
