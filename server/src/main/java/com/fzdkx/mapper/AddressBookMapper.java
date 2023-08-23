package com.fzdkx.mapper;

import com.fzdkx.entity.AddressBook;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

/**
 * @author 发着呆看星
 * @create 2023/8/23 14:22
 */
public interface AddressBookMapper {
    void insert(AddressBook addressBook);

    List<AddressBook> selectAddressList(Long id);

    AddressBook selectDefaultAddress(Long id);

    void updateAddress(AddressBook addressBook);

    void deleteAddressById(Long id);

    AddressBook selectAddressById(Long id);

    void updateDefaultAddress(Long id);

    void abolishDefaultAddress(@Param("userId") Long userId ,@Param("id") Long id);
}
