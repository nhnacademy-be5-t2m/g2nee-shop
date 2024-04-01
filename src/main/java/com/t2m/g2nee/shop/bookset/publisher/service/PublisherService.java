package com.t2m.g2nee.shop.bookset.publisher.service;

import com.t2m.g2nee.shop.bookset.publisher.domain.Publisher;
import com.t2m.g2nee.shop.bookset.publisher.dto.PublisherDto;
import com.t2m.g2nee.shop.bookset.publisher.mapper.PublisherMapper;
import com.t2m.g2nee.shop.bookset.publisher.repository.PublisherRepository;
import com.t2m.g2nee.shop.exception.NotFoundException;
import com.t2m.g2nee.shop.pageUtils.PageResponse;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 출판사 관리에 대한 Service 입니다.
 *
 * @author : 신동민
 * @since : 1.0
 */
@Service
@RequiredArgsConstructor
@Transactional
public class PublisherService {

    private final PublisherRepository publisherRepository;
    private final PublisherMapper mapper;

    /**
     * 출판사 정보를 저장하는 메서드
     *
     * @param publisher 저장할 정보가 담긴 엔티티
     * @return 출판사 객체 응답
     */
    public PublisherDto.Response registerPublisher(Publisher publisher) {

        Publisher savePublisher = publisherRepository.save(publisher);

        return mapper.entityToDto(savePublisher);
    }

    /**
     * 출판사를 수정하는 메서드
     *
     * @param publisher 수정할 정보가 담긴 엔티티
     * @return 출판사 객체 응답
     */
    public PublisherDto.Response updatePublisher(Publisher publisher) {

        Publisher findPublisher = findPublisherById(publisher.getPublisherId());

        Optional.ofNullable(publisher.getPublisherName()).ifPresent(findPublisher::setPublisherName);
        Optional.ofNullable(publisher.getPublisherEngName()).ifPresent(findPublisher::setPublisherEngName);

        Publisher savePublisher = publisherRepository.save(findPublisher);

        return mapper.entityToDto(savePublisher);

    }

    /**
     * 출판사 페이지를 구성하는 메서드
     *
     * @param page 현재 페이지
     * @return 페이징에 필요한 정보가 담긴 PageResponse 객체
     */
    public PageResponse<PublisherDto.Response> getPublisherList(int page) {

        int size = 10;
        Page<Publisher> publisherPage =
                publisherRepository.findAll(PageRequest.of(page - 1, size, Sort.by("publisherName")));

        List<PublisherDto.Response> responses = mapper.entitiesToDtos(publisherPage.getContent());

//        int blockLimit = 3;
//        int startPage = (((int) Math.ceil(((double) pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
//        int endPage = Math.min((startPage + blockLimit - 1), publisherPage.getTotalPages());

        return PageResponse.<PublisherDto.Response>builder()
                .data(responses)
                .currentPage(page)
                .totalPage(publisherPage.getTotalPages())
                .build();
    }

    /**
     * 출판사를 삭제하는 메서드
     *
     * @param publisherId 출판사 Id
     */
    public void deletePublisher(Long publisherId) {

        findPublisherById(publisherId);

        publisherRepository.deleteById(publisherId);
    }

    /**
     * id에 해당하는 출판사 객체를 확인하는 메서드
     *
     * @param publisherId 출판사 id
     * @return 출판사 id에 해당하는 publisher entity
     */
    public Publisher findPublisherById(Long publisherId) {

        Optional<Publisher> optionalPublisher = publisherRepository.findById(publisherId);
        if (optionalPublisher.isPresent()) {

            return optionalPublisher.get();
        } else {
            throw new NotFoundException("출판사 정보가 없습니다.");
        }
    }
}