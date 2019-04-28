package com.mrp.motorhomes.repositories;

import com.mrp.motorhomes.model.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

//#Marius
//Repository connecting to the database of the Customer model
public class CustomerRepository extends CrudRepository<Customer> {
	
	//Singleton Pattern
	private static CustomerRepository instance;
	public static CustomerRepository getInstance(){
		if(instance == null){
			instance = new CustomerRepository();
		}
		return instance;
	}
	private CustomerRepository(){}
	
	//Add a customer in the database
	@Override
	public int create(Customer item) {
		refreshConnection();
		try {
			preparedStatement = connection.prepareStatement(
					"INSERT INTO customers(firstName, lastName, email, address, phone, ssn) VALUES (? ,? ,? ,? ,? ,?)");
			preparedStatement.setString(1, item.getFirstName());
			preparedStatement.setString(2, item.getLastName());
			preparedStatement.setString(3, item.getEmail());
			preparedStatement.setString(4, item.getAddress());
			preparedStatement.setString(5, item.getPhone());
			preparedStatement.setString(6, item.getSsn());
			preparedStatement.execute();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		//the id of the customer is not needed right after they have been added
		//to the database
		return -1;
	}
	
	//Returns the list of all customers in the database
	@Override
	public ArrayList<Customer> readAll() {
		refreshConnection();
		ArrayList<Customer> customers = new ArrayList<>();
		
		try {
			preparedStatement = connection.prepareStatement("SELECT * FROM customers");
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()){
				customers.add(new Customer(
						resultSet.getInt("id"),
						resultSet.getString("firstName"),
						resultSet.getString("lastName"),
						resultSet.getString("email"),
						resultSet.getString("address"),
						resultSet.getString("phone"),
						resultSet.getString("ssn")
				));
			}
			return customers;
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	//gets a customer by their id
	@Override
	public Customer read(int id) {
		refreshConnection();
		try {
			preparedStatement = connection.prepareStatement("SELECT  * FROM customers WHERE  id=?");
			preparedStatement.setInt(1, id);
			
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()){
				return new Customer(
						resultSet.getInt("id"),
					    resultSet.getString("firstName"),
					    resultSet.getString("lastName"),
					    resultSet.getString("email"),
					    resultSet.getString("address"),
					    resultSet.getString("phone"),
					    resultSet.getString("ssn")
				);
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	//Updates a customer in the database
	@Override
	public void update(Customer item) {
		refreshConnection();
		try {
			preparedStatement = connection.prepareStatement(
					"UPDATE customers SET firstName=?, lastName=?, email=?, address=?, phone=?, ssn=? WHERE id=?");
			preparedStatement.setString(1, item.getFirstName());
			preparedStatement.setString(2, item.getLastName());
			preparedStatement.setString(3, item.getEmail());
			preparedStatement.setString(4, item.getAddress());
			preparedStatement.setString(5, item.getPhone());
			preparedStatement.setString(6, item.getSsn());
			preparedStatement.setInt   (7, item.getId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//Deletes a customer from the database
	@Override
	public void delete(int id) {
		refreshConnection();
		try{
			preparedStatement = connection.prepareStatement("DELETE FROM customers WHERE id=?");
			preparedStatement.setInt(1, id);
			preparedStatement.execute();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}

}