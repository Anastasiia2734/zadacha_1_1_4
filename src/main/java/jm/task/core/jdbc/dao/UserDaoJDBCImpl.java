package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.SessionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.connection;

public class UserDaoJDBCImpl implements UserDao {
private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS users (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(20), lastname VARCHAR(20), age TINYINT NOT NULL)" ;
private static final String CREATE_USER = "INSERT INTO users (name, lastName, age) values (?, ?, ?)";
private static final String DELETE_USER = "DELETE FROM users WHERE id = ?";
private static final String GET_ALL_USERS = "SELECT * FROM users";
private static final String DROP_TABLE = "DROP TABLE IF EXISTS users";
private static final String CLEAR_TABLE = "DELETE FROM users";
SessionFactory sessionFactory = Util.getSessionFactory();



    public void createUsersTable() {
        Statement statement = null;
        try {
           statement = connection.createStatement();
           statement.executeUpdate(CREATE_TABLE);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void dropUsersTable() {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.executeUpdate(DROP_TABLE);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
PreparedStatement preparedStatement = null;
try {
   preparedStatement = connection.prepareStatement(CREATE_USER);
   preparedStatement.setString(1, name);
   preparedStatement.setString(2, lastName);
   preparedStatement.setByte(3, age);
   preparedStatement.executeUpdate();
} catch (SQLException e) {
    e.printStackTrace();
} finally {
    if (preparedStatement != null) {
        try {
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
    }

    public void removeUserById(long id) {
PreparedStatement preparedStatement = null;
try {
    preparedStatement = connection.prepareStatement(DELETE_USER);
    preparedStatement.setLong(1, id);
} catch (SQLException e) {
    e.printStackTrace();
}
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(GET_ALL_USERS);
            //preparedStatement.executeQuery();
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User userObj = new User();
                userObj.setId(resultSet.getLong("id"));
                userObj.setName((resultSet.getString("name")));
                userObj.setLastName(resultSet.getString("lastName"));
                userObj.setAge((byte) resultSet.getInt("age"));
                users.add(userObj);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return users;
        }
    }

    public void cleanUsersTable() {
PreparedStatement preparedStatement = null;
try {
    preparedStatement = connection.prepareStatement(CLEAR_TABLE);
    preparedStatement.execute();
} catch (SQLException e) {
    e.printStackTrace();
}
    }
}