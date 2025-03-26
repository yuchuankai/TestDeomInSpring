package 分布式内存网络;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

/**
 * @CreateTime: 2024年 10月 12日 9:14
 * @Description:
 * @Author: MR.YU
 */
public class Client {

    public static void main(String[] args) {
        ClientConfig clientConfig = new ClientConfig();
        HazelcastInstance client = HazelcastClient.newHazelcastClient( clientConfig );
        IMap map = client.getMap( "customers" );
        Object o = map.get(2);
        System.out.println( "Customer with key 1: " + o );
        System.out.println( "Map Size:" + map.size() );

    }
}
