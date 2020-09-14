package login;

import model.staff.EmployeeData;

public class SessionManager {

    private static EmployeeData employeeData;

    public static EmployeeData getEmployeeData() {
        return employeeData;
    }

    public static void setEmployeeData(EmployeeData employeeData) {
        SessionManager.employeeData = employeeData;
    }
}
