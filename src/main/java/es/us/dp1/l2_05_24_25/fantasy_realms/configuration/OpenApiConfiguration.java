package es.us.dp1.l2_05_24_25.fantasy_realms.configuration;

import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Fantasy Realms APIs", version = "v4.0", contact = @Contact(name = "DP1-2024/2025-Group 1 (L2-05)", email = "dp1-2425-group1-l2-05@gmail.com", url = "https://github.com/gii-is-DP1/DP1-2024-2025--l2-05"), license = @License(name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0"), termsOfService = "${tos.uri}", description = "${api.description}"))
@SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer")
public class OpenApiConfiguration {

    @Bean
    public OpenApiCustomizer customizeEndpointResponses() {
        return openApi -> {
            openApi.getPaths().forEach((path, pathItem) -> {
                if ("/api/v1/matches".equals(path)) {
                    pathItem.readOperations().forEach(operation -> {
                        operation.getResponses().remove("404");
                        operation.getResponses().remove("409");
                        operation.getResponses().remove("500");
                    });
                }
                if ("/api/v1/matches/{id}".equals(path)) {
                    pathItem.readOperations().forEach(operation -> {
                        operation.getResponses().remove("400");
                        operation.getResponses().remove("403");
                        operation.getResponses().remove("409");
                        operation.getResponses().remove("500");
                    });
                }
                if ("/api/v1/matches/myMatches".equals(path)) {
                    pathItem.readOperations().forEach(operation -> {
                        operation.getResponses().remove("400");
                        operation.getResponses().remove("403");
                        operation.getResponses().remove("404");
                        operation.getResponses().remove("409");
                        operation.getResponses().remove("500");
                    });
                }
                if ("/api/v1/matches/{id}/creator".equals(path)) {
                    pathItem.readOperations().forEach(operation -> {
                        operation.getResponses().remove("400");
                        operation.getResponses().remove("403");
                        operation.getResponses().remove("409");
                        operation.getResponses().remove("500");
                    });
                }
                if ("/api/v1/matches/create".equals(path)) {
                    pathItem.readOperations().forEach(operation -> {
                        operation.getResponses().remove("409");
                    });
                }
                if ("/api/v1/matches/{id}/join".equals(path)) {
                    pathItem.readOperations().forEach(operation -> {
                        operation.getResponses().remove("400");
                    });
                }
                if ("/api/v1/matches/{id}/start".equals(path)) {
                    pathItem.readOperations().forEach(operation -> {
                        operation.getResponses().remove("400");
                    });
                }
                if ("/api/v1/matches/{id}/delete".equals(path)) {
                    pathItem.readOperations().forEach(operation -> {
                        operation.getResponses().remove("400");
                    });
                }
                if ("/api/v1/players/drawCard/deck/{matchId}".equals(path)) {
                    pathItem.readOperations().forEach(operation -> {
                        operation.getResponses().remove("400");
                    });
                }
                if ("/api/v1/players/drawCard/discardPile/{matchId}/{cardId}".equals(path)) {
                    pathItem.readOperations().forEach(operation -> {
                        operation.getResponses().remove("400");
                    });
                }
                if ("/api/v1/players/discard/{matchId}/{cardId}".equals(path)) {
                    pathItem.readOperations().forEach(operation -> {
                        operation.getResponses().remove("400");
                    });
                }
                if ("/api/v1/turns/{matchId}/cancelTurn".equals(path)) {
                    pathItem.readOperations().forEach(operation -> {
                        operation.getResponses().remove("400");
                        operation.getResponses().remove("403");
                    });
                }
                if ("/api/v1/turns/{matchId}/specialTurn".equals(path)) {
                    pathItem.readOperations().forEach(operation -> {
                        operation.getResponses().remove("400");
                        operation.getResponses().remove("403");
                    });
                }
                if ("/api/v1/developers".equals(path)) {
                    pathItem.readOperations().forEach(operation -> {
                        operation.getResponses().remove("400");
                        operation.getResponses().remove("403");
                        operation.getResponses().remove("404");
                        operation.getResponses().remove("409");
                        operation.getResponses().remove("401");
                    });
                }
                if ("/api/v1/users".equals(path)) {
                    if (pathItem.getGet() != null) {
                        // Configuración específica para GET
                        pathItem.getGet().getResponses().remove("400");
                        pathItem.getGet().getResponses().remove("404");
                        pathItem.getGet().getResponses().remove("409");
                        pathItem.getGet().getResponses().remove("500");
                    }
                    if (pathItem.getPost() != null) {
                        // Configuración específica para POST
                        pathItem.getPost().getResponses().remove("401");
                        pathItem.getPost().getResponses().remove("404");
                    }
                }
                if ("/api/v1/users/authorities".equals(path)) {
                    pathItem.readOperations().forEach(operation -> {
                        operation.getResponses().remove("400");
                        operation.getResponses().remove("404");
                        operation.getResponses().remove("409");
                        operation.getResponses().remove("500");
                    });
                }
                if ("/api/v1/users/{id}".equals(path)) {
                    pathItem.readOperations().forEach(operation -> {
                        operation.getResponses().remove("400");
                        operation.getResponses().remove("401");
                        operation.getResponses().remove("409");
                        operation.getResponses().remove("500");
                    });
                }
                if ("/api/v1/users/{userId}".equals(path)) {
                    pathItem.readOperations().forEach(operation -> {
                        operation.getResponses().remove("400");
                        operation.getResponses().remove("409");
                    });
                }
                if ("/api/v1/auth/signin".equals(path)) {
                    pathItem.readOperations().forEach(operation -> {
                        operation.getResponses().remove("401");
                        operation.getResponses().remove("409");
                        operation.getResponses().remove("404");
                        operation.getResponses().remove("403");
                        operation.getResponses().remove("500");
                    });
                }
                if ("/api/v1/auth/validate".equals(path)) {
                    pathItem.readOperations().forEach(operation -> {
                        operation.getResponses().remove("404");
                        operation.getResponses().remove("409");
                    });
                }
                if ("/api/v1/auth/signup".equals(path)) {
                    pathItem.readOperations().forEach(operation -> {
                        operation.getResponses().remove("404");
                        operation.getResponses().remove("409");
                        operation.getResponses().remove("403");
                    });
                }
                if ("/api/v1/currentuser".equals(path)) {
                    pathItem.readOperations().forEach(operation -> {
                        operation.getResponses().remove("403");
                        operation.getResponses().remove("400");
                        operation.getResponses().remove("409");
                    });
                }
                if ("/api/v1/deck/{id}".equals(path)) {
                    pathItem.readOperations().forEach(operation -> {
                        operation.getResponses().remove("403");
                        operation.getResponses().remove("400");
                        operation.getResponses().remove("409");
                        operation.getResponses().remove("500");
                    });
                }

                if ("/api/v1/statistics/total-matches".equals(path)) {
                    pathItem.readOperations().forEach(operation -> {
                        operation.getResponses().remove("400");
                        operation.getResponses().remove("403");
                        operation.getResponses().remove("404");
                        operation.getResponses().remove("409");
                        operation.getResponses().remove("500");
                    });
                }

                if ("/api/v1/statistics/average-match-duration".equals(path)) {
                    pathItem.readOperations().forEach(operation -> {
                        operation.getResponses().remove("400");
                        operation.getResponses().remove("403");
                        operation.getResponses().remove("404");
                        operation.getResponses().remove("409");
                        operation.getResponses().remove("500");
                    });
                }

                if ("/api/v1/statistics/average-players-per-match".equals(path)) {
                    pathItem.readOperations().forEach(operation -> {
                        operation.getResponses().remove("400");
                        operation.getResponses().remove("403");
                        operation.getResponses().remove("404");
                        operation.getResponses().remove("409");
                        operation.getResponses().remove("500");
                    });
                }

                if ("/api/v1/statistics/average-points".equals(path)) {
                    pathItem.readOperations().forEach(operation -> {
                        operation.getResponses().remove("400");
                        operation.getResponses().remove("403");
                        operation.getResponses().remove("404");
                        operation.getResponses().remove("409");
                        operation.getResponses().remove("500");
                    });
                }

                if ("/api/v1/statistics/max-points".equals(path)) {
                    pathItem.readOperations().forEach(operation -> {
                        operation.getResponses().remove("400");
                        operation.getResponses().remove("403");
                        operation.getResponses().remove("404");
                        operation.getResponses().remove("409");
                        operation.getResponses().remove("500");
                    });
                }

                if ("/api/v1/statistics/min-points".equals(path)) {
                    pathItem.readOperations().forEach(operation -> {
                        operation.getResponses().remove("400");
                        operation.getResponses().remove("403");
                        operation.getResponses().remove("404");
                        operation.getResponses().remove("409");
                        operation.getResponses().remove("500");
                    });
                }

                if ("/api/v1/statistics/win-percentage".equals(path)) {
                    pathItem.readOperations().forEach(operation -> {
                        operation.getResponses().remove("400");
                        operation.getResponses().remove("403");
                        operation.getResponses().remove("404");
                        operation.getResponses().remove("409");
                        operation.getResponses().remove("500");
                    });
                }

                if ("/api/v1/statistics/average-ranking-position".equals(path)) {
                    pathItem.readOperations().forEach(operation -> {
                        operation.getResponses().remove("400");
                        operation.getResponses().remove("403");
                        operation.getResponses().remove("404");
                        operation.getResponses().remove("409");
                        operation.getResponses().remove("500");
                    });
                }

                if ("/api/v1/statistics/average-turns-per-match".equals(path)) {
                    pathItem.readOperations().forEach(operation -> {
                        operation.getResponses().remove("400");
                        operation.getResponses().remove("403");
                        operation.getResponses().remove("404");
                        operation.getResponses().remove("409");
                        operation.getResponses().remove("500");
                    });
                }

                if ("/api/v1/statistics/most-frequent-cards".equals(path)) {
                    pathItem.readOperations().forEach(operation -> {
                        operation.getResponses().remove("400");
                        operation.getResponses().remove("403");
                        operation.getResponses().remove("404");
                        operation.getResponses().remove("409");
                        operation.getResponses().remove("500");
                    });
                }
                if ("/api/v1/statistics/match-players-max".equals(path)) {
                    pathItem.readOperations().forEach(operation -> {
                        operation.getResponses().remove("400");
                        operation.getResponses().remove("403");
                        operation.getResponses().remove("404");
                        operation.getResponses().remove("409");
                        operation.getResponses().remove("500");
                    });
                }

                if ("/api/v1/statistics/match-players-min".equals(path)) {
                    pathItem.readOperations().forEach(operation -> {
                        operation.getResponses().remove("400");
                        operation.getResponses().remove("403");
                        operation.getResponses().remove("404");
                        operation.getResponses().remove("409");
                        operation.getResponses().remove("500");
                    });
                }

                if ("/api/v1/statistics/global-match-players-max".equals(path)) {
                    pathItem.readOperations().forEach(operation -> {
                        operation.getResponses().remove("400");
                        operation.getResponses().remove("403");
                        operation.getResponses().remove("404");
                        operation.getResponses().remove("409");
                        operation.getResponses().remove("500");
                    });
                }

                if ("/api/v1/statistics/global-match-players-min".equals(path)) {
                    pathItem.readOperations().forEach(operation -> {
                        operation.getResponses().remove("400");
                        operation.getResponses().remove("403");
                        operation.getResponses().remove("404");
                        operation.getResponses().remove("409");
                        operation.getResponses().remove("500");
                    });
                }

                if ("/api/v1/statistics/global-match-players-average".equals(path)) {
                    pathItem.readOperations().forEach(operation -> {
                        operation.getResponses().remove("400");
                        operation.getResponses().remove("403");
                        operation.getResponses().remove("404");
                        operation.getResponses().remove("409");
                        operation.getResponses().remove("500");
                    });
                }
                if ("/api/v1/statistics/total-match-duration".equals(path)) {
                    pathItem.readOperations().forEach(operation -> {
                        operation.getResponses().remove("400");
                        operation.getResponses().remove("403");
                        operation.getResponses().remove("404");
                        operation.getResponses().remove("409");
                        operation.getResponses().remove("500");
                    });
                }

                if ("/api/v1/statistics/max-match-duration".equals(path)) {
                    pathItem.readOperations().forEach(operation -> {
                        operation.getResponses().remove("400");
                        operation.getResponses().remove("403");
                        operation.getResponses().remove("404");
                        operation.getResponses().remove("409");
                        operation.getResponses().remove("500");
                    });
                }

                if ("/api/v1/statistics/min-match-duration".equals(path)) {
                    pathItem.readOperations().forEach(operation -> {
                        operation.getResponses().remove("400");
                        operation.getResponses().remove("403");
                        operation.getResponses().remove("404");
                        operation.getResponses().remove("409");
                        operation.getResponses().remove("500");
                    });
                }

                if ("/api/v1/statistics/global-match-duration-average".equals(path)) {
                    pathItem.readOperations().forEach(operation -> {
                        operation.getResponses().remove("400");
                        operation.getResponses().remove("403");
                        operation.getResponses().remove("404");
                        operation.getResponses().remove("409");
                        operation.getResponses().remove("500");
                    });
                }

                if ("/api/v1/statistics/global-match-max-duration".equals(path)) {
                    pathItem.readOperations().forEach(operation -> {
                        operation.getResponses().remove("400");
                        operation.getResponses().remove("403");
                        operation.getResponses().remove("404");
                        operation.getResponses().remove("409");
                        operation.getResponses().remove("500");
                    });
                }

                if ("/api/v1/statistics/global-match-min-duration".equals(path)) {
                    pathItem.readOperations().forEach(operation -> {
                        operation.getResponses().remove("400");
                        operation.getResponses().remove("403");
                        operation.getResponses().remove("404");
                        operation.getResponses().remove("409");
                        operation.getResponses().remove("500");
                    });
                }

                if ("/api/v1/achievements".equals(path)) {
                    if (pathItem.getPost() != null) {
                        pathItem.getPost().getResponses().remove("404");
                    }
                    if (pathItem.getGet() != null) {
                        pathItem.getGet().getResponses().remove("400");
                        pathItem.getGet().getResponses().remove("409");
                        pathItem.getGet().getResponses().remove("401");
                        pathItem.getGet().getResponses().remove("403");
                        pathItem.getGet().getResponses().remove("500");
                        pathItem.getGet().getResponses().remove("404");
                    }
                }

                if ("/api/v1/achievements/{id}".equals(path)) {
                    pathItem.readOperations().forEach(operation -> {
                        operation.getResponses().remove("400");
                        operation.getResponses().remove("401");
                        operation.getResponses().remove("409");
                        operation.getResponses().remove("403");
                        operation.getResponses().remove("500");
                    });
                }

                if ("/api/v1/achievements/{achievementId}".equals(path)) {
                    pathItem.readOperations().forEach(operation -> {
                        operation.getResponses().remove("400");
                        operation.getResponses().remove("401");
                        operation.getResponses().remove("409");
                    });
                }

                if ("/api/v1/achievements/{achievementId}".equals(path)) {
                    if (pathItem.getPut() != null) {
                        pathItem.getPut().getResponses().remove("400");
                        pathItem.getPut().getResponses().remove("401");
                        pathItem.getPut().getResponses().remove("409");
                    }
                    if (pathItem.getDelete() != null) {
                        pathItem.getDelete().getResponses().remove("400");
                        pathItem.getDelete().getResponses().remove("409");
                        pathItem.getDelete().getResponses().remove("401");
                        pathItem.getDelete().getResponses().remove("500");
                    }
                }

                if ("\"/global-ranking\"".equals(path)) {
                    pathItem.readOperations().forEach(operation -> {
                        operation.getResponses().remove("409");
                        operation.getResponses().remove("403");
                        operation.getResponses().remove("500");
                        pathItem.getGet().getResponses().remove("404");
                    });
                }
            });
        };
    }
}
