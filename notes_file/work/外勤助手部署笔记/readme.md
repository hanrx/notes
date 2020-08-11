
# 1.环境说明
* CVS中间层接口文档路径：starfsa_doc\develop\05.design
* 蚌埠开发分支：
    * 主应用BR_STARSMS-6_7_2_R1
    * 中间层BENBU_develop_10.0.0
    * 数据源配置：（BOSS管理员账号：00000，密码：admin）
    <bean id="dataSource"
    		class="org.springframework.jdbc.datasource.DriverManagerDataSource">
    		<property name="driverClassName" value="oracle.jdbc.driver.OracleDriver" />
    		<property name="url" value="jdbc:oracle:thin:@192.168.32.100:1521:stariboss" />
    		<property name="username" value="boss671" />
    		<property name="password" value="boss671" />
    	</bean>
    
* 辽宁开发分支：
    * 主应用BR_STARSMS-8_0_0_R1
    * 中间层7.1.0.0   
    * 数据源配置：（BOSS管理员账号：00000，密码：admin）
    <bean id="dataSource"
    		class="org.springframework.jdbc.datasource.DriverManagerDataSource">
    		<property name="driverClassName" value="oracle.jdbc.driver.OracleDriver" />
    		<property name="url" value="jdbc:oracle:thin:@192.168.32.100:1521:stariboss" />
    		<property name="username" value="shenyang20200226" />
    		<property name="password" value="shenyang20200226" />
    	</bean>
    	
* 天津开发分支：
    * 主应用BR_STARSMS-6_3_4R1
    * 中间层develop-3.0.0   
    * 数据源配置：（BOSS管理员账号：00000，密码：admin）
    <bean id="dataSource"
    		class="org.springframework.jdbc.datasource.DriverManagerDataSource">
    		<property name="driverClassName" value="oracle.jdbc.driver.OracleDriver" />
    		<property name="url" value="jdbc:oracle:thin:@10.0.250.19:1521:starbass" />
    		<property name="username" value="tj_20160217" />
    		<property name="password" value="tj_20160217" />
    	</bean>

# 2. 参考
外勤助手部署参考：http://confluence.startimes.me/pages/viewpage.action?pageId=6684935

外勤助手版本说明：http://confluence.startimes.me/pages/viewpage.action?pageId=63529520&tdsourcetag=s_pctim_aiomsg

国内BOSS环境搭建参考：http://confluence.startimes.me/pages/viewpage.action?pageId=46858487&tdsourcetag=s_pctim_aiomsg

# 3.其他注意事项
* 注释掉config.ini中的后台任务
* 客户端访问server配置文件：prefs.ini
* 设置IDEA启动参数：-Dfile.encoding=UTF-8
* IDEA部署Application context：/starFSAAppIntf
* postMan测试https：
    * http://confluence.startimes.me/pages/viewpage.action?pageId=63520080
    * http://confluence.startimes.me/pages/viewpage.action?pageId=52758208
           

# 4.密码记录
> http://jira.startimes.me:1880/   hanrx      HanRX20161209ZZZ














