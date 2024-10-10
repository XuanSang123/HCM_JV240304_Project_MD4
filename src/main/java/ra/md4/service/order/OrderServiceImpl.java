package ra.md4.service.order;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.md4.dao.order.IOrderDao;
import ra.md4.models.CartItem;
import ra.md4.models.Order;
import ra.md4.models.OrderItem;
import ra.md4.models.User;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderServiceImpl implements IOrderService {
    @Autowired
    private IOrderDao iOrderDao;

    @Transactional
    @Override
    public void save(Order order, List<CartItem> cartItems) {
        iOrderDao.save(order, cartItems);
    }


    @Override
    public List<Order> findByUser(User user) {
        return iOrderDao.findByUser(user);
    }

    @Override
    public BigDecimal calculateTotalPrice(List<CartItem> cartItems) {
        return iOrderDao.calculateTotalPrice(cartItems);
    }

    @Override
    public List<Order> getOrdersByUserId(Integer userId) {
        return iOrderDao.getOrdersByUserId(userId);
    }
}
