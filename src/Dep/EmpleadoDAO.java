package Dep;
public interface EmpleadoDAO {    
    public boolean InsertarEmp(Empleado emp);
    public boolean EliminarEmp(int id); 
    public boolean ModificarEmp(int deptno, Empleado emp);
    public Empleado ConsultarEmp(int id);    
}
