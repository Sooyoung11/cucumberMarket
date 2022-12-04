package com.sohwakmo.cucumbermarket.service;

import com.sohwakmo.cucumbermarket.domain.Member;
import com.sohwakmo.cucumbermarket.domain.Product;
import com.sohwakmo.cucumbermarket.dto.ProductCreateDto;
import com.sohwakmo.cucumbermarket.dto.ProductUpdateDto;
import com.sohwakmo.cucumbermarket.repository.MemberRepository;
import com.sohwakmo.cucumbermarket.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;


    public List<Product> read() { // 전체 상품 목록
        log.info("read()");

        return productRepository.findAll();
    }

    public Product read(Integer productNo) { // 상품 조회
        log.info("read(productNo = {})", productNo);

        return productRepository.findById(productNo).get();
    }

    @Transactional
    public Product update(Integer productNo) { // 상품 클릭 수 update
        log.info("update(productNo = {})", productNo);

        Product entity = productRepository.findById(productNo).get();
        log.info("entity = {}", entity);
        entity.update(entity.getClickCount()+1);
        log.info("entity = {}", entity);

        Member member = memberRepository.findById(entity.getMember().getMemberNo()).get();
        log.info("member = {}", member);

        return entity;
    }

    public List<Product> search(String keyword) {
        log.info("search(keyword = {})", keyword);

        List<Product> list = productRepository.findByTitleIgnoreCaseContainingOrContentIgnoreCaseContainingOrderByProductNoDesc(keyword, keyword);
        log.info("list = {}", list);

        return list;
    }


   // public Product create(ProductCreateDto dto, MultipartFile file) throws Exception { // 상품 등록
      //  log.info("create(dto={})", dto);

      //  Product product = dto.toEntity();

     //   String productPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\images\\product";

     //   UUID uuid = UUID.randomUUID();
     //   String fileName = uuid + "_" + file.getOriginalFilename();
      //  File saveFile = new File(productPath, fileName);
      //  file.transferTo(saveFile);

     //   product.setPhotoName1(dto.getPhotoName1());
      //  product.setPhotoUrl1("/product/" + fileName);


      //  return productRepository.save(product);
  //  }

    public Product delete(Integer productNo){
        Product product = productRepository.findById(productNo).orElse(null);

        if(product==null){
            return null;
        }

        productRepository.delete(product);
        return product;
    }

    @Transactional
    public Integer update(ProductUpdateDto dto) { // 상품 업데이트
        log.info("update(dto={})", dto);

        Product entity = productRepository.findById(dto.getProductNo()).get();
        entity.update(dto.getTitle(), dto.getContent(), dto.getPrice(), dto.getCategory());

        return entity.getProductNo();
    }

    public Product create(ProductCreateDto dto) {
        log.info("create(dto={})", dto);

        Product entity = productRepository.save(dto.toEntity());

        return entity;
    }

    // public Integer delete(Integer productNo) {
    //    log.info("delete(id={})", productNo);

     //   productRepository.deleteById(productNo);

    //    return productNo;
   // }
}
