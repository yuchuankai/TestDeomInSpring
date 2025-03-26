package ServiceLoaderDemo.interfaces;

import com.google.auto.service.AutoService;

/**
 * @CreateTime: 2025年 03月 06日 9:30
 * @Description:
 * @Author: MR.YU
 */
@AutoService(A.class)
public class C implements A{
    @Override
    public void test() {
        System.out.println("C");
    }
}
