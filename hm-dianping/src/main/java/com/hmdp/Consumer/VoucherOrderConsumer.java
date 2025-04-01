package com.hmdp.Consumer;

import com.hmdp.entity.VoucherOrder;
import com.hmdp.service.IVoucherOrderService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VoucherOrderConsumer {
    @Autowired
    private IVoucherOrderService voucherOrderService;

    @RabbitListener(queues = "voucher_order_queue")
    public void handle(VoucherOrder voucherOrder) {
        try {
            voucherOrderService.createVoucherOrder(voucherOrder);
            System.out.println("订单处理成功：" + voucherOrder.getId());
        } catch (Exception e) {
            System.out.println("订单处理失败：" + e.getMessage());
        }
    }
}