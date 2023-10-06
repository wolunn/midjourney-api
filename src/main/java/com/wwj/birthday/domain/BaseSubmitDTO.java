package com.wwj.birthday.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseSubmitDTO {

	protected String state;

	protected String notifyHook;

	protected String parentId;
}
