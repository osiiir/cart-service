package com.osir.cartservice.controller;

import com.osir.cartservice.service.ShoppingCartService;
import com.osir.takeoutpojo.dto.ShoppingCartDTO;
import com.osir.takeoutpojo.entity.ShoppingCart;
import com.osir.takeoutpojo.result.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/cart/user/shoppingCart")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    /**
     * 添加菜品或套餐到购物车
     * @param shoppingCartDTO
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody ShoppingCartDTO shoppingCartDTO){
        log.info("添加到购物车：{}",shoppingCartDTO);
        shoppingCartService.add(shoppingCartDTO);
        return Result.success();
    }

    /**
     * 批量添加到购物车
     * @param shoppingCartList
     * @return
     */
    @PostMapping("/insertBatch")
    public Result insertBatch(@RequestBody List<ShoppingCart> shoppingCartList){
        log.info("批量添加到购物车：{}",shoppingCartList);
        shoppingCartService.insertBatch(shoppingCartList);
        return Result.success();
    }

    /**
     * 查看购物车
     * @return
     */
    @GetMapping("/list")
    public Result list(){
        log.info("查看购物车:list");
        List<ShoppingCart> list = shoppingCartService.list();
        return Result.success(list);
    }

    /**
     * 从购物车删除菜品(&口味)或套餐
     * @param shoppingCartDTO
     * @return
     */
    @PostMapping("/sub")
    public Result sub(@RequestBody ShoppingCartDTO shoppingCartDTO){
        log.info("从购物车删除：{}",shoppingCartDTO);
        shoppingCartService.sub(shoppingCartDTO);
        return Result.success();
    }

    @DeleteMapping("/clean")
    public Result clean(){
        log.info("清空购物车");
        shoppingCartService.clean();
        return Result.success();
    }

}
