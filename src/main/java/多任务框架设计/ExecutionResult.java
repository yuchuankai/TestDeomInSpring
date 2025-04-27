package 多任务框架设计;

import lombok.Getter;

/**
 * @CreateTime: 2025年 04月 25日 16:46
 * @Description:
 * @Author: MR.YU
 */
@Getter
public class ExecutionResult {

    private final boolean success;
    private final Exception error;

    private ExecutionResult(boolean success, Exception error) {
        this.success = success;
        this.error = error;
    }

    public static ExecutionResult success() {
        return new ExecutionResult(true, null);
    }

    public static ExecutionResult failure(Exception error) {
        return new ExecutionResult(false, error);
    }

}
