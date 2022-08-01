package com.luv2code.service;

import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

import com.luv2code.entity.Applications;

@Service
@Transactional
public class ApplicationsService<T>
extends GenericDaoImpl<Applications>
implements GenericDao<Applications>  {

}
