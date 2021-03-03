package jpabook.jpashop.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity // JPA 테이블 선언
@Table(name = "orders") // 테이블명 직접 지정 (미지정시 클래스명)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // protected 생성자
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)  // FK 다대일 (주문하나는 하나의 멤버를 갖는다) , 연관관계의 주인! // xToOne 사용시 항상 LAZY 로 설정해 놔야함!!!!!
    @JoinColumn(name = "member_id") // join 시 FK 컬럼명
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)   // Order 를 persist 시 orderItems 을 자동으로 먼저 persist 후 order 를 persist 함.
    private List<OrderItem> orderItems = new ArrayList<OrderItem>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL) // CascadeType.ALL => persist 시 자동으로 날려줌. // persist 할 lifecycle 이 같을 때만 사용하는것을 권장.
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING) // ENUM 사용시 문자열로 저장
    private OrderStatus status; // 주문상태 [ORDER, CANCEL]

//    protected Order() {
//        // use createOrder
//    }

    //== 양방향 연관관계 편의 메서드==//
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    /* 생성 메서드 */
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        Order order = new Order();  // 주문 생성
        order.setMember(member);        // 주문 회원 설정
        order.setDelivery(delivery);    // 주문 배송지 설정
        for (OrderItem item : orderItems) { // 주문 내역 설정
            order.getOrderItems().add(item);
        }
        order.setStatus(OrderStatus.ORDER); // 주문 상태 설정
        order.setOrderDate(LocalDateTime.now());    // 주문 시간 설정

        return order;
    }

    /* 비지니스 로직 */
    public void cancel() {
        if (delivery.getStatus() == DeliveryStatus.COMP) {  // 배송완료 시 에러 발생
            throw new IllegalStateException("이미 배송이 완료된 상품은 취소가 불가능 합니다.");
        }

        this.setStatus(OrderStatus.CANCEL);
        /*
        for (OrderItem item : this.orderItems) {
            item.cancel();
        }
        */

        // forEach 사용 (java 8)
        this.orderItems.forEach(OrderItem::cancel);
    }

    /* 조회 로직 */

    /**
     * 전체 주문 가격 조회
     * @return
     */
    public int getTotalPrice() {
        /*
        int totalPrice = 0;
        for (OrderItem item : this.orderItems) {
            totalPrice += item.getTotalPrice();
        }
        return totalPrice;
        */

        // stream 사용 (java 8)
        return this.orderItems.stream()
                .mapToInt(OrderItem::getTotalPrice)
                .sum();
    }


}
