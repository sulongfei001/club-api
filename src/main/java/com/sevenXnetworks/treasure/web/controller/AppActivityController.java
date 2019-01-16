package com.sevenXnetworks.treasure.web.controller;

import com.sevenXnetworks.treasure.service.ActivityService;
import com.sevenXnetworks.treasure.vo.ActivityVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description
 * @Author sulongfei
 * @Date 2018/10/31 15:37
 * @Version 1.0
 */
@RestController
@RequestMapping("/app/bar")
public class AppActivityController {
    @Autowired
    private ActivityService activityService;

    @RequestMapping(value = "/{barId}/activity", method = RequestMethod.GET)
    public ResponseEntity<Object> appList(@PathVariable long barId, @RequestParam(defaultValue = "0", required = false) long beginId,
                                          @RequestParam(defaultValue = "20", required = false) int offset) {
        List<ActivityVo> list = activityService.appList(barId, beginId, offset);
        return ResponseEntity.ok(list);
    }

    @RequestMapping(value = "/{barId}/activity/{activityId}", method = RequestMethod.GET)
    public ResponseEntity<Object> appGet(@PathVariable long barId, @PathVariable long activityId, @RequestParam(defaultValue = "0", required = false) long beginId,
                                      @RequestParam(defaultValue = "20", required = false) int offset) {
        ActivityVo vo = activityService.appGet(barId, activityId, beginId, offset);
        return ResponseEntity.ok(vo);
    }
}
