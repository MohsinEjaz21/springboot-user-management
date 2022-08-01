package com.luv2code.service;

import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

import com.luv2code.entity.DepartmentRoles;

@Service
@Transactional
public class DepartmentRolesService<T>
extends GenericDaoImpl<DepartmentRoles>
implements GenericDao<DepartmentRoles>  {

}
