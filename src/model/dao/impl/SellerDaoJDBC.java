package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import database.DB;
import exceptions.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao{

	private Connection conn;
	
	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Seller d) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Seller d) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Seller id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Seller getById(Integer id) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("SELECT seller.*,department.Name as DepName FROM seller "
					+ "INNER JOIN department ON seller.DepartmentId = department.Id WHERE seller.Id = ?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if(rs.next()) {
				Department dep = new Department();
				dep.setId(rs.getInt("DepartmentId"));
				dep.setName(rs.getString("DepName"));
				Seller seller = new Seller();
				seller.setId(rs.getInt("Id"));
				seller.setName(rs.getString("Name"));
				seller.setEmail(rs.getString("Email"));
				seller.setBaseSalary(rs.getDouble("BaseSalary"));
				seller.setBirthDate(rs.getDate("BirthDate"));
				seller.setDepartment(dep);
				
				return seller;
			}
			
			return null;
			
		}catch (SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(ps);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Seller> getAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
}