package com.luv2code.service;

import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

import com.luv2code.entity.Users;

@Service
@Transactional
public class UsersService<T>
extends GenericDaoImpl<Users>
implements GenericDao<Users>  {

}
