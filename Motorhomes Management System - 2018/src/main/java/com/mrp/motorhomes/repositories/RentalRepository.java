package com.mrp.motorhomes.repositories;

import com.mrp.motorhomes.model.Rental;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

//#Paul
//Repository connecting to the database of the Rental model
public class RentalRepository extends CrudRepository<Rental> {
	
	//Singleton Pattern
	private static RentalRepository instance;
	public static RentalRepository getInstance(){
		if(instance == null){
			instance = new RentalRepository();
		}
		return instance;
	}
    private RentalRepository(){}
	
	//Add a rental in the database
	@Override
	public int create(Rental item) {
		refreshConnection();
		try {
			preparedStatement = connection.prepareStatement(
					"INSERT INTO rentals(customerId, motorhomeId, price, startDate, endDate, pickUp, dropOff, paid) " +
						 "VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
			preparedStatement.setInt(1, item.getCustomerId());
			preparedStatement.setInt(2, item.getMotorhomeId());
			preparedStatement.setDouble(3, item.getPrice());
			preparedStatement.setDate(4, Date.valueOf(item.getStartDate()));
			preparedStatement.setDate(5, Date.valueOf(item.getEndDate()));
			preparedStatement.setString(6, item.getPickUp());
			preparedStatement.setString(7, item.getDropOff());
			preparedStatement.setBoolean(8, item.isPaid());
			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			//sort the records by id, in descending order, then get the first entry, that is,
			//the last entry added to the database
			preparedStatement = connection.prepareStatement(
					"SELECT id FROM rentals ORDER BY id DESC LIMIT 1");
			resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()){
				return resultSet.getInt("id");
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return -1;
	}
	
	//Returns the list of all rentals in the database
	@Override
	public ArrayList<Rental> readAll() {
		refreshConnection();
		ArrayList<Rental> rentals = new ArrayList<>();
		try {
			preparedStatement = connection.prepareStatement(
					"SELECT rentals.id, rentals.customerId, customers.firstName, customers.lastName, " +
							"rentals.motorhomeId, motorhomes.brand, motorhomes.model, rentals.price, rentals.startDate, " +
							"rentals.endDate, rentals.pickUp, rentals.dropOff, rentals.paid FROM rentals " +
							"INNER JOIN customers ON rentals.customerId=customers.id " +
							"INNER JOIN motorhomes ON rentals.motorhomeId=motorhomes.id");
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				rentals.add(new Rental(
						resultSet.getInt("id"),
						resultSet.getInt("customerId"),
						resultSet.getString("firstName") + " " + resultSet.getString("lastName"),
						resultSet.getInt("motorhomeId"),
						resultSet.getString("brand") + " " + resultSet.getString("model"),
						resultSet.getDouble("price"),
						resultSet.getDate("startDate").toLocalDate(),
						resultSet.getDate("endDate").toLocalDate(),
						resultSet.getString("pickUp"),
						resultSet.getString("dropOff"),
						resultSet.getBoolean("paid")));
			}
			return rentals;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	//gets a rental by its id
	@Override
	public Rental read(int id) {
		refreshConnection();
		Rental rental = null;
		try {
			preparedStatement = connection.prepareStatement(
					"SELECT rentals.id, rentals.customerId, customers.firstName, customers.lastName, " +
							"rentals.motorhomeId, motorhomes.brand, motorhomes.model, rentals.price, rentals.startDate, " +
							"rentals.endDate, rentals.pickUp, rentals.dropOff, rentals.paid FROM rentals " +
							"INNER JOIN customers ON rentals.customerId=customers.id " +
							"INNER JOIN motorhomes ON rentals.motorhomeId=motorhomes.id WHERE rentals.id=?");
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();

			if(resultSet.next()) {
				rental = new Rental(
						resultSet.getInt("id"),
						resultSet.getInt("customerId"),
						resultSet.getString("firstName") + " " + resultSet.getString("lastName"),
						resultSet.getInt("motorhomeId"),
						resultSet.getString("brand") + " " + resultSet.getString("model"),
						resultSet.getDouble("price"),
						resultSet.getDate("startDate").toLocalDate(),
						resultSet.getDate("endDate").toLocalDate(),
						resultSet.getString("pickUp"),
						resultSet.getString("dropOff"),
						resultSet.getBoolean("paid"));
				//since reading a single item is usually used in the details page, add the accessories to the rental
				rental.setAccessories(AccessoryRepository.getInstance().read(rental.getId()));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("rental repository - read rental: " + rental);
		return rental;
	}
	
	//Updates a rental in the database
	@Override
	public void update(Rental item) {
		refreshConnection();
		try {
			preparedStatement = connection.prepareStatement(
					"UPDATE rentals SET customerId=?, motorhomeId=?, price=?, startDate=?, endDate=?, pickUp=?, dropOff=?, paid=? WHERE id=?");
			preparedStatement.setInt(1, item.getCustomerId());
			preparedStatement.setInt(2, item.getMotorhomeId());
			preparedStatement.setDouble(3, item.getPrice());
			preparedStatement.setDate(4, Date.valueOf(item.getStartDate()));
			preparedStatement.setDate(5, Date.valueOf(item.getEndDate()));
			preparedStatement.setString(6, item.getPickUp());
			preparedStatement.setString(7, item.getDropOff());
			preparedStatement.setBoolean(8, item.isPaid());
            preparedStatement.setInt(9, item.getId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//Deletes a rental from the database
	@Override
	public void delete(int id) {
		refreshConnection();
		try {
			preparedStatement = connection.prepareStatement("DELETE FROM rentals WHERE id = ?");
			preparedStatement.setInt(1, id);

			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}