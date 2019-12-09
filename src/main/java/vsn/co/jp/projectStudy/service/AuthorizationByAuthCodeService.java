package vsn.co.jp.projectStudy.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import vsn.co.jp.projectStudy.DBModel.AuthCodePkey;
import vsn.co.jp.projectStudy.DBModel.ClientMaster;
import vsn.co.jp.projectStudy.DBModel.UserMaster;
import vsn.co.jp.projectStudy.model.AuthRequest;
import vsn.co.jp.projectStudy.model.AuthorizationResult;
import vsn.co.jp.projectStudy.model.JsonModel;
import vsn.co.jp.projectStudy.model.RequestBase;
import vsn.co.jp.projectStudy.repository.AccessTokenRepository;
import vsn.co.jp.projectStudy.repository.AuthCodeRepository;
import vsn.co.jp.projectStudy.repository.ClientMasterRepository;
import vsn.co.jp.projectStudy.utility.TokenUtil;

@Service
public class AuthorizationByAuthCodeService extends AbstractJsonService {

    @Autowired
    ClientMasterRepository clientMaster;

    @Autowired
    AuthCodeRepository authCodeRepo;

    @Autowired
    AccessTokenRepository accessTokenRepo;

    protected String clientId = null;

    @Override
    public JsonModel cliateResp() {
        return new AuthorizationResult();
    }

    @Override
    public boolean validate(RequestBase request) {
        boolean ret = true;

        try {
            AuthRequest req = (AuthRequest) request;

            List<ClientMaster> client = clientMaster
                    .findByClientCodeIs(req.getClient_code());

            Map<String, Object> value = TokenUtil.decodeAuthCode(req.getAuth_code(),
                    client.get(0));

            clientId = client.get(0).getClient_id();

            String userId = (String) value.get(TokenUtil.USER_ID_KEY);

            String authCode = authCodeRepo.findAuthCode(clientId, userId)
                    .get(0);

            ret = req.getAuth_code().equals(authCode);

        } catch (SignatureException | MalformedJwtException
                | ExpiredJwtException e) {
            ret = false;
            ((AuthorizationResult) resp).setError("invalid_request");
            ((AuthorizationResult) resp).setState("failed");
        } catch (UnsupportedJwtException | IllegalArgumentException e) {
            ret = false;
            ((AuthorizationResult) resp).setError("invalid_request");
            ((AuthorizationResult) resp).setState("failed");
        } catch (Exception e) {
            ret = false;
            ((AuthorizationResult) resp).setError("server_error");
            ((AuthorizationResult) resp).setState("failed");
        }
        return ret;
    }

    @Override
    public void mainExecute(RequestBase request) throws Exception {
        AuthRequest req = (AuthRequest) request;

        Map<String, String> authValue = TokenUtil
                .instantDecodeJwt(req.getAuth_code());

        String userId = authValue.get("aud");

        ClientMaster client = new ClientMaster();

        client.setClient_id(clientId);
        client.setClient_code(authValue.get("sub"));

        UserMaster user = new UserMaster();
        user.setUser_id(userId);

        ((AuthorizationResult) resp).setCode(
                TokenUtil.createAccessToken(client, user, accessTokenRepo));
        ((AuthorizationResult) resp).setState("success");
        
        AuthCodePkey key = new AuthCodePkey();
        key.setUser_id(userId);
        key.setClient_id(clientId);
        
        authCodeRepo.deleteById(key);

    }

}
