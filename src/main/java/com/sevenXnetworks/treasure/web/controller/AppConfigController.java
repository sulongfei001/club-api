package com.sevenXnetworks.treasure.web.controller;

import com.sevenXnetworks.treasure.service.DictionaryService;
import com.sevenXnetworks.treasure.vo.ConfigVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description app接口配置类
 * @Author sulongfei
 * @Date 2018/11/2 11:38
 * @Version 1.0
 */
@RestController
@RequestMapping("/app/config")
public class AppConfigController {
    @Autowired
    private DictionaryService dictionaryService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<Object> list() {
        ConfigVo vo = dictionaryService.appList();
        return ResponseEntity.ok(vo);
    }

}
