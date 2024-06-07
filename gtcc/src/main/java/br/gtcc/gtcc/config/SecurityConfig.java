package br.gtcc.gtcc.config;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import br.gtcc.gtcc.config.handlers.LoginInterceptor;
import br.gtcc.gtcc.model.UserType;
import br.gtcc.gtcc.model.neo4j.Users;
import br.gtcc.gtcc.model.neo4j.repository.UsersRepository;
import br.gtcc.gtcc.services.impl.neo4j.UserServices;
import br.gtcc.gtcc.util.JWTUtil;

@Configuration
@EnableWebMvc
public class SecurityConfig implements CommandLineRunner, WebMvcConfigurer {
    @Autowired
    private UserServices userServices;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    JWTUtil jwtUtil;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    private void addUsers() {
        try {
            // Verifica se o usuário Admin já existe no banco de dados
            Optional<Users> existingAdmin = userServices.userrepository.findByLogin("admin");
            if (existingAdmin.isEmpty()) {
                Users admin = new Users();
                admin.setName("Admin");
                admin.setEmail("admin@gmail.com");
                admin.setSenha(passwordEncoder().encode("1234"));
                admin.setCellphone("123456789");
                admin.getUserType().add(UserType.ADMIN);
                admin.setLogin("admin");
                admin.getPermissoes().add("ROLE_USER");
                admin.getPermissoes().add("ROLE_ADMIN");
                admin.getPermissoes().add("ROLE_PROFESSOR");
                admin.getPermissoes().add("ROLE_COORDENADOR");
                admin.getPermissoes().add("ROLE_ALUNO");
                userServices.userrepository.save(admin);
            }

            // Verifica se o usuário Professor já existe no banco de dados
            Optional<Users> existingProfessor = userServices.userrepository.findByLogin("professor");
            if (existingProfessor.isEmpty()) {
                Users professor = new Users();
                professor.setName("Professor");
                professor.setEmail("professor@gmail.com");
                professor.setSenha(passwordEncoder().encode("1234"));
                professor.setCellphone("987654321");
                professor.getUserType().add(UserType.PROFESSOR);
                professor.setLogin("professor");
                professor.getPermissoes().add("ROLE_USER");
                professor.getPermissoes().add("ROLE_PROFESSOR");
                userServices.userrepository.save(professor);
            }

            // Verifica se o usuário Coordenador já existe no banco de dados
            Optional<Users> existingCoordinator = userServices.userrepository.findByLogin("coordenador");
            if (existingCoordinator.isEmpty()) {
                Users coordenador = new Users();
                coordenador.setName("Coordenador");
                coordenador.setEmail("coordenador@gmail.com");
                coordenador.setSenha(passwordEncoder().encode("1234"));
                coordenador.setCellphone("444444444");
                coordenador.getUserType().add(UserType.COORDENADOR);
                coordenador.setLogin("coordenador");
                coordenador.getPermissoes().add("ROLE_USER");
                coordenador.getPermissoes().add("ROLE_COORDENADOR");
                userServices.userrepository.save(coordenador);
            }

            // Verifica se o usuário Aluno já existe no banco de dados
            Optional<Users> existingAluno = userServices.userrepository.findByLogin("aluno");
            if (existingAluno.isEmpty()) {
                Users aluno = new Users();
                aluno.setName("Aluno");
                aluno.setEmail("aluno@gmail.com");
                aluno.setSenha(passwordEncoder().encode("1234"));
                aluno.setCellphone("555555555");
                aluno.getUserType().add(UserType.ALUNO);
                aluno.setLogin("aluno");
                aluno.getPermissoes().add("ROLE_USER");
                aluno.getPermissoes().add("ROLE_ALUNO");
                userServices.userrepository.save(aluno);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void run(String... args) throws Exception {
        addUsers();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor(userServices, usersRepository, jwtUtil))
                .excludePathPatterns("/error**", "/index**", "/doc**", "/auth**", "/swagger-ui**")
                .addPathPatterns("/coordenacao/tcc/v1/usuario/**" , "gtcc/coordenacao/tcc/v1/usuarios", "gtcc/coordenacao/tcc/v1/datas" , "/gtcc/coordenacao/tcc/v1/date/**");
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {}

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {}

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {}

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {}

    @Override
    public void addFormatters(FormatterRegistry registry) {}

    @Override
    public void addCorsMappings(CorsRegistry registry) {}

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {}

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {}

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {}

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> handlers) {}

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {}

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {}

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {}

    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {}
}