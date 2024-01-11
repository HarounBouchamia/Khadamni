package test;


import utiles.Connection;
import java.sql.*;

import static gui.RoleGui.insertRoles;


public class Main {
    public static void main(String[] args) {

        Connection connexion = new Connection();
        java.sql.Connection connection = connexion.getConnection();
        insertRoles();



    }


}