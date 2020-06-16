package coffeeshop;

import coffeeshop.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PolicyHandler{
    @Autowired
    CoffeeRepository coffeeRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverOrderPlaced_QuantityDecrease(@Payload OrderPlaced orderPlaced){

        if(orderPlaced.isMe()){
            System.out.println("##### listener QuantityDecrease : " + orderPlaced.toJson());

            coffeeRepository.findById(orderPlaced.getCoffeeId())
                    .ifPresent(
                            coffee -> {
                            coffee.setQty(coffee.getQty() - orderPlaced.getQty());
                            coffeeRepository.save(coffee);
                            }
                    )
            ;
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverOrderCanceled_QuantityRecover(@Payload OrderCanceled orderCanceled){

        if(orderCanceled.isMe()){
            System.out.println("##### listener QuantityRecover : " + orderCanceled.toJson());

            coffeeRepository.findById(orderCanceled.getCoffeeId())
                    .ifPresent(
                            coffee -> {
                                coffee.setQty(coffee.getQty() + orderCanceled.getQty());
                                coffeeRepository.save(coffee);
                            }
                    )
            ;
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void onEvent(@Payload String message) { }
}
