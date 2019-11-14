package vsn.co.jp.projectStudy.DBModel;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * <p>サンプルテーブルのモデルクラス</P>
 */
@Entity
@Table(name="sample_table")
@Data
public class SampleTable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    
    private String text;
}
