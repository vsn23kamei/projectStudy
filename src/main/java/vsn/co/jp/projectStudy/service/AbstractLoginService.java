package vsn.co.jp.projectStudy.service;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import vsn.co.jp.projectStudy.model.LoginData;
import vsn.co.jp.projectStudy.model.RequestBase;

public abstract class AbstractLoginService extends AbstractService {

    /**
     * 
     */
    @Override
    public String preExecute(RequestBase request, Model model, LoginData loginData, BindingResult valid) {
        
        // TODO ログイン状態の確認をしたい
        
        return SUCCESS_STR;
    }
}
