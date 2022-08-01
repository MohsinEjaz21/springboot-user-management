package com.luv2code.service;

import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

import com.luv2code.entity.Project;

@Service
@Transactional
public class ProjectService<T>
extends GenericDaoImpl<Project>
implements GenericDao<Project>  {

}
