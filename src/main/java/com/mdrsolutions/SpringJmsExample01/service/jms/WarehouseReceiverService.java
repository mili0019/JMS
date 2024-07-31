package com.mdrsolutions.SpringJmsExample01.service.jms;

import com.mdrsolutions.SpringJmsExample01.pojos.BookOrder;
import com.mdrsolutions.SpringJmsExample01.pojos.ProcessedBookOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.listener.adapter.JmsResponse;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

@Service
public class WarehouseReceiverService {
    private static final Logger LOGGER = LoggerFactory.getLogger(WarehouseReceiverService.class);

    @Autowired
    private WarehouseProcessingService warehouseProcessingService;

    @JmsListener(destination = "book.order.queue")
    public JmsResponse<Message<ProcessedBookOrder>> receive(@Payload BookOrder bookOrder,
                                                            @Header(name="orderState")String orderState,
                                                            @Header(name="bookOrderId")String bookOrderId,
                                                            @Header(name="storeId")String storeId
    ) throws IllegalAccessException {
        LOGGER.info("Message received!");
        LOGGER.info("Message is == " + bookOrder);
        LOGGER.info("Message property orderState = {}, bookOrderId = {}, storeId = {}", orderState, bookOrder, storeId);


        if(bookOrder.getBook().getTitle().startsWith("L")){
            throw new IllegalAccessException("bookOrderId=" + bookOrder.getBookOrderId() + " is of a book not allowed!");
        }
        return warehouseProcessingService.processOrder(bookOrder, orderState, storeId);
    }
}
