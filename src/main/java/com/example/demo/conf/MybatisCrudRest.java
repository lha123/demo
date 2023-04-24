package com.example.demo.conf;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.utils.QueryWrapUtil;
import io.swagger.annotations.ApiOperation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface MybatisCrudRest<Input, Entity, Service extends IService<Entity>> {
    default Service getService() {
        Type[] types = this.getClass().getGenericInterfaces();
        return (Service) SpringUtil.getBean((Class)((ParameterizedType)types[0]).getActualTypeArguments()[2]);
    }

    default Class<Entity> entityClass() {
        Type[] types = this.getClass().getGenericInterfaces();
        Class<Entity> entityClass = (Class)((ParameterizedType)types[0]).getActualTypeArguments()[1];
        return entityClass;
    }

    @ApiOperation("保存当前请求对象")
    @PostMapping
    default Entity save(@Validated @RequestBody Input form) {
        Class<Entity> entityClass = this.entityClass();
        Entity entity = BeanUtil.copyProperties(form, entityClass);
        if (!this.getService().save(entity)) {
            return null;
        }

        return entity;
    }

    @ApiOperation("批量保存当前请求对象")
    @PostMapping({"/batch"})
    default List<Entity> save(@Validated @RequestBody List<Input> forms) {
        List<Entity> entitys = BeanUtil.copyToList(forms, this.entityClass());
        if (!this.getService().saveBatch(entitys)) {
            return null;
        }

        return entitys;
    }

    @ApiOperation("根据ID更新记录")
    @PutMapping({"/{id}"})
    default Boolean updById(@RequestBody @Validated Input form) {
        Entity entity = BeanUtil.copyProperties(form, this.entityClass());
        return !this.getService().updateById(entity) ? null : true;
    }


    @ApiOperation("根据ID查询记录")
    @GetMapping(
            path = {"/{id}"}
    )
    default Entity getById(@PathVariable("id") Long id) {
        return this.getService().getById(id);
    }

    @ApiOperation("根据条件查询记录(一条)")
    @GetMapping({"/findOne"})
    default Entity findOne(@Validated Entity entity) {
        try {
            return this.getService().getOne(QueryWrapUtil.getPredicate(entity));
        } catch (Exception var3) {
            return null;
        }
    }

    @ApiOperation("根据条件查询记录(多条)")
    @GetMapping({"/find"})
    default List<Entity> find(@Validated Entity entity) {
        List<Entity> entitys = this.getService().list(QueryWrapUtil.getPredicate(entity));
        return entitys;
    }

    @ApiOperation("列出所有记录")
    @GetMapping({"/list"})
    default List<Entity> list() {
        QueryWrapper qw = new QueryWrapper();
        qw.orderByDesc("dt");
        List<Entity> entitys = this.getService().list(qw);
        return entitys;
    }

    @ApiOperation("根据查询条件获取分页数据")
    @GetMapping({"/page"})
    default IPage<Entity> pageV1(@Validated Entity entity, @RequestParam(value = "page",defaultValue = "1") Integer pageNo, @RequestParam(value = "size",defaultValue = "10") Integer pageSize) {
        Page<Entity> page = new Page((long)pageNo, (long)pageSize);
        QueryWrapper<Entity> queryWrapper = QueryWrapUtil.getPredicate(entity);
        IPage result = this.getService().page(page, queryWrapper);
        return result;
    }

    @ApiOperation("根据查询条件获取分页数据")
    @PostMapping({"/page/{page}/{size}"})
    default IPage<Entity> page(@Validated Entity entity, @PathVariable("page") Integer pageNo, @PathVariable("size") Integer pageSize) {
        Page<Entity> page = new Page((long)pageNo, (long)pageSize);
        QueryWrapper<Entity> queryWrapper = QueryWrapUtil.getPredicate(entity);
        IPage result = this.getService().page(page, queryWrapper);
        return result;
    }

    @ApiOperation("根据ID逻辑删除")
    @DeleteMapping(
            path = {"/{id}"}
    )
    default Boolean delById(@PathVariable("id") Long id) {
        return !this.getService().update((Wrapper)((UpdateWrapper)(new UpdateWrapper()).eq("id", id)).set("deleted", Boolean.TRUE)) ?
                false : true;
    }

    @ApiOperation("根据ID批量逻辑删除")
    @DeleteMapping({"/delByIds"})
    default Boolean delById(@RequestBody List<Long> ids) {
        return !this.getService().update((Wrapper)((UpdateWrapper)(new UpdateWrapper()).in("id", ids)).set("deleted", Boolean.TRUE)) ? false : true;
    }
}

