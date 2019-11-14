package vsn.co.jp.projectStudy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import vsn.co.jp.projectStudy.model.Message;
import vsn.co.jp.projectStudy.service.SampleService;

/**
 * <p>サンプルコントローラ</p>
 * Controllerアノテーションを使用することでコントローラが作成できます。
 *
 */
@Controller
public class SampleController {
    
    /**
     * <p>サービスクラス</p>
     * Autowiredアノテーションを使用することでインスタンスを生成できます。
     * new を使用しないことでメモリ管理をフレームワークに委任できます。
     */
    @Autowired
    SampleService sampleService;

    /**
     * <p>リクエストマッピングメソッド</p>
     * "root/sample"に対するアクションをマッピングしています。
     * RequestMapping("パス")アノテーションでパスに対するアクションが設定できます。
     * @param message 画面入力項目をモデルにマッピングしています。
     * @param model 画面に返却する値を設定します。
     * @return
     */
        @RequestMapping("sample")
        public String samleCont(@ModelAttribute("message")Message message, Model model) {
            
            // サービスクラスの登録メソッドを呼び出しています。
            sampleService.insert(message);
            
            // サービスクラスのselectメソッドを呼び出しています。
            model.addAttribute("messages", sampleService.findAll());
            
            return "sample";
        }
}