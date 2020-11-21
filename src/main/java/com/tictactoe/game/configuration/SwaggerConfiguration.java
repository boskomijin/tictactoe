/*
 * Software Architectures and Solutions d.o.o. Novi Sad. Copyright 2015-2020.
 */

package com.tictactoe.game.configuration;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

/**
 * The <code>SwaggerConfiguration</code> class configures the swagger for Tic Tac Toe game REST API.
 *
 * @NB Swagger is disabled in production (@see @Profile annotation)!
 * @author Bosko Mijin
 */
@Configuration
@Profile("!production")
public class SwaggerConfiguration {

    /**
     * The <code>apiInfo</code> bean sets the api's meta information as included in the json ResourceListing response.
     *
     * @return ApiInfo - the API info populated.
     */
    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI().info(new Info().title(SwaggerEnum.TITLE.getValue()).version(SwaggerEnum.VERSION.getValue())
                .description(SwaggerEnum.DESCRIPTION.getValue()));
    }

    /**
     * The <code>gameApi</code> bean defines the group and paths which have to be displayed.
     *
     * @return GroupedOpenApi - The grouped open api according to settings.
     */
    @Bean
    public GroupedOpenApi gameApi() {
        return GroupedOpenApi.builder().group(SwaggerEnum.DEFINITION_GAME.getValue())
                .pathsToMatch(SwaggerEnum.DEFAULT_PATTERN.getValue()).build();
    }
}
