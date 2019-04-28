package com.mrp.motorhomes.repositories;

import com.mrp.motorhomes.repositories.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

//#Marius & Paul & Razvan
//Abstract class representing the structure of a CRUD repository
public abstract class CrudRepository<T>{
    
    //minimum time that should have passed since the last refresh
	//2 minutes interval
    private static final int REFRESH_THRESHOLD = 1000*60*2;
    //time since last connection refreshing took place
    private   static long lastConnRefresh;
    //the Connection used to execute the sql statements
    static Connection connection;
	static PreparedStatement preparedStatement;
	static ResultSet resultSet;
	
	//Adds a new element in the table
	//and return its database id
	public abstract int create(T item);
	
	//Reads all the elements of the table
	public abstract ArrayList<T> readAll();

	//Reads a specific element from the table
	public abstract T read(int id);

    //Updates an existing element in the table
	public abstract void update(T item);

    //Deletes an element from the table
	public abstract void delete(int id);
	
	//Is called each time the application is accessing the database.
	//On the first call, the method creates the connection and stores the time when created in "lastConnRefresh".
	//Every time the method is called, if the time since the last refresh was more than two minute, rebuilds the
	//connection.
	protected static void refreshConnection(){
		long currentTime = System.currentTimeMillis();
		//If the time since the last refresh is more than the threshold
		if( currentTime - lastConnRefresh > REFRESH_THRESHOLD){
			lastConnRefresh = currentTime;
			connection = DBConnection.getConnection();
		}
	}
}