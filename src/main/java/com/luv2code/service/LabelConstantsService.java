package com.luv2code.service;

import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

import com.luv2code.entity.LabelConstants;

@Service
@Transactional
public class LabelConstantsService<T>
extends GenericDaoImpl<LabelConstants>
implements GenericDao<LabelConstants>  {

}
