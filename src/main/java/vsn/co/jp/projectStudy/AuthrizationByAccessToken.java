package vsn.co.jp.projectStudy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import vsn.co.jp.projectStudy.model.AccessTokenRequest;
import vsn.co.jp.projectStudy.model.AuthorizationResult;
import vsn.co.jp.projectStudy.model.GetDataResult;
import vsn.co.jp.projectStudy.service.AccessTokenAuthService;
import vsn.co.jp.projectStudy.service.AccessTokenGetDataService;

@Controller
@RequestMapping(value = "authorization/accessToken/")
public class AuthrizationByAccessToken {

    @Autowired
    @Qualifier("getData")
    AccessTokenGetDataService getDataService;
    
    @Autowired
    @Qualifier("onlyAuth")
    AccessTokenAuthService accessTokenService;
    
    @RequestMapping(value = "getData", method = {
            RequestMethod.POST})
    @ResponseBody
    public GetDataResult accesstokenGetData(
            @RequestBody AccessTokenRequest accessTokenRequest) {
        return (GetDataResult) getDataService.execute(accessTokenRequest);
    }
    
    @RequestMapping(value = "onlyAuth", method = {
            RequestMethod.POST})
    @ResponseBody
    public AuthorizationResult accesstokenAuth(
            @RequestBody AccessTokenRequest accessTokenRequest) {
        return (AuthorizationResult) accessTokenService.execute(accessTokenRequest);
    }
}
