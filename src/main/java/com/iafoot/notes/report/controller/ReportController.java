package com.iafoot.notes.report.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.star.dss.controller.AbstractCrudRestController;
import com.star.dss.model.Status;
import com.star.dss.model.dashboard.Dashboard;
import com.star.dss.model.report.Report;
import com.star.dss.model.report.ReportClass;
import com.star.dss.model.system.Role;
import com.star.dss.model.system.User;
import com.star.dss.service.CrudService;
import com.star.dss.service.common.IFolderService;
import com.star.dss.service.report.IReportService;
import com.star.dss.service.system.IRoleService;

@RestController
@RequestMapping("/pc/api/report")
public class ReportController  extends AbstractCrudRestController<Report,Integer> {


	@Autowired
	private IReportService reportService;

	@Autowired
	private IFolderService folderService;
	

	@Autowired 
	private IRoleService roleService;
	
	@RequestMapping(value="/getDashboardsByConditions", method=RequestMethod.POST)
	@ResponseBody
	public List<Report> getReportsByConditions(@RequestBody Map<String, String> params){
		return reportService.getReportsByConditions(params);
	}
	
	@RequestMapping(value="/getReportAndConditions/{id}", method=RequestMethod.GET)
	public Map<String, Object> getReportAndConditions(@PathVariable("id") Integer id) {
		return reportService.getReportDefine(id);
	}
	
	@Override
	protected CrudService<Report, Integer> getService() {
		// TODO Auto-generated method stub
		return reportService;
	}
	

	
	@RequestMapping(value="/role/{roleId}", method=RequestMethod.GET)
	public List<Report> getReportByRoleID(@PathVariable("roleId") Integer roleId){
		return reportService.queryReportByRoleId(roleId);
	}

	
	@RequestMapping("/reportLimitUserAndDate")
	@ResponseBody
	public List<String> reportLimitUserAndDate(){
		List<String> userAndDateContiner = new ArrayList<String>();
		userAndDateContiner.add(reportService.reportLimitUserAndDate());
		return userAndDateContiner;
	}
	

	
	@RequestMapping(value="/reportClass", method=RequestMethod.POST)
	public void saveReportClass(@RequestBody Report report){
		this.reportService.maintainClass(report);
	}
	
	@RequestMapping(value="/updateRptClassId/{rptClassIdAndRptId}", method=RequestMethod.GET)
	public void updateRptClassId(@PathVariable("rptClassIdAndRptId") String rptClassIdAndRptId){
		this.reportService.updateRptClassId(rptClassIdAndRptId);
	}

	@RequestMapping("/findValid")
	@ResponseBody
	public List<Report> findValid(){
		return this.reportService.findValid();
	}
	
	@RequestMapping(value="/findReportbyVendor/{vendor}", method=RequestMethod.GET)
	public List<Report> findReportbyVendor(@PathVariable("vendor") String vendor){
		return this.reportService.findReportbyVendor(vendor);
	}
	
	@RequestMapping(value="/bindFolder", method=RequestMethod.POST)
	public String bindFolder(@RequestBody List<Report> reports) {
		if (reports.isEmpty()) {
			return "{ \"result\" : \"FAILED\", \"info\": \"参数列表不能为空\" }";
		}
		Integer folderId = reports.get(0).getFolderId();
		if (reports.get(0).getId() < 0) {
			reports.removeAll(reports);
		}
		List<Role> roles = roleService.getCurrentUserAndDeptRoles("@");
		List<Report> rs = new ArrayList<Report>();
		for(Role r : roles){
			rs.addAll(r.getReports());
		}
		
		for (Report r : rs) {
			if (r.getFolderId() != null && r.getFolderId().equals(folderId)) {
				r.setFolderId(null);
				this.save(r);
			}
		}
		
		for (Report r : reports) {
			r = this.get(r.getId());
			r.setFolderId(folderId);
			
			this.save(r);
		}
		return "{ \"result\" : \"SUCCESS\" }";
	}
	//为角色绑定主页
	@RequestMapping(value="/saveAsHome", method=RequestMethod.POST)
	public void saveAsHome(@RequestBody Map<String, String> params){
		this.reportService.saveAsHome(params);
	}

	//获取主页
	@RequestMapping(value="/getHome", method=RequestMethod.GET)
	public Map<String,Object>  getHome(){
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("homeUrl", this.reportService.getHome());
		return result;
	}
	
}
