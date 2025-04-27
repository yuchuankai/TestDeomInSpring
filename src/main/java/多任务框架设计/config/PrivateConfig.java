package 多任务框架设计.config;

import lombok.Getter;

/**
 * @CreateTime: 2025年 04月 25日 16:41
 * @Description:
 * @Author: MR.YU
 */
@Getter
public class PrivateConfig {
    private final String endpoint;

    public PrivateConfig(String endpoint) {
        this.endpoint = endpoint;
    }
}
