package edu.dmacc.demo;

import java.util.List;
import java.util.Objects;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import edu.dmacc.entity.Student;

public class CreateStudentDemo {

	public static void main(String[] args) {
		
		//Create Session Factory
		SessionFactory factory = new Configuration()
									.configure("hibernate.cfg.xml")
									.addAnnotatedClass(Student.class)
									.buildSessionFactory();
		
		//Create Session
		Session session = factory.getCurrentSession();
		
		try {
			
			//CREATE STUDENTS 
			System.out.println("Creating a new student object...");
			Student tempStudent1 = new Student ("George", "Jones", "countryMusic12@dmacc.edu");
			Student tempStudent2 = new Student ("James", "Rogers", "James@dmacc.edu");
			Student tempStudent3 = new Student ("John", "Doe", "John@dmacc.edu");
			
			//start transaction
			session.beginTransaction();
			
			//save student objects
			System.out.println("Saving the student...");
			session.save(tempStudent1);
			session.save(tempStudent2);
			session.save(tempStudent3);
			
			//commit transaction
			session.getTransaction().commit();
			
		
			//RETRIEVE STUDENT
			/*This section simply queries the database and
			returns a result to the console display.*/
			
			//get new session, start transaction
			session = factory.getCurrentSession();
			session.beginTransaction();
			
			//retrieve student
			Student myStudent = session.get(Student.class, tempStudent1.getId());
			System.out.println("Get complete: " + myStudent);
			
			//commit transaction
			session.getTransaction().commit();	
			
			
			//Other queries
			/*This section uses other methods of querying
			the database and retreiving objects, also, 
			a list is used to display database contents to
			the console.*/
			session = factory.getCurrentSession();
			session.beginTransaction();
			
			//query
			List<Student> theStudents = session.createQuery("from Student").getResultList();
			
			//display
			displayStudents(theStudents);  //created by using refactor/extract method
			
			//students last name "Doe"
			theStudents = session.createQuery("from Student s where s.lastName='Doe'").getResultList();
			
			System.out.println("\n\nThe students with last name, 'Doe'");
			displayStudents(theStudents);
			
			//students last name "Doe" or first name "James"
			theStudents = session.createQuery("from Student s where s.lastName='Doe' OR s.firstName='James'").getResultList();
			
			System.out.println("\n\nThe students with last name, 'Doe' OR first name 'James'");
			displayStudents(theStudents);
			
			session.getTransaction().commit();
		
			
			//UPDATE STUDENT
			/*This section uses a few different methods
			 * of retreiving and updating database objects*/
			
			int studentId = 2;
			
			session = factory.getCurrentSession();
			session.beginTransaction();
			
			System.out.println("Getting student with id: " + studentId);
			
			Student theStudent = session.get(Student.class, studentId);
			
			System.out.println("Updating student...");
			theStudent.setFirstName("Bobby");
			
			session.getTransaction().commit();
			
			//alternative method
			session = factory.getCurrentSession();
			session.beginTransaction();
			
			System.out.println("Update one");
			
			session.createQuery("update Student set email='george@dmacc.edu'" + "where id=1").executeUpdate();
			
			session.getTransaction().commit();
			
			
			//DELETE ALL
			/*session = factory.getCurrentSession();
			session.beginTransaction();
			
			List<Student> allStudents = session.createQuery("from Student").getResultList();
			
			for (Student student : allStudents) {
				session.delete(student);
			}
			session.getTransaction().commit();*/
			
			System.out.println("Done!");
		}
		finally {
			factory.close();
		}

	}

	private static void displayStudents(List<Student> theStudents) {
		for (Student tempStudent : theStudents) {
			System.out.println(tempStudent);
		}
	}

}
