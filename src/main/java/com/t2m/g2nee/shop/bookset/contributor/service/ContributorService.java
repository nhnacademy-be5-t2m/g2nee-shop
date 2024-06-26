package com.t2m.g2nee.shop.bookset.contributor.service;

import com.t2m.g2nee.shop.bookset.contributor.domain.Contributor;
import com.t2m.g2nee.shop.bookset.contributor.dto.ContributorDto;
import com.t2m.g2nee.shop.bookset.contributor.mapper.ContributorMapper;
import com.t2m.g2nee.shop.bookset.contributor.repository.ContributorRepository;
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

@Service
@RequiredArgsConstructor
@Transactional
public class ContributorService {

    private final ContributorRepository contributorRepository;
    private final ContributorMapper mapper;

    /**
     * 기여자 정보를 저장하는 메서드
     *
     * @param contributor 저장할 정보가 담긴 엔티티
     * @return 기여자 객체 응답
     */
    public ContributorDto.Response registerContributor(Contributor contributor) {

        Optional<Contributor> optionalContributor =
                contributorRepository.findByContributorName(contributor.getContributorName());

        if (optionalContributor.isPresent()) {

            Contributor findContributor = optionalContributor.get();
            findContributor.setActivated(true);
            return mapper.entityToDto(findContributor);
        } else {
            contributor.setActivated(true);
            Contributor saveContributor = contributorRepository.save(contributor);

            return mapper.entityToDto(saveContributor);
        }
    }

    /**
     * 기여자를 수정하는 메서드
     *
     * @param contributor 수정할 정보가 담긴 엔티티
     * @return 역할 객체 응답
     */
    public ContributorDto.Response updateContributor(Contributor contributor) {

        Contributor findContributor = findContributorById(contributor.getContributorId());

        Optional.ofNullable(contributor.getContributorName()).ifPresent(findContributor::setContributorName);
        Optional.ofNullable(contributor.getContributorEngName()).ifPresent(findContributor::setContributorEngName);

        return mapper.entityToDto(findContributor);

    }

    /**
     * 모든 기여자를 조회하는 메서드
     *
     * @return List<ContributorDto.Response>
     */
    @Transactional(readOnly = true)
    public List<ContributorDto.Response> getAllContributor() {

        return mapper.entitiesToDtos(contributorRepository.findAllActivated());
    }

    /**
     * 기여자 페이지를 구성하는 메서드
     *
     * @param page 현재 페이지
     * @return 페이징에 필요한 정보가 담긴 PageResponse 객체
     */
    @Transactional(readOnly = true)
    public PageResponse<ContributorDto.Response> getContributorList(int page) {

        int size = 10;
        Page<Contributor> contributorPage =
                contributorRepository.findAllActivated(PageRequest.of(page - 1, size, Sort.by("contributorName")));

        List<ContributorDto.Response> responses = mapper.entitiesToDtos(contributorPage.getContent());

        int maxPageButtons = 5;
        int startPage = (int) Math.max(1, contributorPage.getNumber() - Math.floor((double) maxPageButtons / 2));
        int endPage = Math.min(startPage + maxPageButtons - 1, contributorPage.getTotalPages());

        if (endPage - startPage + 1 < maxPageButtons) {
            startPage = Math.max(1, endPage - maxPageButtons + 1);
        }


        return PageResponse.<ContributorDto.Response>builder()
                .data(responses)
                .currentPage(page)
                .startPage(startPage)
                .endPage(endPage)
                .totalPage(contributorPage.getTotalPages())
                .totalElements(contributorPage.getTotalElements())
                .build();
    }

    /**
     * 기여자를 삭제하는 메서드
     *
     * @param contributorId 기여자 Id
     */
    public void deleteContributor(Long contributorId) {

        findContributorById(contributorId);

        contributorRepository.deleteById(contributorId);
    }

    /**
     * id에 해당하는 기여자 객체를 확인하는 메서드
     *
     * @param contributorId 기여자 id
     * @return 기여자 id에 해당하는 contributor entity
     */
    public Contributor findContributorById(Long contributorId) {

        Optional<Contributor> optionalContributor = contributorRepository.findById(contributorId);
        if (optionalContributor.isPresent()) {

            return optionalContributor.get();
        } else {
            throw new NotFoundException("기여자 정보가 없습니다.");
        }
    }
}
