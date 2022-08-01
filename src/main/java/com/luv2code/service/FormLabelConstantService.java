package com.luv2code.service;

import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

import com.luv2code.entity.FormLabelConstant;

@Service
@Transactional
public class FormLabelConstantService<T>
extends GenericDaoImpl<FormLabelConstant>
implements GenericDao<FormLabelConstant>  {

}
