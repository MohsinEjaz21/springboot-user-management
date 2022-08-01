package com.luv2code.service;

import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

import com.luv2code.entity.ProjectTeam;

@Service
@Transactional
public class ProjectTeamService<T>
extends GenericDaoImpl<ProjectTeam>
implements GenericDao<ProjectTeam>  {

}
