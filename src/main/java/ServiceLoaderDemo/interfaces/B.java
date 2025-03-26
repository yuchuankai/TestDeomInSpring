package ServiceLoaderDemo.interfaces;

import com.google.auto.service.AutoService;

/**
 * @CreateTime: 2025年 03月 06日 9:29
 * @Description:
 * @Author: MR.YU
 */
@AutoService(A.class)
public class B implements A{
    @Override
    public void test() {
        System.out.println("B");
    }
}
