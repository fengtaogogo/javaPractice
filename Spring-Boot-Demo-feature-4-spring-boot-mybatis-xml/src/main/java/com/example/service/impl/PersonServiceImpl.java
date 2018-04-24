package com.example.service.impl;

import com.example.domain.PersonDO;
import com.example.mapper.PersonMapper;
import com.example.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "personService")
public class PersonServiceImpl implements PersonService{

    @Autowired
    private PersonMapper personMapper;
    @Override
    public void insert(PersonDO personDO) {
        personMapper.insert(personDO);
    }

    @Override
    public Long update(PersonDO personDO) {
        return personMapper.update(personDO);
    }

    @Override
    public Long delete(Integer id) {
        return personMapper.delete(id);
    }

    @Override
    public PersonDO selectPersonById(Integer id) {
        return personMapper.selectPersonById(id);
    }

    @Override
    public List<PersonDO> selectAll() {
        return personMapper.selectAll();
    }


}
