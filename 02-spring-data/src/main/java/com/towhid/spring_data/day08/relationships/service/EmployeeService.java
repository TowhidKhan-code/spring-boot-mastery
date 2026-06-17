package com.towhid.spring_data.day08.relationships.service;

import com.towhid.spring_data.day08.relationships.entity.Department;
import com.towhid.spring_data.day08.relationships.entity.Employee;
import com.towhid.spring_data.day08.relationships.repository.DepartmentRepository;
import com.towhid.spring_data.day08.relationships.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
// @Transactional at class level =
// every method in this class is transactional
@Transactional
public class EmployeeService {
    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    public EmployeeService(
            DepartmentRepository departmentRepository,
            EmployeeRepository employeeRepository) {
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
    }

    // ─────────────────────────────────────────
    // CREATE DEPARTMENT
    // ─────────────────────────────────────────
    public Department createDepartment(
            String name,String location){
        Department dept = new Department(name, location);
        return departmentRepository.save(dept);
    }

    // ─────────────────────────────────────────
    // ADD EMPLOYEE TO DEPARTMENT
    // ─────────────────────────────────────────
    public Employee addEmployeeToDepartment(
            Integer deptId, String name,
            String email, String position,
            Double salary){
        // Find department (throws if not found)
        Department department = departmentRepository
                .findById(deptId)
                .orElseThrow(() -> new RuntimeException(
                        "Department not found: " + deptId));

        // Create employee
        Employee employee = new Employee(
                name, email, position, salary);

        // Link employee to department
        department.addEmployee(employee);

        // Save department → cascades to employee!
        // (CascadeType.ALL)
        departmentRepository.save(department);

        System.out.println("Employee added: "
                + name + " → " + department.getName());

        return employee;
    }

    // ─────────────────────────────────────────
    // GET DEPARTMENT WITH EMPLOYEES
    // ─────────────────────────────────────────
    // readOnly = true → optimization for SELECT
    // Spring knows no data is being modified
    @Transactional(readOnly = true)
    public Department getDepartmentWithEmployees(Integer id) {
        return departmentRepository
                .findByIdWithEmployees(id)
                .orElseThrow(() -> new RuntimeException(
                        "Department not found: " + id));
    }

    // ─────────────────────────────────────────
    // TRANSFER EMPLOYEE BETWEEN DEPARTMENTS
    // This is a PERFECT transaction example!
    // Both operations must succeed or both fail
    // ─────────────────────────────────────────
    public void transferEmployee(
            Integer employeeId,
            Integer fromDeptId,
            Integer toDeptId) {

        System.out.println("Transferring employee "
                + employeeId + "...");

        // Step 1: Get employee
        Employee employee = employeeRepository
                .findById(employeeId)
                .orElseThrow(() -> new RuntimeException(
                        "Employee not found: " + employeeId));

        // Step 2: Get source department
        Department fromDept = departmentRepository
                .findById(fromDeptId)
                .orElseThrow(() -> new RuntimeException(
                        "Source dept not found: " + fromDeptId));

        // Step 3: Get target department
        Department toDept = departmentRepository
                .findById(toDeptId)
                .orElseThrow(() -> new RuntimeException(
                        "Target dept not found: " + toDeptId));

        // Step 4: Remove from old department
        fromDept.removeEmployee(employee);

        // Step 5: Add to new department
        toDept.addEmployee(employee);

        // Step 6: Save both
        // If ANY step fails → ROLLBACK all steps!
        departmentRepository.save(fromDept);
        departmentRepository.save(toDept);

        System.out.println("Transferred: "
                + employee.getName()
                + " from " + fromDept.getName()
                + " to " + toDept.getName());
    }

    // ─────────────────────────────────────────
    // GIVE RAISE — Transaction Demo
    // ─────────────────────────────────────────
    public void giveRaiseToAllInDepartment(
            Integer deptId, Double percentage) {

        List<Employee> employees = employeeRepository
                .findByDepartmentId(deptId);

        if (employees.isEmpty()) {
            throw new RuntimeException(
                    "No employees in dept: " + deptId);
        }

        // Update ALL employees in one transaction
        // If any update fails → ALL are rolled back
        for (Employee emp : employees) {
            Double newSalary = emp.getSalary()
                    * (1 + percentage / 100);
            emp.setSalary(newSalary);
            employeeRepository.save(emp);
            System.out.println("Raised: "
                    + emp.getName()
                    + " → $" + String.format(
                    "%.2f", newSalary));
        }
    }

    // ─────────────────────────────────────────
    // TRANSACTION ROLLBACK DEMO
    // ─────────────────────────────────────────
    public void demonstrateRollback(Integer deptId) {

        System.out.println("\nROLLBACK DEMO:");
        System.out.println("Adding 2 employees...");

        Department dept = departmentRepository
                .findById(deptId)
                .orElseThrow(() -> new RuntimeException(
                        "Dept not found"));

        // Add first employee - this will succeed
        Employee emp1 = new Employee(
                "Valid Employee", "valid@email.com",
                "Developer", 60000.0);
        dept.addEmployee(emp1);
        departmentRepository.save(dept);
        System.out.println("First employee added");

        // Simulate an error AFTER first save
        // In a real transaction, first save
        // would be rolled back too!
        if (true) { // simulating error condition
            throw new RuntimeException(
                    "Something went wrong! " +
                            "Rolling back ALL changes including " +
                            "the first employee!");
        }

        // This never runs because exception above
        Employee emp2 = new Employee(
                "Never Added", "never@email.com",
                "Designer", 55000.0);
        dept.addEmployee(emp2);
    }

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public List<Employee> getHighEarners(Double minSalary){
        return employeeRepository.findBySalaryGreaterThan(minSalary);
    }

}
