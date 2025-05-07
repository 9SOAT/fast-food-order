package com.fiap.challenge.order.domain;

import com.fiap.challenge.order.domain.model.payment.PaymentStatus;
import com.fiap.challenge.order.domain.model.webhook.Webhook;
import com.fiap.challenge.order.domain.ports.inbound.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DomainWebhookServiceTest {

    @Mock
    private WebhookRepository webhookRepository;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private DomainWebhookService domainWebhookService;

    @Test
    void shouldApprovePaymentWhenWebhookStatusIsApproved() {
        Webhook webhook = Webhook.builder()
                .transactionId("123")
                .status(PaymentStatus.APPROVED)
                .build();

        domainWebhookService.updatePayment(webhook);

        verify(orderService, times(1)).approvePayment("123");
        verify(orderService, never()).rejectPayment(anyString());
        verify(webhookRepository, times(1)).save(webhook);
    }

    @Test
    void shouldRejectPaymentWhenWebhookStatusIsNotApproved() {
        Webhook webhook = Webhook.builder()
                .transactionId("123")
                .status(PaymentStatus.REJECTED)
                .build();

        domainWebhookService.updatePayment(webhook);

        verify(orderService, times(1)).rejectPayment("123");
        verify(orderService, never()).approvePayment(anyString());
        verify(webhookRepository, times(1)).save(webhook);
    }
}