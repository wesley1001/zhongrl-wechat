package cn.xn.wechat.activity.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.xn.wechat.activity.constant.PrizeInfoConstant;
import cn.xn.wechat.activity.mapper.PrizeInfoMapper;
import cn.xn.wechat.activity.mapper.TicketInfoMapper;
import cn.xn.wechat.activity.model.FansUserInfo;
import cn.xn.wechat.activity.model.PrizeInfo;
import cn.xn.wechat.activity.model.TicketInfo;
import cn.xn.wechat.activity.model.WechatActivity;
import cn.xn.wechat.activity.service.IFansUserInfoService;
import cn.xn.wechat.activity.service.IPrizeInfoService;
import cn.xn.wechat.activity.service.IWechatActivityService;
import cn.xn.wechat.web.util.DateStyle;
import cn.xn.wechat.web.util.DateUtil;
import cn.xn.wechat.web.util.GenerateNumberUtils;

import com.alibaba.fastjson.JSON;

@Service("prizeInfoService")
public class PrizeInfoServiceImpl implements IPrizeInfoService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Resource
	private IWechatActivityService wechatActivityService;
	@Resource
	private PrizeInfoMapper prizeInfoMapper;

	@Resource
	protected IFansUserInfoService fansUserInfoService;
	@Resource
	private TicketInfoMapper ticketInfoMapper;
	
	private String fileName="/data/apache-tomcat-wechat/ticket.txt";

	@Override
	public PrizeInfo getPrizeInfoById(String prizeId) {
		return prizeInfoMapper.getPrizeInfoById(prizeId);
	}

	public PrizeInfo getPrizeInfoDetail(String prizeId, String userId) {
		PrizeInfo prizeInfo = getPrizeInfoById(prizeId);
		FansUserInfo userInfo = fansUserInfoService.getFansUserInfo(userId,
				null, null);
		if (prizeInfo.getIsVirtual() == PrizeInfoConstant.PrizeTypeConstant.PRIZE_VIRTUAL_2) {// 虚拟物品
			if (prizeInfo.getStatus() == PrizeInfoConstant.PrizeStatusConstant.PRIZE_INFO_STATUS_2) {// 审核通过状态
				if (prizeInfo.getRemaNum() > 0
						&& userInfo.getMaterial() >= prizeInfo.getBuyMaterial()) {
					// 才可以点击制作
					prizeInfo.setCanEhange(2);
					logger.info("虚拟物品,prizeId={},userId={},可以制作", prizeId,
							userId);
				}
			}
		}
		if (prizeInfo.getIsVirtual() == PrizeInfoConstant.PrizeTypeConstant.PRIZE_VIRTUAL_1) {// 实体物品
			if (prizeInfo.getStatus() == PrizeInfoConstant.PrizeStatusConstant.PRIZE_INFO_STATUS_2) {// 审核通过状态
				if (prizeInfo.getRemaNum() > 0
						&& userInfo.getMaterial() >= prizeInfo.getBuyMaterial()) {
					WechatActivity wechatActivity = wechatActivityService
							.getAvailableActivityByType(PrizeInfoConstant.WXSS_ACTIVITY_TYPE);
					Date endDate = wechatActivity.getEndDate();// 活动结束日期
					if (DateUtil.DateToString(new Date(), DateStyle.YYYYMMDD)
							.equals(DateUtil.DateToString(endDate,
									DateStyle.YYYYMMDD))) {
						if (fansUserInfoService.isCheckUserNoOne(userId)) {// 查询前一名是否为当前用户
							prizeInfo.setCanEhange(2);// 才可以点击制作
							logger.info("实体物品,prizeId={},userId={},可以制作",
									prizeId, userId);
						}
					}
				}
			}
		}
		return prizeInfo;
	}

	@PostConstruct
	public void init() {
		logger.info("初始化电影票数据到数据库");
		int count = ticketInfoMapper.count();
		if (count > 0) {
			logger.info("已经初始过了，无需再次初始!");
			return;
		}
		File file = new File(fileName);
		List<TicketInfo> list = new ArrayList<TicketInfo>();
		if(file.exists()){
			try {
				InputStream inputStream =  new FileInputStream(file);
				List<String> lines = IOUtils.readLines(inputStream, "utf-8");
				IOUtils.closeQuietly(inputStream);
				for (int i = 0; i < lines.size(); i++) {
					if (StringUtils.isNotBlank(lines.get(i))) {
						TicketInfo info = new TicketInfo();
						info.setInitDate(new Date());
						info.setTicket(lines.get(i));
						info.setRemark("数据初始化");
						info.setStatus(PrizeInfoConstant.PrizeDeliveryConstant.STATUS_UNSEND);
						list.add(info);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			for (int i = 0; i < 1001; i++) {
				TicketInfo info = new TicketInfo();
				info.setInitDate(new Date());
				info.setTicket(GenerateNumberUtils.generateCode(true, 15));
				info.setStatus(PrizeInfoConstant.PrizeDeliveryConstant.STATUS_UNSEND);
				info.setRemark("数据初始化");
				list.add(info);
			}
		}
		ticketInfoMapper.addBatchTicket(list);
		logger.info("初始化成功!");
	}

	@Override
	public Map<String, Object> findPrizeInfoList(String userId, int material,
			int rank) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("userRank", rank);
		retMap.put("material", material);
		WechatActivity wechatActivity =  wechatActivityService.getAvailableActivityByType(PrizeInfoConstant.WXSS_ACTIVITY_TYPE);
		if(wechatActivity==null){
			return null;
		}
		// 查询参与活动的奖品Id集合
		List<String> productIds = new ArrayList<String>();
		if (wechatActivity != null) {
			String[] productArray = wechatActivity.getProductIds().split(",");
			for (int i = 0; i < productArray.length; i++) {
				if (StringUtils.isNotBlank(productArray[i])) {
					productIds.add(productArray[i]);
				}
			}
		}
		try {
			logger.info("参与扫码活动礼品Ids:" + JSON.toJSONString(productIds));
			Map<String, Object> map = new java.util.HashMap<String, Object>();
			map.put("productIds", productIds);
			map.put("multipleStates", new int[] {
					PrizeInfoConstant.PrizeStatusConstant.PRIZE_INFO_STATUS_2,
					PrizeInfoConstant.PrizeStatusConstant.PRIZE_INFO_STATUS_3 });
			List<PrizeInfo> productList = null;
			if(productIds.size()>0){
				productList = prizeInfoMapper.findPrizeInfoList(map);
			}
			if (productList != null && productList.size() > 0) {
				for (PrizeInfo prizeInfo : productList) {
					prizeInfo.setUserMaterial(material);
					if (prizeInfo.getIsVirtual() == PrizeInfoConstant.PrizeTypeConstant.PRIZE_VIRTUAL_2) {// 虚拟物品
						if (prizeInfo.getStatus() == PrizeInfoConstant.PrizeStatusConstant.PRIZE_INFO_STATUS_2) {// 审核通过状态
							if (prizeInfo.getRemaNum() > 0
									&& material >= prizeInfo.getBuyMaterial()) {
								// 才可以点击制作
								prizeInfo.setCanEhange(2);
							}
						}
					}
					if (prizeInfo.getIsVirtual() == PrizeInfoConstant.PrizeTypeConstant.PRIZE_VIRTUAL_1) {// 实体物品
						if (prizeInfo.getStatus() == PrizeInfoConstant.PrizeStatusConstant.PRIZE_INFO_STATUS_2) {// 审核通过状态
							if (prizeInfo.getRemaNum() > 0
									&& material >= prizeInfo.getBuyMaterial()) {
								Date endDate = wechatActivity.getEndDate();// 活动结束日期
								if (DateUtil.DateToString(new Date(),
										DateStyle.YYYYMMDD).equals(
										DateUtil.DateToString(endDate,
												DateStyle.YYYYMMDD))) {
									if (fansUserInfoService.isCheckUserNoOne(userId)) {// 查询前一名是否为当前用户 
										prizeInfo.setCanEhange(2);// 才可以点击制作
									}
								}
							}
						}
					}

				}
			}
			retMap.put("prizeList", productList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retMap;
	}

	@Override
	public List<PrizeInfo> findPrizeInfoList() {
		Map<String, Object> map = new java.util.HashMap<String, Object>();
		map.put("multipleStates", new int[] {
				PrizeInfoConstant.PrizeStatusConstant.PRIZE_INFO_STATUS_2,
				PrizeInfoConstant.PrizeStatusConstant.PRIZE_INFO_STATUS_3 });
		map.put("isVirtual",
				PrizeInfoConstant.PrizeTypeConstant.PRIZE_VIRTUAL_2);
		return prizeInfoMapper.findPrizeInfoList(map);
	}

}
