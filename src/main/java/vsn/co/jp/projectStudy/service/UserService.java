package vsn.co.jp.projectStudy.service;

import java.sql.Timestamp;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import vsn.co.jp.projectStudy.DBModel.UserDetail;
import vsn.co.jp.projectStudy.DBModel.UserMaster;
import vsn.co.jp.projectStudy.model.LoginData;
import vsn.co.jp.projectStudy.model.RequestBase;
import vsn.co.jp.projectStudy.model.UserData;
import vsn.co.jp.projectStudy.repository.UserDetailRepository;
import vsn.co.jp.projectStudy.repository.UserMasterRepository;
import vsn.co.jp.projectStudy.utility.UserUtil;

@Service
public class UserService extends AbstractLoginService {

    @Autowired
    UserMasterRepository masterRep;

    @Autowired
    UserDetailRepository detailRep;

    @Override
    public String preExecute(RequestBase request, Model model,
            LoginData loginData, BindingResult valid) throws Exception {
        UserData userData = (UserData) request;

        if (StringUtils.isEmpty(loginData.getAccessToken())) {
            Optional<UserMaster> master = masterRep
                    .findById(userData.getUserId());

            master.ifPresent(userMaster -> valid.addError(
                    new FieldError("userData", "userId", userData.getUserId(),
                            false, null, null, "Already in use")));

        } else {
            super.preExecute(request, model, loginData, valid);
        }

        if (valid.hasErrors()) {
            return "newUser";
        }

        return SUCCESS_STR;
    }

    @Override
    public String mainExecute(RequestBase request, Model model,
            LoginData loginData, BindingResult valid) throws Exception {
        UserData userData = (UserData) request;

        Optional<UserDetail> optDetail = detailRep.findById(userData.getUserId());

        UserDetail detail = optDetail.orElse(new UserDetail());

        if (null == detail.getAdd_timestamp()) {
            Timestamp now = new Timestamp(System.currentTimeMillis());

            userData.setAdd_timestamp(now);
            userData.setLast_login_time(now);
        } else {
            userData.setAdd_timestamp(detail.getAdd_timestamp());
            userData.setLast_login_time(detail.getLast_login_time());
        }

        UserUtil.saveOrUpdateUser(userData, masterRep, detailRep);

        return SUCCESS_STR;
    }

    @Override
    public String postExecute(RequestBase request, Model model,
            LoginData loginData, BindingResult valid) throws Exception {
        StringBuilder sb = new StringBuilder("redirect:/user/login/");

        sb.append(loginData.getClientCode());

        return sb.toString();
    }

}
