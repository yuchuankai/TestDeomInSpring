package FTP链接;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Set;

/**
 * @CreateTime: 2025年 01月 20日 9:17
 * @Description:
 * @Author: MR.YU
 */
public class StandardFtpHelper {

    private static final Logger LOG = LoggerFactory
            .getLogger(StandardFtpHelper.class);
    FTPClient ftpClient = null;

    public void loginFtpServer(String host, String username, String password,
                               int port, int timeout) {
        this.ftpClient = new FTPClient();
        try {
            this.ftpClient.setControlEncoding("UTF-8");
            // 不需要写死ftp server的OS TYPE,FTPClient getSystemType()方法会自动识别
            // this.ftpClient.configure(new FTPClientConfig(FTPClientConfig.SYST_UNIX));
            this.ftpClient.setDefaultTimeout(timeout);
            this.ftpClient.setConnectTimeout(timeout);
            this.ftpClient.setDataTimeout(timeout);

            // 连接登录
            this.ftpClient.connect(host, port);
            this.ftpClient.login(username, password);

            this.ftpClient.enterRemotePassiveMode();
            this.ftpClient.enterLocalPassiveMode();
            int reply = this.ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                this.ftpClient.disconnect();
                String message = String
                        .format("与ftp服务器建立连接失败,host:%s, port:%s, username:%s, replyCode:%s",
                                host, port, username, reply);
                LOG.error(message);
                throw new RuntimeException(message);
            }
        } catch (UnknownHostException e) {
            String message = String.format(
                    "请确认ftp服务器地址是否正确，无法连接到地址为: [%s] 的ftp服务器, errorMessage:%s",
                    host, e.getMessage());
            LOG.error(message);
            throw new RuntimeException(e);
        } catch (IllegalArgumentException e) {
            String message = String.format(
                    "请确认连接ftp服务器端口是否正确，错误的端口: [%s], errorMessage:%s", port,
                    e.getMessage());
            LOG.error(message);
            throw new RuntimeException(e);
        } catch (Exception e) {
            String message = String
                    .format("与ftp服务器建立连接失败,host:%s, port:%s, username:%s, errorMessage:%s",
                            host, port, username, e.getMessage());
            LOG.error(message);
            throw new RuntimeException(e);
        }

    }

    public void logoutFtpServer() {
        if (this.ftpClient.isConnected()) {
            try {
                this.ftpClient.logout();
            } catch (IOException e) {
                String message = String.format(
                        "与ftp服务器断开连接失败, errorMessage:%s", e.getMessage());
                LOG.error(message);
                throw new RuntimeException(e);
            } finally {
                if (this.ftpClient.isConnected()) {
                    try {
                        this.ftpClient.disconnect();
                    } catch (IOException e) {
                        String message = String.format(
                                "与ftp服务器断开连接失败, errorMessage:%s",
                                e.getMessage());
                        LOG.error(message);
                    }
                }
                this.ftpClient = null;
            }
        }
    }

    public void mkdir(String directoryPath) {
        String message = String.format("创建目录:%s时发生异常,请确认与ftp服务器的连接正常,拥有目录创建权限",
                directoryPath);
        try {
            this.printWorkingDirectory();
            boolean isDirExist = this.ftpClient
                    .changeWorkingDirectory(directoryPath);
            if (!isDirExist) {
                int replayCode = this.ftpClient.mkd(directoryPath);
                message = String
                        .format("%s,replayCode:%s", message, replayCode);
                if (replayCode != FTPReply.COMMAND_OK
                        && replayCode != FTPReply.PATHNAME_CREATED) {
                    throw new RuntimeException(message);
                }
            }
        } catch (IOException e) {
            message = String.format("%s, errorMessage:%s", message,
                    e.getMessage());
            LOG.error(message);
            throw new RuntimeException(e);
        }
    }

    public void mkDirRecursive(String directoryPath){
        StringBuilder dirPath = new StringBuilder();
        dirPath.append(IOUtils.DIR_SEPARATOR_UNIX);
        String[] dirSplit = StringUtils.split(directoryPath,IOUtils.DIR_SEPARATOR_UNIX);
        String message = String.format("创建目录:%s时发生异常,请确认与ftp服务器的连接正常,拥有目录创建权限", directoryPath);
        try {
            // ftp server不支持递归创建目录,只能一级一级创建
            for(String dirName : dirSplit){
                dirPath.append(dirName);
                boolean mkdirSuccess = mkDirSingleHierarchy(dirPath.toString());
                dirPath.append(IOUtils.DIR_SEPARATOR_UNIX);
                if(!mkdirSuccess){
                    throw new RuntimeException(message);
                }
            }
        } catch (IOException e) {
            message = String.format("%s, errorMessage:%s", message,
                    e.getMessage());
            LOG.error(message);
            throw new RuntimeException(message);
        }
    }

    public boolean mkDirSingleHierarchy(String directoryPath) throws IOException {
        boolean isDirExist = this.ftpClient
                .changeWorkingDirectory(directoryPath);
        // 如果directoryPath目录不存在,则创建
        if (!isDirExist) {
            int replayCode = this.ftpClient.mkd(directoryPath);
            if (replayCode != FTPReply.COMMAND_OK
                    && replayCode != FTPReply.PATHNAME_CREATED) {
                return false;
            }
        }
        return true;
    }

    public OutputStream getOutputStream(String filePath) {
        try {
            this.printWorkingDirectory();
            String parentDir = filePath.substring(0,
                    StringUtils.lastIndexOf(filePath, IOUtils.DIR_SEPARATOR_UNIX));
            this.ftpClient.changeWorkingDirectory(parentDir);
            this.printWorkingDirectory();
            OutputStream writeOutputStream = this.ftpClient
                    .appendFileStream(filePath);
            String message = String.format(
                    "打开FTP文件[%s]获取写出流时出错,请确认文件%s有权限创建，有权限写出等", filePath,
                    filePath);
            if (null == writeOutputStream) {
                throw new RuntimeException(message);
            }

            return writeOutputStream;
        } catch (IOException e) {
            String message = String.format(
                    "写出文件 : [%s] 时出错,请确认文件:[%s]存在且配置的用户有权限写, errorMessage:%s",
                    filePath, filePath, e.getMessage());
            LOG.error(message);
            throw new RuntimeException(message);
        }
    }

    public String getRemoteFileContent(String filePath) {
        try {
            this.completePendingCommand();
            this.printWorkingDirectory();
            String parentDir = filePath.substring(0,
                    StringUtils.lastIndexOf(filePath, IOUtils.DIR_SEPARATOR));
            this.ftpClient.changeWorkingDirectory(parentDir);
            this.printWorkingDirectory();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream(22);
            this.ftpClient.retrieveFile(filePath, outputStream);
            String result = outputStream.toString();
            IOUtils.closeQuietly(outputStream);
            return result;
        } catch (IOException e) {
            String message = String.format(
                    "读取文件 : [%s] 时出错,请确认文件:[%s]存在且配置的用户有权限读取, errorMessage:%s",
                    filePath, filePath, e.getMessage());
            LOG.error(message);
            throw new RuntimeException(message);
        }
    }

    public Set<String> getAllFilesInDir(String dir, String prefixFileName) {
        Set<String> allFilesWithPointedPrefix = new HashSet<String>();
        try {
            boolean isDirExist = this.ftpClient.changeWorkingDirectory(dir);
            if (!isDirExist) {
                throw new RuntimeException();
            }
            this.printWorkingDirectory();
            FTPFile[] fs = this.ftpClient.listFiles(dir);
            // LOG.debug(JSON.toJSONString(this.ftpClient.listNames(dir)));
            LOG.debug(String.format("ls: %s",
                    JSON.toJSONString(fs, SerializerFeature.UseSingleQuotes)));
            for (FTPFile ff : fs) {
                String strName = ff.getName();
                if (strName.startsWith(prefixFileName)) {
                    allFilesWithPointedPrefix.add(strName);
                }
            }
        } catch (IOException e) {
            String message = String
                    .format("获取path:[%s] 下文件列表时发生I/O异常,请确认与ftp服务器的连接正常,拥有目录ls权限, errorMessage:%s",
                            dir, e.getMessage());
            LOG.error(message);
            throw new RuntimeException(message);
        }
        return allFilesWithPointedPrefix;
    }

    public void deleteFiles(Set<String> filesToDelete) {
        String eachFile = null;
        boolean deleteOk = false;
        try {
            this.printWorkingDirectory();
            for (String each : filesToDelete) {
                LOG.info(String.format("delete file [%s].", each));
                eachFile = each;
                deleteOk = this.ftpClient.deleteFile(each);
                if (!deleteOk) {
                    String message = String.format(
                            "删除文件:[%s] 时失败,请确认指定文件有删除权限", eachFile);
                    throw new RuntimeException(message);
                }
            }
        } catch (IOException e) {
            String message = String.format(
                    "删除文件:[%s] 时发生异常,请确认指定文件有删除权限,以及网络交互正常, errorMessage:%s",
                    eachFile, e.getMessage());
            LOG.error(message);
            throw new RuntimeException(message);
        }
    }

    private void printWorkingDirectory() {
        try {
            LOG.info(String.format("current working directory:%s",
                    this.ftpClient.printWorkingDirectory()));
        } catch (Exception e) {
            LOG.warn(String.format("printWorkingDirectory error:%s",
                    e.getMessage()));
        }
    }

    public void completePendingCommand() {
        /*
         * Q:After I perform a file transfer to the server,
         * printWorkingDirectory() returns null. A:You need to call
         * completePendingCommand() after transferring the file. wiki:
         * http://wiki.apache.org/commons/Net/FrequentlyAskedQuestions
         */
        try {
            boolean isOk = this.ftpClient.completePendingCommand();
            if (!isOk) {
                throw new RuntimeException("完成ftp completePendingCommand操作发生异常");
            }
        } catch (IOException e) {
            String message = String.format(
                    "完成ftp completePendingCommand操作发生异常, errorMessage:%s",
                    e.getMessage());
            LOG.error(message);
            throw new RuntimeException(message);
        }
    }
}
