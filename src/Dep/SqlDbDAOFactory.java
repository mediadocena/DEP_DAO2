/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dep;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SqlDbDAOFactory extends DAOFactory {

    static Connection conexion = null;
    static String DRIVER = "com.mysql.jdbc.Driver";
    static String URLDB = "jdbc:mysql://remotemysql.com/phpmyadmin/db_structure.php?server=1&db=jHNInj7OkW";
    static String USUARIO = "jHNInj7OkW";
    static String CLAVE = "hoh6LALuy0";

    public SqlDbDAOFactory() {
        DRIVER = "com.mysql.jdbc.Driver";
        URLDB = "jdbc:mysql://remotemysql.com/phpmyadmin/db_structure.php?server=1&db=jHNInj7OkW";
    }

    // crear la conexion
    public static Connection crearConexion() {
        if (conexion == null) {
            try {
                Class.forName(DRIVER); // Cargar el driver
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(SqlDbDAOFactory.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                conexion = DriverManager.getConnection(URLDB, USUARIO, CLAVE);
            } catch (SQLException ex) {
                System.out.printf("HA OCURRIDO UNA EXCEPCIÓN:%n");
                System.out.printf("Mensaje   : %s %n", ex.getMessage());
                System.out.printf("SQL estado: %s %n", ex.getSQLState());
                System.out.printf("Cód error : %s %n", ex.getErrorCode());
            }
        }
        return conexion;
    }

    @Override
    public DepartamentoDAO getDepartamentoDAO() {
        return new SqlDbDepartamentoImpl();
    }
}
