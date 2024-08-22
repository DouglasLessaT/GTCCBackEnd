package br.gtcc.gtcc.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import br.gtcc.gtcc.model.mysql.Grupo;

public class GrupoConfig {

        private List<Grupo> grupoAdmin;
        private List<Grupo> grupoAluno;
        private List<Grupo> grupoCoodenador;
        private List<Grupo> grupoProfessor;

        public GrupoConfig() {
                this.grupoAdmin = new ArrayList<>();
                this.grupoCoodenador = new ArrayList<>();
                this.grupoAluno = new ArrayList<>();
                this.grupoProfessor = new ArrayList<>();
        }

        public void adicionarGrupoAdmin(Grupo grupo) {
                this.grupoAdmin.add(grupo);
        }

        public void adicionarGrupoAluno(Grupo grupo) {
                this.grupoAluno.add(grupo);
        }

        public void adicionarGrupoCoodenador(Grupo grupo) {
                this.grupoCoodenador.add(grupo);
        }

        public void adicionarGrupoProfessor(Grupo grupo) {
                this.grupoProfessor.add(grupo);
        }

        public List<Grupo> listarGrupoAdmin() {
                return this.grupoAdmin;
        }

        public List<Grupo> listarGrupoAluno() {
                return this.grupoAluno;
        }

        public List<Grupo> listarGrupoCoodenadorgrupoCoodenador() {
                return this.grupoCoodenador;
        }

        public List<Grupo> listarGrupoCoodenadorgrupoProfessor() {
                return this.grupoProfessor;
        }

        public Grupo encontrarGrupoAdminPorNome(String nome) {
                return this.grupoAdmin.stream()
                                .filter(grupo -> grupo.getNome().equalsIgnoreCase(nome))
                                .findFirst()
                                .orElse(null);
        }

        public Grupo encontrarGrupoAlunoPorNome(String nome) {
                return this.grupoAluno.stream()
                                .filter(grupo -> grupo.getNome().equalsIgnoreCase(nome))
                                .findFirst()
                                .orElse(null);
        }

        public Grupo encontrarGrupoCoordenadorPorNome(String nome) {
                return this.grupoCoodenador.stream()
                                .filter(grupo -> grupo.getNome().equalsIgnoreCase(nome))
                                .findFirst()
                                .orElse(null);
        }

        public Grupo encontrarGrupoProfessorPorNome(String nome) {
                return this.grupoProfessor.stream()
                                .filter(grupo -> grupo.getNome().equalsIgnoreCase(nome))
                                .findFirst()
                                .orElse(null);
        }

}
