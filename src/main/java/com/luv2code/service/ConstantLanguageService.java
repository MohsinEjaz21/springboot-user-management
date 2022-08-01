package com.luv2code.service;

import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

import com.luv2code.entity.ConstantLanguage;

@Service
@Transactional
public class ConstantLanguageService<T>
extends GenericDaoImpl<ConstantLanguage>
implements GenericDao<ConstantLanguage>  {

}
