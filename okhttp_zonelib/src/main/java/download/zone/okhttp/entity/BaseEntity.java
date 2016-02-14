package download.zone.okhttp.entity;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.PrimaryKey;

/**
 * Created by Zone on 2016/2/14.
 */
public class BaseEntity {
    public static final String COL_ID = "_id";
    @PrimaryKey(PrimaryKey.AssignType.AUTO_INCREMENT)
    @Column(COL_ID)
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
