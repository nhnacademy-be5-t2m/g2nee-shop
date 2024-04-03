package com.t2m.g2nee.shop.bookset.publisher.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.t2m.g2nee.shop.bookset.publisher.domain.Publisher;
import com.t2m.g2nee.shop.bookset.publisher.dto.PublisherDto;
import com.t2m.g2nee.shop.bookset.publisher.mapper.PublisherMapper;
import com.t2m.g2nee.shop.bookset.publisher.repository.PublisherRepository;
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
class PublisherServiceTest {

    @Mock
    private PublisherRepository publisherRepository;
    @Mock
    private PublisherMapper mapper;

    @InjectMocks
    private PublisherService publisherService;


    @Test
    @DisplayName("출판사 등록 테스트")
    void registerPublisher() {

        //given
        PublisherDto.Request request = getRequest();
        Publisher publisher = getPublisher();
        PublisherDto.Response response = getResponse();

        when(publisherRepository.save(publisher)).thenReturn(publisher);
        when(mapper.entityToDto(publisher)).thenReturn(response);

        //when
        PublisherDto.Response actual = publisherService.registerPublisher(publisher);

        // then
        assertEquals(actual.getPublisherName(), request.getPublisherName());
        assertEquals(actual.getPublisherEngName(), request.getPublisherEngName());

    }

    @Test
    @DisplayName("출판사 수정 테스트")
    void updatePublisher() {

        //given
        PublisherDto.Request request = getModifyRequest();
        Publisher publisher = getModifiedPublisher();
        PublisherDto.Response response = getModifiedResponse();

        when(publisherRepository.findById(publisher.getPublisherId())).thenReturn(Optional.of(publisher));
        when(publisherRepository.save(publisher)).thenReturn(publisher);

        //when
        publisherService.updatePublisher(publisher);

        assertEquals(response.getPublisherName(), request.getPublisherName());
        assertEquals(response.getPublisherEngName(), request.getPublisherEngName());

    }

    @Test
    @DisplayName("출판사 리스트 조회 테스트")
    void getPublisherList() {

        //given
        List<Publisher> publisherList = getList();
        List<PublisherDto.Response> responseList = getResponseList();
        PageResponse<PublisherDto.Response> responses = PageResponse.<PublisherDto.Response>builder()
                .data(responseList)
                .currentPage(1)
                .totalPage(2)
                .build();

        Pageable pageable = PageRequest.of(0, 10, Sort.by("publisherName"));
        Page<Publisher> publisherPage = new PageImpl<>(publisherList, pageable, publisherList.size());

        when(publisherRepository.findAll(PageRequest.of(0, 10, Sort.by("publisherName"))))
                .thenReturn(publisherPage);
        when(mapper.entitiesToDtos(publisherList)).thenReturn(responseList);

        //when
        publisherService.getPublisherList(1);

        //then
        assertEquals(10, responses.getData().size());
        assertEquals(1, responses.getCurrentPage());
        assertEquals(2, responses.getTotalPage());
        assertEquals(20, publisherPage.getTotalElements());
        for (int i = 0; i < responseList.size(); i++) {
            assertEquals(responseList.get(i).getPublisherName(), responses.getData().get(i).getPublisherName());
            assertEquals(responseList.get(i).getPublisherEngName(), responses.getData().get(i).getPublisherEngName());
        }

    }

    @Test
    @DisplayName("출판사 삭제 테스트")
    void deletePublisher() {

        //given
        Publisher publisher = getPublisher();

        when(publisherRepository.findById(publisher.getPublisherId())).thenReturn(Optional.of(publisher));
        doNothing().when(publisherRepository).deleteById(publisher.getPublisherId());

        //when //then
        publisherService.deletePublisher(publisher.getPublisherId());

        verify(publisherRepository, times(1)).deleteById(publisher.getPublisherId());

    }

    @Test
    @DisplayName("출판사가 없을 때 예외 테스트")
    void testExistPublisher() {
        Publisher publisher = getPublisher();

        when(publisherRepository.findById(publisher.getPublisherId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> publisherService.deletePublisher(publisher.getPublisherId()));

    }

    private List<Publisher> getList() {

        List<Publisher> publisherList = new ArrayList<>();
        for (int i = 1; i < 21; i++) {
            Publisher publisher = Publisher.builder()
                    .publisherId((long) i)
                    .publisherName("출판사" + i)
                    .publisherEngName("publisher" + i)
                    .build();

            publisherList.add(publisher);
        }
        return publisherList;
    }

    private List<PublisherDto.Response> getResponseList() {

        List<PublisherDto.Response> publisherList = new ArrayList<>();
        for (int i = 1; i < 11; i++) {
            PublisherDto.Response publisher = PublisherDto.Response.builder()
                    .publisherId((long) i)
                    .publisherName("출판사" + i)
                    .publisherEngName("publisher" + i)
                    .build();

            publisherList.add(publisher);
        }
        return publisherList;
    }

    private PublisherDto.Request getRequest() {

        return PublisherDto.Request.builder()
                .publisherName("출판사1")
                .publisherEngName("publisher1")
                .build();
    }

    private PublisherDto.Request getModifyRequest() {


        return PublisherDto.Request.builder()
                .publisherName("출판사2")
                .publisherEngName("publisher2")
                .build();
    }

    private Publisher getPublisher() {

        return Publisher.builder()
                .publisherId(1L)
                .publisherName("출판사1")
                .publisherEngName("publisher1")
                .build();
    }

    private Publisher getModifiedPublisher() {

        return Publisher.builder()
                .publisherId(1L)
                .publisherName("출판사2")
                .publisherEngName("publisher2")
                .build();
    }


    private PublisherDto.Response getResponse() {

        return PublisherDto.Response.builder()
                .publisherId(1L)
                .publisherName("출판사1")
                .publisherEngName("publisher1")
                .build();
    }

    private PublisherDto.Response getModifiedResponse() {

        return PublisherDto.Response.builder()
                .publisherId(1L)
                .publisherName("출판사2")
                .publisherEngName("publisher2")
                .build();
    }
}