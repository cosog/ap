package com.gao.utils;



import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
//import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
	private static SessionFactory sf;
	static {
		Configuration conf = new Configuration().configure();
		ServiceRegistry sr = new StandardServiceRegistryBuilder().applySettings(conf.getProperties()).build();
		//sf = new AnnotationConfiguration().configure().buildSessionFactory();
		sf=conf.buildSessionFactory(sr);
	}

	public static SessionFactory getSessionFactory() {
		return sf;
	}

	
}
