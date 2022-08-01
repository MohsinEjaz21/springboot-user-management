package com.luv2code.service;

import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

import com.luv2code.entity.LoginSecurityQuestions;

@Service
@Transactional
public class LoginSecurityQuestionsService<T>
extends GenericDaoImpl<LoginSecurityQuestions>
implements GenericDao<LoginSecurityQuestions>  {

}
