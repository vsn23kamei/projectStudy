package vsn.co.jp.projectStudy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import vsn.co.jp.projectStudy.DBModel.SampleTable;
import vsn.co.jp.projectStudy.model.Message;
import vsn.co.jp.projectStudy.repository.Sample_tableRepository;

@Service
@Transactional
public class SampleService {

    // サンプルテーブルのリポジトリ
    // アクセサのようなものみたい
    @Autowired
    Sample_tableRepository sampletable;
    
    /**
     * <p>サンプルテーブルのインサートメソッド</p>
     * @param message 画面入力値
     */
    public void insert(Message message) {
        if (null != message && !StringUtils.isEmpty(message.getMessage())) {
            SampleTable insertData = new SampleTable();
            insertData.setText(message.getMessage());
            sampletable.save(insertData);
        }
    }
    
    /**
     * <p>サンプルテーブルの取得メソッド</p>
     * @return 取得レコード
     */
    public List<SampleTable> findAll(){
        return sampletable.findAll();
    }
}
