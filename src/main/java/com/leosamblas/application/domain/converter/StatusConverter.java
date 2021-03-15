package com.leosamblas.application.domain.converter;

import javax.persistence.AttributeConverter;

import com.leosamblas.application.domain.Status;

public class StatusConverter implements AttributeConverter<Status, Long> {

	@Override
	public Long convertToDatabaseColumn(Status attribute) {
		return attribute.getId();
	}

	@Override
	public Status convertToEntityAttribute(Long dbData) {
		return Status.fromKey(dbData);
	}

}
