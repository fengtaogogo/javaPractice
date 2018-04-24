package com.example.service;

import com.example.domain.PersonDO;

import java.util.List;

public interface PersonService {

    void insert(PersonDO personDO);

    Long update(PersonDO personDO);

    Long delete(Integer id);

    PersonDO selectPersonById(Integer id);

    List<PersonDO> selectAll();
}
