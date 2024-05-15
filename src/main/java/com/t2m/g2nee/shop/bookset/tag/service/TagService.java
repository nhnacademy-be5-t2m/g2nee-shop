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
            tag.setActivated(true);
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
     * 모든 태그를 조회하는 메서드
     */
    @Transactional(readOnly = true)
    public List<TagDto.Response> getAllTag() {

        return mapper.entitiesToDtos(tagRepository.findAllActivated());
    }

    /**
     * 태그 페이지를 구성하는 메서드
     *
     * @param page 현재 페이지
     * @return 페이징에 필요한 정보가 담긴 PageResponse 객체
     */
    @Transactional(readOnly = true)
    public PageResponse<TagDto.Response> getTagList(int page) {

        int size = 10;
        Page<Tag> tagPage =
                tagRepository.findAllActivated(PageRequest.of(page - 1, size, Sort.by("tagName")));

        List<TagDto.Response> responses = mapper.entitiesToDtos(tagPage.getContent());

        int maxPageButtons = 5;
        int startPage = (int) Math.max(1, tagPage.getNumber() - Math.floor((double) maxPageButtons / 2));
        int endPage = Math.min(startPage + maxPageButtons - 1, tagPage.getTotalPages());

        if (endPage - startPage + 1 < maxPageButtons) {
            startPage = Math.max(1, endPage - maxPageButtons + 1);
        }


        return PageResponse.<TagDto.Response>builder()
                .data(responses)
                .currentPage(page)
                .startPage(startPage)
                .endPage(endPage)
                .totalPage(tagPage.getTotalPages())
                .totalElements(tagPage.getTotalElements())
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
