package yufei.store.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import yufei.store.domain.Cart;
import yufei.store.domain.CartItem;
import yufei.store.domain.Order;
import yufei.store.domain.OrderItem;
import yufei.store.domain.Page;
import yufei.store.domain.User;
import yufei.store.service.OrderService;
import yufei.store.service.impl.OrderServiceImpl;
import yufei.store.utils.PaymentUtil;
import yufei.store.utils.UUIDUtils;
import yufei.store.web.base.BaseServlet;

public class OrderServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	public String addOrder(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User u = (User) request.getSession().getAttribute("user");
		Cart c = (Cart) request.getSession().getAttribute("cart");

		Order o = new Order();
		List<OrderItem> list = new ArrayList<OrderItem>();
		// 从购物项中封装好订单项
		for (CartItem ci : c.getItems()) {
			OrderItem oi = new OrderItem();
			oi.setItemid(UUIDUtils.getCode());
			oi.setQuantity(ci.getNum());
			oi.setTotal(ci.getSum());
			oi.setP(ci.getP());
			oi.setO(o);
			list.add(oi);
		}
		o.setOid(UUIDUtils.getCode());
		o.setOrdertime(new Date());
		o.setState(1);
		o.setTotal(c.getSum());
		o.setUser(u);
		o.setList(list);
		OrderService os = new OrderServiceImpl();
		try {
			os.addOrder(o);
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("order", o);
		return "/jsp/order_info.jsp";
	}

	public String showOrder(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int pageNum;
		if (request.getParameter("pageNum") == null) {
			pageNum = 1;
		} else {
			pageNum = Integer.parseInt(request.getParameter("pageNum"));
		}

		int everyPage = 3;

		User u = (User) request.getSession().getAttribute("user");

		OrderService os = new OrderServiceImpl();
		try {
			Page p = os.findByUid(u.getUid(), pageNum, everyPage);
			request.setAttribute("page", p);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "/jsp/order_list.jsp";

	}

	public String showOneOrder(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");
		OrderService os = new OrderServiceImpl();

		try {
			Order o = os.findOne(id);
			request.setAttribute("order", o);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "/jsp/order_info.jsp";
	}

	public String payOrder(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");
		String address = request.getParameter("address");
		String name = request.getParameter("name");
		String telephone = request.getParameter("telephone");
		String pd_FrpId = request.getParameter("pd_FrpId");
		OrderService os = new OrderServiceImpl();
		try {
			Order o = os.findOrderByOid(id);
			o.setAddress(address);
			o.setName(name);
			o.setTelephone(telephone);
			os.update(o);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 把付款所需要的参数准备好:
		String p0_Cmd = "Buy";
		// 商户编号
		String p1_MerId = "10001126856";
		// 订单编号
		String p2_Order = id;
		// 金额
		String p3_Amt = "0.01";
		String p4_Cur = "CNY";
		String p5_Pid = "";
		String p6_Pcat = "";
		String p7_Pdesc = "";
		// 接受响应参数的Servlet
		String p8_Url = "http://localhost:8080/GuguStore/OrderServlet?method=callBack";
		String p9_SAF = "";
		String pa_MP = "";
		String pr_NeedResponse = "1";
		// 公司的秘钥
		String keyValue = "69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl";

		// 调用易宝的加密算法,对所有数据进行加密,返回电子签名
		String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt, p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc,
				p8_Url, p9_SAF, pa_MP, pd_FrpId, pr_NeedResponse, keyValue);

		StringBuffer sb = new StringBuffer("https://www.yeepay.com/app-merchant-proxy/node?");
		sb.append("p0_Cmd=").append(p0_Cmd).append("&");
		sb.append("p1_MerId=").append(p1_MerId).append("&");
		sb.append("p2_Order=").append(p2_Order).append("&");
		sb.append("p3_Amt=").append(p3_Amt).append("&");
		sb.append("p4_Cur=").append(p4_Cur).append("&");
		sb.append("p5_Pid=").append(p5_Pid).append("&");
		sb.append("p6_Pcat=").append(p6_Pcat).append("&");
		sb.append("p7_Pdesc=").append(p7_Pdesc).append("&");
		sb.append("p8_Url=").append(p8_Url).append("&");
		sb.append("p9_SAF=").append(p9_SAF).append("&");
		sb.append("pa_MP=").append(pa_MP).append("&");
		sb.append("pd_FrpId=").append(pd_FrpId).append("&");
		sb.append("pr_NeedResponse=").append(pr_NeedResponse).append("&");
		sb.append("hmac=").append(hmac);

		System.out.println(sb.toString());
		//
		// 使用重定向：
		response.sendRedirect(sb.toString());

		return null;
	}

	public String callBack(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 验证请求来源和数据有效性
		// 阅读支付结果参数说明
		// System.out.println("==============================================");
		String p1_MerId = request.getParameter("p1_MerId");
		String r0_Cmd = request.getParameter("r0_Cmd");
		String r1_Code = request.getParameter("r1_Code");
		String r2_TrxId = request.getParameter("r2_TrxId");
		String r3_Amt = request.getParameter("r3_Amt");
		String r4_Cur = request.getParameter("r4_Cur");
		String r5_Pid = request.getParameter("r5_Pid");
		String r6_Order = request.getParameter("r6_Order");
		String r7_Uid = request.getParameter("r7_Uid");
		String r8_MP = request.getParameter("r8_MP");
		String r9_BType = request.getParameter("r9_BType");
		String rb_BankId = request.getParameter("rb_BankId");
		String ro_BankOrderId = request.getParameter("ro_BankOrderId");
		String rp_PayDate = request.getParameter("rp_PayDate");
		String rq_CardNo = request.getParameter("rq_CardNo");
		String ru_Trxtime = request.getParameter("ru_Trxtime");

		// hmac
		String hmac = request.getParameter("hmac");
		// 利用本地密钥和加密算法 加密数据
		String keyValue = "69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl";
		boolean isValid = PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd, r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid,
				r6_Order, r7_Uid, r8_MP, r9_BType, keyValue);
		if (isValid) {
			// 有效
			if (r9_BType.equals("1")) {
				OrderService os = new OrderServiceImpl();
				try {
					Order o = os.findOrderByOid(r6_Order);
					o.setState(2);
					os.update(o);
				} catch (Exception e) {
					e.printStackTrace();
				}
				// 浏览器重定向
				request.setAttribute("msg", "支付成功！订单号：" + r6_Order + "金额：" + r3_Amt);
			} else if (r9_BType.equals("2")) {
				// 修改订单状态:
				// 服务器点对点，来自于易宝的通知
				System.out.println("收到易宝通知，修改订单状态！");//
				// 回复给易宝success，如果不回复，易宝会一直通知
				response.getWriter().print("success");
			}

		} else {
			throw new RuntimeException("数据被篡改！");
		}

		return "/jsp/info.jsp";
	}
}
