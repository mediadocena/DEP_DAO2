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
        String sql = "INSERT INTO empleados VALUES(?, ?, ?, ?, ?,?,?)";
        PreparedStatement sentencia;
        
        try {
             sentencia = conexion.prepareStatement(sql);
          if(ifExists("SELECT * from departamentos where dept_no="+emp.getEdept())&&ifExists("SELECT * from empleados where emp_no="+emp.getDir())){
           
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
          }else{
              System.out.println("ERROR, EL EMPLEADO YA EXISTE EN LA BASE DE DATOS");
          }

        } catch (SQLException e) {
            MensajeExcepcion(e);      
        }
        
        
        return valor;
    }
    public boolean ifExists(String sSQL) throws SQLException {
    PreparedStatement ps = conexion.prepareStatement(sSQL);
    ResultSet rs = ps.executeQuery();
    return rs.next();
    }

    @Override
    public boolean EliminarEmp(int id) {
          boolean valor = false;
        String sql = "DELETE FROM empleados WHERE emp_no = ? ";
        PreparedStatement sentencia;
        try {
            sentencia = conexion.prepareStatement(sql);
          if(!ifExists("SELECT dir from empleados where dir=+"+id)){
              
            sentencia.setInt(1, id);
            int filas = sentencia.executeUpdate();
            //System.out.printf("Filas eliminadas: %d%n", filas);
            if (filas > 0) {
                valor = true;
                System.out.printf("Empleado %d eliminado%n", id);
            }
            sentencia.close();
          }else{
              System.out.println("No es posible eliminar si tiene empleados a cargo");
          }
        } catch (SQLException e) {
            MensajeExcepcion(e);      
        }
        return valor;
    }

    @Override
    public boolean ModificarEmp(int Emp_no, Empleado emp) {
          boolean valor = false;
        String sql = "UPDATE empleados SET eapellido= ?, edept = ?,oficio = ?,dir = ?,fecha_alt = ?,salario = ? WHERE emp_no = ? ";
        PreparedStatement sentencia;
        try {
            sentencia = conexion.prepareStatement(sql);
         if(ifExists("SELECT * from departamentos where dept_no="+emp.getEdept())&&ifExists("SELECT * from empleados where emp_no="+emp.getDir())){
            sentencia.setInt(7, Emp_no);
            sentencia.setString(1, emp.getEapellido());
            sentencia.setInt(2, emp.getEdept());
            sentencia.setString(3, emp.getOficio());
            sentencia.setInt(4, emp.getDir());
            sentencia.setString(5, emp.getFecha_alt());
            sentencia.setDouble(6, emp.getSalario());
            int filas = sentencia.executeUpdate();
            //System.out.printf("Filas modificadas: %d%n", filas);
            if (filas > 0) {
                valor = true;
                System.out.printf("Empleado %d modificado%n", Emp_no);
            }
            sentencia.close();
         }else{
             System.out.println("NO EXISTE EL DIRECTOR");
         }
        } catch (SQLException e) {
           MensajeExcepcion(e);      
        }
        return valor;
    }

   
    @Override
    public Empleado ConsultarEmp(int id) {
          String sql = "SELECT * FROM empleados WHERE emp_no =  ?";
        PreparedStatement sentencia;
        Empleado emp = new Empleado();        
        try {
            sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(1, id);
            ResultSet rs = sentencia.executeQuery();          
            if (rs.next()) {
                emp.setEdept(rs.getInt("edept"));
                emp.setEapellido(rs.getString("eapellido"));
                emp.setDir(rs.getInt("dir"));
                emp.setOficio(rs.getString("oficio"));
                emp.setSalario(rs.getDouble("salario"));
               
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