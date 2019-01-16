package com.sevenXnetworks.treasure.web.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sevenXnetworks.treasure.bean.OrderBean;
import com.sevenXnetworks.treasure.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * @Description 支付完成后微信回调类，用于获取订单信息
 * @Author sulongfei
 * @Date 2018/11/16 16:42
 * @Version 1.0
 */
@RestController
@RequestMapping(value = "/pay/call_back")
public class PayResultController {

    @Autowired
    private OrderService orderService;

    /**
     * @Description 支付成功后，处理业务信息，新增订单
     * @param: [request]
     * @Return: java.lang.String
     * @Auther: sulongfei
     * @Date: 2018/11/20 16:19
     * @Version 1.0
     */
    @RequestMapping(value = "/wxPay", method = RequestMethod.POST)
    @ResponseBody
    public String payResult(HttpServletRequest request) throws Exception {
        InputStream inStream = request.getInputStream();
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        String resultxml = new String(outSteam.toByteArray(), "utf-8");
        outSteam.close();
        inStream.close();

        Map<String, String> params = new Gson().fromJson(resultxml, new TypeToken<Map<String, String>>() {
        }.getType());
        if (params.get("result_code").equals("SUCCESS")) {
            Map<String, String> payDetail = new Gson().fromJson(params.get("payDetail"), new TypeToken<Map<String, String>>() {
            }.getType());
            String transaction_id = params.get("transaction_id");
            String cash_fee = params.get("cash_fee");
            String time_end = params.get("time_end");
            String openid = params.get("openid");
            String activityId = payDetail.get("activityId");
            String phoneNum = payDetail.get("phoneNum");
            String buyNumber = payDetail.get("buyNumber");
            String createDate = payDetail.get("createDate");
            String userName = payDetail.get("userName");
            String memberId = payDetail.get("memberId");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            OrderBean orderBean = new OrderBean(transaction_id, Long.valueOf(activityId), openid, memberId, userName, phoneNum, new Timestamp(Long.valueOf(createDate)), new Timestamp(sdf.parse(time_end).getTime()), Integer.valueOf(buyNumber), new BigDecimal(cash_fee));
            orderService.appCreate(orderBean);
        }
        return "SUCCESS";
    }
}
