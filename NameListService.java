package com.wagyeke.controller.service;

import com.wagyeke.model.domain.*;

import static com.wagyeke.view.ui.utils.Data.*;

public class NameListService {

    /**
     * 封装Data的数据
     */
    private Employee[] employees;

    /**
     * 根据项目提供的Data类构建相应大小的employees数组
     * 再根据Data类中的数据构建不同的对象，包括Employee、Programmer、Designer和Architect对象，以及相关联的Equipment子类的对象
     * 将对象存于数组中
     * Data类位于com.atguigu.team.service包中
     */
    public NameListService() {

        employees = new Employee[EMPLOYEES.length];


        for (int i = 0;i < employees.length;i++){
            int type = Integer.parseInt(EMPLOYEES[i][0]);
            int id  = Integer.parseInt(EMPLOYEES[i][1]);
            String name = EMPLOYEES[i][2];
            int age = Integer.parseInt(EMPLOYEES[i][3]);
            double salsy = Double.parseDouble(EMPLOYEES[i][4]);

            switch(type){
                case EMPLOYEE:
                    employees[i] = new Employee(id,name,age,salsy);
                    break;

                case PROGRAMMER:
                    Equipment equipment = createEquipment(i);
                    employees[i] = new Programmer(id,name,age,salsy,equipment);
                    break;

                case DESIGNER:
                    Equipment equipments = createEquipment(i);
                    double bonus = Double.parseDouble(EMPLOYEES[i][5]);
                    employees[i] = new Designer(id,name,age,salsy,bonus,equipments);
                    break;

                case ARCHTECT:
                    Equipment equipmentss = createEquipment(i);
                    double bonuss = Double.parseDouble(EMPLOYEES[i][5]);
                    int stock = Integer.parseInt(EMPLOYEES[i][6]);
                    employees[i] = new Architect(id,name,age,salsy,bonuss,stock,equipmentss);
                    break;
            }
        }
    }

    /**
     * 获取指定位置索引的员工设备
     * @param index
     * @return
     */
    private Equipment createEquipment(int index) {
        for (int i = 0;i < EQUIPMENTS.length;i++){
            int type = Integer.parseInt(EQUIPMENTS[index][0]);

            switch (type){
                case PC:
                    return new PC(EQUIPMENTS[index][1],EQUIPMENTS[index][2]);

                case NOTEBOOK:
                    double price = Double.parseDouble(EQUIPMENTS[index][2]);
                    return new NoteBook(EQUIPMENTS[index][1],price);

                case PRINTER:
                    return new Printer(EQUIPMENTS[index][1],EQUIPMENTS[index][2]);
            }
        }
       return null;
    }

    /**
     * 方法：获取当前所有员工。
     * 返回：包含所有员工对象的数组
     * @return
     */
    public Employee[] getAllEmployees() {
        return employees;
    }

    /**
     * 方法：获取指定ID的员工对象。
     * 参数：指定员工的ID
     * 返回：指定员工对象
     * 异常：找不到指定的员工
     * @param id
     * @return
     */
    public Employee getEmployee(int id) throws TeamExcoption{
        for (int i = 0;i < employees.length;i++){
            if(employees[i].getId() == id ){
                return employees[i];
            }
        }
        throw new TeamExcoption("找不到指定的员工!!!");
    }

}