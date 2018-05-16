package com.example.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by FlySheep on 17/3/25.
 */
@Mapper
public interface PersonMapper {

    List<Map<String,Object>> selectYewuyuans();

    List<Map<String,Object>> selectchanneltype(BigInteger stType);

    List<Map<String,Object>> selectbrands();

    List<Map<String,Object>> selectcategories();

    List<Map<String,Object>> selectstoretype();

    List<Map<String,Object>> selectstorelevel();

    void insertStores(HashMap hashMap);

    void insertRepresentatives(HashMap hashMap);

    void insertCustomers(HashMap paramMapCustomer);

    List<Map<String,Object>> selectchannels(BigInteger saleareaid);

    void insertStoresupplier(HashMap storesupplierMap);

    void insertStorebrand(HashMap brand);

    void insertcategory(HashMap category);

    List<Map<String,Object>> selectPresentatives(HashMap paramMapstoreId);

    void updatePresentatives(HashMap paramMappresentatives);
}
