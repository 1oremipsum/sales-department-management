package model.dao;

import java.util.List;

import model.entities.Department;

public interface DepartmentDao {
	void insert(Department d);
	void update(Department d);
	void deleteById(Integer id);
	Department getById(Integer id);
	List<Department> getAll();
}
