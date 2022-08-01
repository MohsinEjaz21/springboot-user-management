package com.luv2code.service;

import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

import com.luv2code.entity.UserPreferences;

@Service
@Transactional
public class UserPreferencesService<T>
extends GenericDaoImpl<UserPreferences>
implements GenericDao<UserPreferences>  {

}
