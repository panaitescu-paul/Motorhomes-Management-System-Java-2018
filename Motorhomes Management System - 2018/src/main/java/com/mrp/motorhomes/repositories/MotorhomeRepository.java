package com.mrp.motorhomes.repositories;

import com.mrp.motorhomes.model.Motorhome;

import java.sql.SQLException;
import java.util.ArrayList;

//#Razvan
//Repository connecting to the database of the Motorhome model
public class MotorhomeRepository extends CrudRepository<Motorhome> {
	
	//Singleton Pattern
	private static MotorhomeRepository instance;
	public static MotorhomeRepository getInstance(){
		if(instance == null){
			instance = new MotorhomeRepository();
		}
		return instance;
	}
	private MotorhomeRepository(){}
	
	//Add a motorhome in the database
	@Override
	public int create(Motorhome item) {

		refreshConnection();
		try {
			preparedStatement = connection.prepareStatement("INSERT INTO motorhomes(brand, type, model, year, basePrice, cleaned, serviced) VALUES (?, ?, ?, ?, ?, ?, ?)");
			preparedStatement.setString(1, item.getBrand());
			preparedStatement.setString(2, item.getType());
			preparedStatement.setString(3, item.getModel());
			preparedStatement.setInt(4, item.getYear());
			preparedStatement.setDouble(5, item.getBasePrice());
			preparedStatement.setBoolean(6, true);
			preparedStatement.setBoolean(7, true);

			preparedStatement.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		//the id of the motorhome is not needed right after it has been added to the database
		return -1;
	}
	
	//Returns the list of all motorhomes in the database
	@Override
	public ArrayList<Motorhome> readAll() {
		refreshConnection();
		ArrayList<Motorhome> motorhomes = new ArrayList<>();

		try {
			preparedStatement = connection.prepareStatement("SELECT * FROM motorhomes");
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next())
			{
				motorhomes.add(new Motorhome(resultSet.getInt("id"),
						resultSet.getString("brand"),
						resultSet.getString("type"),
						resultSet.getString("model"),
						resultSet.getInt("year"),
						resultSet.getDouble("basePrice"),
						resultSet.getBoolean("cleaned"),
						resultSet.getBoolean("serviced")));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return motorhomes;
	}
	
	//gets a motorhome by its id
	@Override
	public Motorhome read(int id) {
		refreshConnection();
		try {
			preparedStatement = connection.prepareStatement("SELECT * FROM motorhomes WHERE id = ?");
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();

			if(resultSet.next()) {
				return new Motorhome(resultSet.getInt("id"),
						resultSet.getString("brand"),
						resultSet.getString("type"),
						resultSet.getString("model"),
						resultSet.getInt("year"),
						resultSet.getDouble("basePrice"),
						resultSet.getBoolean("cleaned"),
						resultSet.getBoolean("serviced"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//Updates a motorhome in the database
	@Override
	public void update(Motorhome item) {
		refreshConnection();
		try {
			preparedStatement = connection.prepareStatement("UPDATE motorhomes SET brand = ?, type = ?, model = ?, year = ?, basePrice = ?, cleaned = ?, serviced = ? WHERE id = ?");
			preparedStatement.setString(1, item.getBrand());
			preparedStatement.setString(2, item.getType());
			preparedStatement.setString(3, item.getModel());
			preparedStatement.setInt(4, item.getYear());
			preparedStatement.setDouble(5, item.getBasePrice());
			preparedStatement.setBoolean(6, item.isCleaned());
			preparedStatement.setBoolean(7, item.isServiced());
			preparedStatement.setInt(8, item.getId());

			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//Deletes a motorhome from the database
	@Override
	public void delete(int id) {
		refreshConnection();
		try {
			preparedStatement = connection.prepareStatement("DELETE FROM motorhomes WHERE id = ?");
			preparedStatement.setInt(1, id);

			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}