package com.prash.sdr.model.projections;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import com.prash.sdr.model.ModelType;

import springfox.documentation.schema.Model;

//Virtual Projection
@Projection(name = "ModelDetailsView", types = { Model.class })
public interface ModelDetailsView {
	@Value("#{target.name}")
	String getModelName();

	BigDecimal getPrice();

	@Value("#{target.manufacturer.name}")
	String getManufacturerName();

	ModelType getModelType();

	int getFrets();

	String getWoodType();

	@Value("#{target.manufacturer.name.split(' ')[0]} #{target.name}")
	String getFullName();

}
