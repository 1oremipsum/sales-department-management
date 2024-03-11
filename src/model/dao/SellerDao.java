package model.dao;

import java.util.List;

import model.entities.Seller;

public interface SellerDao {
	void insert(Seller d);
	void update(Seller d);
	void deleteById(Seller id);
	Seller getById(Integer id);
	List<Seller> getAll();
}
