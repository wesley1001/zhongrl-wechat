package cn.xn.wechat.activity.constant;

/**
 * 奖品活动信息常量
 */
public class PrizeInfoConstant {
	
	//活动类型
	public static final String WXSS_ACTIVITY_TYPE="10";

	public static class PrizeStatusConstant {
		/**
		 * 待审核
		 */
		public static final int PRIZE_INFO_STATUS_1 = 1;
		/**
		 * 审核通过
		 */
		public static final int PRIZE_INFO_STATUS_2 = 2;
		/**
		 * 已售完
		 */
		public static final int PRIZE_INFO_STATUS_3 = 3;
		/**
		 * 已删除
		 */
		public static final int PRIZE_INFO_STATUS_4 = 4;
	}

	public static class PrizeRespConstant {
		public static final int ERROR = -1;
		public static final int SUCCESS = 1;
	}

	public static class PrizeSmsModuleConstant {
		public static final String MOVIE_MODULEID = "SCANINVIT1";
		public static final String WATCH_MODULEID = "SCANINVIT2";
	}

	public static class PrizeTypeConstant {
		/**
		 * 实体物品(iwatch)
		 */
		public static final int PRIZE_VIRTUAL_1 = 1;
		/**
		 * 虚物物品(movie)
		 */
		public static final int PRIZE_VIRTUAL_2 = 2;
		/**
		 * 虚物物品(流量)
		 */
		public static final int PRIZE_VIRTUAL_3 = 3;
	}
	

	public static class PrizeOrderConstant {
		/**
		 * 申请兑换
		 */
		public static final String PRIZE_ORDER_APPLY = "APPLY";
		/**
		 * 兑换成功
		 */
		public static final String PRIZE_ORDER_SUCCESS = "SUCCESS";
		/**
		 * 兑换失败
		 */
		public static final String PRIZE_ORDER_FAIL = "FAIL";
	}

	public static class PrizeMaterialConstant {
		/**
		 * 邀请好友原料新增频步
		 */
		public static final int FREQUENCY = 1;
	}

	/**
	 * 奖品派送常量
	 */
	public static class PrizeDeliveryConstant {
		/**
		 * 状态 UNSEND 未派送
		 */
		public static final String STATUS_UNSEND = "UNSEND";
		/**
		 * 状态 SEND 已派送
		 */
		public static final String STATUS_SEND = "SEND";
	}
}
