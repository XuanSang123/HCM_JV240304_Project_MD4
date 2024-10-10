package ra.md4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ra.md4.dto.request.FormAddressOrder;
import ra.md4.dto.response.UserInfo;
import ra.md4.models.CartItem;
import ra.md4.models.Order;
import ra.md4.models.User;
import ra.md4.service.cartitem.ICartItemService;
import ra.md4.service.order.IOrderService;
import ra.md4.service.user.IUserService;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private IOrderService iOrderService;
    @Autowired
    private IUserService iUserService;
    @Autowired
    private ICartItemService iCartItemService;

    // Phương thức này để hiển thị trang nhập địa chỉ giao hàng
    @GetMapping("/shipping/checkout")
    public String showCheckoutForm(Model model, HttpSession session) {
        model.addAttribute("formAddressOrder", new FormAddressOrder());
        return "layout/cart/userinformation"; // Tên của trang checkout
    }

//    @PostMapping("/checkout")
//    public String createOrder(@ModelAttribute FormAddressOrder formAddressOrder, HttpSession session) {
//        UserInfo userInfo = (UserInfo) session.getAttribute("userLogin");
//        User user = iUserService.findById(userInfo.getId());
//        // Tạo một đơn hàng mới
//        Order order = new Order();
//        order.setReceiveName(formAddressOrder.getReceiveName());
//        order.setReceiveAddress(formAddressOrder.getReceiveAddress());
//        order.setReceivePhone(formAddressOrder.getReceivePhone())
//        order.setSerialNumber(UUID.randomUUID().toString());
//        order.setNode(formAddressOrder.getNode());
//        order.setReceivedAt(new Date());
//        order.setUser(user);
//        // Tính toán tổng tiền từ giỏ hàng
//        List<CartItem> cartItems = iCartItemService.getCartItems(user.getId());
//        BigDecimal totalPrice = iOrderService.calculateTotalPrice(cartItems);
//        order.setTotalPrice(totalPrice);
//        // Lưu đơn hàng vào cơ sở dữ liệu thông qua service
//        iOrderService.save(order);
//        // Xóa giỏ hàng sau khi đặt hàng thành công
//        iCartItemService.clearCart(user.getId());
//        return "redirect:/cart/success";
//    }
@PostMapping("/shipping/checkout")
public String createOrder(@ModelAttribute FormAddressOrder formAddressOrder, HttpSession session) {
    UserInfo userInfo = (UserInfo) session.getAttribute("userLogin");

    // Kiểm tra người dùng có đăng nhập không
    if (userInfo == null) {
        return "redirect:/login"; // Nếu chưa đăng nhập, chuyển hướng đến trang đăng nhập
    }

    User user = iUserService.findById(userInfo.getId());

    // Kiểm tra xem người dùng có tồn tại không
    if (user == null) {
        return "redirect:/error"; // Nếu không tìm thấy người dùng, chuyển hướng đến trang lỗi
    }

    // Tạo đơn hàng mới
    Order order = new Order();
    order.setReceiveName(formAddressOrder.getReceiveName());
    order.setReceiveAddress(formAddressOrder.getReceiveAddress());
    order.setReceivePhone(formAddressOrder.getReceivePhone());
    order.setSerialNumber(UUID.randomUUID().toString());
    order.setNode(formAddressOrder.getNode());
    order.setReceivedAt(new Date());
    order.setUser(user);

    // Lấy các mục trong giỏ hàng
    List<CartItem> cartItems = iCartItemService.getCartItems(user.getId());

    // Kiểm tra xem giỏ hàng có rỗng không
    if (cartItems.isEmpty()) {
        return "redirect:/cart"; // Nếu giỏ hàng rỗng, chuyển hướng đến trang giỏ hàng
    }

    // Lưu đơn hàng và các mục của nó
    iOrderService.save(order, cartItems);

    // Xóa giỏ hàng của người dùng
    iCartItemService.clearCart(user.getId());

    return "redirect:/cart/success"; // Chuyển hướng đến trang thành công
}








    @GetMapping("/history")
    public String getOrderHistory(Model model, HttpSession session) {
        UserInfo userInfo = (UserInfo) session.getAttribute("userLogin");
        if (userInfo == null) {
            return "redirect:/login"; // Nếu người dùng chưa đăng nhập, chuyển hướng đến trang đăng nhập
        }

        // Lấy danh sách đơn hàng của người dùng
        List<Order> orders = iOrderService.getOrdersByUserId(userInfo.getId());

        // Kiểm tra và thêm vào model
        model.addAttribute("orders", orders);

        return "layout/cart/orderHistory"; // Trả về tên của trang lịch sử giao hàng
    }

}
