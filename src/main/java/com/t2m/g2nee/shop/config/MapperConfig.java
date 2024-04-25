package com.t2m.g2nee.shop.config;

import com.t2m.g2nee.shop.bookset.book.mapper.BookMapper;
import com.t2m.g2nee.shop.bookset.book.mapper.BookMapperImpl;
import com.t2m.g2nee.shop.bookset.bookcontributor.mapper.BookContributorMapper;
import com.t2m.g2nee.shop.bookset.bookcontributor.mapper.BookContributorMapperImpl;
import com.t2m.g2nee.shop.bookset.contributor.mapper.ContributorMapper;
import com.t2m.g2nee.shop.bookset.contributor.mapper.ContributorMapperImpl;
import com.t2m.g2nee.shop.bookset.publisher.mapper.PublisherMapper;
import com.t2m.g2nee.shop.bookset.publisher.mapper.PublisherMapperImpl;
import com.t2m.g2nee.shop.bookset.role.mapper.RoleMapper;
import com.t2m.g2nee.shop.bookset.role.mapper.RoleMapperImpl;
import com.t2m.g2nee.shop.bookset.tag.mapper.TagMapper;
import com.t2m.g2nee.shop.bookset.tag.mapper.TagMapperImpl;
import net.bytebuddy.utility.nullability.NeverNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Repository Test에 import할 Mapstruct Configuration
 *
 * @DataJpa 테스트 시 mapper bean에 대한 오류가 생길 경우
 * @Import(value = {MapperConfig.class}) 를 추가해주세요
 */
@Configuration
public class MapperConfig {

    @Bean
    public BookContributorMapper bookContributorMapper() {
        return new BookContributorMapperImpl();
    }

    @Bean
    public ContributorMapper contributorMapper() {
        return new ContributorMapperImpl();
    }

    @Bean
    public BookMapper bookMapper() {
        return new BookMapperImpl();
    }

    @Bean
    public TagMapper tagMapper() {
        return new TagMapperImpl();
    }

    @Bean
    public RoleMapper roleMapper() {

        return new RoleMapperImpl();
    }

    @Bean
    public PublisherMapper publisherMapper() {
        return new PublisherMapperImpl();
    }

}
