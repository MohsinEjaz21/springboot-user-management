package com.luv2code.service;

import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

import com.luv2code.entity.Client;

@Service
@Transactional
public class ClientService<T>
extends GenericDaoImpl<Client>
implements GenericDao<Client>  {

}
