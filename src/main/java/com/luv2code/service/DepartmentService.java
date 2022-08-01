package com.luv2code.service;

import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

import com.luv2code.entity.Department;

@Service
@Transactional
public class DepartmentService<T>
extends GenericDaoImpl<Department>
implements GenericDao<Department>  {

}
