package com.example.demo.po;

import com.example.demo.annotation.*;

import com.example.demo.enums.TypeEnums;

import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.util.List;


@Data
@FieldNameConstants
@OrderGroup(groupBy = {UserInfo.Fields.roleId},orderBys = {@OrderByIsAsc(isAsc = false,column = UserInfo.Fields.roleId)})
@YkcEntityMapping(join = {
        @JoinMapping(joinClass = UserRoles.class,
                thisField = UserRoles.Fields.id,joinField = UserInfo.Fields.roleId,
                select = {UserRoles.Fields.name +" as ee"}),
        @JoinMapping(joinClass = UserRoles.class,joinAlias = "r",
                thisField = UserRoles.Fields.id,joinField = UserInfo.Fields.roleId)
})

public class UserInfoTest {

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
