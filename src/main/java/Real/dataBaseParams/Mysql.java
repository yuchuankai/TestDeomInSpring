/**
 * @Project: testDeomInSpring
 * @ClassName: Mysql
 * @Date: 2024年 03月 11日 10:46
 * @version V1.0
 * @Author: MR.Yu
 * Copyright (c) All Rights Reserved, 2024.
 **/
package Real.dataBaseParams;

import Real.dataBaseParams.DatabaseParams;

import java.util.List;

/**
 * @Description:
 * @Date: 2024年 03月 11日 10:46
 * @Author: MR.Yu
 **/
public class Mysql extends DatabaseParams {

    private static final long serialVersionUID = 2332798099928474977L;

    private List<String> cat;

    private String readPosition;

    private String journalName;

    private String position;

    public List<String> getCat() {
        return cat;
    }

    public void setCat(List<String> cat) {
        this.cat = cat;
    }

    public String getReadPosition() {
        return readPosition;
    }

    public void setReadPosition(String readPosition) {
        this.readPosition = readPosition;
    }

    public String getJournalName() {
        return journalName;
    }

    public void setJournalName(String journalName) {
        this.journalName = journalName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
