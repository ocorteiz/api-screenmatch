package com.ocorteiz.apiscreenmatch.service;

public interface IConverterDados {
    <T> T obterDados(String json, Class<T> tClass);
}
