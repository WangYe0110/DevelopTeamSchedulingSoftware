package com.wagyeke.controller.service;

import com.wagyeke.model.domain.*;
import com.wagyeke.view.ui.utils.Status;

public class TeamService {
    /**
     * 静态变量，用来为开发团队新增成员自动生成团队中的唯一ID
     * 即memberId。（提示：应使用增1的方式）
     */
    private static int counter = 1;
    /**
     * 开发团队最大成员数
     */
    private final int MAX_MEMBER = 5;
    /**
     * 保存当前团队中的各成员对象
     */
    private Programmer[] team = new Programmer[MAX_MEMBER];
    /**
     * 记录团队成员的实际人数
     */
    private int total;

    public TeamService() {
        super();
    }

    /**
     * 方法：返回当前团队的所有对象
     * 返回：包含所有成员对象的数组，数组大小与成员人数一致
     * addMember(e: Employee)方法：向团队中添加成员
     * @return
     */
    public Programmer[] getTeam(){
        Programmer[] team = new Programmer[total];
        for (int i = 0;i < team.length;i++){
            team[i] = this.team[i];
        }
        return team;
    }

    /**
     * 方法：向团队中添加成员
     * 参数：待添加成员的对象
     * 异常：添加失败， TeamException中包含了失败原因
     * @param e
     * @throws TeamExcoption
     */
    public void addMember(Employee e) throws TeamExcoption{
        // 开发团队已满，添加失败
        if (total >= 5){
            throw new TeamExcoption("开发团队已满，添加失败");
        }
        // 非程序员，添加失败
        if(!(e instanceof Programmer)){
            throw new TeamExcoption("非程序员，添加失败");
        }
        // 员工在本团队，添加失败
        if(isExist(e)){
            throw new TeamExcoption("员工在本团队，添加失败");
        }
        // 员工已在其他团队，添加失败
        // 员工正在休假，添加失败
        Programmer p = (Programmer) e;
        if("BUSY".equalsIgnoreCase(p.getStats().getNAME())){
            throw new TeamExcoption("员工已在其他团队，添加失败");
        }else if("VOCATION".equalsIgnoreCase(p.getStats().getNAME())){
            throw new TeamExcoption("员工正在休假，添加失败");
        }
        // 团队中只能有一位架构师
        // 团队中只能有二位设计师
        // 团队中只能有三位程序员
        int numofArch = 0,numofDes = 0,numofPro = 0;
        for(int i = 0;i < team.length;i++){
            if(team[i] instanceof Architect){
                numofArch++;
            }else if(team[i] instanceof Designer){
                numofDes++;
            }else if(team[i] instanceof Programmer){
                numofPro++;
            }
        }
        if(p instanceof Architect){
            if(numofArch >= 1){
                throw new TeamExcoption("团队中只能有一位架构师");
            }
        }else if(p instanceof Designer){
            if(numofDes >= 2){
                throw new TeamExcoption("团队中只能有两位设计师");
            }
        }else{
            if (numofPro >= 3){
                throw new TeamExcoption("团队中只能有三位位程序员");
            }
        }

        p.setMemberld(counter++);
        p.setStats(Status.BUSY);
        team[total++] = p;
    }

    /**
     * 判断e.id与当前team中有没有相同的
     * @param e
     * @return
     */
    private boolean isExist(Employee e) {
        for (int i = 0;i < total;i++){
            if(team[i].getId() == e.getId()){
                return true;
            }
        }
        return false;
    }

    /**
     * 方法：从团队中删除成员
     * 参数：待删除成员的memberId
     * 异常：找不到指定memberId的员工，删除失败
     * @param memberld
     * @throws TeamExcoption
     */
    public  void removeMember(int memberId) throws TeamExcoption{
        int i = 0;
        for(;i < total;i++){
            if(team[i].getMemberld() == memberId){
                team[i].setStats(Status.FREE);
                break;
            }
        }
        if(i == total){
            throw new TeamExcoption("找不到指定memberId的员工，删除失败");
        }
        for(int j = i + 1;j < total;j++){
            team[j - 1] = team[j];
        }
        team[--total] = null;
    }

}
