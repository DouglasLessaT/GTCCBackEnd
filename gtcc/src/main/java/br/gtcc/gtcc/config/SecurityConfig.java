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
import br.gtcc.gtcc.model.mysql.Usuario;
import br.gtcc.gtcc.model.neo4j.repository.UsersRepository;
import br.gtcc.gtcc.services.impl.neo4j.UserServices;
import br.gtcc.gtcc.util.JWTUtil;
import br.gtcc.gtcc.util.services.UserUtil;

@Configuration
@EnableWebMvc
public class SecurityConfig implements CommandLineRunner, WebMvcConfigurer {
    
    @Autowired
    private UserUtil userUtil;

    @Autowired
    private UserServices userServices;

    @Autowired
    JWTUtil jwtUtil;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    private void addUsers() {
        try {
            // Verifica se o usuário Admin já existe no banco de dados
            Optional<Usuario> existingAdmin = userUtil.repository.findByLogin("admin");
            if (existingAdmin.isEmpty()) {
                Usuario admin = new Usuario();
                admin.setNome("Admin");
                admin.setEmail("admin@gmail.com");
                admin.setSenha(passwordEncoder().encode("1234"));
                admin.setTelefone("123456789");
                //admin.getUserType().add(UserType.ADMIN);
                admin.getPermissoes().add("ROLE_USER");
                admin.getPermissoes().add("ROLE_ADMIN");
                admin.getPermissoes().add("ROLE_PROFESSOR");
                admin.getPermissoes().add("ROLE_COORDENADOR");
                admin.getPermissoes().add("ROLE_ALUNO");
                userUtil.repository.save(admin);
            }

            // Verifica se o usuário Professor já existe no banco de dados
            Optional<Usuario> existingProfessor = userUtil.repository.findByLogin("professor");
            if (existingProfessor.isEmpty()) {
                Usuario professor = new Usuario();
                professor.setNome("Professor");
                professor.setEmail("professor@gmail.com");
                professor.setSenha(passwordEncoder().encode("1234"));
                professor.setTelefone("987654321");
                //professor.getUserType().add(UserType.PROFESSOR);
                professor.getPermissoes().add("ROLE_USER");
                professor.getPermissoes().add("ROLE_PROFESSOR");
                userUtil.repository.save(professor);
            }

            // Verifica se o usuário Coordenador já existe no banco de dados
            Optional<Usuario> existingCoordinator = userUtil.repository.findByLogin("coordenador");
            if (existingCoordinator.isEmpty()) {
                Usuario coordenador = new Usuario();
                coordenador.setNome("Coordenador");
                coordenador.setEmail("coordenador@gmail.com");
                coordenador.setSenha(passwordEncoder().encode("1234"));
                coordenador.setTelefone("444444444");
                //coordenador.getUserType().add(UserType.COORDENADOR);
                coordenador.getPermissoes().add("ROLE_USER");
                coordenador.getPermissoes().add("ROLE_COORDENADOR");
                userUtil.repository.save(coordenador);
            }

            // Verifica se o usuário Aluno já existe no banco de dados
            Optional<Usuario> existingAluno = userUtil.repository.findByEmail("aluno@gmail.com");
            if (existingAluno.isEmpty()) {
                Usuario aluno = new Usuario();
                aluno.setNome("Aluno");
                aluno.setEmail("aluno@gmail.com");
                aluno.setSenha(passwordEncoder().encode("1234"));
                aluno.setTelefone("555555555");
                //aluno.getUserType().add(UserType.ALUNO);
                aluno.getPermissoes().add("ROLE_USER");
                aluno.getPermissoes().add("ROLE_ALUNO");
                userUtil.repository.save(aluno);
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
        registry.addInterceptor(new LoginInterceptor(userServices,  userUtil.repository, jwtUtil))
                .excludePathPatterns("/error**", "/index**", "/doc**", "/auth**", "/swagger-ui**")
                .addPathPatterns("/coordenacao/tcc/v1/apresentacao",
                        "/coordenacao/tcc/v1/apresentacao/**",
                        "/coordenacao/tcc/v1/apresentacoes",
                        "/coordenacao/tcc/v1/coordenador/alunos",
                        "/coordenacao/tcc/v1/coordenador/professor",
                        "/coordenacao/tcc/v1/coordenador/professores",
                        "/coordenacao/tcc/v1/coordenador/usuario/**",
                        "/coordenacao/tcc/v1/Professor/usuario/",
                        "/coordenacao/tcc/v1/Professor/alunos",
                        "/coordenacao/tcc/v1/Professor/aluno",
                        "/coordenacao/tcc/v1/Professor/aluno/**",
                        "/coordenacao/tcc/v1/Professor/",
                        "/coordenacao/tcc/v1/agenda/**",
                        "/coordenacao/tcc/v1/agenda",
                        "/coordenacao/tcc/v1/agendas",
                        "/coordenacao/tcc/v1/tcc",
                        "/coordenacao/tcc/v1/tccs",
                        "/coordenacao/tcc/v1/tcc/**",
                        "/coordenacao/tcc/v1/usuario/**",
                        "/coordenacao/tcc/v1/usuario",
                        "/coordenacao/tcc/v1/usuarios"
                        );
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
    }

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
    }

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> handlers) {
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
    }

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
    }

    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
    }
}