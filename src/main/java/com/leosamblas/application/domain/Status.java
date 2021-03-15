package com.leosamblas.application.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public enum Status {

	@JsonProperty(value = "Ativo")
	ATIVO(2L, "Ativo"),

	@JsonProperty(value = "Inativo")
	INATIVO(4L, "Inativo");

	private Long id;
	private String status;

	Status(Long id, String status) {
		this.id = id;
		this.status = status;
	}

	public static Status fromStatus(String status) {

		if (status != null) {

			for (Status s : Status.values()) {

				if (s.getStatus().equalsIgnoreCase(status)) {
					return s;
				}
			}
		}
		return null;
	}

	public static Status fromKey(Long id) {

		if (id != null) {

			for (Status s : Status.values()) {

				if (s.getId().equals(id)) {
					return s;
				}
			}
		}
		return null;
	}
}
