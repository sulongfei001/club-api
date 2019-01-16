package com.sevenXnetworks.treasure.web.controller;

import com.sevenXnetworks.treasure.service.BarService;
import com.sevenXnetworks.treasure.vo.BarVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @Description
 * @Author sulongfei
 * @Date 2018/10/31 15:16
 * @Version 1.0
 */
@RestController
@RequestMapping("/app/bar")
public class AppBarController extends BaseController {
    @Autowired
    private BarService barService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<Object> appList(@RequestParam(defaultValue = "0", required = false) long beginId,
                                          @RequestParam(defaultValue = "20", required = false) int offset) {
        List<BarVo> list = barService.appPassList(beginId, offset);
        return ResponseEntity.ok(list);
    }

}
