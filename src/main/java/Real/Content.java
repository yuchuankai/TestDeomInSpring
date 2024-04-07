/**
 * @Project: testDeomInSpring
 * @ClassName: Content
 * @Date: 2024年 03月 11日 17:21
 * @version V1.0
 * @Author: MR.Yu
 * Copyright (c) All Rights Reserved, 2024.
 **/
package Real;

import org.apache.commons.lang.builder.ToStringBuilder;
import task.utils.CanalToStringStyle;

import java.io.Serializable;

/**
 * @Description:
 * @Date: 2024年 03月 11日 17:21
 * @Author: MR.Yu
 **/
public class Content implements Serializable {

    private static final long serialVersionUID = 2332798099928474910L;

    private Reader reader;

    private Writer writer;

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    public Writer getWriter() {
        return writer;
    }

    public void setWriter(Writer writer) {
        this.writer = writer;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, CanalToStringStyle.DEFAULT_STYLE);
    }
}
