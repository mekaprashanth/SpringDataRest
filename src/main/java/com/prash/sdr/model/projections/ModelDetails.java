package com.prash.sdr.model.projections;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.rest.core.config.Projection;

import com.prash.sdr.model.Manufacturer;
import com.prash.sdr.model.Model;
import com.prash.sdr.model.ModelType;

@Projection(name="ModelDetails", types=Model.class)
public interface ModelDetails {
	Long getId();
	String getName();	
	BigDecimal getPrice();
	int getFrets();
	ModelType getModelType();
	String getWoodType();
	Date getYearFirstMade();
	Manufacturer getManufacturer();
}
