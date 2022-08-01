package com.luv2code.service;

import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

import com.luv2code.entity.BusinessRoles;

@Service
@Transactional
public class BusinessRolesService<T>
extends GenericDaoImpl<BusinessRoles>
implements GenericDao<BusinessRoles>  {

}
