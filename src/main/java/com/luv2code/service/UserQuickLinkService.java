package com.luv2code.service;

import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

import com.luv2code.entity.UserQuickLink;

@Service
@Transactional
public class UserQuickLinkService<T>
extends GenericDaoImpl<UserQuickLink>
implements GenericDao<UserQuickLink>  {

}
