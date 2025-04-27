package 重写equals;

import java.util.Objects;

/**
 * @CreateTime: 2025年 04月 27日 9:34
 * @Description:
 * @Author: MR.YU
 */
public class Person {

    String name;

    public Person(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Person && this.name.equals(((Person) obj).name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
