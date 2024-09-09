package 监听文件夹变化;


import com.hierynomus.mssmb2.SMBApiException;
import com.hierynomus.smbj.SMBClient;
import com.hierynomus.smbj.SmbConfig;
import com.hierynomus.smbj.auth.AuthenticationContext;
import com.hierynomus.smbj.connection.Connection;
import com.hierynomus.smbj.session.Session;
import com.hierynomus.smbj.share.DiskShare;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import org.apache.commons.io.IOUtils;



import java.io.IOException;
import java.net.SocketTimeoutException;


import java.util.concurrent.TimeUnit;


/**
 * @CreateTime: 2024年 07月 16日 13:47
 * @Description:
 * @Author: MR.YU
 */
public class WinSMBClient {

    private static final Logger LOG = LoggerFactory.getLogger(WinSMBClient.class);

    private SMBClient client;
    private Session session;
    private DiskShare diskShare;

    // 连接配置
    private static final SmbConfig config = SmbConfig.builder().withTimeout(120, TimeUnit.SECONDS)
            .withTimeout(120, TimeUnit.SECONDS) // 超时设置读，写和Transact超时（默认为60秒）
            .withSoTimeout(180, TimeUnit.SECONDS) // Socket超时（默认为0秒）
            .build();

    public void loginWinSMB(String host, String username, String password,String domain, String path) {
        if (client == null) {
            client = new SMBClient(config); // 创建连接客户端
        }

        try {
            Connection connection = client.connect(host);// 执行连接

            AuthenticationContext ac = new AuthenticationContext(username, password.toCharArray(), domain);
            session = connection.authenticate(ac); // 执行权限认证
            diskShare = (DiskShare) session.connectShare(path);
        } catch (SocketTimeoutException e) {
            LOG.error("执行远程连接是失败，请检查远程地址是否正确，或远程共享是否已开启",e);
            throw new RuntimeException("执行远程连接是失败，请检查远程地址是否正确，或远程共享是否已开启");
        } catch (SMBApiException e) {
            String errMessage = e.getMessage();
            if (errMessage.contains("Could not connect to")) {
                LOG.error("请检查共享目录是否正确",e);
            }

            if (errMessage.contains("Authentication failed")) {
                LOG.error("请检查连接用户名或密码是否正确",e);
            }

            if (errMessage.contains("STATUS_OBJECT_NAME_NOT_FOUND")) {
                LOG.error("远程目录{}不存在", path, e);
            }
            throw new RuntimeException(e.getMessage());
        } catch (Exception e) {
            LOG.error("未知错误",e);
            throw new RuntimeException("未知错误"+e.getMessage());
        }

    }

    public void logoutServer() {
        if (diskShare != null) {
            try {
                diskShare.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (session != null) {
            try {
                session.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (client != null) {
            client.close();
        }
    }

    public boolean isDirExist(String directoryPath) {
        try {
            return diskShare.folderExists(directoryPath);
        } catch (Exception e) {
            if (e.getMessage().equalsIgnoreCase("no such file")) {
                String message = String.format("请确认您的配置项path:[%s]存在，且配置的用户有权限读取", directoryPath);
                LOG.error(message,e);
            }
            String message = String.format("进入目录：[%s]时发生I/O异常,请确认与服务器的连接正常", directoryPath);
            LOG.error(message,e);
            throw new RuntimeException("服务器连接异常");
        }
    }


    public boolean isFileExist(String filePath) {
        boolean isExitFlag = false;
        try {
            isExitFlag = diskShare.fileExists(filePath);
        } catch (Exception e) {
            if (e.getMessage().equalsIgnoreCase("no such file")) {
                String message = String.format("请确认您的配置项path:[%s]存在，且配置的用户有权限读取", filePath);
                LOG.error(message,e);
            } else {
                String message = String.format("获取文件：[%s] 属性时发生I/O异常,请确认服务器的连接正常", filePath);
                LOG.error(message,e);
            }
            throw new RuntimeException("服务器连接异常");
        }
        return isExitFlag;
    }

    public boolean isSymbolicLink(String filePath) {
        return false;
    }


    public static String getRegexPathParentPath(String regexPath){
        int lastDirSeparator = regexPath.lastIndexOf(IOUtils.DIR_SEPARATOR_WINDOWS);
        String parentPath = "";
        parentPath = regexPath.substring(0,lastDirSeparator + 1);
        if(parentPath.contains("*") || parentPath.contains("?")){
            throw new RuntimeException(String.format("配置项目path中：[%s]不合法，目前只支持在最后一级目录使用通配符*或者?", regexPath));
        }
        return parentPath;
    }
}
