package application;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {
	private static SellerDao sellerDao = DaoFactory.createSellerDao();
	private static DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
	
	public static void main(String[] args) {
		System.out.println("* Get By Id Test:");
		getByIdTest();
		System.out.println();
		
		System.out.println("* Get By Department Test:");
		getByDepartmentTest();
		System.out.println();
		
		System.out.println("* Seller Get All Test:");
		sellerGetAllTest();
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
	
}
