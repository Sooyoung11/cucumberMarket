package com.sohwakmo.cucumbermarket.repository;

import com.sohwakmo.cucumbermarket.domain.Member;
import com.sohwakmo.cucumbermarket.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

//     select * from PRODUCTS where lower(TITLE) like ? or lower (CONTENT) like ? order by PRODUCT_NO desc
//    List<Product> findByTitleIgnoreCaseContainingOrContentIgnoreCaseContainingOrMemberNicknameIgnoreCaseContainingOrderByProductNoDesc(String title, String content, String memberNickname);

    @Query(
            "select p from PRODUCTS p" +
                    " where p.status = :status" +
                    " and ( lower(p.title) like lower('%' || :title || '%')" +
                    " or lower(p.content) like lower('%' || :content || '%')" +
                    " or lower(p.member.nickname) like lower('%' || :author || '%') )" +
                    " order by p.productNo desc"
    )
    List<Product> searchByKeyword(@Param(value = "status") Boolean status, @Param(value = "title") String title,
            @Param(value = "content") String content, @Param(value = "author") String author);

    // select * from PRODUCTS where lower(deal_address) like '??%' order by PRODUCT_NO desc
    List<Product> findByStatusAndDealAddressIgnoreCaseContainingOrderByProductNoDesc(boolean status, String key);

    @Query(
            "select p from PRODUCTS p" +
                    " where p.status = :status" +
                    " and lower(p.dealAddress) like lower('%' || :type || '%')" +
                    " and ( lower(p.title) like lower('%' || :title || '%')" +
                    " or lower(p.content) like lower('%' || :content || '%')" +
                    " or lower(p.member.nickname) like lower('%' || :author || '%') )" +
                    " order by p.productNo desc"
    )
    List<Product> searchByTypeAndKeyword(@Param(value = "status") Boolean status, @Param(value = "type") String type,
                                         @Param(value = "title") String title, @Param(value = "content") String content, @Param(value = "author") String author);

    List<Product> findByMember(Member member);

    // select * from PRODUCTS where status = 0;
    List<Product> findByStatusOrderByProductNoDesc(Boolean status);

    Page<Product> findByStatusOrderByProductNoDesc(Boolean status, Pageable pageable);

    List<Product> findByMemberAndStatus(Member member, boolean status);

    List<Product> findByBoughtMemberNo(Integer boughtMemberNo);

    List<Product> findByOrderByLikeCountDescProductNoDesc();
}
