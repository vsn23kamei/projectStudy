package vsn.co.jp.projectStudy.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vsn.co.jp.projectStudy.DBModel.ClientDetail;
import vsn.co.jp.projectStudy.DBModel.ClientMaster;
import vsn.co.jp.projectStudy.DBModel.UserDetail;
import vsn.co.jp.projectStudy.model.AccessTokenRequest;
import vsn.co.jp.projectStudy.model.GetDataResult;
import vsn.co.jp.projectStudy.model.JsonModel;
import vsn.co.jp.projectStudy.model.RequestBase;
import vsn.co.jp.projectStudy.repository.ClientDetailRepository;
import vsn.co.jp.projectStudy.repository.UserDetailRepository;
import vsn.co.jp.projectStudy.utility.TokenUtil;

@Service("getData")
public class AccessTokenGetDataService extends AccessTokenAuthService {

    @Autowired
    ClientDetailRepository clientDetailRepo;

    @Autowired
    UserDetailRepository userDetailRepo;

    @Override
    public JsonModel cliateResp() throws Exception {
        return new GetDataResult();
    }

    @Override
    public void mainExecute(RequestBase request) throws Exception {

        AccessTokenRequest req = (AccessTokenRequest) request;

        List<ClientMaster> client = clientMaster
                .findByClientCodeIs(req.getClient_code());

        Map<String, Object> value = TokenUtil
                .decodeAccessToken(req.getAccessToken());

        String userId = (String) value.get(TokenUtil.USER_ID_KEY);

        Optional<ClientDetail> clientDetail = clientDetailRepo
                .findById(client.get(0).getClient_id());

        Optional<UserDetail> userDetail = userDetailRepo.findById(userId);

        ((GetDataResult) resp).setState("success");

        ((GetDataResult) resp).setUser_id(userId);

        if (clientDetail.get().isUser_name_flg())
            ((GetDataResult) resp)
                    .setUser_name(userDetail.get().getUser_name());

        if (clientDetail.get().isAddress_flg())
            ((GetDataResult) resp).setAddress(userDetail.get().getAddress());

        if (clientDetail.get().isTel_number_flg())
            ((GetDataResult) resp)
                    .setTel_number(userDetail.get().getTel_number());

        if (clientDetail.get().isMail_address_flg())
            ((GetDataResult) resp)
                    .setMail_address(userDetail.get().getMail_address());

        if (clientDetail.get().isAdd_timestamp_flg())
            ((GetDataResult) resp)
                    .setAdd_timestamp(userDetail.get().getAdd_timestamp());

        if (clientDetail.get().isLast_login_time_flg())
            ((GetDataResult) resp)
                    .setLast_login_time(userDetail.get().getLast_login_time());
    }
}
