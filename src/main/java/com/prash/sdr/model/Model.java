package com.prash.sdr.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.prash.sdr.config.ApplicationContextProvider;
import com.prash.sdr.model.custom.validator.CustomAnnotatonProcessor;
import com.prash.sdr.model.custom.validator.ModelAnnotationProcessor;
import com.prash.sdr.model.custom.validator.NotNullOnDemand;

@Entity
@NamedQuery(name="Model.findAllModelsByType", query="select m from Model m where m.modelType.name = :name")
public class Model {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@NotNull
	private String name;	
	private BigDecimal price;
	private int frets;
	
	@Column(name="WOODTYPE")
	private String woodType;
	
	@Column(name="YEARFIRSTMADE")
	private Date yearFirstMade;
	
	@ManyToOne
	@JoinColumn(name="manufacturer_id")
	@JsonBackReference
	private Manufacturer manufacturer;

	@ManyToOne
	@JoinColumn(name="MODELTYPE_ID")
	@JsonManagedReference
	private ModelType modelType;

	@NotNullOnDemand
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@NotNullOnDemand
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@NotNullOnDemand
	public int getFrets() {
		return frets;
	}

	public void setFrets(int frets) {
		this.frets = frets;
	}

	@NotNullOnDemand
	public String getWoodType() {
		return woodType;
	}

	public void setWoodType(String woodType) {
		this.woodType = woodType;
	}

	public Date getYearFirstMade() {
		return yearFirstMade;
	}

	public void setYearFirstMade(Date yearFirstMade) {
		this.yearFirstMade = yearFirstMade;
	}

	public Manufacturer getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(Manufacturer manufacturer) {
		this.manufacturer = manufacturer;
	}

	public ModelType getModelType() {
		return modelType;
	}

	public void setModelType(ModelType modelType) {
		this.modelType = modelType;
	}

	public Long getId() {
		return id;
	}
	
	
	
	
	@Override
	public String toString() {
		return "Model [id=" + id + ", name=" + name + ", price=" + price + ", frets=" + frets + ", woodType=" + woodType
				+ ", yearFirstMade=" + yearFirstMade + ", manufacturer=" + manufacturer + ", modelType=" + modelType
				+ "]";
	}

	@PrePersist
	public void prePersist()	{
		System.out.println(this.toString());
		CustomAnnotatonProcessor<Model> annProcessor = ApplicationContextProvider.getContext().getBean(ModelAnnotationProcessor.class);
		annProcessor.validateObjectInstance(this);
	}
}
