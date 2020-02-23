package employee;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Database {

	/*
	 * Each entry in a 'departments' map contains a unique department id and its
	 * associated information object.
	 */

	HashMap<Integer, DepartmentInfo> departments;

	/*
	 * Each entry in a 'employees' map contains a unique employee id and its
	 * associated information object.
	 */

	HashMap<String, EmployeeInfo> employees;

	/**
	 * Initialize an empty database.
	 */

	public Database() {
		departments = new HashMap<>();
		employees = new HashMap<>();
	}

	/**
	 * Add a new employee entry.
	 * 
	 * @param id
	 *            id of the new employee
	 * @param info
	 *            information object of the new employee
	 * @throws IdAlreadyExistsExceptoin
	 *             if 'id' is an existing employee id
	 */

	public void addEmployee(String id, EmployeeInfo info) throws IdAlreadyExistsExceptoin {
		if (employees.containsKey(id)) {
			throw new IdAlreadyExistsExceptoin("Employee id " + id + " already exists");
		} else {
			employees.put(id, info);
		}
	}

	/**
	 * Remove an existing employee entry.
	 * 
	 * @param id
	 *            id of some employee
	 * @throws IdNotFoudException
	 *             if 'id' is not an existing employee id
	 */

	public void removeEmployee(String id) throws IdNotFoundException {
		if (!employees.containsKey(id)) {
			throw new IdNotFoundException("Employee id " + id + " does not exist");
		} else {
			employees.remove(id);
		}
	}

	/**
	 * Add a new department entry.
	 * 
	 * @param id
	 *            id of the new department
	 * @param info
	 *            information object of the new department
	 * @throws IdAlreadyExistsExceptoin
	 *             if 'id' is an existing department id
	 */

	public void addDepartment(Integer id, DepartmentInfo info) throws IdAlreadyExistsExceptoin {
		if (departments.containsKey(id)) {
			throw new IdAlreadyExistsExceptoin("Department id " + id + " already exists");
		} else {
			departments.put(id, info);
		}
	}

	/**
	 * Remove an existing department entry.
	 * 
	 * @param id
	 *            id of some employee
	 * @throws IdNotFoudException
	 *             if 'id' is not an existing employee id
	 */

	public void removeDepartment(Integer id) throws IdNotFoundException {
		if (!departments.containsKey(id)) {
			throw new IdNotFoundException("Department id " + id + " does not exist");
		} else {
			departments.remove(id);
		}
	}

	/**
	 * Change the department of employee with id 'eid' to a new department with id
	 * 'did'.
	 * 
	 * You can assume that 'did' denotes a department different from the current
	 * department of the employee denoted by 'eid'.
	 * 
	 * @param eid
	 *            id of some employee
	 * @param did
	 *            id of some department
	 * @throws IdNotFoundException
	 *             if either 'eid' is a non-existing employee id or 'did' is a
	 *             non-existing department id.
	 */

	public void changeDepartment(String eid, Integer did) throws IdNotFoundException {
		if (!(employees.containsKey(eid) && departments.containsKey(did))) {
			throw new IdNotFoundException("Non-existing id");
		} else {
			employees.get(eid).setDepartmentId(did);
		}
	}

	/**
	 * Retrieve the name of employee with id 'id'.
	 * 
	 * @param id
	 *            id of some employee
	 * @return name of the employee with id 'id'
	 * @throws IdNotFoudException
	 *             if 'id' is not an existing employee id
	 */

	public String getEmployeeName(String id) throws IdNotFoundException {
		if (!employees.containsKey(id)) {
			throw new IdNotFoundException("Employee id " + id + " does not exist");
		} else {
			return employees.get(id).getName();
		}
	}

	/**
	 * Retrieve the names of all employees of the department with id 'id'. If 'id' a
	 * non-existing department id, then return an empty list.
	 * 
	 * @param id
	 *            id of some department
	 * @return List of names of employees whose home department has id 'id'
	 */

	public ArrayList<String> getEmployeeNames(Integer id) {
		ArrayList<String> names = new ArrayList<>();
		for (String eid : employees.keySet()) {
			EmployeeInfo einfo = employees.get(eid);
			if (einfo.getDepartmentId().equals(id)) {
				names.add(einfo.getName());
			}
		}
		return names;
	}

	/**
	 * Retrieve an employee's department's information object.
	 * 
	 * @param id
	 *            id of some existing employee
	 * @return The information object of the employee's home department
	 * @throws IdNotFoudException
	 *             if 'id' is not an existing employee id
	 */

	public DepartmentInfo getDepartmentInfo(String id) throws IdNotFoundException {
		if (!employees.containsKey(id)) {
			throw new IdNotFoundException("Employee id " + id + " does not exist");
		} else {
			return departments.get(employees.get(id).getDepartmentId());
		}
	}

	/**
	 * Retrieve a list, sorted in increasing order, the information objects of all
	 * stored employees.
	 * 
	 * Hints: 1. Override the 'comareTo' method in EmployeeInfo class. 2. Look up
	 * the Arrays.sort method in Java API.
	 * 
	 * @param id
	 *            id of some department
	 * @return A sorted list of information objects of all employees.
	 */

	public EmployeeInfo[] getSortedEmployeeInfo() {
		ArrayList<EmployeeInfo> list = new ArrayList<>();
		for (String eid : employees.keySet()) {
			EmployeeInfo einfo = employees.get(eid);
			list.add(einfo);
		}
		EmployeeInfo[] sortedList = new EmployeeInfo[list.size()];
		for (int i = 0; i < sortedList.length; i++) {
			sortedList[i] = list.get(i);
		}
		Arrays.sort(sortedList);
		return sortedList;
	}

	/**
	 * Retrieve the average salary of all employees in department with id 'id'.
	 * 
	 * @param id
	 *            id of some department
	 * @return average salary of all employees in department with id 'id'
	 */

	public double getAverageSalary(Integer id) throws IdNotFoundException {
		if (!departments.containsKey(id)) {
			throw new IdNotFoundException("Department id " + id + " does not exist");
		} else {
			int count = 0;
			double total = 0;
			for (String eid : employees.keySet()) {
				EmployeeInfo einfo = employees.get(eid);
				Integer did = einfo.getDepartmentId();
				if (did.equals(id)) {
					double salary = einfo.getSalary();
					count++;
					total += salary;
				}
			}
			return total / count;
		}
	}

	/**
	 * Retrieve the information object of the department with the highest average
	 * salary among its employees.
	 * 
	 * @return the information object of the department with the highest average
	 *         salary among its employees
	 * 
	 *         Hint: Use 'getAverageSalary(Integer id)' as a helper method.
	 */

	public DepartmentInfo getDepartmentOfHighestAverageSalary() throws IdNotFoundException {
		HashMap<Integer, Double> averageSalaries = new HashMap<>();
		for (Integer did : departments.keySet()) {
			// try {
			averageSalaries.put(did, getAverageSalary(did));
			// }
			// catch(IdNotFoudException e) {
			// guaranteed not to happen.
			// }
		}

		Integer maxDid = 0;
		double maxAvgSalary = 0.0;
		for (Integer did : averageSalaries.keySet()) {
			if (averageSalaries.get(did) > maxAvgSalary) {
				maxDid = did;
			}
		}

		return departments.get(maxDid);
	}
}