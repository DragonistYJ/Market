package love.dragoinst.market.service;

import love.dragoinst.market.Mapper.OrderMapper;
import love.dragoinst.market.bean.BriefOrder;
import love.dragoinst.market.bean.Collection;
import love.dragoinst.market.bean.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderMapper orderMapper;

    public int updateStateById(int id, String state) {
        return orderMapper.updateStateById(id, state);
    }

    public BriefOrder getBriefOrderById(int id) {
        return orderMapper.getBriefOrderById(id);
    }

    public List<Order> getAllOrders() {
        return orderMapper.getAllOrders();
    }

    public List<Order> getOrdersByBuyer(String buyer) {
        return orderMapper.getOrdersByBuyer(buyer.toUpperCase());
    }

    public List<Order> getOrdersByProduct(String product) {
        return orderMapper.getOrdersByProduct(product.toUpperCase());
    }

    public List<Integer> getFailOrderNumbersByDate(String toDate, int length) {
        List<Integer> numbers = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, Integer.parseInt(toDate.split("-")[0]));
        calendar.set(Calendar.MONTH, Integer.parseInt(toDate.split("-")[1]) - 1);
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(toDate.split("-")[2]));
        calendar.add(Calendar.DAY_OF_MONTH, 1);

        for (int i = 0; i < length; i++) {
            String fromDate = calendar.get(Calendar.YEAR) + "-" + String.format("%02d", calendar.get(Calendar.MONTH) + 1) + "-" + String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH));
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            String endDate = calendar.get(Calendar.YEAR) + "-" + String.format("%02d", calendar.get(Calendar.MONTH) + 1) + "-" + String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH));
            numbers.add(orderMapper.countFailOrderBetweenDate(endDate, fromDate));
        }
        Collections.reverse(numbers);
        return numbers;
    }

    public List<Integer> getSuccessOrderNumbersByDate(String toDate, int length) {
        List<Integer> numbers = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, Integer.parseInt(toDate.split("-")[0]));
        calendar.set(Calendar.MONTH, Integer.parseInt(toDate.split("-")[1]) - 1);
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(toDate.split("-")[2]));
        calendar.add(Calendar.DAY_OF_MONTH, 1);

        for (int i = 0; i < length; i++) {
            String fromDate = calendar.get(Calendar.YEAR) + "-" + String.format("%02d", calendar.get(Calendar.MONTH) + 1) + "-" + String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH));
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            String endDate = calendar.get(Calendar.YEAR) + "-" + String.format("%02d", calendar.get(Calendar.MONTH) + 1) + "-" + String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH));
            numbers.add(orderMapper.countSuccessOrderBetweenDate(endDate, fromDate));
        }
        Collections.reverse(numbers);
        return numbers;
    }

    public int countFailOrderBetweenDate(String fromDate, String toDate) {
        return orderMapper.countFailOrderBetweenDate(fromDate, toDate);
    }

    public int countFailOrderFromDate(String date) {
        return orderMapper.countFailOrderFromDate(date);
    }

    public int countFailOrderToDate(String date) {
        return orderMapper.countFailOrderToDate(date);
    }

    public int countFailOrder() {
        return orderMapper.countFailOrder();
    }

    public int countSuccessOrderBetweenDate(String fromDate, String toDate) {
        return orderMapper.countSuccessOrderBetweenDate(fromDate, toDate);
    }

    public int countSuccessOrderFromDate(String date) {
        return orderMapper.countSuccessOrderFromDate(date);
    }

    public int countSuccessOrderToDate(String date) {
        return orderMapper.countSuccessOrderToDate(date);
    }

    public int countSuccessOrder() {
        return orderMapper.countSuccessOrder();
    }

    public int countTransitingOrder() {
        return orderMapper.countTransitingOrder();
    }
}
