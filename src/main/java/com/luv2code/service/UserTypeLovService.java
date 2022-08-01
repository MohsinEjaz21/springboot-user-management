package com.luv2code.service;

import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

import com.luv2code.entity.UserTypeLov;

@Service
@Transactional
public class UserTypeLovService<T>
extends GenericDaoImpl<UserTypeLov>
implements GenericDao<UserTypeLov>  {

}
