package com.luv2code.service;

import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

import com.luv2code.entity.WorkingDays;

@Service
@Transactional
public class WorkingDaysService<T>
extends GenericDaoImpl<WorkingDays>
implements GenericDao<WorkingDays>  {

}
