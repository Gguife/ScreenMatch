package br.com.gguife.screenmatch.service;

public interface IConvertData {
    <T> T getData(String json, Class<T> tClass);
}
