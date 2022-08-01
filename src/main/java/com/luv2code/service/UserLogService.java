package com.luv2code.service;

import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

import com.luv2code.entity.UserLog;

@Service
@Transactional
public class UserLogService<T>
extends GenericDaoImpl<UserLog>
implements GenericDao<UserLog>  {

}
