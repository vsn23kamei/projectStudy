package vsn.co.jp.projectStudy.service;

import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;

import vsn.co.jp.projectStudy.model.LoginData;
import vsn.co.jp.projectStudy.model.RequestBase;

public abstract class AbstractLoginService extends AbstractService {

    /**
     * 
     */
    @Override
    public String preExecute(RequestBase request, Model model,
            LoginData loginData, BindingResult valid) throws Exception {

        if (StringUtils.isEmpty(loginData.getAccessToken())) {
            String authCode = (String)model.getAttribute("authCode");
            if (StringUtils.isEmpty(authCode)) {
                model.addAttribute("message", "ログインしてください。");
                return "";
            }
        }

        return SUCCESS_STR;
    }
}
