package vsn.co.jp.projectStudy.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vsn.co.jp.projectStudy.model.JsonModel;
import vsn.co.jp.projectStudy.model.RequestBase;

public abstract class AbstractJsonService {

    protected JsonModel resp;

    protected final static Logger logger = LoggerFactory
            .getLogger(AbstractService.class);

    public JsonModel execute(RequestBase request) {

        logger.info("started by" + this.toString());

        try {
            resp = cliateResp();

            if (validate(request)) {
                mainExecute(request);
            }
        } catch (Exception e) {
            // TODO ログを出す
            logger.error("不測の事態");
            logger.error(e.getMessage());
            for (StackTraceElement st : e.getStackTrace()) {
                logger.debug(st.toString());
            }
        }

        return resp;
    }

    public abstract JsonModel cliateResp() throws Exception;

    public abstract boolean validate(RequestBase request) throws Exception;

    public abstract void mainExecute(RequestBase request) throws Exception;
}
