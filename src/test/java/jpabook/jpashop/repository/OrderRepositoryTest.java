package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderRepositoryTest {

    @Autowired
    OrderRepository orderRepository;

    @Test
    public void 주문목록() throws Exception {
        //given
        OrderSearch orderSearch = new OrderSearch();
//        orderSearch.setOrderStatus();
//        orderSearch.setMemberName();

        //when
        orderRepository.findAll(orderSearch);

        //then
//        assertEquals(item, itemRepository.findOne(item.getId()));
    }

}