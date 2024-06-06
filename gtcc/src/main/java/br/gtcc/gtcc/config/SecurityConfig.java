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
import br.gtcc.gtcc.util.JWTUtil;

@Configuration
@EnableWebMvc
public class SecurityConfig implements CommandLineRunner, WebMvcConfigurer {
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
            Optional<Users> existingAdmin = usersRepository.findByLogin("admin");
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
                usersRepository.save(admin);
            }

            // Verifica se o usuário Professor já existe no banco de dados
            Optional<Users> existingProfessor = usersRepository.findByLogin("professor");
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
                usersRepository.save(professor);
            }

            // Verifica se o usuário Coordenador já existe no banco de dados
            Optional<Users> existingCoordinator = usersRepository.findByLogin("coordenador");
            if (existingCoordinator.isEmpty()) {
                Users coordinator = new Users();
                coordinator.setName("Coordenador");
                coordinator.setEmail("coordenador@gmail.com");
                coordinator.setSenha(passwordEncoder().encode("1234"));
                coordinator.setCellphone("444444444");
                coordinator.getUserType().add(UserType.COORDENADOR);
                coordinator.setLogin("coordenador");
                coordinator.getPermissoes().add("ROLE_USER");
                coordinator.getPermissoes().add("ROLE_COORDENADOR");
                usersRepository.save(coordinator);
            }

            // Verifica se o usuário Aluno já existe no banco de dados
            Optional<Users> existingAluno = usersRepository.findByLogin("aluno");
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
                usersRepository.save(aluno);
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
        registry.addInterceptor(new LoginInterceptor(usersRepository, jwtUtil))
                .excludePathPatterns("/error**", "/index**", "/doc**", "/auth**", "/swagger-ui**", "/v3/api-docs**")
                .addPathPatterns("/coordenacao/tcc/v1/tcc**", "/coordenacao/tcc/v1/users**",
                        "/coordenacao/tcc/v1/apresentacao**", "/coordenacao/tcc/v1/data**",
                        "/users-controller/createUser/");
    }
}