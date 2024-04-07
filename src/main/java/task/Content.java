/**
 * @Project: testDeomInSpring
 * @ClassName: Content
 * @Date: 2024年 02月 26日 15:23
 * @version V1.0
 * @Author: MR.Yu
 * Copyright (c) All Rights Reserved, 2024.
 **/
package task;

/**
 * @Description:
 * @Date: 2024年 02月 26日 15:23
 * @Author: MR.Yu
 **/
public class Content extends Task {

    private static final long serialVersionUID = 3875012010277005819L;

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
}
