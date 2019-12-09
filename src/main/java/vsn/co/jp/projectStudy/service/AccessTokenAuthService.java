package vsn.co.jp.projectStudy.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import vsn.co.jp.projectStudy.DBModel.AccessToken;
import vsn.co.jp.projectStudy.DBModel.AuthCodePkey;
import vsn.co.jp.projectStudy.DBModel.ClientMaster;
import vsn.co.jp.projectStudy.model.AccessTokenRequest;
import vsn.co.jp.projectStudy.model.AuthorizationResult;
import vsn.co.jp.projectStudy.model.JsonModel;
import vsn.co.jp.projectStudy.model.RequestBase;
import vsn.co.jp.projectStudy.repository.AccessTokenRepository;
import vsn.co.jp.projectStudy.repository.ClientMasterRepository;
import vsn.co.jp.projectStudy.utility.TokenUtil;

@Service("onlyAuth")
public class AccessTokenAuthService extends AbstractJsonService {

    @Autowired
    ClientMasterRepository clientMaster;

    @Autowired
    AccessTokenRepository accessTokenRepo;

    @Override
    public JsonModel cliateResp() throws Exception {
        return new AuthorizationResult();
    }

    @Override
    public boolean validate(RequestBase request) throws Exception {
        boolean ret = true;

        try {
            AccessTokenRequest req = (AccessTokenRequest) request;
            List<ClientMaster> client = clientMaster
                    .findByClientCodeIs(req.getClient_code());

            Map<String, Object> value = TokenUtil
                    .decodeAccessToken(req.getAccessToken());

            String clientId = client.get(0).getClient_id();

            String userId = (String) value.get(TokenUtil.USER_ID_KEY);

            AuthCodePkey key = new AuthCodePkey();
            key.setUser_id(userId);
            key.setClient_id(clientId);

            Optional<AccessToken> accessTokenOpt = accessTokenRepo
                    .findById(key);

            if (!accessTokenOpt.isPresent() || !req.getAccessToken()
                    .equals(accessTokenOpt.get().getAccess_token())) {
                ret = false;
                ((AuthorizationResult) resp).setError("invalid_request");
                ((AuthorizationResult) resp).setState("failed");
            }

        } catch (SignatureException | MalformedJwtException
                | ExpiredJwtException | UnsupportedJwtException
                | IllegalArgumentException e) {
            ret = false;
            ((AuthorizationResult) resp).setError("invalid_request");
            ((AuthorizationResult) resp).setState("failed");
        } catch (Exception e) {
            ret = false;
            ((AuthorizationResult) resp).setError("server_error");
            ((AuthorizationResult) resp).setState("failed");
            logger.debug(e.getLocalizedMessage());
        }

        return ret;
    }

    @Override
    public void mainExecute(RequestBase request) throws Exception {

        ((AuthorizationResult) resp).setState("success");
    }

}
