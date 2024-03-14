package model.dao;

import java.util.List;

import model.entities.Department;
import model.entities.Seller;

public interface SellerDao {
	void insert(Seller s);
	void update(Seller s);
	void deleteById(Integer id);
	Seller getById(Integer id);
	List<Seller> getAll();
	List<Seller> getByDepartment(Department dep);
}
