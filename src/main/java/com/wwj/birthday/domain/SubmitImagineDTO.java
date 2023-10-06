package com.wwj.birthday.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


@Data
@EqualsAndHashCode(callSuper = true)
public class SubmitImagineDTO extends BaseSubmitDTO {

	private static final long serialVersionUID = 1L;

	private String prompt;

	private List<String> base64Array;

	private String base64;

}
