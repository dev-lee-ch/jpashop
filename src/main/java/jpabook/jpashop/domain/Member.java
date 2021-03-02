package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity // JPA 테이블 선언
@Getter //
@Setter
public class Member {

    @Id // 테이블 ID
    @GeneratedValue // 자동증가
    @Column(name="member_id")   // 컬럼명 지정
    private Long id;

    private String name;

    @Embedded   // 임베디드된 타입이다 선언
    private Address address;

    @OneToMany(mappedBy = "member")  // 일대다 (하나의 멤버는 여러개의 주문을 갖는다) // mappedBy = "member" => order 테이블에 있는 member 필드에 의해서 매핑이 된것.
    private List<Order> orders = new ArrayList<Order>();
}
