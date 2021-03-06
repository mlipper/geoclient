package gov.nyc.doitt.gis.geoclient.service.mapper;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractParameterMapper<T> implements Mapper<T> {

    private final static Logger LOGGER = LoggerFactory.getLogger(AbstractParameterMapper.class);

    @Override
    public T fromParameters(Map<String, Object> source, Class<T> destinationClass) throws MappingException {
        return fromParameters(source, newInstance(destinationClass));
    }

    @Override
    public abstract T fromParameters(Map<String, Object> source, T destination) throws MappingException;

    @Override
    public Map<String, Object> toParameters(T source) throws MappingException {
        return toParameters(source, new HashMap<>());
    }

    @Override
    public abstract Map<String, Object> toParameters(T source, Map<String, Object> destination)
            throws MappingException;

    protected T newInstance(Class<T> clazz) throws MappingException {
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            String className = clazz.getCanonicalName();
            String msg = String.format("Error creating instance of class %s using Class<%s>#newInstance()", className,
                    clazz.getSimpleName());
            LOGGER.error(e.getMessage());
            throw new MappingException(msg, e);
        }
    }
}