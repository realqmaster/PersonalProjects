package it.shopping.h2;

import org.hibernate.Session;

import it.shopping.models.ProductType;

public class HibTest {
	public static void main(String[] args) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        ProductType sample = new ProductType("A new Product");
        session.save(sample);        
        session.getTransaction().commit();		
	}
}
