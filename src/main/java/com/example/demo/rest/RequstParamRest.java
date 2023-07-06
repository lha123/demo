package com.example.demo.rest;


import cn.hutool.json.JSONUtil;
import com.example.demo.po.RequsetParamForm;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "公告模块RequsetParamForm", description = "公告模块RequsetParamForm", tags = {"公告模块RequsetParamForm"})
public class RequstParamRest {



    @PostMapping("/insert")
    @ApiOperation(value = "添加修改公告RequsetParamForm",notes = "添加修改公告RequsetParamForm")
    public void insert(@RequestBody @JsonView(value = RequsetParamForm.InsertRest.class) RequsetParamForm form){
        System.out.println(JSONUtil.toJsonPrettyStr(form));
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改公告RequsetParamForm",notes = "修改公告RequsetParamForm")
    @JsonView(value = RequsetParamForm.UpdateRest.class)
    public RequsetParamForm update(@RequestBody RequsetParamForm form){
        return form;
    }
}
