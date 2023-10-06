package com.wwj.birthday.service.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.wwj.birthday.domain.SubmitImagineDTO;
import com.wwj.birthday.service.OpenAIService;
import com.wwj.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * 登陆接口实现类
 */
@Service
public class OpenAIServiceImpl implements OpenAIService {

    @Value("${openai.url}")
    private String baseUrl;

    @Autowired
    private RedisCache redisCache;

    private static final String KEY_PREFIX_WWJ = "mj-wwj-store::";

    @Override
    public Object submitImagine(SubmitImagineDTO submitImagineDTO) {
        String result = HttpUtil.createPost(baseUrl+"/mj/submit/imagine")
                .contentType("application/json")
                .body(JSONUtil.toJsonStr(submitImagineDTO)).execute().body();
        Map entries = JSONUtil.parseObj(result);
        return entries;
    }

    @Override
    public Object getInfo(String id) {
        Map<String, Object> cacheMap = redisCache.getCacheMap(getRedisKeyWWJ(id));
        return cacheMap;
    }

    private String getRedisKeyWWJ(String id) {
        return KEY_PREFIX_WWJ + id;
    }
}
