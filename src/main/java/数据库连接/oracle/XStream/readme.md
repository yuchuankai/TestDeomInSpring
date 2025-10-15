
Oracle XStream 是 Oracle 官方提供的 逻辑变更数据捕获（CDC/Streams）API，它基于 redo log 实时推送事务变更。下面我给你详细整理一下：

1. Oracle XStream API 支持版本
   版本	XStream 支持情况
   Oracle 10g	初步支持（XStream Out 10.2 开始）
   Oracle 11g	完整支持 XStream Out / In，Java API 可用
   Oracle 12c/18c/19c	完整支持，并提供增强的 Java API 和 PL/SQL API
   最新版本	XStream API 已成为 Oracle Streams 的核心组成部分，可实现实时流式推送

总结：

只要是 Oracle 11g 及以上版本，XStream API 都可以使用。

低于 11g 的版本不推荐做实时订阅。

2. XStream 的组成

Oracle XStream 分为两个方向：

XStream Out

订阅 Oracle 数据库的变更，推送给客户端。

通过 redo log → stream → 客户端。

XStream In

客户端向 Oracle 数据库推送事务变更，实现远程应用写入 Oracle。

可以做双向同步场景。

3. 如何订阅 Oracle XStream API
   步骤概览

在 Oracle 端配置 XStream Out

-- 创建捕获用户
CREATE USER xstrm_user IDENTIFIED BY password;
GRANT CONNECT, RESOURCE TO xstrm_user;
GRANT CREATE DATABASE LINK TO xstrm_user;
GRANT XSTREAM_OUT TO xstrm_user;


创建捕获发布（outbound server）

BEGIN
DBMS_XSTREAM_ADM.CREATE_OUTBOUND(
server_name => 'xstream_out_server',
schema_name => 'SCOTT',
description => 'XStream outbound server for SCOTT schema'
);
END;
/


启动 XStream Out

BEGIN
DBMS_XSTREAM_ADM.START_OUTBOUND(
server_name => 'xstream_out_server'
);
END;
/


客户端订阅

Java 客户端使用 Oracle 提供的 XStream Java API，示例：

import oracle.streams.XStreamOutClient;

XStreamOutClient client = new XStreamOutClient(
"xstream_out_server",
"xstrm_user",
"password",
"jdbc:oracle:thin:@//host:1521/orclpdb1"
);

client.connect();

while (true) {
XStreamOutClient.Event event = client.readEvent();
System.out.println("收到事件: " + event);
}

4. 注意事项

XStream 需要 redo log 和 supplemental logging 开启：

ALTER DATABASE ADD SUPPLEMENTAL LOG DATA;


客户端连接保持长连接，Oracle 会主动推送事务变更。

适合 实时同步、跨库同步、消息推送，延迟非常低。

✅ 总结

版本要求：Oracle 11g 及以上。

订阅流程：

创建捕获用户

配置 XStream Out server

开启 supplemental logging

客户端通过 XStream API 长连接订阅

XStream 是 Oracle 官方提供的 主动推送 redo log 变更的机制，非常适合实时同步数据。