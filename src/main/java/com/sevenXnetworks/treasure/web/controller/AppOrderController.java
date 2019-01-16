package com.sevenXnetworks.treasure.web.controller;

import com.sevenXnetworks.treasure.service.OrderService;
import com.sevenXnetworks.treasure.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description
 * @Author sulongfei
 * @Date 18-11-6 上午10:53
 * @Version 1.0
 */
@RestController
@RequestMapping("/app/order")
public class AppOrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<Object> appList(@RequestParam String memberId, @RequestParam(defaultValue = "0", required = false) long beginId,
                                          @RequestParam(defaultValue = "20", required = false) int offset) {
        List<OrderVo> vos = orderService.appList(memberId, beginId, offset);
        return ResponseEntity.ok(vos);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> appGet(@PathVariable long id) {
        OrderVo vo = orderService.appGet(id);
        return ResponseEntity.ok(vo);
    }
}
