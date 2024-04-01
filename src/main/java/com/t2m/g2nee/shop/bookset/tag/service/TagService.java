package com.t2m.g2nee.shop.bookset.tag.service;

import com.t2m.g2nee.shop.bookset.tag.domain.Tag;
import com.t2m.g2nee.shop.bookset.tag.dto.TagDto;
import com.t2m.g2nee.shop.bookset.tag.mapper.TagMapper;
import com.t2m.g2nee.shop.bookset.tag.repository.TagRepository;
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
 * 태그 관리에 대한 Service 입니다.
 *
 * @author : 신동민
 * @since : 1.0
 */
@Service
@RequiredArgsConstructor
@Transactional
public class TagService {

    private final TagRepository tagRepository;
    private final TagMapper mapper;

    /**
     * 태그 정보를 저장하는 메서드
     *
     * @param tag 저장할 정보가 담긴 엔티티
     * @return 출판사 객체 응답
     */
    public TagDto.Response registerTag(Tag tag) {

        // 태그 이름이 존재하는지 확인 후 있으면 상태를 변경하고 없으면 저장합니다.
        Optional<Tag> optionalTag = tagRepository.findByTagName(tag.getTagName());

        if (optionalTag.isPresent()) {

            Tag findTag = optionalTag.get();
            findTag.setActivated(true);

            return mapper.entityToDto(findTag);
        } else {

            Tag saveTag = tagRepository.save(tag);
            return mapper.entityToDto(saveTag);
        }
    }

    /**
     * 태그를 수정하는 메서드
     *
     * @param tag 수정할 정보가 담긴 엔티티
     * @return 출판사 객체 응답
     */
    public TagDto.Response updateTag(Tag tag) {

        Tag findTag = findTagById(tag.getTagId());

        Optional.ofNullable(tag.getTagName()).ifPresent(findTag::setTagName);

        Tag saveTag = tagRepository.save(findTag);

        return mapper.entityToDto(saveTag);

    }

    /**
     * 출판사 페이지를 구성하는 메서드
     *
     * @param page 현재 페이지
     * @return 페이징에 필요한 정보가 담긴 PageResponse 객체
     */
    public PageResponse<TagDto.Response> getTagList(int page) {

        int size = 10;
        Page<Tag> tagPage =
                tagRepository.findAll(PageRequest.of(page - 1, size, Sort.by("tagName")));

        List<TagDto.Response> responses = mapper.entitiesToDtos(tagPage.getContent());

//        int blockLimit = 3;
//        int startPage = (((int) Math.ceil(((double) pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
//        int endPage = Math.min((startPage + blockLimit - 1), publisherPage.getTotalPages());

        return PageResponse.<TagDto.Response>builder()
                .data(responses)
                .currentPage(page)
                .totalPage(tagPage.getTotalPages())
                .build();
    }

    /**
     * 태그를 삭제하는 메서드
     *
     * @param tagId 태그 Id
     */
    public void deleteTag(Long tagId) {

        Tag tag = findTagById(tagId);
        tag.setActivated(false);
    }

    /**
     * id에 해당하는 태그 객체를 확인하는 메서드
     *
     * @param tagId 출판사 id
     * @return 태그 id에 해당하는 tag entity
     */
    private Tag findTagById(Long tagId) {

        Optional<Tag> optionalTag = tagRepository.findById(tagId);
        if (optionalTag.isPresent()) {

            return optionalTag.get();
        } else {
            throw new NotFoundException("태그 정보가 없습니다.");
        }
    }
}
