package com.luv2code.service;

import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

import com.luv2code.entity.UserDepartment;

@Service
@Transactional
public class UserDepartmentService<T>
extends GenericDaoImpl<UserDepartment>
implements GenericDao<UserDepartment>  {

}
