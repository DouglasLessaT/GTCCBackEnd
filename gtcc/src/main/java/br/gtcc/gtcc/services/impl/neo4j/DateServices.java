package br.gtcc.gtcc.services.impl.neo4j;

import br.gtcc.gtcc.services.spec.DateInterface;
import br.gtcc.gtcc.model.neo4j.Date;
import br.gtcc.gtcc.model.neo4j.repository.DateRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DateServices implements DateInterface<Date, String> {

    @Autowired
    public DateRepository repository;

    @Override
    public Date createDate(Date date) {
        return null;
    }

    @Override
    public Date updateDate(Date Date) {
        return null;
    }

    @Override
    public Date deleteDate(String date) {
        return null;
    }

    @Override
    public Date getDate(String date) {
        return null;
    }

    @Override
    public List<Date> getAllDate() {
        return null;
    }

}
