package com.luv2code.service;

import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

import com.luv2code.entity.ApplicationRoles;

@Service
@Transactional
public class ApplicationRolesService<T>
extends GenericDaoImpl<ApplicationRoles>
implements GenericDao<ApplicationRoles>  {

}
