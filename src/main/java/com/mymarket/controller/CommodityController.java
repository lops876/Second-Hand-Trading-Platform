package com.mymarket.controller;

import com.mymarket.pojo.Commodity;
import com.mymarket.pojo.Result;
import com.mymarket.service.CommodityService;
import com.mymarket.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommodityController {
    public final CommodityService commodityService;
    public CommodityController(CommodityService commodityService) {
        this.commodityService = commodityService;
    }
    @PostMapping("/commodity")
    public Result put(@RequestBody Commodity commodity, HttpServletRequest request){
        var ret = JwtUtils.checkToken(request);
        if(ret[0] == 0) commodity.setPublisher(ret[1]);
        else if(ret[0] == 1 && commodity.getPublisher() == null) return Result.error("未指定发布者id!");
        try {
            commodityService.put(commodity);
        }
        catch (DataAccessException e){
            return Result.error("发布商品失败！");
        }
        return Result.success();
    }
    @DeleteMapping("/commodity/{id}")
    public Result delete(@PathVariable Integer id, HttpServletRequest request){
        try{
            if(JwtUtils.check(commodityService.get(id).getPublisher(),request)) return Result.error("用户id不匹配!");
            commodityService.delete(id);
        }
        catch (Exception e){
            //实际上不存在对应id的商品也不会返回错误
            return Result.error("删除失败！不存在该商品。");
        }
        return Result.success();
    }
    @PutMapping("/commodity")
    public Result update(@RequestBody Commodity commodity,HttpServletRequest request){
        if(commodity.getId() == null) return Result.error("修改商品失败！未提供商品id。");
        if(JwtUtils.check(commodity.getPublisher(),request)) return Result.error("用户id不匹配!");
        try{
            commodityService.update(commodity);
        }
        catch (DataAccessException e){
            return Result.error("修改商品失败！");
        }
        return Result.success();
    }
    @GetMapping("/commodity")
    public Result getCommodityByUserIdAndName(Integer id, String name){
        List<Commodity> ret = commodityService.getCommodityByUserIdAndName(id,name);
        return Result.success(ret);
    }
    @GetMapping("/commodity/all")
    public Result getAllCommodities(){
        List<Commodity> ret = commodityService.getAllCommodities();
        return Result.success(ret);
    }
    @GetMapping("/commodity/{id}")
    public Result getDetail(@PathVariable Integer id){
        Commodity ret = commodityService.get(id);
        if(ret == null) return Result.error("未找到该商品！");
        return Result.success(ret);
    }
}
