/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dep;

import java.time.LocalDate;
import org.neodatis.odb.ODB;
import org.neodatis.odb.Objects;
import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;

/**
 *
 * @author 
 */
public class NeodatisEmpleadoImpl implements EmpleadoDAO {

    static ODB bd;

    public NeodatisEmpleadoImpl() {
        bd = NeodatisDAOFactory.crearConexion();
    }

    @Override
    public boolean InsertarEmp(Empleado emp) {
        Empleado empAux=ConsultarEmp(emp.getEmp_no());
        
        IQuery queryDepartamentos = new CriteriaQuery(Departamento.class, Where.equal("deptno", emp.getEdept()));
        Objects<Departamento> objetos = bd.getObjects(queryDepartamentos);
        
        IQuery queryJefes = new CriteriaQuery(Empleado.class, Where.equal("dir", emp.getDir()));
        Objects<Departamento> jefes = bd.getObjects(queryJefes);
        
        if(objetos.isEmpty()){
            System.out.println("No existe ese departamento");
            return false;
        }
        
        if(jefes.isEmpty() && emp.getDir()!=0){
            System.out.println("No existe ese jefe");
            return false;
        }
        
        if(empAux.getEdept()<0){
            System.out.println(empAux.getEdept());
            bd.store(emp);
            bd.commit();
            System.out.printf("Empleado: %d Insertado %n", emp.getEmp_no());
            return true;
        }else{
            System.out.println("Ya existe ese empleado");
            return false;
        }
    }

    @Override
    public boolean EliminarEmp(int emp_no) {
        boolean valor = false;
        IQuery query = new CriteriaQuery(Empleado.class, Where.equal("emp_no", emp_no));
        IQuery queryJefes = new CriteriaQuery(Empleado.class, Where.equal("dir", emp_no));
        Objects<Empleado> objetos = bd.getObjects(query);
        Objects<Empleado> jefes=bd.getObjects(queryJefes);
        if(jefes.isEmpty()){
            try {
                Empleado empleado = (Empleado) objetos.getFirst();
                bd.delete(empleado);
                System.out.printf("Empleado eliminado");
                bd.commit();
                valor = true;
            } catch (IndexOutOfBoundsException i) {
                System.out.printf("Empleado a eliminar: %d No existe%n", emp_no);
            }
        }else{
            System.out.printf("Ese empleado tiene subordinados");
        }

        return valor;
    }

    @Override
    public boolean ModificarEmp(int emp_no, Empleado emp) {
        boolean valor = false;
        
        IQuery queryDepartamentos = new CriteriaQuery(Departamento.class, Where.equal("deptno", emp.getEdept()));
        Objects<Departamento> dptos = bd.getObjects(queryDepartamentos);
        
        IQuery queryJefes = new CriteriaQuery(Empleado.class, Where.equal("dir", emp.getDir()));
        Objects<Departamento> jefes = bd.getObjects(queryJefes);
        
        if(dptos.isEmpty()){
            System.out.println("No existe ese departamento");
            return false;
        }
        
        if(jefes.isEmpty() && emp.getDir()!=0){
            System.out.println("No existe ese jefe");
            return false;
        }
        
        IQuery query = new CriteriaQuery(Empleado.class, Where.equal("emp_no", emp_no));
        Objects<Empleado> objetos = bd.getObjects(query);
        try {
            Empleado empleado = (Empleado) objetos.getFirst();
            empleado.setEapellido(emp.getEapellido());
            empleado.setDir(emp.getDir());
            empleado.setEdept(emp.getEdept());
            empleado.setEmp_no(emp.getEmp_no());
            empleado.setFecha_alt(emp.getFecha_alt());
            empleado.setOficio(emp.getOficio());
            empleado.setSalario(emp.getSalario());
            bd.store(empleado); // actualiza el objeto 
            valor = true;
            bd.commit();
        } catch (IndexOutOfBoundsException i) {
            System.out.printf("Empleado: %d No existe%n", emp_no);
        }

        return valor;
    }

    @Override
    public Empleado ConsultarEmp(int emp_no) {
        IQuery query = new CriteriaQuery(Empleado.class, Where.equal("emp_no", emp_no));
        Objects<Empleado> objetos = bd.getObjects(query);
        Empleado emp = new Empleado();
        if (objetos != null) {
            try {
                emp = (Empleado) objetos.getFirst();
            } catch (IndexOutOfBoundsException i) {
                System.out.printf("Departamento: %d No existe%n", emp_no);
            }
        }
        return emp;
    }

}
