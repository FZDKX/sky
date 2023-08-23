package com.fzdkx.service;

import com.fzdkx.entity.AddressBook;

import java.util.List;

/**
 * @author 发着呆看星
 * @create 2023/8/23 14:21
 */
public interface AddressBookService {
    void addAddress(AddressBook addressBook);

    List<AddressBook> getAddressList();

    AddressBook getDefaultAddress();

    void changeAddress(AddressBook addressBook);

    void clean(Long id);

    AddressBook getAddress(Long id);

    void changeDefaultAddress(Long id);
}
