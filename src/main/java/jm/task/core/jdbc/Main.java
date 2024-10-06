package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;

public class Main {
    public static void main(String[] args) {
        UserDaoHibernateImpl userDaoHibernate = new UserDaoHibernateImpl();
        userDaoHibernate.createUsersTable();
        userDaoHibernate.saveUser("Илья", "Набоков", (byte) 21);
        userDaoHibernate.saveUser("Василий", "Кошечкин", (byte) 40);
        userDaoHibernate.saveUser("Евгений", "Аликин", (byte) 27);
        userDaoHibernate.saveUser("Владимир", "Галкин", (byte) 24);
        userDaoHibernate.getAllUsers();
        userDaoHibernate.cleanUsersTable();
        userDaoHibernate.dropUsersTable();

    }
}
