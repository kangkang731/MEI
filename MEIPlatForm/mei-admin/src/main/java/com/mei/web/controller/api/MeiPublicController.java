package com.mei.web.controller.api;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mei.common.core.controller.BaseController;
import com.mei.common.core.domain.AjaxResult;
import com.mei.common.core.page.TableDataInfo;
import com.mei.domain.MeiPolicy;
import com.mei.domain.MeiAchievement;
import com.mei.domain.MeiStatistics;
import com.mei.domain.MeiCompany;
import com.mei.domain.MeiFinance;
import com.mei.domain.MeiProject;
import com.mei.domain.MeiRecruit;
import com.mei.domain.MeiConsult;
import com.mei.domain.MeiMessage;
import com.mei.domain.MeiExpert;
import com.mei.domain.MeiLab;
import com.mei.service.IMeiPolicyService;
import com.mei.service.IMeiAchievementService;
import com.mei.service.IMeiStatisticsService;
import com.mei.service.IMeiCompanyService;
import com.mei.service.IMeiFinanceService;
import com.mei.service.IMeiProjectService;
import com.mei.service.IMeiRecruitService;
import com.mei.service.IMeiConsultService;
import com.mei.service.IMeiMessageService;
import com.mei.service.IMeiExpertService;
import com.mei.service.IMeiLabService;

/**
 * 双创平台公开API控制器
 * 供MEI-UI前端调用（通过Vite代理 /api -> /）
 */
@RestController
@RequestMapping("/")
public class MeiPublicController extends BaseController {

    @Autowired
    private IMeiPolicyService meiPolicyService;

    @Autowired
    private IMeiAchievementService meiAchievementService;

    @Autowired
    private IMeiStatisticsService meiStatisticsService;

    @Autowired
    private IMeiCompanyService meiCompanyService;

    @Autowired
    private IMeiFinanceService meiFinanceService;

    @Autowired
    private IMeiProjectService meiProjectService;

    @Autowired
    private IMeiRecruitService meiRecruitService;

    @Autowired
    private IMeiConsultService meiConsultService;

    @Autowired
    private IMeiMessageService meiMessageService;

    @Autowired
    private IMeiExpertService meiExpertService;

    @Autowired
    private IMeiLabService meiLabService;

    // ========== 政策公告 ==========

    /**
     * 政策公告列表（含分页/类型/关键词）
     * GET /api/policy/list -> /policy/list
     */
    @GetMapping("/policy/list")
    public AjaxResult policyList(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        startPage();
        MeiPolicy query = new MeiPolicy();
        query.setType(type);
        if (keyword != null && !keyword.trim().isEmpty()) {
            query.setTitle(keyword.trim());
        }
        List<MeiPolicy> list = meiPolicyService.selectMeiPolicyList(query);
        TableDataInfo tableData = getDataTable(list);
        Map<String, Object> data = new HashMap<>();
        data.put("list", tableData.getRows());
        data.put("total", tableData.getTotal());
        return AjaxResult.success(data);
    }

    /**
     * 政策公告详情（query参数方式）
     * /policy/detail?id=1
     */
    @GetMapping("/policy/detail")
    public AjaxResult policyDetail(@RequestParam Long id) {
        MeiPolicy policy = meiPolicyService.selectMeiPolicyById(id);
        return AjaxResult.success(policy);
    }

    /**
     * 政策公告详情（路径参数方式）
     * GET /api/policy/1 -> /policy/1
     */
    @GetMapping("/policy/{id}")
    public AjaxResult policyDetailById(@PathVariable Long id) {
        MeiPolicy policy = meiPolicyService.selectMeiPolicyById(id);
        return AjaxResult.success(policy);
    }

    // ========== 成果展示 ==========

    /**
     * 成果列表
     * GET /api/mei_achievement -> /mei_achievement
     */
    @GetMapping("/mei_achievement")
    public AjaxResult achievementList(
            @RequestParam(required = false) String isShow,
            @RequestParam(required = false) String projectId) {
        MeiAchievement query = new MeiAchievement();
        query.setIsShow(isShow);
        if (projectId != null && !projectId.isEmpty()) {
            query.setProjectId(projectId);
        }
        List<MeiAchievement> list = meiAchievementService.selectMeiAchievementList(query);
        return AjaxResult.success(list);
    }

    /**
     * 成果详情
     * GET /api/mei_achievement/{id} -> /mei_achievement/{id}
     */
    @GetMapping("/mei_achievement/{id}")
    public AjaxResult achievementDetail(@PathVariable String id) {
        MeiAchievement achievement = meiAchievementService.selectMeiAchievementById(id);
        return AjaxResult.success(achievement);
    }

    // ========== 数据统计 ==========

    /**
     * 统计数据列表
     * GET /api/statistics/list -> /statistics/list
     */
    @GetMapping("/statistics/list")
    public AjaxResult statisticsList(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        startPage();
        MeiStatistics query = new MeiStatistics();
        List<MeiStatistics> list = meiStatisticsService.selectMeiStatisticsList(query);
        TableDataInfo tableData = getDataTable(list);
        return AjaxResult.success(tableData);
    }

    /**
     * 最新统计数据
     * GET /api/mei_statistics/latest -> /mei_statistics/latest
     */
    @GetMapping("/mei_statistics/latest")
    public AjaxResult latestStatistics() {
        MeiStatistics query = new MeiStatistics();
        List<MeiStatistics> list = meiStatisticsService.selectMeiStatisticsList(query);
        if (!list.isEmpty()) {
            return AjaxResult.success(list.get(list.size() - 1));
        }
        return AjaxResult.success(new MeiStatistics());
    }

    // ========== 企业入驻 ==========

    /**
     * 企业入驻申请
     * POST /api/enterprise/entry -> /enterprise/entry
     * POST /api/company -> /company  (前端 /mei/company 代理后路径)
     * 前端字段: userId, companyName, region, business, linkman, phone
     */
    @SuppressWarnings("unchecked")
    @PostMapping({"/enterprise/entry", "/company"})
    public AjaxResult enterpriseEntry(@RequestBody Map<String, Object> body) {
        MeiCompany company = new MeiCompany();
        // 兼容新旧两种前端字段名
        company.setCompanyName(or(body, "companyName", "enterpriseName"));
        company.setRegion(or(body, "region", "address"));
        company.setBusiness(or(body, "business", "mainBusiness"));
        company.setLinkman(or(body, "linkman", "contactPerson"));
        company.setPhone(or(body, "phone", "contactPhone"));
        company.setStatus("0");

        // 设置用户ID
        Object userIdObj = body.get("userId");
        if (userIdObj != null) {
            company.setUserId(Long.parseLong(userIdObj.toString()));
        }

        return toAjax(meiCompanyService.insertMeiCompany(company));
    }

    private String or(Map<String, Object> map, String key1, String key2) {
        Object val = map.get(key1);
        if (val != null) return val.toString();
        val = map.get(key2);
        return val == null ? null : val.toString();
    }

    /**
     * 查询用户的企业列表
     * GET /api/company/list -> /company/list
     */
    @GetMapping("/company/list")
    public AjaxResult companyList(@RequestParam(required = false) Long userId) {
        MeiCompany query = new MeiCompany();
        query.setUserId(userId);
        List<MeiCompany> list = meiCompanyService.selectMeiCompanyList(query);
        return AjaxResult.success(list);
    }

    private String getString(Map<String, Object> map, String key) {
        Object val = map.get(key);
        return val == null ? null : val.toString();
    }

    @SuppressWarnings("unchecked")
    private String joinList(Map<String, Object> map, String key) {
        Object val = map.get(key);
        if (val instanceof List) {
            return ((List<Object>) val).stream()
                    .map(Object::toString)
                    .collect(Collectors.joining(","));
        }
        return val == null ? null : val.toString();
    }

    // ========== 融资申请 ==========

    /**
     * 融资列表（支持按userId过滤）
     * GET /api/finance/list -> /finance/list
     */
    @GetMapping("/finance/list")
    public AjaxResult financeList(
            @RequestParam(required = false) Long userId,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        startPage();
        MeiFinance query = new MeiFinance();
        // 如果指定userId，先查该用户的企业，再按企业ID过滤融资记录
        if (userId != null) {
            MeiCompany companyQuery = new MeiCompany();
            companyQuery.setUserId(userId);
            List<MeiCompany> companies = meiCompanyService.selectMeiCompanyList(companyQuery);
            if (companies != null && !companies.isEmpty()) {
                // 取用户所有企业的ID进行过滤（当前Mapper只支持单companyId，取第一个）
                query.setCompanyId(companies.get(0).getId());
            } else {
                // 用户没有企业，返回空列表
                Map<String, Object> empty = new HashMap<>();
                empty.put("list", java.util.Collections.emptyList());
                empty.put("total", 0);
                return AjaxResult.success(empty);
            }
        }
        List<MeiFinance> list = meiFinanceService.selectMeiFinanceList(query);
        TableDataInfo tableData = getDataTable(list);
        Map<String, Object> data = new HashMap<>();
        data.put("list", tableData.getRows());
        data.put("total", tableData.getTotal());
        return AjaxResult.success(data);
    }

    /**
     * 融资申请
     * POST /api/finance/apply -> /finance/apply
     * 前端字段: userId, productName, amount, contact, description -> 后端: companyId, money, reason
     */
    @PostMapping("/finance/apply")
    public AjaxResult financingApply(@RequestBody Map<String, Object> body) {
        MeiFinance finance = new MeiFinance();
        
        // 获取用户ID并查找对应的企业ID
        Object userIdObj = body.get("userId");
        Long userId = userIdObj != null ? Long.parseLong(userIdObj.toString()) : null;
        
        if (userId != null) {
            // 查询用户的企业入驻信息
            MeiCompany query = new MeiCompany();
            query.setUserId(userId);
            List<MeiCompany> companies = meiCompanyService.selectMeiCompanyList(query);
            if (companies != null && !companies.isEmpty()) {
                // 取第一个审核通过的企业
                for (MeiCompany company : companies) {
                    if ("1".equals(company.getStatus())) {
                        finance.setCompanyId(company.getId());
                        break;
                    }
                }
                if (finance.getCompanyId() == null) {
                    return AjaxResult.error("请先完成企业入驻并等待审核通过");
                }
            } else {
                return AjaxResult.error("请先完成企业入驻");
            }
        } else {
            return AjaxResult.error("用户ID不能为空");
        }
        
        String amountStr = getString(body, "amount");
        if (amountStr != null && !amountStr.isEmpty()) {
            try { finance.setMoney(new BigDecimal(amountStr.replaceAll("[^\\d.]", ""))); }
            catch (NumberFormatException e) { finance.setMoney(BigDecimal.ZERO); }
        }
        finance.setReason(getString(body, "description"));
        finance.setStatus("0");
        return toAjax(meiFinanceService.insertMeiFinance(finance));
    }

    // ========== 项目申报 ==========

    /**
     * 项目申报列表（支持按userId过滤）
     * GET /api/project/list -> /project/list
     */
    @GetMapping("/project/list")
    public AjaxResult projectList(
            @RequestParam(required = false) Long userId,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        startPage();
        MeiProject query = new MeiProject();
        if (userId != null) {
            MeiCompany companyQuery = new MeiCompany();
            companyQuery.setUserId(userId);
            List<MeiCompany> companies = meiCompanyService.selectMeiCompanyList(companyQuery);
            if (companies != null && !companies.isEmpty()) {
                query.setCompanyId(companies.get(0).getId());
            } else {
                Map<String, Object> empty = new HashMap<>();
                empty.put("list", java.util.Collections.emptyList());
                empty.put("total", 0);
                return AjaxResult.success(empty);
            }
        }
        List<MeiProject> list = meiProjectService.selectMeiProjectList(query);
        TableDataInfo tableData = getDataTable(list);
        Map<String, Object> data = new HashMap<>();
        data.put("list", tableData.getRows());
        data.put("total", tableData.getTotal());
        return AjaxResult.success(data);
    }

    /**
     * 项目申报
     * POST /api/project/apply -> /project/apply
     * 前端字段: userId, projectName, projectType, desc, contact, fileList -> 后端: companyId, projectName, projectType, content, attach
     */
    @PostMapping("/project/apply")
    public AjaxResult projectApply(@RequestBody Map<String, Object> body) {
        MeiProject project = new MeiProject();

        // 获取用户ID并查找对应的企业ID
        Object userIdObj = body.get("userId");
        Long userId = userIdObj != null ? Long.parseLong(userIdObj.toString()) : null;

        if (userId == null) {
            return AjaxResult.error("用户ID不能为空");
        }

        // 查询用户的企业入驻信息
        MeiCompany query = new MeiCompany();
        query.setUserId(userId);
        List<MeiCompany> companies = meiCompanyService.selectMeiCompanyList(query);
        if (companies == null || companies.isEmpty()) {
            return AjaxResult.error("您尚未完成企业入驻，请先前往「企业入驻」模块提交入驻申请");
        }
        for (MeiCompany company : companies) {
            if ("1".equals(company.getStatus())) {
                project.setCompanyId(company.getId());
                break;
            }
        }
        if (project.getCompanyId() == null) {
            return AjaxResult.error("您的企业入驻申请正在审核中，请等待审核通过后再提交项目申报");
        }
        
        project.setProjectName(getString(body, "projectName"));
        project.setProjectType(getString(body, "projectType"));
        String desc = getString(body, "desc");
        String contactName = getString(body, "contactName");
        String contactPhone = getString(body, "contactPhone");
        StringBuilder content = new StringBuilder();
        if (desc != null) content.append(desc);
        if (contactName != null || contactPhone != null) {
            content.append("\n【申报人】姓名:").append(contactName != null ? contactName : "");
            content.append(" 电话:").append(contactPhone != null ? contactPhone : "");
        }
        project.setContent(content.toString());
        project.setAttach(joinList(body, "fileList"));
        project.setStatus("0");
        return toAjax(meiProjectService.insertMeiProject(project));
    }

    // ========== 人才招聘 ==========

    /**
     * 招聘信息列表（关联企业表获取企业名称和地区）
     * GET /api/talent/recruit -> /talent/recruit
     */
    @GetMapping("/talent/recruit")
    public AjaxResult talentRecruit() {
        MeiRecruit query = new MeiRecruit();
        List<MeiRecruit> list = meiRecruitService.selectMeiRecruitList(query);
        // 关联企业信息
        List<Map<String, Object>> result = new java.util.ArrayList<>();
        for (MeiRecruit r : list) {
            Map<String, Object> item = new HashMap<>();
            item.put("id", r.getId());
            item.put("companyId", r.getCompanyId());
            item.put("jobName", r.getJobName());
            item.put("requirement", r.getRequirement());
            item.put("salary", r.getSalary());
            item.put("status", r.getStatus());
            item.put("createTime", r.getCreateTime());
            // 查询关联企业
            if (r.getCompanyId() != null) {
                MeiCompany company = meiCompanyService.selectMeiCompanyById(r.getCompanyId());
                if (company != null) {
                    item.put("companyName", company.getCompanyName());
                    item.put("workLocation", company.getRegion());
                    item.put("business", company.getBusiness());
                    item.put("linkman", company.getLinkman());
                    item.put("phone", company.getPhone());
                }
            }
            if (!item.containsKey("companyName")) {
                item.put("companyName", "未知企业");
            }
            if (!item.containsKey("workLocation")) {
                item.put("workLocation", "西藏");
            }
            result.add(item);
        }
        return AjaxResult.success(result);
    }

    /**
     * 求职申请（存入留言表，绑定userId）
     * POST /api/talent/apply -> /talent/apply
     * 前端字段: userId, name, phone, position, experience, resumeUrl
     */
    @PostMapping("/talent/apply")
    public AjaxResult talentApply(@RequestBody Map<String, Object> body) {
        MeiMessage message = new MeiMessage();
        // 绑定用户ID
        Object userIdObj = body.get("userId");
        Long userId = userIdObj != null ? Long.parseLong(userIdObj.toString()) : null;
        if (userId != null) {
            message.setUserId(userId);
        }

        String name = getString(body, "name");
        String phone = getString(body, "phone");
        String position = getString(body, "position");
        String experience = getString(body, "experience");
        String resumeUrl = getString(body, "resumeUrl");

        // 如果name是"待填写"，从已有的求职登记中获取用户信息
        if ("待填写".equals(name) && userId != null) {
            MeiMessage query = new MeiMessage();
            query.setUserId(userId);
            List<MeiMessage> list = meiMessageService.selectMeiMessageList(query);
            MeiMessage latest = list.stream()
                    .filter(m -> m.getContent() != null && m.getContent().startsWith("【求职申请】"))
                    .reduce((a, b) -> b)
                    .orElse(null);
            if (latest != null) {
                String content = latest.getContent();
                String[] parts = content.replace("【求职申请】", "").split(",");
                for (String part : parts) {
                    String[] kv = part.split(":", 2);
                    if (kv.length == 2) {
                        String key = kv[0].trim();
                        String value = kv[1].trim();
                        switch (key) {
                            case "姓名": name = value; break;
                            case "电话": phone = value; break;
                            case "经验": experience = value; break;
                            case "简历": resumeUrl = value; break;
                        }
                    }
                }
            }
        }

        String content = String.format("【求职申请】岗位:%s, 姓名:%s, 电话:%s, 经验:%s, 简历:%s",
                position, name, phone, experience, resumeUrl);
        message.setContent(content);
        return toAjax(meiMessageService.insertMeiMessage(message));
    }

    /**
     * 检查用户是否已填写求职登记
     * GET /talent/check?userId={userId}
     */
    @GetMapping("/talent/check")
    public AjaxResult talentCheck(@RequestParam Long userId) {
        MeiMessage query = new MeiMessage();
        query.setUserId(userId);
        List<MeiMessage> list = meiMessageService.selectMeiMessageList(query);
        boolean hasRegistered = list.stream()
                .anyMatch(m -> m.getContent() != null && m.getContent().startsWith("【求职申请】"));
        return AjaxResult.success(hasRegistered);
    }

    /**
     * 获取用户求职登记信息
     * GET /talent/info?userId={userId}
     */
    @GetMapping("/talent/info")
    public AjaxResult talentInfo(@RequestParam Long userId) {
        MeiMessage query = new MeiMessage();
        query.setUserId(userId);
        List<MeiMessage> list = meiMessageService.selectMeiMessageList(query);
        // 取最新的求职申请记录
        MeiMessage latest = list.stream()
                .filter(m -> m.getContent() != null && m.getContent().startsWith("【求职申请】"))
                .reduce((a, b) -> b) // 取最后一条
                .orElse(null);
        if (latest == null) {
            return AjaxResult.success(null);
        }
        // 解析内容: 【求职申请】岗位:xx, 姓名:xx, 电话:xx, 经验:xx, 简历:xx
        Map<String, String> info = new HashMap<>();
        String content = latest.getContent();
        String[] parts = content.replace("【求职申请】", "").split(",");
        for (String part : parts) {
            String[] kv = part.split(":", 2);
            if (kv.length == 2) {
                String key = kv[0].trim();
                String value = kv[1].trim();
                switch (key) {
                    case "岗位": info.put("position", value); break;
                    case "姓名": info.put("name", value); break;
                    case "电话": info.put("phone", value); break;
                    case "经验": info.put("experience", value); break;
                    case "简历": info.put("resumeUrl", value); break;
                }
            }
        }
        return AjaxResult.success(info);
    }

    // ========== 在线咨询 ==========

    /**
     * 咨询列表（分页）
     * GET /api/consult/list -> /consult/list
     */
    @GetMapping("/consult/list")
    public AjaxResult consultList(
            @RequestParam(required = false) Long userId,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        startPage();
        MeiConsult query = new MeiConsult();
        query.setUserId(userId);
        List<MeiConsult> list = meiConsultService.selectMeiConsultList(query);
        TableDataInfo tableData = getDataTable(list);
        Map<String, Object> data = new HashMap<>();
        data.put("list", tableData.getRows());
        data.put("total", tableData.getTotal());
        return AjaxResult.success(data);
    }

    /**
     * 咨询列表
     * GET /api/consultation -> /consultation
     */
    @GetMapping("/consultation")
    public AjaxResult consultationList(
            @RequestParam(required = false) Long userId) {
        MeiConsult query = new MeiConsult();
        query.setUserId(userId);
        List<MeiConsult> list = meiConsultService.selectMeiConsultList(query);
        return AjaxResult.success(list);
    }

    /**
     * 提交咨询
     * POST /api/consult -> /consult
     * POST /api/consultation -> /consultation
     */
    @PostMapping({"/consult", "/consultation"})
    public AjaxResult consultationAdd(@RequestBody MeiConsult consult) {
        consult.setStatus("0");

        // 根据咨询类型自动匹配专家（失败不影响提交）
        try {
            if (consult.getType() != null && !consult.getType().isEmpty() && consult.getExpertId() == null) {
                MeiExpert query = new MeiExpert();
                query.setStatus("0");
                List<MeiExpert> experts = meiExpertService.selectMeiExpertList(query);
                if (experts != null && !experts.isEmpty()) {
                    String type = consult.getType();
                    String keyword;
                    switch (type) {
                        case "project": keyword = "项目"; break;
                        case "achievement": keyword = "成果"; break;
                        case "financing": keyword = "融资"; break;
                        case "talent": keyword = "人才"; break;
                        case "policy": keyword = "政策"; break;
                        default: keyword = ""; break;
                    }
                    final String kw = keyword;
                    MeiExpert matched = experts.stream()
                            .filter(e -> e.getMajor() != null && e.getMajor().contains(kw))
                            .findFirst()
                            .orElse(null);
                    if (matched == null) {
                        matched = experts.get(0);
                    }
                    consult.setExpertId(matched.getId());
                }
            }
        } catch (Exception e) {
            // 专家匹配失败不影响咨询提交
        }

        return toAjax(meiConsultService.insertMeiConsult(consult));
    }

    // ========== 平台互动（留言） ==========

    /**
     * 留言列表
     * GET /api/message/list -> /message/list
     */
    @GetMapping("/message/list")
    public AjaxResult messageList(@RequestParam(required = false) Long userId) {
        MeiMessage query = new MeiMessage();
        query.setUserId(userId);
        List<MeiMessage> list = meiMessageService.selectMeiMessageList(query);
        return AjaxResult.success(list);
    }

    /**
     * 提交留言
     * POST /api/message -> /message
     */
    @PostMapping("/message")
    public AjaxResult messageAdd(@RequestBody MeiMessage message) {
        return toAjax(meiMessageService.insertMeiMessage(message));
    }

    // ========== 专家信息 ==========

    /**
     * 专家列表
     * GET /api/expert/list -> /expert/list
     */
    @GetMapping("/expert/list")
    public AjaxResult expertList(@RequestParam(required = false) String major) {
        MeiExpert query = new MeiExpert();
        query.setMajor(major);
        List<MeiExpert> list = meiExpertService.selectMeiExpertList(query);
        return AjaxResult.success(list);
    }

    // ========== 实验室 ==========

    /**
     * 实验室列表
     * GET /api/lab/list -> /lab/list
     */
    @GetMapping("/lab/list")
    public AjaxResult labList(@RequestParam(required = false) String schoolName) {
        MeiLab query = new MeiLab();
        query.setSchoolName(schoolName);
        List<MeiLab> list = meiLabService.selectMeiLabList(query);
        return AjaxResult.success(list);
    }
}
