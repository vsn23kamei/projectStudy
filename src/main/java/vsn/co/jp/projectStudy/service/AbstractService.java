package vsn.co.jp.projectStudy.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import vsn.co.jp.projectStudy.model.LoginData;
import vsn.co.jp.projectStudy.model.RequestBase;

public abstract class AbstractService {

    public static final String SUCCESS_STR = "success";

    public static final String FAILURE_STR = "error";

    protected final static Logger logger = LoggerFactory
            .getLogger(AbstractService.class);

    public String execute(RequestBase request, Model model, LoginData loginData,
            BindingResult valid) {
        String result = FAILURE_STR;
        
        logger.info("started by" + this.toString());

        try {

            result = preExecute(request, model, loginData, valid);

            if (!SUCCESS_STR.equals(result))
                return result;

            result = mainExecute(request, model, loginData, valid);

            if (!SUCCESS_STR.equals(result))
                return result;

            result = postExecute(request, model, loginData, valid);

        } catch (Exception e) {
            // TODO ログを出す
            logger.error("不測の事態");
            logger.error(e.getMessage());
            for (StackTraceElement st : e.getStackTrace()) {
                logger.debug(st.toString());
            }
            result = FAILURE_STR;
        }
        return result;
    }

    /**
     * 前処理
     * 
     * @param request
     * @param model
     * @param loginData
     * @param valid
     * @return
     */
    public abstract String preExecute(RequestBase request, Model model,
            LoginData loginData, BindingResult valid) throws Exception;

    /**
     * メイン処理
     * 
     * @param request
     * @param model
     * @param loginData
     * @param valid
     * @return
     */
    public abstract String mainExecute(RequestBase request, Model model,
            LoginData loginData, BindingResult valid) throws Exception;

    /**
     * 後処理
     * 
     * @param request
     * @param model
     * @param loginData
     * @param valid
     * @return
     */
    public abstract String postExecute(RequestBase request, Model model,
            LoginData loginData, BindingResult valid) throws Exception;
}
