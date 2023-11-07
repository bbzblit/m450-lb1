package dev.bbzblit.m450.service;

public interface ParentService<T, ID>{

    public T save(T entity);

    public T findById(ID id);

    public Iterable<T> findAll();

    public void deleteById(ID id);

}
