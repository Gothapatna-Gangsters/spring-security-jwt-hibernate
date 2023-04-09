package com.practice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.practice.models.User;
import com.practice.util.HibernateUtil;

@Service
public class SecurityService {
	private Criteria criteria = null;
	
	public Optional<User> getByUserName(String userName) {
		System.out.println("Inside getByUserName()" + userName);
		Session session = null;
		
		List<Optional<User>> user = new ArrayList<>();
		List<User> users = new ArrayList<>();
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			
			criteria = session.createCriteria(User.class);
			criteria.add(Restrictions.eq("userName", userName));
			users = criteria.list();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("user is: "+users.get(0).getUserName());
		    if(session != null)
		        session.close();
		}
		
		user = users.stream()
	                .map((o) -> Optional.ofNullable(o))
	                .collect(Collectors.toList());
		
		System.out.println("user is: "+user.get(0).get().getUserName());
		
		return user.get(0);
		
	}

}
