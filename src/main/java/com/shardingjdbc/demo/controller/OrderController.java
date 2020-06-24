package com.shardingjdbc.demo.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shardingjdbc.demo.constant.Constant;
import com.shardingjdbc.demo.constant.ResponseCode;
import com.shardingjdbc.demo.exception.ServiceException;
import com.shardingjdbc.demo.pojo.Order;
import com.shardingjdbc.demo.service.OrderService;
import com.shardingjdbc.demo.util.ServerResponseUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.List;


@Controller
@RequestMapping("/order")
public class OrderController {
    @Resource
    private OrderService orderService;

    @Resource
    private DataSource dataSource;

    @RequestMapping("/added")
    @ResponseBody
    public ServerResponseUtil added(@RequestParam(value="orderid",required = false,defaultValue = "0") Long orderId,
                        @RequestParam(value="goodsname",required = false,defaultValue = "") String goodsName){

        Order orderOne = new Order();
        orderOne.setOrderId(orderId);
        orderOne.setGoodsName(goodsName);

        boolean isAdd = orderService.addOneOrder(orderOne);

        System.out.println("isAdd:"+isAdd);
        if (isAdd == true) {
            return ServerResponseUtil.success("添加成功");
        } else {
            throw new ServiceException(ResponseCode.DATA_INS_FAIL.getMsg());
        }
    }


    @RequestMapping("/add")
    public String add(){
        return "order/add";
    }


    @GetMapping("/list")
    public String list(Model model,
                     @RequestParam(value="currentPage",required = false,defaultValue = "1") Integer currentPage){

        PageHelper.startPage(currentPage, Constant.ORDER_PAGE_SIZE);
        List<Order> order_list = orderService.getAllOrder();
        model.addAttribute("order_list",order_list);
        PageInfo<Order> pageInfo = new PageInfo<>(order_list);
        model.addAttribute("pageInfo", pageInfo);
        return "order/list";
    }

}
