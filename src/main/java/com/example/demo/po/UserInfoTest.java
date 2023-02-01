package com.example.demo.po;

import com.example.demo.annotation.JoinInfo;
import com.example.demo.annotation.OrderByIsAsc;
import com.example.demo.annotation.OrderGroup;
import com.example.demo.annotation.QueryMatching;
import com.example.demo.enums.TypeEnums;
import com.github.yulichang.toolkit.Constant;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@OrderGroup(groupBy = {"t.age","t.name"}, orderBys = {@OrderByIsAsc(column = "t.age"),
                @OrderByIsAsc(column = "t.name",isAsc = false)})
public class UserInfoTest implements Serializable {

    @QueryMatching(type = TypeEnums.eq)
    private String name;
    @QueryMatching(type = TypeEnums.is_not_null)
    private Integer age;
    @QueryMatching(type = TypeEnums.lt)
    private String email;

    @QueryMatching(matching = "t.name",type = TypeEnums.in)
    private List<String> names;

//    @QueryMatching(join = @JoinInfo(joinType = Constant.LEFT_JOIN,on = "user_roles r on r.user_id = t.id",select = {"r.role_id","r.user_id"}))
//    private Class<UserRoles> rolesClass = UserRoles.class;
//
//    @QueryMatching(alias = "r",type = TypeEnums.eq)
//    private Long roleId;


}
