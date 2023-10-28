package br.com.b3social.colaboracaoservice.producers;

import br.com.b3social.colaboracaoservice.domain.models.Email;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ServiceProducers {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @Value(value = "${broker.queue.email.name}")
    private String routingKey;


    public void teste(Email email) {
        email.setEmailDe("reynaldoscrip@gmail.com");
        email.setEmailPara(email.getEmailPara());
        email.setAssunto(email.getAssunto());
        email.setTexto(email.getTexto());
        this.rabbitTemplate.convertAndSend("", routingKey, email);
    }
    
}