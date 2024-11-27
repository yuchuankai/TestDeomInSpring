package 流应用;

import java.util.Arrays;
import java.util.List;

/**
 * @CreateTime: 2024年 09月 09日 11:00
 * @Description:
 * @Author: MR.YU
 */
public class DistinctDemo {

    static class Person{

        private String name;
        private int age;

        public Person(String name, int i) {
            this.name = name;
            this.age = i;
        }


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
    public static void main(String[] args) {
        List<Person> list = Arrays.asList(new Person("张三", 18), new Person("李四", 19), new Person("王五", 20), new Person("张三", 18));

        list.stream().map(Person::getAge).distinct().forEach(System.out::println);
    }
}
