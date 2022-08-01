package com.luv2code.service;

import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

import com.luv2code.entity.FormLabelConstantTranslation;

@Service
@Transactional
public class FormLabelConstantTranslationService<T>
extends GenericDaoImpl<FormLabelConstantTranslation>
implements GenericDao<FormLabelConstantTranslation>  {

}
