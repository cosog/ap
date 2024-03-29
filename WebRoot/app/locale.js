//国际化数据信息列表

var cosog = {};    
cosog.string = {};    

//项目名称
//cosog.string.title='AgileProduction 敏捷生产 V7.1';

//登录信息
cosog.string.userlogin='用户登录';
//cosog.string.loginInfo='AgileProduction 油气生产敏捷计算分析系统 V7.1主要在采集、控制的基础上，侧重油井智能分析。模块主要包括实时评价、全天评价、生产报表、图形查询等。系统应用大数据分析方法，对工况、产量、时率、平衡、能耗等生产关键指标进行统计分析，及时发现生产不正常井，挖掘生产潜力井，提升对目标区块和单井的管控能力。';
cosog.string.myself='我自己';
cosog.string.entername='请输入用户名';
cosog.string.enterpwd='请输入密码';
cosog.string.rememberpassword='记住密码';
cosog.string.forgerpassword='忘记密码了？';
cosog.string.contact='请联系管理员重置密码。';
cosog.string.login='立即登录';
cosog.string.logining='系统正在登录中...';

//版权信息
//cosog.string.copy='';
//cosog.string.linkaddress='http://www.cosogoil.com';
//cosog.string.linkshow='';


//分页工具栏
cosog.string.currentRecord='当前记录 {0} -- {1} 条 共 {2} 条记录';
cosog.string.nodataDisplay='没有记录可显示';
cosog.string.lastPage='上一页';
cosog.string.nextPage='下一页';
cosog.string.finalPage='最后页';
cosog.string.firstPage='第一页';
cosog.string.currentPage='当前页';
cosog.string.gong='共{0}页';
cosog.string.totalCount='总记录数';

cosog.string.pumpUnit='抽油机井';
cosog.string.screwPump='螺杆泵井';

cosog.string.realtimedata='实时数据采集时间';

//加载数据
cosog.string.loading='数据加载中，请稍后...';

//组织信息
cosog.string.superOrg = '上级单位';    
cosog.string.chooseOrg='--请选择单位--';
cosog.string.pleaseCheckOrg='请选择单位';
cosog.string.tips='信息提示';
cosog.string.msg='信息';
cosog.string.fail='后台获取数据失败！'
cosog.string.orgType='单位类别';
cosog.string.all='--全部--';
cosog.string.orgName='单位名称';
cosog.string.orgCode='单位编码';
cosog.string.orgLevel='单位级别';
cosog.string.orgMemo='单位说明';
cosog.string.queryOrg='查询单位信息...';
cosog.string.add='创建';
cosog.string.save='保存';
cosog.string.saveCoord="保存坐标";
cosog.string.update='修改';
cosog.string.cancel='取消';
cosog.string.del='删除';
cosog.string.yes='是';
cosog.string.no='否';
cosog.string.sure='确认';
cosog.string.name='名称';
cosog.string.search='查询';
cosog.string.collapse='收缩';
cosog.string.collapseAll='收缩全部';
cosog.string.expand='展开';
cosog.string.expandAll='展开全部';
cosog.string.refresh='刷新';
cosog.string.refreshAll='刷新菜单';
cosog.string.sendServer='正在向服务器提交数据';
cosog.string.bdZDynSuccess='标准功图标定成功';
cosog.string.ts='提示';
cosog.string.success='成功创建';
cosog.string.dataInfo='数据信息'
cosog.string.failInfo='创建失败';
cosog.string.execption='异常抛出';
cosog.string.grandFail='分配失败';
cosog.string.editdatException='修改数据字典信息异常';
cosog.string.adddatException='创建数字字典异常';
cosog.string.contactadmin='请与管理员联系';
cosog.string.validdata='请先检查数据有效性,再次提交';
cosog.string.updatewait='正在更新数据，请稍后...';
cosog.string.sucupate='成功更新';
cosog.string.updatefail='信息更新失败';
cosog.string.root='根节点';
cosog.string.addOrg='创建组织信息';
cosog.string.yesdel='是否要删除？'
cosog.string.yesdeldata='是否要删除这些被选择的数据？';
cosog.string.deleteCommand='信息';
cosog.string.checkOne='您必须选择一行数据以便操作！';
cosog.string.noDataChange="无数据被修改";
cosog.string.updateOrg='修改组织信息';
//区块信息
cosog.string.superRes='上级区块';
cosog.string.checkRes='--请选择区块--';
cosog.string.resLevel='区块显示级别';
cosog.string.checkLevel='请选择级别';
cosog.string.resName='区块名称';
cosog.string.qyeryRes='查询区块信息...';
cosog.string.rescode='区块编码';
cosog.string.resOrder='区块排序';
cosog.string.resMemo='区块说明';
cosog.string.resOne='一级';
cosog.string.resTwo='二级';
cosog.string.resThree='三级';
cosog.string.resFour='四级';
cosog.string.resFive='五级';
cosog.string.addRes="创建区块信息";
cosog.string.editRes="修改区块信息";
//模块维护
cosog.string.superModule='父级模块';
cosog.string.checkModule='--请选择模块--';
cosog.string.moduleType='模块类别';
cosog.string.moduleName="模块名称";
cosog.string.queryModule='查询模块信息...';
cosog.string.moduleMemo='模块简介';
cosog.string.moduleCode='模块编码';
cosog.string.moduleView='模块视图';
cosog.string.moduleControlller='模块控制器';
cosog.string.moduleIcon='模块图标';
cosog.string.moduleOrder='模块排序';
cosog.string.addmodule="创建模块信息";
cosog.string.editmodule="修改模块信息";
//字典维护
cosog.string.dataModuleName='字典模块名称';
cosog.string.required='该输入项为必输项';
cosog.string.dataModuleCode='字典模块代码';
cosog.string.dataSorts='显示顺序';
cosog.string.dataShowParams='添加参数';
cosog.string.dataValue='数据项值';
cosog.string.dataType='类型';
cosog.string.dataCheckType='请选择类型...';
cosog.string.dataColumnName='字段名称';
cosog.string.dataColumnCode='字段代码';
cosog.string.dataColumnParams='字段参数设置';
cosog.string.dataColumnEnabled='是否启用';
cosog.string.sorts='顺序';
cosog.string.command="操作";
cosog.string.nodata='无数据信息';
cosog.string.addDataValue='创建字典数据值';
cosog.string.editDataValue='修改字典数据值';
cosog.string.addDataInfo='创建字典信息';
cosog.string.editDataInfo='修改字典信息';
cosog.string.addDataColumnInfo='创建字典数据项值';
cosog.string.editDataColumnInfo='修改字典数据项值';
//用户维护
cosog.string.userTitle='职务';
cosog.string.userType='用户类型';
cosog.string.userName='用户名称';
cosog.string.userId='用户账号';
cosog.string.exist='已经存在';
cosog.string.userPwd='用户密码';
cosog.string.userPhone='用户电话';
cosog.string.userInEmail='内部邮箱';
cosog.string.userRegTime='注册时间';
cosog.string.queryUserName='查询用户信息...';
cosog.string.editPwd='修改密码';
cosog.string.enterOldPwd='请输入旧密码';
cosog.string.enterNewPwd='请输入新密码';
cosog.string.enterNewPwdAgain='请重新输入密码';
cosog.string.enterNewPwdAgain1='请再输入密码';
cosog.string.enterpwdNotEqual='两次密码不一致！';
cosog.string.wxtooltip='温馨提示';
cosog.string.requiredItem='信息以 * 号的文本框是必填项';
cosog.string.pwdPattern='密码格式：由数字与字母组合';
cosog.string.sessionINvalid='当前SESSION会话已失效';
cosog.string.addUser='创建用户信息';
cosog.string.editUser='修改用户信息';
//角色维护
cosog.string.roleType='角色类别';
cosog.string.roleName='角色名称';
cosog.string.roleCode='角色编码';
cosog.string.roleType='角色类别';
cosog.string.addRole='创建角色信息';
cosog.string.editRole='修改角色信息';
cosog.string.queryRole="查询角色信息...";
//权限分配
cosog.string.peopleList='人员列表';
cosog.string.userList='用户列表';
cosog.string.grantRole='安排角色';
cosog.string.sureDo='确认';
cosog.string.pleaseCheckRole='请选择角色';
cosog.string.grantRight='访问权限分配';
cosog.string.sureGive='确认';
cosog.string.pleaseCheckRole='请选择角色';
cosog.string.roleList='角色列表';
cosog.string.funcList='功能列表';
cosog.string.sucGrant='成功分配了';
cosog.string.jgRole='个角色';
cosog.string.jgModule='个模块';
cosog.string.grantFail='分配失败';
cosog.string.pleaseChooseRole='请先选择一个角色  ?';
cosog.string.chooseGrantModule='请您选择需要分配的模块';
//区块物性数据
cosog.string.crudeOilDensity='原油密度(g/cm^3)';
cosog.string.waterDensity='水密度(g/cm^3)';
cosog.string.naturalGasRelativeDensity='天然气相对密度';
cosog.string.ysrjqyb='原始溶解气油比(m^3/t)';
cosog.string.saturationPressure='饱和压力(MPa)';
cosog.string.reservoirDepth='油藏深度(m)';
cosog.string.reservoirTemperature='油藏温度(℃)';
cosog.string.editRespro='修改区块物性数据信息';
cosog.string.addRespro='创建区块物性数据信息';
//井名基本信息
cosog.string.wellName = '井名'; 
cosog.string.wellType='井类型';
cosog.string.checkWellType='--请选择井类型--';
cosog.string.runRange='日工作时间段';
cosog.string.runTime='日工作时间(h)';
cosog.string.addWell='创建井名基本信息';
cosog.string.editWell='修改井名基本信息';
//报警设置
cosog.string.alarmSet='工况报警设置';
cosog.string.waveAlarmSet='波动报警设置';
cosog.string.alarmType='报警类型';
cosog.string.alarmLevel='报警级别';
cosog.string.alarmSign='报警状态';
cosog.string.normal='正常';
cosog.string.alarm='报警';
cosog.string.remark='备注';
cosog.string.editAlarm='修改工况报警设置信息';
cosog.string.editWaveAlarm='修改波动报警设置信息';
cosog.string.alarmLevel1='一级报警';
cosog.string.alarmLevel1='二级报警';
cosog.string.alarmLevel1='三级报警';
cosog.string.offLine='离线';

//地图
cosog.string.longitude='经度';
cosog.string.latitude='纬度';
cosog.string.setCoord='设置坐标';
cosog.string.showLevel='显示级别';

//主界面信息
cosog.string.exportExcel='导出';
cosog.string.backAdmin='后台管理';
cosog.string.navPanel='功能导航';
cosog.string.orgNav='组织导航';
cosog.string.index='首页';
cosog.string.startDate='开始日期';
cosog.string.endDate='结束日期';
//功图类型
cosog.string.FSDiagram='光杆功图';
cosog.string.pumpFSDiagram='泵功图';
cosog.string.IDiagram='电流曲线';
//图形查询
cosog.string.rodStress='杆柱应力';
cosog.string.pumpEff='泵效组成';
cosog.string.imageInstantquery='实时查询';
cosog.string.history='历史查询';
cosog.string.imageInstantqueryAllday='全天查询';
cosog.string.position='位移(m)';
cosog.string.load='载荷(kN)';
cosog.string.fmax='最大载荷kN:';
cosog.string.fmin='最小载荷kN:';
cosog.string.stroke='冲程m:';
cosog.string.SPM='冲次/min:';
cosog.string.rodStressRatio='应力百分比(%)';
cosog.string.rod1='一级杆';
cosog.string.rod2='二级杆';
cosog.string.rod3='三级杆';
cosog.string.rod4='四级杆';
cosog.string.percent='百分数(%)';
cosog.string.pumpEff1='η冲程';
cosog.string.pumpEff2='η充满';
cosog.string.pumpEff3='η漏失';
cosog.string.pumpEff4='η收缩';
//平衡
cosog.string.netTorqueMaxValue='扭矩最大值法';
cosog.string.netTorqueMethod='扭矩法';

//平衡监测
cosog.string.balanceStatusTable='平衡状态表';
cosog.string.balanceStattable='平衡统计表';
cosog.string.balanceAnalysisData='计算数据';
cosog.string.torqueCurve='扭矩曲线';
cosog.string.pumpingUnotMotion='抽油机运动特性';
cosog.string.exportBalanceReport='导出单井报告';

//导出的文件名称
cosog.string.monitorExcel='实时监测';
cosog.string.historyExcel='历史查询';
cosog.string.alldayProExcel='全天产量';
cosog.string.realtimeProExcel='实时产量';
cosog.string.historyProExcel='历史产量';
cosog.string.dayReportExcel='日报';
cosog.string.monthReportExcel='月报';
cosog.string.measureExcel='实测数据';
cosog.string.curveAllProExcel='总产量数据';
cosog.string.curveWellringProExcel='井环产量数据';
cosog.string.curveWellProExcel='单井产量数据';
cosog.string.curveDividProExcel='分产系数数据';
cosog.string.balanceStatusExcel='实时评价平衡状态';
cosog.string.balanceReportPdf='实时评价单井报告';
cosog.string.balanceTotalReportPdf='全天评价单井报告';
cosog.string.balanceCycleReportPdf='周期评价单井报告';
cosog.string.bananceCycleExcel='周期评价平衡状态';
cosog.string.bananceAllDayExcel='全天评价平衡状态';
cosog.string.bananceRecordExcel='操作日志';