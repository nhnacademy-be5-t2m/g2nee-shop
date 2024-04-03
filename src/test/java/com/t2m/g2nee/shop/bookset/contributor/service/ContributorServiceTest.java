package com.t2m.g2nee.shop.bookset.contributor.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.t2m.g2nee.shop.bookset.contributor.domain.Contributor;
import com.t2m.g2nee.shop.bookset.contributor.dto.ContributorDto;
import com.t2m.g2nee.shop.bookset.contributor.mapper.ContributorMapper;
import com.t2m.g2nee.shop.bookset.contributor.repository.ContributorRepository;
import com.t2m.g2nee.shop.exception.NotFoundException;
import com.t2m.g2nee.shop.pageUtils.PageResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@ExtendWith(MockitoExtension.class)
class ContributorServiceTest {

    @Mock
    private ContributorRepository contributorRepository;
    @Mock
    private ContributorMapper mapper;

    @InjectMocks
    private ContributorService contributorService;


    @Test
    @DisplayName("기여자 등록 테스트")
    void registerContributor() {

        //given
        ContributorDto.Request request = getRequest();
        Contributor contributor = getContributor();
        ContributorDto.Response response = getResponse();

        when(contributorRepository.save(contributor)).thenReturn(contributor);
        when(mapper.entityToDto(contributor)).thenReturn(response);

        //when
        ContributorDto.Response actual = contributorService.registerContributor(contributor);

        // then
        assertEquals(actual.getContributorName(), request.getContributorName());
        assertEquals(actual.getContributorEngName(), request.getContributorEngName());

    }

    @Test
    @DisplayName("기여자 재활성화 테스트")
    void activateContributorTest(){

        //given
        ContributorDto.Request request = getRequest();
        Contributor contributor = Contributor.builder()
                .contributorName("기여자1")
                .isActivated(false)
                .build();

        when(contributorRepository.findByContributorName(request.getContributorName())).thenReturn(Optional.ofNullable(contributor));

        //when
        contributorService.registerContributor(contributor);

        //then
        assertTrue(contributor.isActivated());
    }


    @Test
    @DisplayName("기여자 수정 테스트")
    void updateContributor() {

        //given
        ContributorDto.Request request = getModifyRequest();
        Contributor contributor = getContributor();
        ContributorDto.Response response = getModifiedResponse();

        when(contributorRepository.findById(contributor.getContributorId())).thenReturn(Optional.of(contributor));

        //when
        contributorService.updateContributor(contributor);
        //then
        assertEquals(response.getContributorName(), request.getContributorName());
        assertEquals(response.getContributorEngName(), request.getContributorEngName());

    }

    @Test
    @DisplayName("기여자 리스트 조회 테스트")
    void getContributorList() {

        //given
        List<Contributor> contributorList = getList();
        List<ContributorDto.Response> responseList = getResponseList();
        PageResponse<ContributorDto.Response> responses = PageResponse.<ContributorDto.Response>builder()
                .data(responseList)
                .currentPage(1)
                .totalPage(2)
                .build();

        Pageable pageable = PageRequest.of(0, 10,Sort.by("contributorName"));
        Page<Contributor> contributorPage = new PageImpl<>(contributorList, pageable, contributorList.size());

        when(contributorRepository.findAll(PageRequest.of(0, 10, Sort.by("contributorName"))))
                .thenReturn(contributorPage);
        when(mapper.entitiesToDtos(contributorList)).thenReturn(responseList);

        //when
        contributorService.getContributorList(1);

        //then
        assertEquals(10, responses.getData().size());
        assertEquals(1, responses.getCurrentPage());
        assertEquals(2, responses.getTotalPage());
        assertEquals(20, contributorPage.getTotalElements());
        for (int i = 0; i < responseList.size(); i++) {
            assertEquals(responseList.get(i).getContributorName(), responses.getData().get(i).getContributorName());
            assertEquals(responseList.get(i).getContributorEngName(), responses.getData().get(i).getContributorEngName());
        }

    }

    @Test
    @DisplayName("기여자 삭제 테스트")
    void deleteContributor() {

        //given
        Contributor contributor = getContributor();

        when(contributorRepository.findById(contributor.getContributorId())).thenReturn(Optional.of(contributor));
        doNothing().when(contributorRepository).deleteById(contributor.getContributorId());

        //when //then
        contributorService.deleteContributor(contributor.getContributorId());

        verify(contributorRepository, times(1)).deleteById(contributor.getContributorId());

    }
    @Test
    @DisplayName("기여자가 없을 때 예외 테스트")
    void testExistPublisher(){
        Contributor contributor = getContributor();

        when(contributorRepository.findById(contributor.getContributorId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> contributorService.deleteContributor(contributor.getContributorId()));

    }

    private List<Contributor> getList() {

        List<Contributor> contributorList = new ArrayList<>();
        for (int i = 1; i < 21; i++) {
            Contributor contributor = Contributor.builder()
                    .contributorId((long) i)
                    .contributorName("기여자" + i)
                    .contributorEngName("contributor" + i)
                    .build();

            contributorList.add(contributor);
        }
        return contributorList;
    }

    private List<ContributorDto.Response> getResponseList() {

        List<ContributorDto.Response> contributorList = new ArrayList<>();
        for (int i = 1; i < 11; i++) {
            ContributorDto.Response publisher = ContributorDto.Response.builder()
                    .contributorId((long) i)
                    .contributorName("기여자" + i)
                    .contributorEngName("contributor" + i)
                    .build();

            contributorList.add(publisher);
        }
        return contributorList;
    }

    private ContributorDto.Request getRequest() {

        return ContributorDto.Request.builder()
                .contributorName("기여자1")
                .contributorEngName("contributor1")
                .build();
    }

    private ContributorDto.Request getModifyRequest() {


        return ContributorDto.Request.builder()
                .contributorName("기여자2")
                .contributorEngName("contributor2")
                .build();
    }

    private Contributor getContributor() {

        return Contributor.builder()
                .contributorId(1L)
                .contributorName("기여자1")
                .contributorEngName("contributor1")
                .build();
    }

    private Contributor getModifiedContributor() {

        return Contributor.builder()
                .contributorId(1L)
                .contributorName("기여자2")
                .contributorEngName("contributor2")
                .build();
    }


    private ContributorDto.Response getResponse() {

        return ContributorDto.Response.builder()
                .contributorId(1L)
                .contributorName("기여자1")
                .contributorEngName("contributor1")
                .build();
    }

    private ContributorDto.Response getModifiedResponse() {

        return ContributorDto.Response.builder()
                .contributorId(1L)
                .contributorName("기여자2")
                .contributorEngName("contributor2")
                .build();
    }
}