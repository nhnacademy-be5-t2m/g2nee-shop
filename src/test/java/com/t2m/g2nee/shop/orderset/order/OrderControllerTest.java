package com.t2m.g2nee.shop.orderset.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.t2m.g2nee.shop.orderset.order.controller.OrderController;
import com.t2m.g2nee.shop.orderset.order.domain.Order;
import com.t2m.g2nee.shop.orderset.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;

/**
 * 주문 컨트롤러 테스트
 *
 * @author 박재희
 * @since 1.0
 */
@WebMvcTest(OrderController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs(outputDir = "target/snippets")
public class OrderControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    OrderService orderService;
    ObjectMapper objectMapper;
    Order order;
}
