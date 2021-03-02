package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity // JPA 테이블 선언
@Table(name = "orders") // 테이블명 직접 지정 (미지정시 클래스명)
@Getter
@Setter
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

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // 주문상태 [ORDER, CANCEL]

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

}
