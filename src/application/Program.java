package application;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {
	private static SellerDao sellerDao = DaoFactory.createSellerDao();
	private static DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
	private static Scanner scan = new Scanner(System.in);
	
	public static void main(String[] args) {
		/*
		System.out.println("* Get By Id Test:");
		getByIdTest();
		
		System.out.println("* Get By Department Test:");
		getByDepartmentTest();
		
		System.out.println("* Seller Get All Test:");
		sellerGetAllTest();
		
		System.out.println("* Seller Insert Test: ");
		sellerInsertTest();
		
		System.out.println("* Seller Update Test: ");
		sellerUpdateTest();
		*/
		
		System.out.println("* Seller Delete Test:");
		sellerDeleteTest();
	}
	
	private static void getByIdTest() {
		Seller seller = sellerDao.getById(3);
		System.out.println(seller);
	}
	
	private static void getByDepartmentTest() {
		Department department = new Department(2, null);
		List<Seller> list = sellerDao.getByDepartment(department);
		list.forEach(System.out::println);
	}

	private static void sellerGetAllTest() {
		List<Seller> list = sellerDao.getAll();
		list.forEach(System.out::println);
	}
	
	private static void sellerInsertTest() {
		Seller seller = new Seller(null, "Allan", "allan@gmail.com", new Date(), 4000.0);
		seller.setDepartment(new Department(2, "Electronics"));
		sellerDao.insert(seller);
		System.out.println("Inserted! New id = " + seller.getId());
	}
	
	private static void sellerUpdateTest() {
		Seller seller = new Seller();
		seller = sellerDao.getById(9);
		seller.setName("Allan Terzi");
		sellerDao.update(seller);
		System.out.println("Update Completed! Updated seller id: " + seller.getId());
	}
	
	private static void sellerDeleteTest() {
		System.out.print("Enter id for delete test: ");
		int id = scan.nextInt();
		Seller seller = new Seller();
		seller = sellerDao.getById(id);
		sellerDao.deleteById(id);
		System.out.println("Delete completed! Deleted seller name: " + seller.getName());
	}
	
}
