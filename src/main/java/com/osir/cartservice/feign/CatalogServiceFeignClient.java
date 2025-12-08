package com.osir.cartservice.feign;

import com.osir.takeoutpojo.vo.DishVO;
import com.osir.takeoutpojo.vo.SetmealVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "catalog-service", path = "/catalog")
public interface CatalogServiceFeignClient {

    /**
     * 根据id查询套餐
     * @param id
     * @return
     */
    @GetMapping("/inner/setmeal/getById")
    SetmealVO getBySetmealId(@RequestParam("id") Long id);

    /**
     * 根据id查询菜品
     * @param id
     * @return
     */
    @GetMapping("/inner/dish/getById")
    DishVO getByDishId(@RequestParam("id") Long id);

}
