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

    @Query(
            "select p from PRODUCTS p" +
                    " where p.status = :status" +
                    " and ( lower(p.title) like lower('%' || :title || '%')" +
                    " or lower(p.content) like lower('%' || :content || '%')" +
                    " or lower(p.member.nickname) like lower('%' || :author || '%') )" +
                    " order by p.productNo desc"
    )
    Page<Product> searchByKeyword(@Param(value = "status") Boolean status, @Param(value = "title") String title,
            @Param(value = "content") String content, @Param(value = "author") String author, Pageable pageable);

    // select * from PRODUCTS where lower(deal_address) like '??%' order by PRODUCT_NO desc
    Page<Product> findByStatusAndDealAddressIgnoreCaseContainingOrderByProductNoDesc(boolean status, String key, Pageable pageable);

    @Query(
            "select p from PRODUCTS p" +
                    " where p.status = :status" +
                    " and lower(p.dealAddress) like lower('%' || :type || '%')" +
                    " and ( lower(p.title) like lower('%' || :title || '%')" +
                    " or lower(p.content) like lower('%' || :content || '%')" +
                    " or lower(p.member.nickname) like lower('%' || :author || '%') )" +
                    " order by p.productNo desc"
    )
    Page<Product> searchByTypeAndKeyword(@Param(value = "status") Boolean status, @Param(value = "type") String type,
                                         @Param(value = "title") String title, @Param(value = "content") String content, @Param(value = "author") String author, Pageable pageable);

    List<Product> findByMember(Member member);

    // select * from PRODUCTS where status = 0;
//    List<Product> findByStatusOrderByProductNoDesc(Boolean status);

    Page<Product> findByStatusOrderByProductNoDesc(Boolean status, Pageable pageable);

    //마이페이지 count
    List<Product> findByMemberAndStatus(Member member, boolean status);
    //마이페이지 count
    List<Product> findByBoughtMemberNo(Integer boughtMemberNo);
    //마이페이지 count
    List<Product> findByOrderByLikeCountDescProductNoDesc();

    //마이페이지 > 전체보기
    Page<Product> findByMemberOrderByProductNoDesc(Member member, Pageable pageable);

    //마이페이지 > 진행중, 거래완료
    Page<Product> findByMemberAndStatusOrderByProductNoDesc(Member member, boolean b, Pageable pageable);

    //마이페이지 > 구매내역
    Page<Product> findByBoughtMemberNoOrderByProductNoDesc(Integer memberNo, Pageable pageable);
}
