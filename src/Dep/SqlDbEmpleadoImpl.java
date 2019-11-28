/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dep;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 *
 * @author 
 */
public class SqlDbEmpleadoImpl implements EmpleadoDAO {

    Connection conexion;

    public SqlDbEmpleadoImpl() {
        conexion = SqlDbDAOFactory.crearConexion();
    }

   

    private void MensajeExcepcion(SQLException e) {
       System.out.printf("HA OCURRIDO UNA EXCEPCIÓN:%n");
       System.out.printf("Mensaje   : %s %n", e.getMessage());
       System.out.printf("SQL estado: %s %n", e.getSQLState());
       System.out.printf("Cód error : %s %n", e.getErrorCode());
    }

    @Override
    public boolean InsertarEmp(Empleado emp) {
       boolean valor = false;
        String sql = "INSERT INTO empleados VALUES(?, ?, ?, ?, ?)";
        PreparedStatement sentencia;
        try {
            sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(1, emp.getEmp_no());
            sentencia.setString(2, emp.getEapellido());
            sentencia.setDouble(7, emp.getSalario());
            sentencia.setInt(3, emp.getEdept());
            sentencia.setString(6, emp.getOficio());
            sentencia.setInt(4, emp.getDir());
            sentencia.setString(5, emp.getFecha_alt());
            int filas = sentencia.executeUpdate();
            //System.out.printf("Filas insertadas: %d%n", filas);
            if (filas > 0) {
                valor = true;
                 System.out.printf("Empleado %d insertado%n", emp.getEmp_no());
            }
            sentencia.close();

        } catch (SQLException e) {
            MensajeExcepcion(e);      
        }
        return valor;
    }

    @Override
    public boolean EliminarEmp(int id) {
          boolean valor = false;
        String sql = "DELETE FROM empleados WHERE Emp_no = ? ";
        PreparedStatement sentencia;
        try {
            sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(1, id);
            int filas = sentencia.executeUpdate();
            //System.out.printf("Filas eliminadas: %d%n", filas);
            if (filas > 0) {
                valor = true;
                System.out.printf("Empleado %d eliminado%n", id);
            }
            sentencia.close();
        } catch (SQLException e) {
            MensajeExcepcion(e);      
        }
        return valor;
    }

    @Override
    public boolean ModificarEmp(int Emp_no, Empleado emp) {
          boolean valor = false;
        String sql = "UPDATE empleados SET Eapellido= ?, Edept = ?,Oficio = ?,Dir = ?,Fecha_alt = ?,Salario = ? WHERE dept_no = ? ";
        PreparedStatement sentencia;
        try {
            sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(7, Emp_no);
            sentencia.setString(1, emp.getEapellido());
            sentencia.setInt(2, emp.getEdept());
            sentencia.setString(3, emp.getOficio());
            sentencia.setInt(2, emp.getDir());
            sentencia.setString(4, emp.getFecha_alt());
            sentencia.setDouble(5, emp.getSalario());
            int filas = sentencia.executeUpdate();
            //System.out.printf("Filas modificadas: %d%n", filas);
            if (filas > 0) {
                valor = true;
                System.out.printf("Departamento %d modificado%n", Emp_no);
            }
            sentencia.close();
        } catch (SQLException e) {
           MensajeExcepcion(e);      
        }
        return valor;
    }

   
    @Override
    public Empleado ConsultarEmp(int id) {
          String sql = "SELECT * FROM empleados WHERE Emp_no =  ?";
        PreparedStatement sentencia;
        Empleado emp = new Empleado();        
        try {
            sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(1, id);
            ResultSet rs = sentencia.executeQuery();          
            if (rs.next()) {
                emp.setEdept(rs.getInt("Edept"));
                emp.setEapellido(rs.getString("Eapellido"));
                emp.setDir(rs.getInt("Dir"));
                emp.setOficio(rs.getString("Oficio"));
                emp.setSalario(rs.getDouble("Salario"));
                //TODO DATE
            }
            else
                System.out.printf("Empleado: %d No existe%n",id);
            
            rs.close();// liberar recursos
            sentencia.close();
         
        } catch (SQLException e) {
            MensajeExcepcion(e);            
        }
        return emp;
    }

   
}