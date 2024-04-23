package com.t2m.g2nee.shop.bookset.contributor.controller;


import com.t2m.g2nee.shop.bookset.contributor.domain.Contributor;
import com.t2m.g2nee.shop.bookset.contributor.dto.ContributorDto;
import com.t2m.g2nee.shop.bookset.contributor.service.ContributorService;
import com.t2m.g2nee.shop.pageUtils.PageResponse;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/api/v1/shop/contributors")
@RequiredArgsConstructor
public class ContributorController {

    private final ContributorService contributorService;

    /**
     * 기여자를 생성하는 컨트롤러 입니다.
     *
     * @param request 기여자 정보가 담긴 객체
     * @return 생성한 기여자 정보
     */
    @PostMapping
    public ResponseEntity<ContributorDto.Response> postContributor(@RequestBody @Valid ContributorDto.Request request) {

        Contributor contributor = Contributor.builder()
                .contributorName(request.getContributorName())
                .contributorEngName(request.getContributorEngName())
                .build();

        ContributorDto.Response response = contributorService.registerContributor(contributor);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * 기여자 정보를 수정하는 컨트롤러 입니다.
     *
     * @param request       수정할 기여자 정보가 담긴 객체
     * @param contributorId 수정할 기여자 id
     * @return 수정 후 기여자 객체 정보
     */
    @PatchMapping("/{contributorId}")
    public ResponseEntity<ContributorDto.Response> modifyContributor(@RequestBody @Valid ContributorDto.Request request,
                                                                     @PathVariable("contributorId")
                                                                     Long contributorId) {

        Contributor contributor = Contributor.builder()
                .contributorId(contributorId)
                .contributorName(request.getContributorName())
                .contributorEngName(request.getContributorEngName())
                .build();

        ContributorDto.Response response = contributorService.updateContributor(contributor);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * 기여자 리스트를 보여주는 컨트롤러 입니다
     * @return 기여자 정보들과 페이지 정보
     */
    @GetMapping("/list")
    public ResponseEntity<List<ContributorDto.Response>> getAllContributor() {

        List<ContributorDto.Response> response = contributorService.getAllContributor();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * 기여자 리스트를 보여주는 컨트롤러 입니다
     *
     * @param page 페이지 값
     * @return 기여자 정보들과 페이지 정보
     */
    @GetMapping
    public ResponseEntity<PageResponse<ContributorDto.Response>> getContributors(@RequestParam int page) {

        PageResponse<ContributorDto.Response> response = contributorService.getContributorList(page);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * 기여자 삭제하는 컨트롤러 입니다.
     *
     * @param contributorId 기여자 id
     * @return X
     */

    @DeleteMapping("/{contributorId}")
    public ResponseEntity deleteContributor(@PathVariable("contributorId") Long contributorId) {

        contributorService.deleteContributor(contributorId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

