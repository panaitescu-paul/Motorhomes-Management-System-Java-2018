package com.mrp.motorhomes.repositories;

import com.mrp.motorhomes.model.Accessory;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

//#Paul
//Repository connecting to the database of the Accessory model
//We typically need to read multiple Accessories from the database at a time using a rental id,
//but the CrudRepository interface does not allow retrieving multiple items by an id. Since adding another
//method in the interface just for reading multiple entries by id just for this class would have been redundant,
//and a loop inside the controller for each accessory would have been inefficient,
//we set this to have an array list of accessories as the generic object
public class AccessoryRepository extends CrudRepository<ArrayList<Accessory>> {

	//Singleton Pattern
	private static AccessoryRepository instance;
	public static AccessoryRepository getInstance(){
		if(instance == null){
			instance = new AccessoryRepository();
		}
		return instance;
	}
	private AccessoryRepository(){}
	
	//Add a list of accessories in the database
	@Override
	public int create(ArrayList<Accessory> accessories) {
		refreshConnection();
		try {
			preparedStatement = connection.prepareStatement(
					"INSERT INTO rented_accessories(title, rentalId) VALUES (?, ?)");
			for(Accessory accessory : accessories){
				preparedStatement.setString(1, accessory.getTitle());
				preparedStatement.setInt(2, accessory.getRentalId());
				preparedStatement.addBatch();
			}
			preparedStatement.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//the id returning is not available in this repository
		return -1;
	}
	
	//Returns the list of all accessories in the database
	//This function is not supported by this class, for the aforementioned reason
	@Override
	public ArrayList<ArrayList<Accessory>> readAll() {
		throw new UnsupportedOperationException();
	}
	
	//gets a list of accessories having a specific rentalId
	@Override
	public ArrayList<Accessory> read(int id) {
		refreshConnection();
		ArrayList<Accessory> accessories = new ArrayList<>();
		try {
			preparedStatement = connection.prepareStatement("SELECT * FROM rented_accessories WHERE rentalId=?");
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()){
				accessories.add(new Accessory(
						resultSet.getString("title"),
						id));
			}
			return accessories;
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	//**UNUSED**
	@Override
	public void update(ArrayList<Accessory> item) {
		refreshConnection();
		throw new UnsupportedOperationException();
	}
	
	//**UNUSED**
	@Override
	public void delete(int id) {
		refreshConnection();
		throw new UnsupportedOperationException();
	}
}