package br.com.b3social.colaboracaoservice.api.producers;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import br.com.b3social.colaboracaoservice.api.dtos.EmailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value(value = "${broker.queue.email.name}")
    private String routingKey;

    public void publishMessageEmailServiceAcaoSocial(EmailDto email) {
        var emailDto = new EmailDto();
        emailDto.setEmailPara(email.getEmailPara());
        emailDto.setAssunto(email.getAssunto());
        emailDto.setTexto(email.getTexto());

        this.rabbitTemplate.convertAndSend("", routingKey, emailDto);
    }
    
}