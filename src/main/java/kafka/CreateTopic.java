package kafka;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.config.TopicConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @CreateTime: 2025年 03月 25日 14:53
 * @Description: 创建topic以及分区
 * @Author: MR.YU
 */
public class CreateTopic {

    private static final String BOOTSTRAP_SERVERS = "10.0.47.74:9092";

    private static final String TOPIC_NAME = "test_max";

    private static final int PARTITION_NUM = 1; // 分区数量

    private static final int REPLICATION_FACTOR = 1; // 副本数量

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        try (AdminClient adminClient = createAdminClient()) {
            if (!topicExists(adminClient, TOPIC_NAME)) {
                createTopic(adminClient, TOPIC_NAME, PARTITION_NUM, (short) REPLICATION_FACTOR);
            } else {
                System.out.println("Topic " + TOPIC_NAME + " already exists.");
            }

            int num = 10;
            final CountDownLatch countDownLatch = new CountDownLatch(num);
            Properties properties = setProducerConfig();

            Producer<String, String> producer = new KafkaProducer<>(properties);

            for (int i = 0; i < num; i++) {
                sendMessage(producer, setMessage("id" + i, new String(new char[1000]).replace('\0', 'a')), countDownLatch);
            }

            countDownLatch.await(5, TimeUnit.SECONDS);
            producer.close();
        }
    }

    private static AdminClient createAdminClient() {
        Properties config = new Properties();
        config.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        return AdminClient.create(config);
    }

    private static boolean topicExists(AdminClient adminClient, String topicName) throws ExecutionException, InterruptedException {
        ListTopicsResult topics = adminClient.listTopics();
        Set<String> existingTopics = null; // 10秒超时
        try {
            existingTopics = topics.names().get(10, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
        return existingTopics.contains(topicName);
    }

    private static void createTopic(AdminClient adminClient, String topicName, int numPartitions, short replicationFactor) throws ExecutionException, InterruptedException {
        NewTopic newTopic = new NewTopic(topicName, numPartitions, replicationFactor).configs(setTopicConfig());
        adminClient.createTopics(Collections.singletonList(newTopic)).all().get(); // 阻塞直到完成
        System.out.println("Topic " + topicName + " created successfully.");
    }


    private static Map<String, String> setTopicConfig() {
        // 配置参数（可选）
        Map<String, String> topicConfigs = new HashMap<>();
        topicConfigs.put(TopicConfig.RETENTION_MS_CONFIG, "604800000");       // 保留时间（7天）
        topicConfigs.put(TopicConfig.RETENTION_BYTES_CONFIG, String.valueOf(1024 * 10));  // 单位byte
        topicConfigs.put(TopicConfig.SEGMENT_BYTES_CONFIG, String.valueOf(1024 * 10));  // 单位byte
        topicConfigs.put(TopicConfig.CLEANUP_POLICY_CONFIG, "delete");       // 清理策略（delete/compact）
        topicConfigs.put(TopicConfig.MIN_CLEANABLE_DIRTY_RATIO_CONFIG, "0.0");

        return topicConfigs;
    }

    private static Properties setProducerConfig() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        //把发送的key从字符串序列化为字节数组
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        //把发送消息value从字符串序列化为字节数组
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

         /*
         发出消息持久化机制参数
        （1）acks=0： 表示producer不需要等待任何broker确认收到消息的回复，就可以继续发送下一条消息。性能最高，但是最容易丢消息。
        （2）acks=1： 至少要等待leader已经成功将数据写入本地log，但是不需要等待所有follower是否成功写入。就可以继续发送下一条消息。这种情况下，如果follower没有成功备份数据，而此时leader又挂掉，则消息会丢失。
        （3）acks=-1或all： 需要等待 min.insync.replicas(默认为1，推荐配置大于等于2) 这个参数配置的副本个数都成功写入日志，这种策略会保证
            只要有一个备份存活就不会丢失数据。这是最强的数据保证。一般除非是金融级别，或跟钱打交道的场景才会使用这种配置。
         */
        /*
        props.put(ProducerConfig.ACKS_CONFIG, "1");
         */
         /*
        发送失败会重试，默认重试间隔100ms，重试能保证消息发送的可靠性，但是也可能造成消息重复发送，比如网络抖动，所以需要在接收者那边做好消息接收的幂等性处理
        */
        /*
        props.put(ProducerConfig.RETRIES_CONFIG, 3);
        //重试间隔设置
        props.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, 300);
        //设置发送消息的本地缓冲区，如果设置了该缓冲区，消息会先发送到本地缓冲区，可以提高消息发送性能，默认值是33554432，即32MB
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        */
        /*
        kafka本地线程会从缓冲区取数据，批量发送到broker，设置批量发送消息的大小，默认值是16384，即16kb，就是说一个batch满了16kb就发送出去
        */
        //props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        /*
        默认值是0，意思就是消息必须立即被发送，但这样会影响性能一般设置10毫秒左右，就是说这个消息发送完后会进入本地的一个batch，如果10毫秒内，这个batch满了16kb就会随batch一起被发送出去, 如果10毫秒内，batch没满，那么也必须把消息发送出去，不能让消息的发送延迟时间太长
        */
        //props.put(ProducerConfig.LINGER_MS_CONFIG, 10);

        return props;
    }

    private static ProducerRecord<String, String> setMessage(String key, String value) {
        return new ProducerRecord<>(TOPIC_NAME, key, value);
    }

    public static void sendMessage(Producer<String, String> producer, ProducerRecord<String, String> producerRecord, CountDownLatch countDownLatch) {

        //异步回调方式发送消息
        producer.send(producerRecord, new Callback() {
            @Override
            public void onCompletion(RecordMetadata metadata, Exception exception) {
                if (exception != null) {
                    System.err.println("发送消息失败：" + exception.getStackTrace());

                }
                if (metadata != null) {
                    System.out.println("异步方式发送消息结果：" + "topic-" + metadata.topic() + "|partition-"
                            + metadata.partition() + "|offset-" + metadata.offset());
                }
                countDownLatch.countDown();
            }
        });
    }
}
