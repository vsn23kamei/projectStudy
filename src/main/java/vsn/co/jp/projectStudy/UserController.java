package vsn.co.jp.projectStudy;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vsn.co.jp.projectStudy.model.Client;
import vsn.co.jp.projectStudy.model.LoginData;
import vsn.co.jp.projectStudy.model.LoginRequest;
import vsn.co.jp.projectStudy.model.UserData;
import vsn.co.jp.projectStudy.service.AuthenticationService;
import vsn.co.jp.projectStudy.service.LoginPreService;
import vsn.co.jp.projectStudy.service.UserService;

@Controller
@RequestMapping("user/")
public class UserController {

    @Autowired
    LoginPreService loginPre;

    @Autowired
    UserService userService;

    @Autowired
    AuthenticationService authService;

    @Autowired
    protected LoginData loginData;
    
    @RequestMapping("login")
    public String lostClient() {
        return "clientError";
    }

    @RequestMapping("login/{clientCode}")
    public String login(@PathVariable String clientCode, Model model) {

        if (StringUtils.isEmpty(clientCode)) {
            return "clientError";
        }

        Client client = new Client();

        client.setClientCode(clientCode);
        loginData.setClientCode(clientCode);

        return loginPre.execute(client, model, null, null);
    }

    @RequestMapping("auth")
    public String authUser(
            @Valid @ModelAttribute("loginRequest") LoginRequest loginRequest,
            BindingResult valid, RedirectAttributes attributes, Model model) {

        String redirect = authService.execute(loginRequest, model, loginData, valid);
        attributes.addAttribute("authCode", model.getAttribute("authCode"));
        return redirect;
    }

    @RequestMapping("new")
    public String newUser(Model model) {

        if (StringUtils.isEmpty(loginData.getClientCode())) {
            return "clientError";
        }

        UserData user = new UserData();

        model.addAttribute("userData", user);

        model.addAttribute("clientCode", loginData.getClientCode());

        return "newUser";
    }

    @RequestMapping("addUser")
    public String addUser(@Valid @ModelAttribute("userData") UserData userData,
            BindingResult valid, Model model) {

        if (valid.hasErrors()) {
            return "newUser";
        }

        return userService.execute(userData, model, loginData, valid);
    }

}
