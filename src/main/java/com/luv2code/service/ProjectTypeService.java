package com.luv2code.service;

import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

import com.luv2code.entity.ProjectType;

@Service
@Transactional
public class ProjectTypeService<T>
extends GenericDaoImpl<ProjectType>
implements GenericDao<ProjectType>  {

}
