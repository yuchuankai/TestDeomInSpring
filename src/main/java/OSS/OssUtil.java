package OSS;


import com.sun.xml.internal.messaging.saaj.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 不支持高可用，ossuri地址只能指定一个，仅仅可用于测试
 * @author oscar
 *
 */
public class OssUtil {
    private static final String AUTH = "Authorization";
    private static final String DATE = "Date";
    private static final String BUCKETACL = "x-oss-acl";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String OSS_ACL = "x-oss-object-acl";

    private static String ossuri = "http://10.0.47.240:8082/oss";
//  private static String ossuri = "http://10.0.69.65:9090/oss";

    private static String accessKey = "admin";
    //	private static String accessKey = "testuser";
    //获取方式见文档4.2.3
    private static String securityKey = "ca24cc3bd847f89238ee692a4e0e6c21";
//	private static String securityKey = "aebd798a63fd99428f85048cdb8948b0";

    public static String authorization = null;

    static {
        try {
            authorization = new String(Base64.encode((accessKey + ":" + securityKey).getBytes("UTF-8")),"UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public enum Acl {
        ACL_DEFAULT, ACL_PUBLIC_READ_WRITER, ACL_PUBLIC_READ, ACL_PRIVATE
    }

    /**
     * 对应文档5.2.9。获取BucketInfo
     * @param bucket
     * @return
     * @throws Exception
     */
    public static int getBucketInfo(String bucket) throws Exception {
        String date = getDate();
        HttpURLConnection httpConnection = null;
        int code = 0;
        try {
            URL url = new URL(ossuri + "?bucketInfo&bucketName=" + URLEncoder.encode(bucket, "UTF-8"));
            httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setRequestMethod("GET");
            httpConnection.setDoInput(true);
            httpConnection.setDoOutput(true);
            httpConnection.setRequestProperty(AUTH, authorization);
            httpConnection.setRequestProperty(DATE, date);
            httpConnection.connect();

            code = httpConnection.getResponseCode();
            System.out.println("code:" + code);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (httpConnection != null)
                httpConnection.disconnect();
        }
        return code;
    }

    /**
     * 创建bucket
     * @param bucket
     * @return
     * @throws Exception
     */
    public static int putBucket(String bucket) throws Exception {
        return putBucket(bucket, (String) null);
    }

    /**
     * 创建bucket
     * @param bucket
     * @param acl
     * @return
     * @throws Exception
     */
    public static int putBucket(String bucket, String acl) throws Exception {
        String date = getDate();
        HttpURLConnection httpConnection = null;
        int code = 0;
        try {
            URL url = new URL(ossuri + "?bucketName=" + URLEncoder.encode(bucket, "UTF-8"));
            httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setRequestMethod("PUT");
            httpConnection.setDoInput(true);
            httpConnection.setDoOutput(true);
            httpConnection.setRequestProperty(AUTH, authorization);
            httpConnection.setRequestProperty(DATE, date);
            if (acl != null) {
                httpConnection.setRequestProperty(BUCKETACL, acl);
            }
            httpConnection.setFixedLengthStreamingMode(0);
            httpConnection.connect();

            code = httpConnection.getResponseCode();
            System.out.println("code:" + code);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (httpConnection != null)
                httpConnection.disconnect();
        }
        return code;
    }

    /**
     * 创建oject对象
     *
     * @param bucket
     * @param object
     * @param acl
     * @param metas
     * @param contentType
     * @param contentLength
     * @param is
     * @return
     */
    public static int putObject(String bucket, String object, Acl acl, Map<String, String> metas,
                                String contentType, long contentLength, InputStream is) throws Exception {
        String date = getDate();
        HttpURLConnection httpConnection = null;
        OutputStream os = null;
        int code = 0;
        try {
            URL url = new URL(ossuri + "/" + getObjectInUrl(object) + "?bucketName=" + URLEncoder.encode(bucket, "UTF-8"));
            httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setRequestMethod("PUT");
            httpConnection.setDoInput(true);
            httpConnection.setDoOutput(true);
            httpConnection.setRequestProperty(AUTH, authorization);
            httpConnection.setRequestProperty(DATE, date);
            httpConnection.setFixedLengthStreamingMode(contentLength);

            if (contentType != null)
                httpConnection.setRequestProperty(CONTENT_TYPE, contentType);
            if (acl != null)
                httpConnection.setRequestProperty(OSS_ACL, acl.ordinal() + "");
            if (metas != null) {
                for (Map.Entry<String, String> e : metas.entrySet()) {
                    httpConnection.setRequestProperty(e.getKey(), e.getValue());
                }
            }
            httpConnection.connect();
            os = httpConnection.getOutputStream();
            if (is != null) {
                writeData(is, os);
                os.flush();
            }

            code = httpConnection.getResponseCode();
            System.out.println("code:" + code);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (os != null && is != null)
                try {
                    is.close();
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            if (httpConnection != null)
                httpConnection.disconnect();
        }
        return code;
    }

    /**
     * 获取对象
     *
     * @param bucket
     * @param object
     * @return
     */
    public static int getObject(String bucket, String object) throws Exception {
        String date = getDate();
        HttpURLConnection httpConnection = null;
        InputStream is = null;
        int code = 0;
        try {
            URL url = new URL(ossuri + "/" + getObjectInUrl(object) + "?bucketName=" + URLEncoder.encode(bucket, "UTF-8"));
            httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setRequestMethod("GET");
            httpConnection.setDoInput(true);
            httpConnection.setDoOutput(true);
            httpConnection.setRequestProperty(AUTH, authorization);
            httpConnection.setRequestProperty(DATE, date);
            httpConnection.connect();

            code = httpConnection.getResponseCode();
            System.out.println("code:" + code);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null)
                try {
                    is.close();
                } catch (IOException ie) {
                    ie.printStackTrace();
                }
            if (httpConnection != null)
                httpConnection.disconnect();
        }
        return code;
    }

    /**
     * 获取对象
     *
     * @param bucket
     * @param object
     * @return
     */
    public static int getObject(String bucket, String object, File downFile) throws Exception {
        String date = getDate();
        HttpURLConnection httpConnection = null;
        InputStream is = null;
        FileOutputStream output = null;
        int code = 0;
        try {
            URL url = new URL(ossuri + "/" + getObjectInUrl(object) + "?bucketName=" + URLEncoder.encode(bucket, "UTF-8"));
            httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setRequestMethod("GET");
            httpConnection.setDoInput(true);
            httpConnection.setDoOutput(true);
            httpConnection.setRequestProperty(AUTH, authorization);
            httpConnection.setRequestProperty(DATE, date);
            httpConnection.connect();

            code = httpConnection.getResponseCode();
            System.out.println("code:" + code);

            if (code == 200) {
                is = httpConnection.getInputStream();
                output = new FileOutputStream(downFile);
                writeData(is, output);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null)
                try {
                    output.close();
                    is.close();
                } catch (IOException ie) {
                    ie.printStackTrace();
                }
            if (httpConnection != null)
                httpConnection.disconnect();
        }
        return code;
    }

    /**
     * 删除对象
     *
     * @param bucket
     * @param object
     */
    public static int deleteObject(String bucket, String object) throws Exception {
        String date = getDate();
        HttpURLConnection httpConnection = null;
        int code = 0;
        try {
            URL url = new URL(ossuri + "/" + getObjectInUrl(object) + "?bucketName=" + URLEncoder.encode(bucket, "UTF-8"));
            httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setRequestMethod("DELETE");
            httpConnection.setDoInput(true);
            httpConnection.setDoOutput(true);
            httpConnection.setRequestProperty(AUTH, authorization);
            httpConnection.setRequestProperty(DATE, date);
            httpConnection.connect();

            code = httpConnection.getResponseCode();
            System.out.println("code:" + code);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (httpConnection != null)
                httpConnection.disconnect();
        }
        return code;
    }

    /**
     * 获取下载地址
     * @param bucket
     * @param object
     * @return
     * @throws Exception
     */
    public static String generDownloadPath(String bucket, String object) throws Exception {
        String date = getDate();
        HttpURLConnection httpConnection = null;
        InputStream is = null;
        int code = 0;

        String objectPath = null;

        try {
            URL url = new URL(ossuri + "/generDownloadPath?bucketName=" + URLEncoder.encode(bucket, "UTF-8")+"&objectName="+ getObjectInUrl(object));
            httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setRequestMethod("GET");
            httpConnection.setDoInput(true);
            httpConnection.setDoOutput(true);
            httpConnection.setRequestProperty(AUTH, authorization);
            httpConnection.setRequestProperty(DATE, date);
            httpConnection.connect();

            code = httpConnection.getResponseCode();
            System.out.println("code:" + code);
            if (code == 200) {
                is = httpConnection.getInputStream();
                StringBuffer out = new StringBuffer();
                byte[] b = new byte[4096];
                for (int n; (n = is.read(b)) != -1;) {
                    out.append(new String(b, 0, n));
                }
                objectPath = out.toString();
                System.out.println(objectPath);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null)
                try {
                    is.close();
                } catch (IOException ie) {
                    ie.printStackTrace();
                }
            if (httpConnection != null)
                httpConnection.disconnect();
        }
        return objectPath;
    }

    /**
     * 查看用户
     * @return
     * @throws Exception
     */
    public static int getUserInfo() throws Exception {
        String date = getDate();
        HttpURLConnection httpConnection = null;
        int code = 0;
        try {
            URL url = new URL(ossuri + "?user");
            httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setRequestMethod("GET");
            httpConnection.setDoInput(true);
            httpConnection.setDoOutput(true);
            httpConnection.setRequestProperty(AUTH, authorization);
            httpConnection.setRequestProperty(DATE, date);
            httpConnection.connect();

            code = httpConnection.getResponseCode();
            System.out.println("code:" + code);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (httpConnection != null)
                httpConnection.disconnect();
        }
        return code;
    }

    private static String getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return sdf.format(date);
    }

    private static void writeData(InputStream is, OutputStream os) throws IOException {
        byte[] b = new byte[4096];
        int len = 0;
        while ((len = is.read(b)) > 0) {
            os.write(b, 0, len);
        }
    }

    private static String getObjectInUrl(String path) {
        String objectRebuild = "";
        try {
            path = path.replace('/', '|');
            objectRebuild = URLEncoder.encode(path, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return objectRebuild;
    }

    private static String appendParameter(String url, HashMap<String, String> map, boolean firstFlag) throws Exception {
        boolean isFirst = firstFlag;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (key != null && !key.equals("") && value != null ) {
                if (isFirst) {
                    url = url + "?" + key + "=" + URLEncoder.encode(value, "UTF-8");
                    isFirst = false;
                } else {
                    url = url + "&" + key + "=" + URLEncoder.encode(value, "UTF-8");
                }

            }
        }
        return url;
    }


    /**
     * 输出：code:200或code:204
     * @param args
     */
    public static void main(String[] args) {
        try {
            String bucket = "yck";
            String object = "ST-MYSQL.json";

            String pathname = "C:\\Users\\sr\\Desktop\\2025_08\\fileUp\\ST-MYSQL.json";
            File file = new File(pathname);

            String downpath = "C://Users//oscar//test.java";
            File downFile = new File(downpath);

//            putBucket(bucket);
//            getBucketInfo(bucket);
//            putObject(bucket, object, Acl.ACL_PRIVATE, null, "text/plain", file.length(), new FileInputStream(file));
//            getObject(bucket, object);
//            getObject(bucket, object, downFile);
            generDownloadPath(bucket, object);
//            getUserInfo();
//			deleteObject(bucket, object);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
