package com.luv2code.service;

import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

import com.luv2code.entity.Version;

@Service
@Transactional
public class VersionService<T>
extends GenericDaoImpl<Version>
implements GenericDao<Version>  {

}
