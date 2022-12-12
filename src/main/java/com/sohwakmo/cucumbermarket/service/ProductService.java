package com.sohwakmo.cucumbermarket.service;

import com.sohwakmo.cucumbermarket.domain.Member;
import com.sohwakmo.cucumbermarket.domain.Product;
import com.sohwakmo.cucumbermarket.domain.ProductOfInterested;
import com.sohwakmo.cucumbermarket.dto.ProductCreateDto;
import com.sohwakmo.cucumbermarket.dto.ProductOfInterestedRegisterOrDeleteOrCheckDto;
import com.sohwakmo.cucumbermarket.dto.ProductUpdateDto;
import com.sohwakmo.cucumbermarket.repository.MemberRepository;
import com.sohwakmo.cucumbermarket.repository.ProductOfInterestedRepository;
import com.sohwakmo.cucumbermarket.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final ProductOfInterestedRepository productOfInterestedRepository;

    public List<Product> read() { // 전체 상품 목록
        log.info("read()");

//        return productRepository.findAll();
        return productRepository.findByStatusOrderByProductNoDesc(false);
    }

    public Product read(Integer productNo) { // 상품 조회
        log.info("read(productNo = {})", productNo);

        return productRepository.findById(productNo).get();
    }


    @Transactional
    public Product detail(Integer productNo) {
        log.info("detail(productNo = {})", productNo);

        Product product = productRepository.findById(productNo).get();
        log.info("product = {}", product);
        product.updateClickCount(product.getClickCount()+1);
        log.info("product = {}", product);

//        Member member = memberRepository.findById(product.getMember().getMemberNo()).get();
//        log.info("member = {}", member);

        return product;
    }

    public List<Product> search(String keyword) {
        log.info("search(keyword = {})", keyword);

//        List<Product> list = productRepository.findByTitleIgnoreCaseContainingOrContentIgnoreCaseContainingOrMemberNicknameIgnoreCaseContainingOrderByProductNoDesc(keyword, keyword, keyword);
        List<Product> list = productRepository.searchByKeyword(false, keyword, keyword, keyword);
        log.info("list = {}", list);

        return list;
    }

    @Transactional
    public void addInterested(ProductOfInterestedRegisterOrDeleteOrCheckDto dto) {
        log.info("addInterested(dto = {}", dto);

//        Member member = memberRepository.findById(dto.getMemberNo()).get();
//        log.info("member = {}", member);
        Product product = productRepository.findById(dto.getProductNo()).get();
        log.info("product = {}", product);

//        ProductOfInterested entity = ProductOfInterested.builder()
//                        .member(member).product(product)
//                        .build();
//        log.info("entity = {}", entity);

        ProductOfInterested entity = ProductOfInterested.builder()
                .member(dto.getMemberNo()).product(product)
                .build();
        log.info("entity = {}", entity);

        productOfInterestedRepository.save(entity); // DB에 insert

        product.updateLikeCount(product.getLikeCount()+1); // 상품의 관심목록 1증가
        log.info("product = {}", product);
    }

    @Transactional
    public void deleteInterested(ProductOfInterestedRegisterOrDeleteOrCheckDto dto) {
        log.info("deleteInterested(dto = {})", dto);

//        Member member = memberRepository.findById(dto.getMemberNo()).get();
//        log.info("member = {}", member);
        Product product = productRepository.findById(dto.getProductNo()).get();
        log.info("product = {}", product);

        product.updateLikeCount(product.getLikeCount()-1);

        productOfInterestedRepository.deleteByMemberAndProduct(dto.getMemberNo(), product);
    }

    public String checkInterestedProduct(ProductOfInterestedRegisterOrDeleteOrCheckDto dto) {
        log.info("checkInterestedProduct(dto = {})", dto);

//        Member member = memberRepository.findById(dto.getMemberNo()).get();
//        log.info("member = {}", member);
        Product product = productRepository.findById(dto.getProductNo()).get();
        log.info("product = {}", product);

        Optional<ProductOfInterested> result = productOfInterestedRepository.findByMemberAndProduct(dto.getMemberNo(), product);
        if (result.isPresent()) {
            log.info("result = {}", result);
            return "ok";
        } else {
            log.info("없음");
            return "nok";
        }

    }

    @Transactional
    public List<Product> interestedRead(Integer memberNo) {
        log.info("interested(memberNo = {})", memberNo);

//        Member member = memberRepository.findById(memberNo).get();
//        log.info("member = {}", member);

        List<ProductOfInterested> list = productOfInterestedRepository.findByMember(memberNo);
        log.info("list = {}", list);

        List<Product> productsList = new ArrayList<>();
        for(ProductOfInterested s : list) {
            productsList.add(s.getProduct());
        }
        log.info("productsList = {}", productsList);

        return productsList;
    }

    @Transactional
    public List<Product> myProductListRead(Integer memberNo) {
        log.info("myProductListRead(memberNo = {})", memberNo);

        Member member = memberRepository.findById(memberNo).get();
        log.info("member = {}", member);

        List<Product> list = productRepository.findByMember(member);
        log.info("list = {}", list);

        return list;
    }

    @Transactional
    public void dealStatusIng(Integer productNo, Integer boughtMemberNo) {
        log.info("dealStatusIng(productNo = {}, boughtMemberNo = {})", productNo, boughtMemberNo);

        Product product = productRepository.findById(productNo).get();
        log.info("product = {}", product);

        Member boughtMember = memberRepository.findById(boughtMemberNo).get();
        log.info("boughtMember = {}", boughtMember);

        product.updateStatusAndBoughtMemberNo(false, null);
    }

    @Transactional
    public void dealStatusDone(Integer productNo, Integer boughtMemberNo) {
        log.info("dealStatusDone(productNo = {}, boughtMemberNo = {})", productNo, boughtMemberNo);

        Product product = productRepository.findById(productNo).get();
        log.info("product = {}", product);

//        Member boughtMember = memberRepository.findById(boughtMemberNo).get();
//        log.info("boughtMember = {}", boughtMember);

        product.updateStatusAndBoughtMemberNo(true, boughtMemberNo);
    }

    public Product isDealStatus(Integer productNo) {
        log.info("isDealStatus(productNo = {})", productNo);

        Product product = productRepository.findById(productNo).get();
        log.info("product = {}", product);

        return product;
    }


    @Transactional
    public Integer update(ProductUpdateDto dto) { // 상품 업데이트.
            log.info("update(dto={})", dto);
            Product entity = productRepository.findById(dto.getProductNo()).get();
            Product newProduct = entity.update(dto.getTitle(), dto.getContent(), dto.getPrice(), dto.getCategory());
            log.info("newProduct={}");
            return entity.getProductNo();
        }

    public Integer delete(Integer productNo) {
            log.info("deleteProduct(productNo={})", productNo);

            Product product = productRepository.findById(productNo).get();
            log.info("product = {}", product);

            ProductOfInterested interestedProduct = productOfInterestedRepository.findByProduct(product);
            log.info("interestedProduct = {}", interestedProduct);

            if (interestedProduct != null) { // 찜 목록에 데이터가 있는 경우
                log.info("not null");
                productOfInterestedRepository.deleteById(interestedProduct.getNo()); // 찜 목록에 상품 번호 전부 삭제
                productRepository.deleteById(productNo); // 상품 테이블 해당 번호로 삭제
            } else { // 찜 목록에 데이터가 없는 경우
                log.info("null");
                productRepository.deleteById(productNo); // 상품 테이블 해당 번호로 삭제
            }

            return productNo;
        }

     
    public Product create(ProductCreateDto dto, MultipartFile file) throws Exception {
        log.info("create(dto={})", dto);
        // dto -> entity
        String projectFilePath = "/images/product/" + file.getOriginalFilename();
        log.info("projectFilePath={}", projectFilePath);
        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\images\\product";  // 저장할 경로 지정
        log.info("productPath()" + projectPath);
        File saveFile = new File(projectPath, file.getOriginalFilename());
        file.transferTo(saveFile);
        dto.setPhotoUrl1(projectFilePath);
        dto.setPhotoName1(file.getOriginalFilename());
        Member member = memberRepository.findById(dto.getMemberNo()).get();
        Product product = Product.builder()
                .member(member)
                .title(dto.getTitle())
                .content(dto.getContent())
                .price(dto.getPrice())
                .category(dto.getCategory())
                .clickCount(dto.getClickCount())
                .likeCount(dto.getLikeCount())
                .photoUrl1(dto.getPhotoUrl1()).photoUrl2(dto.getPhotoUrl2()).photoUrl3(dto.getPhotoUrl3()).photoUrl4(dto.getPhotoUrl4())
                .photoUrl5(dto.getPhotoUrl5())
                .photoName1(dto.getPhotoName1()).photoName2(dto.getPhotoName2()).photoName3(dto.getPhotoName3()).photoName4(dto.getPhotoName4()).photoName5(dto.getPhotoName5())
                .dealAddress(dto.getDealAddress())
                .build();
        product = productRepository.save(product);
        return product;

    };

}



