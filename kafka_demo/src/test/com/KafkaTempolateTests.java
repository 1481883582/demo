

import com.kafka.kafka_demo.KafkaDemoApplication;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import service.OrderService;

import javax.annotation.Resource;

@SpringBootTest(classes = {KafkaDemoApplication.class})
@RunWith(SpringRunner.class)
public class KafkaTempolateTests {
    @Resource
    private KafkaTemplate kafkaTemplate;
    @Resource
    private OrderService orderService;


    /**
     *  利用Spring事物 发送kafka事物
     */
    @Test
    public void testOrderService(){
        orderService.saveOrder("001","利用Spring事物 发送kafka事物");
    }

    /**
     *  kafka事物发送
     */
    @Test
    public void testKafkaTemplate(){
        kafkaTemplate.executeInTransaction(new KafkaOperations.OperationsCallback() {
            @Override
            public Object doInOperations(KafkaOperations kafkaOperations) {
                return kafkaOperations.send(new ProducerRecord("aa","002","发送Kafka事务"));
            }
        });
    }

    /**
     *  非事物发送
     *   配 transaction-id-prefix 会报错
     */
    @Test
    public void testNotTransaction(){
        kafkaTemplate.send(new ProducerRecord("aa","003","非事务消息"));
    }



}
