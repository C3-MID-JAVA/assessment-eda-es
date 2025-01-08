package ec.com.sofka.adapter;

/*
//@Repository
public class LogAdapter implements LogRepository {

    private final IMongoLogRepository repository;

    public LogAdapter(IMongoLogRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<Void> create(Log log) {
        LogEntity logEntity = new LogEntity(log.getMessage(), log.getEntity(), log.getTimestamp());
        return repository.save(logEntity).then();
    }
}*/
