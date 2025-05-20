package com.fiap.challenge.order.application.controller;

import com.fiap.challenge.order.application.request.OrderStatusFilter;
import com.fiap.challenge.order.domain.model.PageResult;
import com.fiap.challenge.order.domain.model.exception.NotFoundException;
import com.fiap.challenge.order.domain.model.order.Order;
import com.fiap.challenge.order.domain.model.order.OrderStatus;
import com.fiap.challenge.order.domain.ports.inbound.OrderService;
import com.fiap.challenge.order.fixture.OrderFixture;
import com.fiap.challenge.order.fixture.OrderViewFixture;
import com.fiap.challenge.order.infrastructure.mapper.ViewMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @Mock
    private ViewMapper viewMapper;

    @InjectMocks
    private OrderController orderController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @Test
    void getOrdersByStatusReturnsPageResult() throws Exception {
        OrderStatusFilter statusFilter = OrderStatusFilter.ALL;
        PageResult<Order> orderPage = new PageResult<>(List.of(OrderFixture.validOrder()), 1, 1, 1, 1);
        when(orderService.getAllByStatus(any(), anyInt(), anyInt())).thenReturn(orderPage);
        when(viewMapper.toOrderView(any())).thenReturn(OrderViewFixture.validOrderView());

        mockMvc.perform(MockMvcRequestBuilders.get("/orders")
                        .param("page", "1")
                        .param("size", "10")
                        .param("status", statusFilter.name()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

    @Test
    void getOrdersByStatusWithInvalidPageReturnsBadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/orders")
                .param("page", "0")
                .param("size", "10")
                .param("status", OrderStatusFilter.ALL.name()))
            .andExpect(status().isBadRequest());
    }

    @Test
    void getOrdersByStatusWithInvalidSizeReturnsBadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/orders")
                .param("page", "1")
                .param("size", "21")
                .param("status", OrderStatusFilter.ALL.name()))
            .andExpect(status().isBadRequest());
    }

    @Test
    void getOrdersByStatusWithInvalidStatusReturnsBadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/orders")
                .param("page", "1")
                .param("size", "10")
                .param("status", "TODOS"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void getOrdersByStatusWithEmptyStatusReturnsBadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/orders")
                .param("page", "1")
                .param("size", "10")
                .param("status", ""))
            .andExpect(status().isBadRequest());
    }

    @Test
    void getOrdersByStatusWithNullStatusReturnsBadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/orders")
                .param("page", "1")
                .param("size", "10")
            )
            .andExpect(status().isBadRequest());
    }

    @Test
    void getOrdersSortedByStatusAndCreatedDateReturnsPageResult() throws Exception {
        PageResult<Order> orderPage = new PageResult<>(List.of(OrderFixture.validOrder()), 1, 1, 1, 1);
        when(orderService.getAllByStatusInOrderByCreatedAt(any(), anyInt(), anyInt())).thenReturn(orderPage);
        when(viewMapper.toOrderView(any())).thenReturn(OrderViewFixture.validOrderView());

        mockMvc.perform(MockMvcRequestBuilders.get("/orders/list")
                .param("page", "1")
                .param("size", "10"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk());
    }

    @Test
    void patchOrderTransitionReturnsOk() throws Exception {
        long orderId = 1L;
        var requestBody = """
                {
                    "status": "IN_PREPARATION"
                }
            """;

        mockMvc.perform(MockMvcRequestBuilders.patch("/orders/{orderId}/status", orderId)
            .content(requestBody)
            .contentType(APPLICATION_JSON))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk());

        verify(orderService).updateStatus(orderId, OrderStatus.IN_PREPARATION);
    }

    @Test
    void saveOrderCallsService() throws Exception {
        Order order = OrderFixture.validOrder();
        String orderJson = """
                    {
                        "status": "%s",
                        "items": [{"id": "%s", "quantity": %d}]
                    }
                """.formatted(order.getStatus(), order.getItems().get(0).getId(), order.getItems().get(0).getQuantity());

        mockMvc.perform(MockMvcRequestBuilders.post("/orders/create")
                        .contentType(APPLICATION_JSON)
                        .content(orderJson))
                .andExpect(status().isCreated());

        verify(orderService).saveOrder(any(Order.class));
    }

    @Test
    void saveOrderWithInvalidRequestReturnsBadRequest() throws Exception {
        String invalidOrderJson = """
                    {
                        "status": "",
                        "items": []
                    }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/orders/create")
                        .contentType(APPLICATION_JSON)
                        .content(invalidOrderJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void approveOrderPaymentReturnsOk() throws Exception {
        long paymentId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.patch("/orders/payment/{id}/approve", paymentId)
                        .contentType(APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());

        verify(orderService).approveOrderPayment(paymentId);
    }


    @Test
    void rejectOrderPaymentReturnsOk() throws Exception {
        long paymentId = 2L;

        mockMvc.perform(MockMvcRequestBuilders.patch("/orders/payment/{id}/reject", paymentId)
                        .contentType(APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());

        verify(orderService).rejectOrderPayment(paymentId);
    }

}
