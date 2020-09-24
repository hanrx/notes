package com.iafoot.notes.report.server;

import java.io.InputStream;
import java.lang.reflect.Array;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.star.dss.controller.system.dto.UsersAndRoleDTO;
import com.star.dss.dao.BasicRepository;
import com.star.dss.dao.common.repository.FolderRepository;
import com.star.dss.dao.report.repository.ReportRepository;
import com.star.dss.dao.system.repository.DeptsAndRolesRepository;
import com.star.dss.dao.system.repository.LimitsMirrorRepository;
import com.star.dss.dao.system.repository.RoleRepository;
import com.star.dss.dao.system.repository.UserRepository;
import com.star.dss.model.Status;
import com.star.dss.model.dashboard.Dashboard;
import com.star.dss.model.report.Report;
import com.star.dss.model.report.ReportClass;
import com.star.dss.model.system.DataCountry;
import com.star.dss.model.system.DeptsAndRoles;
import com.star.dss.model.system.LimitsMirror;
import com.star.dss.model.system.Menu;
import com.star.dss.model.system.Role;
import com.star.dss.model.system.User;
import com.star.dss.service.AbstractCrudService;
import com.star.dss.service.system.ILimitsMirrorService;
import com.star.dss.service.system.IRoleService;

@Service
public class ReportService extends AbstractCrudService<Report, Integer> implements IReportService, ResourceLoaderAware {
	@Value("${reports.filepath}")
	private String reportsPath;
	@Value("${reports.filepath.foreshow}")  
	private String reportsPathForeshow;
	@Value("${showParamConst.yonghong.url}")
	private String yonghongServer;
	@Value("${showParamConst.yonghong.appPath}")
	private String yonghongAppPath;	
	@Value("${system.reportLimitUserAndDate}")
	public String limitUserAndDate;	
	public String lookReportplatformUrl;	
	@Autowired
	@Qualifier("jdbcTemplate")
	private NamedParameterJdbcTemplate jdbcTemplate;
	@Autowired 
	private ReportRepository reportRepository;
	@Autowired 
	private RoleRepository roleRepository;
	@Autowired 
	private IRoleService roleService;
	@Value("${showParamConst.yonghong.url}")
	protected String YH_URL;
	@Value("${showParamConst.yonghong.admin}")
	protected String YH_USER;
	@Value("${showParamConst.yonghong.passwd}")
	protected String YH_PWD;	
	@Autowired 
	private DeptsAndRolesRepository deptsAndRolesRepository;
	@Autowired 
	private UserRepository userRepository;
	@Autowired
	private LimitsMirrorRepository limitsMirrorRepository;
	@Autowired
	private ILimitsMirrorService limitsMirrorService;
	@Autowired
	private FolderRepository folderRepository;
	
	private ResourceLoader resourceLoader;
	private MessageSource messageSource;
	private Log log = LogFactory.getLog(this.getClass());	
	@PostConstruct
	public void afterPropertiesSet() throws Exception {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("reports/message");
		this.messageSource = messageSource;
	}


	@Override
	public BasicRepository<Report, Integer> getRepository() {
		return reportRepository;
	}
	
	
	@SuppressWarnings("unchecked")
	public void setReportRelation(Map<String,Object> params){
		List<Role> roles = (List<Role>) params.get("roles");
		List<Report> reports = (List<Report>) params.get("reports");
		for(Role r : roles){
			Role oldRole = roleRepository.findOne(r.getId());					
			List<Report> oldReport = oldRole.getReports();
			oldReport.removeAll(reports);
			reports.addAll(oldReport);
			oldRole.setReports(reports);;
			roleRepository.save(oldRole);
		}	
	}
	
	public List<Report> getReportsByConditions(Map<String, String> params){
		String [] roleIds = params.get("roleIds").split(",");
		String folderId = params.get("folderId");
		String name = params.get("name");
		List<Integer> ids = new ArrayList<Integer>();
		for(String id : roleIds){
			ids.add(Integer.valueOf(id));
		}
		List<Role> roles = roleRepository.findAll(ids);
		
		List<Report> reports = roles.get(0).getReports();
		for(int i=0;i<roles.size();i++){
			//取所有集合的交集
			reports.retainAll(roles.get(i).getReports());
		}
		
		Iterator<Report> iter = reports.iterator();
		   
		while(iter.hasNext()){
			if(iter.next().getFolderId().intValue() != Integer.valueOf(folderId).intValue()){
				iter.remove();
			}
		}
		
		if(name == null || "".equals(name)){
			return reports;
		}
		
		if(reports.size() == 0){
			
		}else{
			Iterator<Report> ite = reports.iterator();
			
			while(ite.hasNext()){
				if(ite.next().getName().indexOf(name)<0){
					ite.remove();
				}
			}
		}	
		return reports;
		
	}


	@Override
	public List<Report> queryReportByRoleId(Integer roleId) {
		List<Report> reports = reportRepository.findValidByRoleId(roleId);
		for (Report rpt : reports) {
			rpt.setRoles(null);
		}
		return reports;
	}

	@Override
	public void maintainRel(Role role) {
		Role oldRole = roleRepository.findOne(role.getId());
		oldRole.setReports(role.getReports());;
		roleRepository.save(oldRole);
	}
	
	@Override
	public void maintainClass(Report report){
		Report rpt = reportRepository.findOne(report.getId());
		rpt.setReportClass(report.getReportClass());
		this.reportRepository.save(rpt);
	}

	@Override
	public List<Report> findValid() {
		return reportRepository.findAllValid();
	}
	
	@Override
	public List<Report> findReportbyVendor(String vendor) {
		return reportRepository.findReportbyVendor(vendor);
	}

	
	@Override
	public Map<String, Object> getReportDefine(Integer id) {

		Report report = this.reportRepository.findOne(id);
		
		checkPermission(report);
		
		if("yonghong".equalsIgnoreCase(report.getVendor())){
			HashMap<String, Object> result = new HashMap<String, Object>();
			result.put("name", report.getName());
			result.put("display", report.getDisplay());
			result.put("url", "http://" + yonghongServer + "/" + yonghongAppPath + "/Viewer?proc=1&action=viewer&hback=true&db=" + report.getName() + ".db");
			result.put("height", getHeight(report.getMem()));
			return result;
		}else{
			return runqianReport(report);
		}
	}
	private String getHeight(String mem) {
		int start = mem.indexOf("height:");
		String height = mem.substring(start);
		int end = height.indexOf("|");
		return height.substring(0, end);
	}
	
	//h.yf system.pre_release 灰度发布权限判断
	private boolean UserPreRelease(){
		boolean bool = false;
		for(Role role: getCurrentUser().getRoles()){
			for(Menu menu: role.getMenus()){
				if(menu.getCode().equals("system.pre_release")){
					bool =true;
				}
	           // System.out.println(menu.getCode());
	        }
           // System.out.println(role.getName());
        }
		return bool;
	}
	
	private Map<String, Object> runqianReport(Report report) throws FactoryConfigurationError {
		String name = report.getName();

		Map<String, Object> result = new LinkedHashMap<String, Object>();

		// 读取报表xml
		//H.yf
		String str="";
		if(UserPreRelease()){
			str = reportsPathForeshow;
		}else{
			str = reportsPath;
			if("runqianV5".equals(report.getVendor())){
				str = str.replaceAll("rq4-runtime", "rq5-runtime");
			}
		}
		Resource r = resourceLoader.getResource(str + name + ".xml");
		
		InputStream in = null;
		try {
			in = r.getInputStream();
			Document doc = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder().parse(in);
			Element elmtInfo = doc.getDocumentElement();

			result.putAll(getNodeAttrs(elmtInfo));

			List<Object> conditions = new ArrayList<Object>();
			result.put("conditions", conditions);

			// statquery/conditions/datalist
			NodeList nodes = find(elmtInfo.getChildNodes(), "conditions")
					.getChildNodes();
			for (int i = 0; i < nodes.getLength(); i++) {
				if (nodes.item(i).getNodeType() != Node.ELEMENT_NODE) {
					continue;
				}
				Map<String, Object> condition = new LinkedHashMap<String, Object>();
				conditions.add(condition);

				condition.put("type", nodes.item(i).getNodeName());
				condition.putAll(getNodeAttrs(nodes.item(i)));

				List<Object> deps = new ArrayList<Object>();
				condition.put("dependency", deps);

				NodeList depNodes = nodes.item(i).getChildNodes();
				for (int j = 0; j < depNodes.getLength(); j++) {
					Node t = depNodes.item(j);
					if (t.getNodeName().equals("dependency")) {
						deps.add(getNodeAttrs(t));
					}
				}
			}
			// statquery/dimensions.value
			doValuesNode(result, elmtInfo, "dimensions");
			doValuesNode(result, elmtInfo, "measures");
		} catch (Exception e) {
			log.error("", e);
			throw new RuntimeException(e);
		} finally {
			try {
				in.close();
			} catch (Exception e) {
				log.error("", e);
			}
		}

		result.put("display", report.getDisplay());
		return result;
	}

	private void doValuesNode(Map<String, Object> result, Element elmtInfo,
			String key) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		Node node = find(elmtInfo.getChildNodes(), key);
		if (node != null) {
			String[] ts = node.getAttributes().getNamedItem("value")
					.getNodeValue().split(",");
			for (String t : ts) {
				Map<String, String> m = new HashMap<String, String>();
				m.put("name", t);
				m.put("display", getMsg(t));
				list.add(m);
			}
		}
		result.put(key, list);
	}

	private Node find(NodeList cs, String name) {
		for (int i = 0; i < cs.getLength(); i++) {
			Node result = cs.item(i);
			if (result.getNodeType() == Node.ELEMENT_NODE
					&& result.getNodeName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	private Map<String, Object> getNodeAttrs(Node node) {
		NamedNodeMap map = node.getAttributes();
		Map<String, Object> attrs = new LinkedHashMap<String, Object>();
		for (int j = 0; j < map.getLength(); j++) {
			Node t = map.item(j);
			String tn = t.getNodeName();
			String tv = t.getNodeValue();
			attrs.put(tn, tv);

			if ("name".equals(tn)) {
				attrs.put("display", getMsg(tv));
			}

		}
		return attrs;
	}

	private void checkPermission(Report report) {
		List<Role> roles = roleService.getCurrentUserAndDeptRoles("@");
		List<Report> reports = new ArrayList<Report>();
		for(Role r:roles){
			reports.addAll(r.getReports());
		}
		boolean b = false;
		for (Report t : reports) {
			if(t.getId().equals(report.getId())){
				b = true;
				break;
			}
		}
		if(!b){
			throw new RuntimeException("Users haven't Permission to access Report id = " + report.getId() + "." );
		}
	}
	
	private String getMsg(String tn) {
		return this.messageSource.getMessage(tn, null, tn, null);
	}

	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}


	@Override
	public void updateRptClassId(String rptClassIdAndRptId) {
		// TODO Auto-generated method stub
		String sql = "";
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		String reportclassId=rptClassIdAndRptId.split(",")[0];
		paramMap.put("id", reportclassId);
		sql = "update rpt_report set reportclass_id='' where reportclass_id=:id";
		jdbcTemplate.update(sql, paramMap);
		String sql1 = "";
		HashMap<String, Object> paramMap1 = new HashMap<String, Object>();
		paramMap1.put("id", reportclassId);
		String str=rptClassIdAndRptId.substring(rptClassIdAndRptId.indexOf(",")+1);
		String [] strs = str.split(",");
		for(int i=0;i<strs.length;i++){
			paramMap1.put("reportId", strs[i]);
			sql1 = "update rpt_report set reportclass_id = :id where id=:reportId";
			jdbcTemplate.update(sql1, paramMap1);
		}
	}


	@Override
	public Map<String, Object> getOldReport(Integer id) {
		Report r = this.get(id);
		Map<String, Object> returnThis = new HashMap<String, Object>();
		returnThis.put("url", this.lookReportplatformUrl + r.getName());
		returnThis.put("display", r.getDisplay());
		return returnThis;
	}

	
	
	@Override
	public void updateReportRelationForRoles(Map<String,String> params) {
		String reportId = params.get("reportId");
		Report report = reportRepository.findOne(Integer.valueOf(reportId));
		
		String selectRoleIds = params.get("selectRoleIds");
		String noSelectRoleIds = params.get("noSelectRoleIds");
		if(!"".equals(selectRoleIds)){
			String roleIds[] = selectRoleIds.split(",");
			for(String roleId : roleIds){
				Role r = roleRepository.findOne(Integer.valueOf(roleId));
				if(!r.getReports().contains(report)){
					r.getReports().add(report);
					roleRepository.save(r);				
				}
			}		
		}
		if(!"".equals(noSelectRoleIds)){
			String roleIds[] = noSelectRoleIds.split(",");
			for(String roleId : roleIds){
				Role r = roleRepository.findOne(Integer.valueOf(roleId));
				if(r.getReports().contains(report)){
					r.getReports().remove(report);
					roleRepository.save(r);				
				}
			}			
		}
		
	}


	@Override
	public UsersAndRoleDTO updateReportRelation(String params) {
		// TODO 修改实现方式！！！！！！
		params = params.trim();
		String[] paramArr = params.split(";");
		int roleId = Integer.parseInt(paramArr[0].trim());
		String[] selectedRPIds = paramArr[1].trim().split(",");
		
		String[] noSelectedRPIds = null;
		if (paramArr.length == 3) {
			noSelectedRPIds = paramArr[2].trim().split(",");
		}
		Role role = roleRepository.getOne(roleId);
		List<Report> reports = role.getReports();
		if (paramArr[1].trim().length() > 0) {
			for (String id : selectedRPIds) {
				Report r = reportRepository.findOne(Integer.valueOf(id));
				reports.remove(r);
			}
		}
		if (paramArr.length == 3 && paramArr[2].trim().length() > 0) {
			for (String id : noSelectedRPIds) {
				Report r = reportRepository.findOne(Integer.valueOf(id));
				reports.remove(r);
			}
		}
		for (String id : selectedRPIds) {
			if (paramArr[1].trim().length() != 0) {
				Report r = reportRepository.findOne(Integer.valueOf(id));
				reports.add(r);
			}			
		}
		//201809如下添加查询接口
		UsersAndRoleDTO dto = new UsersAndRoleDTO();
		List<User> allUsers = new ArrayList<User>();
		List<DeptsAndRoles> dars = deptsAndRolesRepository.findByRoleId(roleId);
		for(DeptsAndRoles dar : dars){
			List<User> deptUsers = userRepository.findUsersByDeptId(dar.getDepartment().getId());
			allUsers.addAll(deptUsers);
		}
		//List<User> users = userRepository.findUsersByRoleId(roleId);
		Role role1 = roleRepository.save(role);
		allUsers.addAll(role1.getUsers());
		List<LimitsMirror> limitsMirrors = limitsMirrorRepository.findValidByRoleId(roleId);//r.hy添加权限单元begin
		for(LimitsMirror limitsMirror : limitsMirrors){
			limitsMirror.setDisplayName(limitsMirrorService.getDisplayName(limitsMirror.getMirror_table(), limitsMirror.getMirror_id()));
			limitsMirror.setFolder(folderRepository.findOne(limitsMirror.getFolderId()));
		}	
		role1.setLimitsMirrors(limitsMirrors);//r.hy添加权限单元end
		dto.setUsers(allUsers);
		dto.setRole(role1);		
		return dto;
	}


	@Override
	public  String reportLimitUserAndDate() {
		// TODO Auto-generated method stub
		return limitUserAndDate;
	}
	
	public void saveAsHome(Map<String, String> params){
		String reportId = params.get("reportId");
		Integer ruleId = Integer.valueOf(params.get("ruleId"));
		Role role=roleRepository.getOne(ruleId);
		if(reportId ==null||reportId==""){
			role.setReportid(null);
		}else{
			role.setReportid(Integer.valueOf(reportId));			
		}
		roleRepository.save(role);
	}
	 //获取所有有效的永洪类报表
	public List<Report> findValidByVendor(String vendor){
		return reportRepository.findValidByVendor(vendor);
	 }
	//获取主页
	public String getHome(){
		User currentUser = this.getCurrentUser();	
		//调用永洪URL添加&countryId= 
		String countryIds = "&countryId=";
		List<DataCountry> countries = currentUser.getCountries();
		for(DataCountry d:countries){
			countryIds += ("!a!" +d.getCountry_pk());
		}
		countryIds = countryIds.replace("=!a!", "=");
		List<Role>  roles =   roleService.getCurrentUserAndDeptRoles("@");
		for(Role r : roles){
			if(r.getReportid() !=null){
				String st = YH_URL+reportRepository.findOne(r.getReportid()).getReport_url()+"&adminv="+ YH_USER +"&passv="+YH_PWD+countryIds;
				return st;
			}
		}
		return null;
	}
}
