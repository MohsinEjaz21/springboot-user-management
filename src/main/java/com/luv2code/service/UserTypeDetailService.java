package com.luv2code.service;

import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

import com.luv2code.entity.UserTypeDetail;

@Service
@Transactional
public class UserTypeDetailService<T>
extends GenericDaoImpl<UserTypeDetail>
implements GenericDao<UserTypeDetail>  {

}
