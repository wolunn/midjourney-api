package com.wwj.birthday.controller;

import com.wwj.birthday.domain.AjaxResult;
import com.wwj.birthday.domain.SubmitImagineDTO;
import com.wwj.birthday.service.OpenAIService;
import com.wwj.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mj")
public class OpenAIController {

    @Autowired
    private OpenAIService openAIService;

    @PostMapping("/submit/imagine")
    public AjaxResult submitImagine(@RequestBody SubmitImagineDTO submitImagineDTO){
        return AjaxResult.success("调用成功",openAIService.submitImagine(submitImagineDTO));
    }

    @GetMapping("/getInfo")
    public AjaxResult getInfo(String id){
        return AjaxResult.success("调用成功",openAIService.getInfo(id));
    }

}
