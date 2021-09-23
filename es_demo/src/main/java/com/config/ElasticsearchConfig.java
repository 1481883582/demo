package com.config;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@Configuration
public class ElasticsearchConfig {

    /**
     * 集群地址，多个用,隔开 ="192.168.142.131:9200"
     */
    @Value("${elasticsearch.hosts:192.168.142.131}")
    private String hosts;
    /**
     * 使用的端口号= 9200
     */
    @Value("${elasticsearch.port:9200}")
    private int port;
    /**
     * 使用的协议
     */
    private String schema = "http";
    /**
     * 连接超时时间
     */
    @Value("${elasticsearch.connect-time-out:1000}")
    private int connectTimeOut;
    /**
     * 连接超时时间
     */
    @Value("${elasticsearch.socket-time-out:180000}")
    private int socketTimeOut;
    /**
     * 获取连接的超时时间
     */
    @Value("${elasticsearch.connection-request-time-out:500}")
    private int connectionRequestTimeOut;
    /**
     * 最大连接数
     */
    @Value("${elasticsearch.max-connect-num:100}")
    private int maxConnectNum;
    /**
     * 最大路由连接数
     */
    @Value("${elasticsearch.max-connect-perRoute:100}")
    private int maxConnectPerRoute;
    /**
     * 连接缓存
     */
    private static ArrayList<HttpHost> hostList = null;

    @Bean
    public RestHighLevelClient client() {
        hostList = new ArrayList<>();
        String[] hostStr = hosts.split(",");
        for (String service : hostStr) {
            String[] host = service.split(":");
            if(host.length >= 2){
                // 自定义端口
                hostList.add(new HttpHost(host[0], Integer.parseInt(host[1]), schema));
            }else{
                // 端口默认是9200
                hostList.add(new HttpHost(service, port, schema));
            }
        }
        RestClientBuilder builder = RestClient.builder(hostList.toArray(new HttpHost[0]));

        // 异步httpclient连接延时配置
        builder.setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {
            @Override
            public RequestConfig.Builder customizeRequestConfig(RequestConfig.Builder requestConfigBuilder) {
                requestConfigBuilder.setConnectTimeout(connectTimeOut);
                requestConfigBuilder.setSocketTimeout(socketTimeOut);
                requestConfigBuilder.setConnectionRequestTimeout(connectionRequestTimeOut);
                return requestConfigBuilder;
            }
        });

        // 异步httpclient连接数配置
        builder.setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
            @Override
            public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                httpClientBuilder.setMaxConnTotal(maxConnectNum);
                httpClientBuilder.setMaxConnPerRoute(maxConnectPerRoute);
                return httpClientBuilder;
            }
        });

        return new RestHighLevelClient(builder);
    }
    //    @Bean
//    public RestHighLevelClient restHighLevelClient(){
//        return new RestHighLevelClient(
//                RestClient.builder(
//                        new HttpHost(
//                                "192.168.142.131",
//                                9200,
//                                "http"
//                        )
//                )
//        );
//    }
}
