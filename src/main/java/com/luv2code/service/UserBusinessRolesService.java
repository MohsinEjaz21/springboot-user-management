package com.luv2code.service;

import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

import com.luv2code.entity.UserBusinessRoles;

@Service
@Transactional
public class UserBusinessRolesService<T>
extends GenericDaoImpl<UserBusinessRoles>
implements GenericDao<UserBusinessRoles>  {

}
