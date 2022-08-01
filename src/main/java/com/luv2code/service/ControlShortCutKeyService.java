package com.luv2code.service;

import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

import com.luv2code.entity.ControlShortCutKey;

@Service
@Transactional
public class ControlShortCutKeyService<T>
extends GenericDaoImpl<ControlShortCutKey>
implements GenericDao<ControlShortCutKey>  {

}
