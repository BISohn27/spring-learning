package hello.core.order;

import hello.core.member.Member;

public interface OrderService {

    public Order createOrder(Long memberId, String itemName, int itemPrice);
}
