package com.example.controller;

import com.example.domain.PersonDO;
import com.example.mapper.PersonMapper;
import com.example.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by FlySheep on 17/3/25.
 */
@RestController
public class PersonController {
    @Autowired
    private PersonMapper personMapper;

    @Autowired
    private PersonService personService;

    @RequestMapping("/save")
    public Integer save() {
        PersonDO personDO = new PersonDO();
        personDO.setName("wind");
        personDO.setAge(18);
        personService.insert(personDO);
        return personDO.getId();
    }

    @RequestMapping("/update")
    public Long update() {
        PersonDO personDO = new PersonDO();
        personDO.setId(1);
        personDO.setName("tao");
        personDO.setAge(12);
        return personService.update(personDO);
    }

    @RequestMapping("/delete/{id}")
    public Long delete(@PathVariable Integer id) {
        return personService.delete(id);
    }

    @RequestMapping("/selectById/{id}")
    public PersonDO selectById(@PathVariable Integer id) {
        return personService.selectPersonById(id);
    }

    @RequestMapping("/selectAll")
    public List<PersonDO> selectAll() {
        return personService.selectAll();
    }

    @RequestMapping("/transaction")
    @Transactional  // 需要事务的时候加上
    public Boolean transaction() {
        delete(5);

        int i = 3 / 0;

        save();

        return true;
    }

}
