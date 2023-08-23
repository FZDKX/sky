package com.fzdkx.service.impl;

import com.fzdkx.entity.AddressBook;
import com.fzdkx.mapper.AddressBookMapper;
import com.fzdkx.service.AddressBookService;
import com.fzdkx.utils.UserThreadLocal;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 发着呆看星
 * @create 2023/8/23 14:21
 */
@Service
public class AddressBookServiceImpl implements AddressBookService {

    @Resource
    private AddressBookMapper addressBookMapper;

    @Override
    public void addAddress(AddressBook addressBook) {
        addressBook.setUserId(UserThreadLocal.getId());
        addressBookMapper.insert(addressBook);
    }

    @Override
    public List<AddressBook> getAddressList() {
        return addressBookMapper.selectAddressList(UserThreadLocal.getId());
    }

    @Override
    public AddressBook getDefaultAddress() {
        return addressBookMapper.selectDefaultAddress(UserThreadLocal.getId());
    }

    @Override
    public void changeAddress(AddressBook addressBook) {
        // 查看是否为默认地址
        Integer isDefault = addressBook.getIsDefault();

        // 如果为默认地址，那么则取消之前的默认地址
        if ( isDefault != null && isDefault == 1){
            abolishDefaultAddress();
        }
        // 修改地址
        addressBook.setUserId(UserThreadLocal.getId());
        addressBookMapper.updateAddress(addressBook);
    }

    @Override
    public void clean(Long id) {
        addressBookMapper.deleteAddressById(id);
    }

    @Override
    public AddressBook getAddress(Long id) {
        return addressBookMapper.selectAddressById(id);
    }

    @Override
    @Transactional
    public void changeDefaultAddress(Long id) {
        // 取消默认地址
        abolishDefaultAddress();
        // 设置默认地址
        addressBookMapper.updateDefaultAddress(id);
    }

    private void abolishDefaultAddress(){
        Long userId = UserThreadLocal.getId();
        // 查询当前用户默认地址
        AddressBook addressBook = addressBookMapper.selectDefaultAddress(userId);
        if (addressBook != null){
            // 取消默认地址
            addressBookMapper.abolishDefaultAddress(userId,addressBook.getId());
        }
    }
}
