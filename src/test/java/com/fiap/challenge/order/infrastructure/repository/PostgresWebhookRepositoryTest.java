package com.fiap.challenge.order.infrastructure.repository;

import com.fiap.challenge.order.domain.model.webhook.Webhook;
import com.fiap.challenge.order.infrastructure.mapper.EntityMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
public class PostgresWebhookRepositoryTest {

    @InjectMocks
    private PostgresWebhookRepository postgresWebhookRepository;

    @Mock
    private JpaWebhookRepository jpaWebhookRepository;

    @Mock
    private EntityMapper entityMapper;

    @Test
    public void shouldSaveWebhookSuccessfully() {
        Webhook webhook = Webhook.builder().build();
        WebhookEntity webhookEntity = WebhookEntity.builder().build();
        WebhookEntity savedEntity = WebhookEntity.builder().build();

        Mockito.when(entityMapper.toWebhookEntity(webhook)).thenReturn(webhookEntity);
        Mockito.when(jpaWebhookRepository.save(webhookEntity)).thenReturn(savedEntity);
        Mockito.when(entityMapper.toWebhook(savedEntity)).thenReturn(webhook);

        Webhook savedWebhook = postgresWebhookRepository.save(webhook);

        assertThat(savedWebhook).isNotNull();
        assertThat(savedWebhook).isEqualTo(webhook);
        Mockito.verify(entityMapper).toWebhookEntity(webhook);
        Mockito.verify(jpaWebhookRepository).save(webhookEntity);
        Mockito.verify(entityMapper).toWebhook(savedEntity);
    }
}