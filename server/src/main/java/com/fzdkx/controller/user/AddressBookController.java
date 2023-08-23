package com.fzdkx.controller.user;

import com.fzdkx.dto.DefaultAddressDTO;
import com.fzdkx.entity.AddressBook;
import com.fzdkx.result.Result;
import com.fzdkx.service.AddressBookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 发着呆看星
 * @create 2023/8/23 14:12
 */
@RestController
@RequestMapping("/user/addressBook")
@Api(tags = "地址簿接口")
public class AddressBookController {

    @Resource
    private AddressBookService addressBookService;

    @PostMapping()
    @ApiOperation("新增地址")
    public Result saveAddress(@RequestBody AddressBook addressBook){
        addressBookService.addAddress(addressBook);
        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation("查询用户所有地址")
    public Result<List<AddressBook>> queryAddressList(){
        List<AddressBook> addressList = addressBookService.getAddressList();
        return Result.success(addressList);
    }

    @GetMapping("/default")
    @ApiOperation("查询用户默认地址")
    public Result<AddressBook> queryDefaultAddress(){
        AddressBook address = addressBookService.getDefaultAddress();
        return Result.success(address);
    }

    @PutMapping()
    @ApiOperation("修改地址")
    public Result editAddress(@RequestBody AddressBook addressBook){
        addressBookService.changeAddress(addressBook);
        return Result.success();
    }

    @DeleteMapping()
    @ApiOperation("删除地址")
    public Result removeAddress(Long id){
        addressBookService.clean(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    @ApiOperation("根据地址ID查询地址")
    public Result queryAddress(@PathVariable("id") Long id){
        AddressBook address = addressBookService.getAddress(id);
        return Result.success(address);
    }

    @PutMapping("/default")
    @ApiOperation("修改为默认地址")
    public Result editDefaultAddress(@RequestBody DefaultAddressDTO defaultAddressDTO){
        addressBookService.changeDefaultAddress(defaultAddressDTO.getId());
        return Result.success();
    }
}
