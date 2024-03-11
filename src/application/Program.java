package application;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.dao.SellerDao;
import model.entities.Seller;

public class Program {
	private static SellerDao sellerDao = DaoFactory.createSellerDao();
	private static DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
	
	public static void main(String[] args) {
		getByIdTest();
	}
	
	private static void getByIdTest() {
		Seller seller = sellerDao.getById(3);
		System.out.println(seller);
	}

}
