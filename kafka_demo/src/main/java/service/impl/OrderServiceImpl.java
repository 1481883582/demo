package service.impl;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.OrderService;

import javax.annotation.Resource;

@Transactional
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private KafkaTemplate kafkaTemplate;

    @Override
    public void saveOrder(String id,Object message) {
        kafkaTemplate.send(new ProducerRecord("aa",id,message));
    }
}
