package com.springboot.blog.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        description = "PostDto Model Information"
)
public class PostRequestDto {

    @Schema(
            description = "Blog post title"
    )
    @NotBlank
    @Size(min = 2, max = 80)
    private String title;

    @Schema(
            description = "Blog post content"
    )
    @NotBlank
    @Size(min = 10)
    private String content;

    @Schema(
            description = "Blog post tag"
    )
    private Set<TagRequestDto> tags;

}
