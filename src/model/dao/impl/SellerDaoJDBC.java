package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("DepartmentId"));
		dep.setName(rs.getString("DepName"));
		return dep;
	}
	
	private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
		Seller sel = new Seller();
		sel.setId(rs.getInt("Id"));
		sel.setName(rs.getString("Name"));
		sel.setEmail(rs.getString("Email"));
		sel.setBaseSalary(rs.getDouble("BaseSalary"));
		sel.setBirthDate(rs.getDate("BirthDate"));
		sel.setDepartment(dep);
		return sel;
	}
	
	@Override
	public void insert(Seller s) {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("INSERT INTO seller "
					+ "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
					+ "VALUES (?,?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, s.getName());
			ps.setString(2, s.getEmail());
			ps.setDate(3, new java.sql.Date(s.getBirthDate().getTime()));
			ps.setDouble(4, s.getBaseSalary());
			ps.setInt(5, s.getDepartment().getId());
			
			int rowsAffected = ps.executeUpdate();
			
			if(rowsAffected > 0) {
				ResultSet rs = ps.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);
					s.setId(id);
				}
				DB.closeResultSet(rs);
			}else {
				throw new DbException("Unexpected error! No rows affectd!");
			}
			
		}catch (SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(ps);
		}
	}

	@Override
	public void update(Seller s) {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("UPDATE seller "
					+ "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? "
					+ "WHERE Id = ?");
			
			ps.setString(1, s.getName());
			ps.setString(2, s.getEmail());
			ps.setDate(3, new java.sql.Date(s.getBirthDate().getTime()));
			ps.setDouble(4, s.getBaseSalary());
			ps.setInt(5, s.getDepartment().getId());
			ps.setInt(6, s.getId());
			
			ps.executeUpdate();
			
		}catch (SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(ps);
		}
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
				Department dep = instantiateDepartment(rs);
				Seller seller = instantiateSeller(rs, dep);
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
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("SELECT seller.*,department.Name as DepName "
					+ "FROM seller INNER JOIN department ON seller.DepartmentId = department.Id "
					+ "ORDER BY Name");
			rs = ps.executeQuery();
			//o resultado pode ser igual a 0 ou >= 1, então use while para verificar...
			List<Seller> list = new ArrayList<>();
			//map é necessário para evitar a criação de objetos iguais (1 departamento tem 1 ou mais vendedores)
			Map<Integer, Department> map = new HashMap<>();
			
			while(rs.next()) {
				Department d = map.get(rs.getInt("DepartmentId"));
				if(d == null) {	// verificando se dep já existe no map
					d = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), d);
				}
				Seller s = instantiateSeller(rs, d);
				list.add(s);
			}
			
			return list;
			
		}catch (SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(ps);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Seller> getByDepartment(Department dep) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("SELECT seller.*,department.Name as DepName "
					+ "FROM seller INNER JOIN department ON seller.DepartmentId = department.Id "
					+ "WHERE DepartmentId = ? ORDER BY Name");
			ps.setInt(1, dep.getId());
			rs = ps.executeQuery();
			//o resultado pode ser igual a 0 ou >= 1, então use while para verificar...
			List<Seller> list = new ArrayList<>();
			//map é necessário para evitar a criação de objetos iguais (1 departamento tem 1 ou mais vendedores)
			Map<Integer, Department> map = new HashMap<>();
			
			while(rs.next()) {
				Department d = map.get(rs.getInt("DepartmentId"));
				if(d == null) {	// verificando se dep já existe no map
					d = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), d);
				}
				Seller s = instantiateSeller(rs, d);
				list.add(s);
			}
			
			return list;
			
		}catch (SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(ps);
			DB.closeResultSet(rs);
		}
	}
	
}
