package 多任务框架设计.config;

import lombok.Getter;

/**
 * @CreateTime: 2025年 04月 25日 16:40
 * @Description:
 * @Author: MR.YU
 */
@Getter
public class PublicConfig {

    private final String apiEndpoint;
    private final int timeout;

    public PublicConfig(String apiEndpoint, int timeout) {
        this.apiEndpoint = apiEndpoint;
        this.timeout = timeout;
    }

}
