package com.springboot.blog;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.payload.*;
import com.springboot.blog.utils.CommentUtils;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Spring boot blog app REST APIs",
				description = "Spring boot blog app rest APIs documentation",
				version = "v1.0",
				contact = @Contact(
						name = "aohkgnadnart",
						email = "aohkgnadnart@gmail.com",
						url = "http://13.210.185.51:3000"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Spring boot blog app documentation",
				url = "http://13.210.185.51:3000"
		)
)
public class SpringbootBlogRestApiApplication {

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();

		modelMapper.addMappings(new PropertyMap<CommentSimpleResponseDto, Comment>() {
			@Override
			protected void configure() {
				map().setId(source.getId());
			}
		});

		modelMapper.addMappings(new PropertyMap<Comment, CommentResponseDto>() {
			@Override
			protected void configure() {
				map(source.getParentComment(), destination.getParentComment());

				// important!
				map().setReplies(new ArrayList<>());

//				map(source.getDislikeCount(), destination.getDislikeCount());
//				map(source.getLikeCount(), destination.getLikeCount());
			}
		});

		modelMapper.addMappings(new PropertyMap<Post, PostFullResponseDto>() {
			@Override
			protected void configure() {
				using(ctx -> {
					List<CommentResponseDto> commentResponseDtos = ((List<Comment>) ctx.getSource())
							.stream()
							.map(comment -> modelMapper.map(comment, CommentResponseDto.class))
							.collect(Collectors.toList());

					return CommentUtils.convertToCommentTree(commentResponseDtos);
				}).map(source.getComments(), destination.getComments());
			}
		});



//		modelMapper.addMappings(new PropertyMap<Comment, CommentPersonalizedResponseDto>() {
//			@Override
//			protected void configure() {
////				map(source.getParentComment(), destination.getParentComment());
//
//				// important!
//				map().setReplies(new ArrayList<>());
//
//				map(source.getDislikeCount(), destination.getDislikeCount());
//				map(source.getLikeCount(), destination.getLikeCount());
//			}
//		});

		return modelMapper;
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringbootBlogRestApiApplication.class, args);
	}

}
