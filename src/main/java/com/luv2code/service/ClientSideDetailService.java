package com.luv2code.service;

import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

import com.luv2code.entity.ClientSideDetail;

@Service
@Transactional
public class ClientSideDetailService<T>
extends GenericDaoImpl<ClientSideDetail>
implements GenericDao<ClientSideDetail>  {

}
