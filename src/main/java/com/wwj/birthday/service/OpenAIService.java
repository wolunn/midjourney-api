package com.wwj.birthday.service;

import com.wwj.birthday.domain.AjaxResult;
import com.wwj.birthday.domain.SubmitImagineDTO;
import com.wwj.birthday.domain.SysUser;

public interface OpenAIService {

    Object submitImagine(SubmitImagineDTO submitImagineDTO);

    Object getInfo(String id);
}
