package com.fiap.challenge.order.domain;

import com.fiap.challenge.order.domain.model.exception.NotFoundException;
import com.fiap.challenge.order.domain.model.exception.UnprocessableEntityException;
import com.fiap.challenge.order.domain.model.order.Order;
import com.fiap.challenge.order.domain.model.order.OrderStatus;
import com.fiap.challenge.order.domain.model.payment.PaymentStatus;
import com.fiap.challenge.order.domain.ports.outbound.OrderRepository;
import com.fiap.challenge.order.fixture.OrderFixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DomainOrderServiceTest {

    @Mock
    private OrderRepository orderRepositoryMock;

    @InjectMocks
    private DomainOrderService target;

    @Test
    void testApprovePayment() {
        // Arrange
        Long orderId = 1L;
        Order order = OrderFixture.validOrder();
        order.setId(orderId);
        order.setStatus(OrderStatus.WAITING_PAYMENT);
        when(orderRepositoryMock.findById(orderId)).thenReturn(Optional.of(order));

        // Act
        target.approvePayment(orderId);

        // Assert
        verify(orderRepositoryMock).save(ArgumentMatchers.argThat(o ->
                o.getStatus() == OrderStatus.READY_FOR_PREPARATION &&
                        order.getPayment().getStatus() == PaymentStatus.APPROVED &&
                        order.getPayment().getApprovedAt() != null
        ));
    }

    @Test
    void testApprovePaymentWithOrderNotFound() {
        // Arrange
        Long orderId = 1L;
        when(orderRepositoryMock.findById(orderId)).thenReturn(Optional.empty());

        // Act
        NotFoundException exception = assertThrows(NotFoundException.class, () -> target.approvePayment(orderId));

        // Assert
        assertEquals("Order not found. ID: 1", exception.getMessage());
    }

    @Test
    void testApprovePaymentWithOrderNotWaitingPayment() {
        // Arrange
        Long orderId = 1L;
        Order order = OrderFixture.validOrder();
        order.setId(orderId);
        order.setStatus(OrderStatus.READY_FOR_PREPARATION);
        when(orderRepositoryMock.findById(orderId)).thenReturn(Optional.of(order));

        // Act
        UnprocessableEntityException exception = assertThrows(UnprocessableEntityException.class, () -> target.approvePayment(orderId));

        // Assert
        assertEquals("Order is not waiting for payment. ID: 1", exception.getMessage());
    }

    @Test
    void testApprovePaymentWithTransactionId() {
        // Arrange
        String transactionId = "transaction-id";
        Order order = OrderFixture.validOrder();
        order.setStatus(OrderStatus.WAITING_PAYMENT);
        when(orderRepositoryMock.findByPaymentTransactionId(transactionId)).thenReturn(Optional.of(order));

        // Act
        target.approvePayment(transactionId);

        // Assert
        verify(orderRepositoryMock).save(ArgumentMatchers.argThat(o ->
                o.getStatus() == OrderStatus.READY_FOR_PREPARATION &&
                        order.getPayment().getStatus() == PaymentStatus.APPROVED &&
                        order.getPayment().getApprovedAt() != null
        ));
    }

    @Test
    void testApprovePaymentWithTransactionIdAndOrderNotFound() {
        // Arrange
        String transactionId = "transaction-id";
        when(orderRepositoryMock.findByPaymentTransactionId(transactionId)).thenReturn(Optional.empty());

        // Act
        NotFoundException exception = assertThrows(NotFoundException.class, () -> target.approvePayment(transactionId));

        // Assert
        assertEquals("Order not found. Transaction ID: transaction-id", exception.getMessage());
    }

    @Test
    void testApprovePaymentWithTransactionIdAndOrderNotWaitingPayment() {
        // Arrange
        String transactionId = "transaction-id";
        Order order = OrderFixture.validOrder();
        order.setStatus(OrderStatus.READY_FOR_PREPARATION);
        when(orderRepositoryMock.findByPaymentTransactionId(transactionId)).thenReturn(Optional.of(order));

        // Act
        UnprocessableEntityException exception = assertThrows(UnprocessableEntityException.class, () -> target.approvePayment(transactionId));

        // Assert
        assertEquals("Order is not waiting for payment. ID: 1", exception.getMessage());
    }

    @Test
    void testGetAllByStatus() {
        // Arrange
        OrderStatus status = OrderStatus.WAITING_PAYMENT;
        when(orderRepositoryMock.findAllByStatusIn(ArgumentMatchers.anyList(), ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt()))
                .thenReturn(OrderFixture.pageResultWithOneOrder());

        // Act
        var result = target.getAllByStatus(List.of(status), 0, 10);

        // Assert
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getContent().size());
    }

    @Test
    void testGetPaymentStatusById() {
        // Arrange
        Long orderId = 1L;
        Order order = OrderFixture.validOrder();
        order.setId(orderId);
        order.getPayment().setStatus(PaymentStatus.APPROVED);
        when(orderRepositoryMock.findById(orderId)).thenReturn(Optional.of(order));

        // Act
        var paymentStatus = target.getPaymentStatusById(orderId);

        // Assert
        assertEquals(PaymentStatus.APPROVED, paymentStatus);
    }

    @Test
    void testUpdateStatusSuccessfully() {
        // Arrange
        Long orderId = 1L;
        Order order = OrderFixture.validOrder();
        order.setId(orderId);
        order.setStatus(OrderStatus.WAITING_PAYMENT);
        when(orderRepositoryMock.findById(orderId)).thenReturn(Optional.of(order));

        OrderStatus newStatus = OrderStatus.READY_FOR_PREPARATION;

        // Act
        target.updateStatus(orderId, newStatus);

        // Assert
        verify(orderRepositoryMock).save(ArgumentMatchers.argThat(o ->
                o.getId().equals(orderId) &&
                        o.getStatus() == newStatus
        ));
    }

    @Test
    void testUpdateStatusWithOrderNotFound() {
        // Arrange
        Long orderId = 1L;
        when(orderRepositoryMock.findById(orderId)).thenReturn(Optional.empty());

        // Act
        NotFoundException exception = assertThrows(NotFoundException.class, () -> target.updateStatus(orderId, OrderStatus.READY_FOR_PREPARATION));

        // Assert
        assertEquals("Order not found. ID: 1", exception.getMessage());
    }

    @Test
    void testRejectPaymentWithTransactionIdAndOrderNotFound() {
        // Arrange
        String transactionId = "transaction-id";
        when(orderRepositoryMock.findByPaymentTransactionId(transactionId)).thenReturn(Optional.empty());

        // Act
        NotFoundException exception = assertThrows(NotFoundException.class, () -> target.rejectPayment(transactionId));

        // Assert
        assertEquals("Order not found. ID: transaction-id", exception.getMessage());
    }

    @Test
    void testRejectPaymentWithTransactionIdAndOrderNotWaitingPayment() {
        // Arrange
        String transactionId = "transaction-id";
        Order order = OrderFixture.validOrder();
        order.setStatus(OrderStatus.READY_FOR_PREPARATION);
        when(orderRepositoryMock.findByPaymentTransactionId(transactionId)).thenReturn(Optional.of(order));

        // Act
        UnprocessableEntityException exception = assertThrows(UnprocessableEntityException.class, () -> target.rejectPayment(transactionId));

        // Assert
        assertEquals("Order is not waiting for payment. ID: 1", exception.getMessage());
    }

    @Test
    void testGetAllByStatusInOrderByCreatedAt() {
        // Arrange
        OrderStatus status = OrderStatus.READY_FOR_PREPARATION;
        when(orderRepositoryMock.findAllByStatusInOrderByCreatedAt(ArgumentMatchers.anyList(), ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt()))
                .thenReturn(OrderFixture.pageResultWithOneOrder());

        // Act
        var result = target.getAllByStatusInOrderByCreatedAt(List.of(status), 0, 10);

        // Assert
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getContent().size());
    }

    @Test
    void testSaveOrderSuccessfully() {
        // Arrange
        Order order = OrderFixture.validOrder();
        when(orderRepositoryMock.save(order)).thenReturn(order);

        // Act
        Order savedOrder = target.saveOrder(order);

        // Assert
        verify(orderRepositoryMock).save(order);
        assertEquals(order, savedOrder);
    }

}
