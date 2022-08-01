package com.luv2code.service;

import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

import com.luv2code.entity.BusinessApplicationRole;

@Service
@Transactional
public class BusinessApplicationRoleService<T>
extends GenericDaoImpl<BusinessApplicationRole>
implements GenericDao<BusinessApplicationRole>  {

}
