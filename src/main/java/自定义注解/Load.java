package 自定义注解;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @CreateTime: 2024年 12月 13日 9:44
 * @Description: 模拟依赖注入@Autowired
 * @Author: MR.YU
 */
// 只能用于字段上
@Target(ElementType.FIELD)
// 运行时有效，这样可以通过反射解析注解
@Retention(RetentionPolicy.RUNTIME)
public @interface Load {
}


