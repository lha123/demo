package com.example.demo.po;

import com.example.demo.annotation.ChooseMapper;
import com.example.demo.annotation.QueryMatching;
import com.example.demo.enums.TypeEnums;
import com.example.demo.mapper.UserInfoMapper;
import lombok.*;

import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@ChooseMapper(mapperClass = UserInfoMapper.class,selectAsClass = UserInfoRolesVo.class)
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
