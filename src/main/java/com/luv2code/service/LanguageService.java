package com.luv2code.service;

import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

import com.luv2code.entity.Language;

@Service
@Transactional
public class LanguageService<T>
extends GenericDaoImpl<Language>
implements GenericDao<Language>  {

}
