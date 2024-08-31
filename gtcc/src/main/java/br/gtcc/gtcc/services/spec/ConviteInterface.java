package br.gtcc.gtcc.services.spec;

import java.util.List;

public interface ConviteInterface<T, E> {

    public T enviarConvite(T convite);

    public T aceitarConvite(E conviteId);

    public T visualizarConvite(E conviteId);

    public T recusarConvite(E conviteId);

    public T atualizarConvite(T convite, E conviteId);

    public T deletarConvite(E conviteId);

    public List<T> getTodosConvites();

    public T getConvite(E conviteId);

    public List<T> getConvitesPendentes();

    public List<T> getConvitesAceitos();

    public List<T> getConvitesRecusados();

    public List<T> getConvitesVisualizados();
}
