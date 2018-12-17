/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/yogee/youge">JeeSite</a> All rights reserved.
 */
package com.yogee.youge.modules.check.entity;

import org.hibernate.validator.constraints.Length;
import com.yogee.youge.common.persistence.DataEntity;

/**
 * 员工信息Entity
 * @author sunye
 * @version 2018-12-13
 */
public class CheckUser extends DataEntity<CheckUser> {
	
	private static final long serialVersionUID = 1L;
	private String number;		// 工号
	private String name;		// 姓名
	private String xingbie;		// 性别(0：男 1：女）
	private String bumen;		// 部门
	private String erjiBumen;		// 二级部门
	private String gangwei;		// 岗位
	private String jishuLeibie;		// 技术类别
	private String cengjiLeibie;		// 层级类别
	private String yuangongType;		// 员工类型
	private String ruzhiDate;		// 入职时间
	private String hetongType;		// 合同类型
	private String hetongTime;		// 合同年限
	private String hetongdaoqiTime;   //合同到期时间
	private String hetongNumber;		// 第几次签订合同
	private String shiyongqiTime;		// 试用期期限/月
	private String shiyongqiDate;		// 试用期到期日期
	private String hetongLeixing;		// 合同类型(0:未转正 1：转正)
	private String zhuanzhengDate;		// 转正日期
	private String shenfenzheng;		// 身份证号
	private String birthday;		// 出生日期
	private String canjiagongzuoDate;		// 参加工作时间
	private String jiguan;		// 籍贯
	private String minzu;		// 民族
	private String zhengzhiMianmao;		// 政治面貌
	private String hunyinZhuangkuang;		// 婚姻状况
	private String hujiXingzhi;		// 户籍性质
	private String diyiXueli;		// 第一学历
	private String diyiZhuanye;		// 第一专业
	private String diyiYuanxiao;		// 第一专业毕业院校
	private String shifouTongzhao;		// 是否统招
	private String zuigaoXueli;		// 最高学历
	private String zhuanye;		// 专业
	private String biyeYuanxiao;		// 毕业院校
	private String telephone;		// 联系电话
	private String xianzhuzhi;		// 现住址
	private String jinjiLianxiren;		// 紧急联系人
	private String jinjiTelephone;		// 紧急联系人电话
	private String yuangongzuodanwei;		// 原来工作单位
	private String shifouLizhi;		// 是否离职
	private String lizhiLeixing;		// 离职类型(0：主动  1：被动）
	private String lizhiTime;         //离职时间


	/**
	 * others
	 */
	private String no;  //序号
	private String nowDate;//现在日期
	private String workAgeYear; //工作年限/年
	private String workAgeMonth; //工作年限
	private String companyAgeYear; //司龄/年
	private String companyAgeMonth; //司龄
	private String age;   //年龄


	
	public CheckUser() {
		super();
	}

	public CheckUser(String id){
		super(id);
	}

	@Length(min=0, max=64, message="工号长度必须介于 0 和 64 之间")
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	
	@Length(min=0, max=64, message="姓名长度必须介于 0 和 64 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=64, message="性别(0：男 1：女）长度必须介于 0 和 64 之间")
	public String getXingbie() {
		return xingbie;
	}

	public void setXingbie(String xingbie) {
		this.xingbie = xingbie;
	}
	
	@Length(min=0, max=64, message="部门长度必须介于 0 和 64 之间")
	public String getBumen() {
		return bumen;
	}

	public void setBumen(String bumen) {
		this.bumen = bumen;
	}
	
	@Length(min=0, max=64, message="二级部门长度必须介于 0 和 64 之间")
	public String getErjiBumen() {
		return erjiBumen;
	}

	public void setErjiBumen(String erjiBumen) {
		this.erjiBumen = erjiBumen;
	}
	
	@Length(min=0, max=64, message="岗位长度必须介于 0 和 64 之间")
	public String getGangwei() {
		return gangwei;
	}

	public void setGangwei(String gangwei) {
		this.gangwei = gangwei;
	}
	
	@Length(min=0, max=64, message="技术类别长度必须介于 0 和 64 之间")
	public String getJishuLeibie() {
		return jishuLeibie;
	}

	public void setJishuLeibie(String jishuLeibie) {
		this.jishuLeibie = jishuLeibie;
	}
	
	@Length(min=0, max=64, message="层级类别长度必须介于 0 和 64 之间")
	public String getCengjiLeibie() {
		return cengjiLeibie;
	}

	public void setCengjiLeibie(String cengjiLeibie) {
		this.cengjiLeibie = cengjiLeibie;
	}
	
	@Length(min=0, max=64, message="员工类型长度必须介于 0 和 64 之间")
	public String getYuangongType() {
		return yuangongType;
	}

	public void setYuangongType(String yuangongType) {
		this.yuangongType = yuangongType;
	}
	
	@Length(min=0, max=64, message="入职时间长度必须介于 0 和 64 之间")
	public String getRuzhiDate() {
		return ruzhiDate;
	}

	public void setRuzhiDate(String ruzhiDate) {
		this.ruzhiDate = ruzhiDate;
	}
	
	@Length(min=0, max=64, message="合同类型长度必须介于 0 和 64 之间")
	public String getHetongType() {
		return hetongType;
	}

	public void setHetongType(String hetongType) {
		this.hetongType = hetongType;
	}
	
	@Length(min=0, max=64, message="合同年限长度必须介于 0 和 64 之间")
	public String getHetongTime() {
		return hetongTime;
	}

	public void setHetongTime(String hetongTime) {
		this.hetongTime = hetongTime;
	}
	
	@Length(min=0, max=64, message="第几次签订合同长度必须介于 0 和 64 之间")
	public String getHetongNumber() {
		return hetongNumber;
	}

	public void setHetongNumber(String hetongNumber) {
		this.hetongNumber = hetongNumber;
	}
	
	@Length(min=0, max=64, message="试用期期限/月长度必须介于 0 和 64 之间")
	public String getShiyongqiTime() {
		return shiyongqiTime;
	}

	public void setShiyongqiTime(String shiyongqiTime) {
		this.shiyongqiTime = shiyongqiTime;
	}
	
	@Length(min=0, max=64, message="试用期到期日期长度必须介于 0 和 64 之间")
	public String getShiyongqiDate() {
		return shiyongqiDate;
	}

	public void setShiyongqiDate(String shiyongqiDate) {
		this.shiyongqiDate = shiyongqiDate;
	}
	
	@Length(min=0, max=64, message="合同类型(0:未转正 1：转正)长度必须介于 0 和 64 之间")
	public String getHetongLeixing() {
		return hetongLeixing;
	}

	public void setHetongLeixing(String hetongLeixing) {
		this.hetongLeixing = hetongLeixing;
	}
	
	@Length(min=0, max=64, message="转正日期长度必须介于 0 和 64 之间")
	public String getZhuanzhengDate() {
		return zhuanzhengDate;
	}

	public void setZhuanzhengDate(String zhuanzhengDate) {
		this.zhuanzhengDate = zhuanzhengDate;
	}
	
	@Length(min=0, max=64, message="身份证号长度必须介于 0 和 64 之间")
	public String getShenfenzheng() {
		return shenfenzheng;
	}

	public void setShenfenzheng(String shenfenzheng) {
		this.shenfenzheng = shenfenzheng;
	}
	
	@Length(min=0, max=64, message="出生日期长度必须介于 0 和 64 之间")
	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	
	@Length(min=1, max=64, message="参加工作时间长度必须介于 1 和 64 之间")
	public String getCanjiagongzuoDate() {
		return canjiagongzuoDate;
	}

	public void setCanjiagongzuoDate(String canjiagongzuoDate) {
		this.canjiagongzuoDate = canjiagongzuoDate;
	}
	
	@Length(min=0, max=64, message="籍贯长度必须介于 0 和 64 之间")
	public String getJiguan() {
		return jiguan;
	}

	public void setJiguan(String jiguan) {
		this.jiguan = jiguan;
	}
	
	@Length(min=0, max=64, message="民族长度必须介于 0 和 64 之间")
	public String getMinzu() {
		return minzu;
	}

	public void setMinzu(String minzu) {
		this.minzu = minzu;
	}
	
	@Length(min=0, max=64, message="政治面貌长度必须介于 0 和 64 之间")
	public String getZhengzhiMianmao() {
		return zhengzhiMianmao;
	}

	public void setZhengzhiMianmao(String zhengzhiMianmao) {
		this.zhengzhiMianmao = zhengzhiMianmao;
	}
	
	@Length(min=0, max=64, message="婚姻状况长度必须介于 0 和 64 之间")
	public String getHunyinZhuangkuang() {
		return hunyinZhuangkuang;
	}

	public void setHunyinZhuangkuang(String hunyinZhuangkuang) {
		this.hunyinZhuangkuang = hunyinZhuangkuang;
	}
	
	@Length(min=0, max=64, message="户籍性质长度必须介于 0 和 64 之间")
	public String getHujiXingzhi() {
		return hujiXingzhi;
	}

	public void setHujiXingzhi(String hujiXingzhi) {
		this.hujiXingzhi = hujiXingzhi;
	}
	
	@Length(min=0, max=64, message="第一学历长度必须介于 0 和 64 之间")
	public String getDiyiXueli() {
		return diyiXueli;
	}

	public void setDiyiXueli(String diyiXueli) {
		this.diyiXueli = diyiXueli;
	}
	
	@Length(min=0, max=64, message="第一专业长度必须介于 0 和 64 之间")
	public String getDiyiZhuanye() {
		return diyiZhuanye;
	}

	public void setDiyiZhuanye(String diyiZhuanye) {
		this.diyiZhuanye = diyiZhuanye;
	}
	
	@Length(min=0, max=64, message="第一专业毕业院校长度必须介于 0 和 64 之间")
	public String getDiyiYuanxiao() {
		return diyiYuanxiao;
	}

	public void setDiyiYuanxiao(String diyiYuanxiao) {
		this.diyiYuanxiao = diyiYuanxiao;
	}
	
	@Length(min=0, max=64, message="是否统招长度必须介于 0 和 64 之间")
	public String getShifouTongzhao() {
		return shifouTongzhao;
	}

	public void setShifouTongzhao(String shifouTongzhao) {
		this.shifouTongzhao = shifouTongzhao;
	}
	
	@Length(min=0, max=64, message="最高学历长度必须介于 0 和 64 之间")
	public String getZuigaoXueli() {
		return zuigaoXueli;
	}

	public void setZuigaoXueli(String zuigaoXueli) {
		this.zuigaoXueli = zuigaoXueli;
	}
	
	@Length(min=0, max=64, message="专业长度必须介于 0 和 64 之间")
	public String getZhuanye() {
		return zhuanye;
	}

	public void setZhuanye(String zhuanye) {
		this.zhuanye = zhuanye;
	}
	
	@Length(min=0, max=64, message="毕业院校长度必须介于 0 和 64 之间")
	public String getBiyeYuanxiao() {
		return biyeYuanxiao;
	}

	public void setBiyeYuanxiao(String biyeYuanxiao) {
		this.biyeYuanxiao = biyeYuanxiao;
	}
	
	@Length(min=0, max=64, message="联系电话长度必须介于 0 和 64 之间")
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	@Length(min=0, max=64, message="现住址长度必须介于 0 和 64 之间")
	public String getXianzhuzhi() {
		return xianzhuzhi;
	}

	public void setXianzhuzhi(String xianzhuzhi) {
		this.xianzhuzhi = xianzhuzhi;
	}
	
	@Length(min=0, max=64, message="紧急联系人长度必须介于 0 和 64 之间")
	public String getJinjiLianxiren() {
		return jinjiLianxiren;
	}

	public void setJinjiLianxiren(String jinjiLianxiren) {
		this.jinjiLianxiren = jinjiLianxiren;
	}
	
	@Length(min=0, max=64, message="紧急联系人电话长度必须介于 0 和 64 之间")
	public String getJinjiTelephone() {
		return jinjiTelephone;
	}

	public void setJinjiTelephone(String jinjiTelephone) {
		this.jinjiTelephone = jinjiTelephone;
	}
	
	@Length(min=0, max=64, message="原来工作单位长度必须介于 0 和 64 之间")
	public String getYuangongzuodanwei() {
		return yuangongzuodanwei;
	}

	public void setYuangongzuodanwei(String yuangongzuodanwei) {
		this.yuangongzuodanwei = yuangongzuodanwei;
	}
	
	@Length(min=0, max=64, message="是否离职长度必须介于 0 和 64 之间")
	public String getShifouLizhi() {
		return shifouLizhi;
	}

	public void setShifouLizhi(String shifouLizhi) {
		this.shifouLizhi = shifouLizhi;
	}
	
	@Length(min=0, max=64, message="离职类型(0：主动  1：被动）长度必须介于 0 和 64 之间")
	public String getLizhiLeixing() {
		return lizhiLeixing;
	}

	public void setLizhiLeixing(String lizhiLeixing) {
		this.lizhiLeixing = lizhiLeixing;
	}

	public String getLizhiTime() {
		return lizhiTime;
	}

	public void setLizhiTime(String lizhiTime) {
		this.lizhiTime = lizhiTime;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getNowDate() {
		return nowDate;
	}

	public void setNowDate(String nowDate) {
		this.nowDate = nowDate;
	}

	public String getWorkAgeYear() {
		return workAgeYear;
	}

	public void setWorkAgeYear(String workAgeYear) {
		this.workAgeYear = workAgeYear;
	}

	public String getWorkAgeMonth() {
		return workAgeMonth;
	}

	public void setWorkAgeMonth(String workAgeMonth) {
		this.workAgeMonth = workAgeMonth;
	}

	public String getCompanyAgeYear() {
		return companyAgeYear;
	}

	public void setCompanyAgeYear(String companyAgeYear) {
		this.companyAgeYear = companyAgeYear;
	}

	public String getCompanyAgeMonth() {
		return companyAgeMonth;
	}

	public void setCompanyAgeMonth(String companyAgeMonth) {
		this.companyAgeMonth = companyAgeMonth;
	}

	public String getHetongdaoqiTime() {
		return hetongdaoqiTime;
	}

	public void setHetongdaoqiTime(String hetongdaoqiTime) {
		this.hetongdaoqiTime = hetongdaoqiTime;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}
}