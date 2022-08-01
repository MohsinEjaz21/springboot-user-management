package com.luv2code.service;

import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

import com.luv2code.entity.Holidays;

@Service
@Transactional
public class HolidaysService<T>
extends GenericDaoImpl<Holidays>
implements GenericDao<Holidays>  {

}
