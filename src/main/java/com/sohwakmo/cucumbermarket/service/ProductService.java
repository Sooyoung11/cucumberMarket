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

        return productRepository.findAll();
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

        List<Product> list = productRepository.findByTitleIgnoreCaseContainingOrContentIgnoreCaseContainingOrderByProductNoDesc(keyword, keyword);
        log.info("list = {}", list);

        return list;
    }

    @Transactional
    public void addInterested(ProductOfInterestedRegisterOrDeleteOrCheckDto dto) {
        log.info("addInterested(dto = {}", dto);

        Member member = memberRepository.findById(dto.getMemberNo()).get();
        log.info("member = {}", member);
        Product product = productRepository.findById(dto.getProductNo()).get();
        log.info("product = {}", product);

        ProductOfInterested entity = ProductOfInterested.builder()
                        .member(member).product(product)
                        .build();
        log.info("entity = {}", entity);

        productOfInterestedRepository.save(entity); // DB에 insert

        product.updateLikeCount(product.getLikeCount()+1); // 상품의 관심목록 1증가
        log.info("product = {}", product);
    }

    @Transactional
    public void deleteInterested(ProductOfInterestedRegisterOrDeleteOrCheckDto dto) {
        log.info("deleteInterested(dto = {})", dto);

        Member member = memberRepository.findById(dto.getMemberNo()).get();
        log.info("member = {}", member);
        Product product = productRepository.findById(dto.getProductNo()).get();
        log.info("product = {}", product);

        product.updateLikeCount(product.getLikeCount()-1);

        productOfInterestedRepository.deleteByMemberAndProduct(member, product);
    }

    public String checkInterestedProduct(ProductOfInterestedRegisterOrDeleteOrCheckDto dto) {
        log.info("checkInterestedProduct(dto = {})", dto);

        Member member = memberRepository.findById(dto.getMemberNo()).get();
        log.info("member = {}", member);
        Product product = productRepository.findById(dto.getProductNo()).get();
        log.info("product = {}", product);

        Optional<ProductOfInterested> result = productOfInterestedRepository.findByMemberAndProduct(member, product);
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

        Member member = memberRepository.findById(memberNo).get();
        log.info("member = {}", member);

        List<ProductOfInterested> list = productOfInterestedRepository.findByMember(member);
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

        Member boughtMember = memberRepository.findById(boughtMemberNo).get();
        log.info("boughtMember = {}", boughtMember);

        product.updateStatusAndBoughtMemberNo(true, boughtMember);
    }

    public Product isDealStatus(Integer productNo) {
        log.info("isDealStatus(productNo = {})", productNo);

        Product product = productRepository.findById(productNo).get();
        log.info("product = {}", product);

        return product;
    }

    public Product create(ProductCreateDto dto) { // 상품 등록
        log.info("create(dto={})", dto);

        Product entity = productRepository.save(dto.toEntity());

        return entity;
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

//            Product product = productRepository.findById(productNo).get();
//            log.info("product = {}", product);
//
//            productOfInterestedRepository.deleteById(product);

            productRepository.deleteById(productNo);

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
                .build();
        product = productRepository.save(product);
        return product;

    };

}



