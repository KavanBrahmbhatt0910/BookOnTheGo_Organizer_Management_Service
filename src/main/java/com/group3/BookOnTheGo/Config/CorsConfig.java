package com.group3.BookOnTheGo.Config;

    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.web.servlet.config.annotation.CorsRegistry;
    import org.springframework.web.servlet.config.annotation.EnableWebMvc;
    import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

    @Configuration
    @EnableWebMvc
    public class CorsConfig {
        @Bean
        public WebMvcConfigurer corsConfigurer() {
            return new WebMvcConfigurer() {
                @Override
                public void addCorsMappings(CorsRegistry registry) {
                    registry.addMapping("/**")
                            .allowedOrigins(
                                    "http://localhost:5173",
                                    "http://localhost:5173/**"

                            )
                            .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH")
                            .allowedHeaders("*")
                            .allowCredentials(true);
                }
            };
        }
    }
