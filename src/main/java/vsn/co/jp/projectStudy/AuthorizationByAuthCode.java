package vsn.co.jp.projectStudy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import vsn.co.jp.projectStudy.model.AuthRequest;
import vsn.co.jp.projectStudy.model.AuthorizationResult;
import vsn.co.jp.projectStudy.service.AuthorizationByAuthCodeService;

@Controller
public class AuthorizationByAuthCode {

    @Autowired
    AuthorizationByAuthCodeService authService;

    @RequestMapping(value = "authorization/authCode", method = {
            RequestMethod.POST})
    @ResponseBody
    public AuthorizationResult authorizationByAuthCode(
            @RequestBody AuthRequest authRequest) {
        return (AuthorizationResult) authService.execute(authRequest);
    }
}
