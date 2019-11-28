/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dep;

import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 * @author aleja
 */
public class Empleado implements Serializable{
    
    int emp_no;
    String eapellido;
    int edept;
    int dir;
    String fecha_alt;
    String oficio;
    double salario;

    public Empleado(int emp_no, String eapellido, int edept, int dir, String fecha_alt, String oficio, double salario) {
        this.emp_no = emp_no;
        this.eapellido = eapellido;
        this.edept = edept;
        this.dir = dir;
        this.fecha_alt = fecha_alt;
        this.oficio = oficio;
        this.salario = salario;
    }
    public Empleado(){
    }

    public int getEmp_no() {
        return emp_no;
    }

    public void setEmp_no(int emp_no) {
        this.emp_no = emp_no;
    }

    public String getEapellido() {
        return eapellido;
    }

    public void setEapellido(String eapellido) {
        this.eapellido = eapellido;
    }

    public int getEdept() {
        return edept;
    }

    public void setEdept(int edept) {
        this.edept = edept;
    }

    public int getDir() {
        return dir;
    }

    public void setDir(int dir) {
        this.dir = dir;
    }

    public String getFecha_alt() {
        return fecha_alt;
    }

    public void setFecha_alt(String fecha_alt) {
        this.fecha_alt = fecha_alt;
    }

    public String getOficio() {
        return oficio;
    }

    public void setOficio(String oficio) {
        this.oficio = oficio;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }
    
    
}
