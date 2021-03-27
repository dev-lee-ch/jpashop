package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item) {
        if (item.getId() == null) { // id가 없을 경우 신규 등록
            em.persist(item);
        } else {    // id가 있을 경우 update 처리?
            em.merge(item); // object 의 모든 정보가 업데이트됨,, 실무에서는 merge 를 쓰지 않음. 더티체킹으로 변경할 내역만 지정하여 사용해야함!!
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
