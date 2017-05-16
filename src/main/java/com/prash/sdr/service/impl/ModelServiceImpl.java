/**
 * 
 */
package com.prash.sdr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prash.sdr.model.Model;
import com.prash.sdr.repository.ModelJpaRepository;
import com.prash.sdr.service.ModelService;

/**
 * @author f2u85i8
 *
 */
@Service("model")
public class ModelServiceImpl implements ModelService {


	@Autowired
	ModelJpaRepository modelJpaRepository;
	
	@Override
	public List<Model> getAllModels() {
		return modelJpaRepository.findAll();
	}

}
