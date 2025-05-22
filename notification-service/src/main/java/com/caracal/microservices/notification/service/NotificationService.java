package com.caracal.microservices.notification.service;

import com.caracal.microservices.notification.order.event.OrderPlacedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final JavaMailSender javaMailSender;

    @KafkaListener(topics = "order-placed")
    public void listen(OrderPlacedEvent orderPlacedEvent) {
        log.info("Got message from order-placed topic {}", orderPlacedEvent);
        //send email to customer
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("springshop@email.com");
            messageHelper.setTo(orderPlacedEvent.getOrderEmail());
            messageHelper.setSubject(String.format("Order Number %s", orderPlacedEvent.getOrderNumber()));
            messageHelper.setText("""
                    Hello,
                    
                    Order number %s was placed successfully!
                    
                    Best regards,
                    Spring Shop
                    """, orderPlacedEvent.getOrderNumber());
        };
        try {
            javaMailSender.send(messagePreparator);
            log.info("Email for order notification sent successfully");
        } catch (MailException e) {
            log.error("Error while sending email for order notification", e);
            throw new RuntimeException("Exception while sending email for order notification", e);
        }
    }

}
