package vsn.co.jp.projectStudy.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import vsn.co.jp.projectStudy.DBModel.ClientDetail;
import vsn.co.jp.projectStudy.DBModel.ClientMaster;
import vsn.co.jp.projectStudy.DBModel.UserDetail;
import vsn.co.jp.projectStudy.DBModel.UserMaster;
import vsn.co.jp.projectStudy.model.Client;
import vsn.co.jp.projectStudy.model.LoginData;
import vsn.co.jp.projectStudy.model.LoginRequest;
import vsn.co.jp.projectStudy.model.RequestBase;
import vsn.co.jp.projectStudy.repository.AuthCodeRepository;
import vsn.co.jp.projectStudy.repository.ClientDetailRepository;
import vsn.co.jp.projectStudy.repository.ClientMasterRepository;
import vsn.co.jp.projectStudy.repository.UserDetailRepository;
import vsn.co.jp.projectStudy.repository.UserMasterRepository;
import vsn.co.jp.projectStudy.utility.TokenUtil;

@Service
public class AuthenticationService extends AbstractService {

    @Autowired
    ClientMasterRepository clientMasterRepo;

    @Autowired
    ClientDetailRepository clientDetailRepo;

    @Autowired
    UserMasterRepository userMasterRepo;

    @Autowired
    UserDetailRepository userDetailRepo;

    @Autowired
    AuthCodeRepository authCodeRepo;

    @Override
    public String preExecute(RequestBase request, Model model,
            LoginData loginData, BindingResult valid) throws Exception {

        if (StringUtils.isEmpty(loginData.getClientCode()))
            return "clientError";

        Client client = new Client();

        List<Map<String, Object>> clientNameMap = clientMasterRepo
                .findCliantNameByClientCodeIs(loginData.getClientCode());

        if (null == clientNameMap || clientNameMap.size() == 0) {
            return "clientError";
        }

        BeanUtils.populate(client, clientNameMap.get(0));

        model.addAttribute("clientData", client);

        return SUCCESS_STR;
    }

    @Override
    public String mainExecute(RequestBase request, Model model,
            LoginData loginData, BindingResult valid) throws Exception {
        LoginRequest loginRequest = (LoginRequest) request;

        List<String> users = userMasterRepo.authByIdPassIs(
                loginRequest.getUserId(),
                DigestUtils.md5Hex(loginRequest.getPassword()));

        if (null == users || users.size() == 0) {
            FieldError error = new FieldError("loginRequest", "userId", "",
                    false, null, null, "mistaken your id or password");
            valid.addError(error);
            return "login";
        }

        return SUCCESS_STR;
    }

    @Override
    public String postExecute(RequestBase request, Model model,
            LoginData loginData, BindingResult valid) throws Exception {

        Client client = (Client) model.getAttribute("clientData");

        ClientMaster clientMaster = new ClientMaster();
        clientMaster.setClient_id(client.getClientId());
        clientMaster.setClient_code(client.getClientCode());

        LoginRequest loginRequest = (LoginRequest) request;
        UserMaster user = new UserMaster();
        user.setUser_id(loginRequest.getUserId());

        ClientDetail clientDetail = clientDetailRepo
                .getOne(client.getClientId());

        String authCode = TokenUtil.createAuthToken(clientMaster, user,
                authCodeRepo);

        UserDetail userDetail = userDetailRepo.getOne(loginRequest.getUserId());
        userDetail
                .setLast_login_time(new Timestamp(System.currentTimeMillis()));
        userDetailRepo.save(userDetail);

        model.addAttribute("authCode", authCode);
        return "redirect:" + clientDetail.getRedirect_address();
    }

}
