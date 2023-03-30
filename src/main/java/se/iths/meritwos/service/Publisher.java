package se.iths.meritwos.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import se.iths.meritwos.ad.Ad;
import se.iths.meritwos.ad.AdDTO;
import se.iths.meritwos.ad.AdRepository;
import se.iths.meritwos.company.CompanyRepository;

@Service
public class Publisher {

    RabbitTemplate rabbitTemplate;
    private final CompanyRepository companyRepository;
    private final AdRepository adRepository;

    private final ObjectMapper objectMapper;

    static final String AD_QUEUE = "ads";

    public Publisher(RabbitTemplate rabbitTemplate, CompanyRepository companyRepository, AdRepository adRepository, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.companyRepository = companyRepository;
        this.adRepository = adRepository;
        this.objectMapper = objectMapper;
    }

    public void publishMessage(AdDTO ad) {

        try {
            var JsonAd = objectMapper.writeValueAsString(ad);
            Message message = MessageBuilder
                    .withBody(JsonAd.getBytes())
                    .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                    .build();
            this.rabbitTemplate.convertAndSend(AD_QUEUE, message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


    }

    @Bean
    public Queue addQueue() {
        return new Queue(AD_QUEUE);
    }

    @Bean
    public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(AD_QUEUE);
        container.setMessageListener(message -> {
            var incommingMessage = new String(message.getBody());

            AdDTO incommingAd = new AdDTO();
            try {
                incommingAd = objectMapper.readValue(incommingMessage, AdDTO.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            adRepository.save(new Ad(incommingAd.getName(), incommingAd.getDescription()));

            connectCompany(incommingAd.getName(), incommingAd.getCompanyId());

        });

        return container;
    }

    private void connectCompany(String adName, long companyId) {

        var company = companyRepository.findById(companyId).orElseThrow();

        var adFound = adRepository.findByName(adName);
        company.getAds().add(adFound);
        companyRepository.save(company);
    }

}
