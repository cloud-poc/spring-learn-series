package org.akj.springboot.common.exception;

import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.akj.springboot.common.constant.ErrorCodeConstant;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;

@Component
@Order(-2)
@Slf4j
public class GlobalWebExceptionHandler implements ErrorWebExceptionHandler {

    private String getExceptionString(WebExchangeBindException ex) {
        return ex.getFieldErrors().stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .reduce("", (s1, s2) -> s1 + " \n " + s2);
    }


    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        if (ex instanceof ServerWebInputException) {
            BaseResponse response = BaseResponse.builder().code(ErrorCodeConstant.TECHNICAL_EXCEPTION_GENERIC)
                    .message(ex.toString())
                    .build();
            return handleException(exchange, response, HttpStatus.BAD_REQUEST, MediaType.APPLICATION_JSON);
        } else if (ex instanceof WebExchangeBindException) {
            BaseResponse response = BaseResponse.builder().code(ErrorCodeConstant.TECHNICAL_EXCEPTION_GENERIC)
                    .message(getExceptionString((WebExchangeBindException) ex))
                    .build();
            return handleException(exchange, response, HttpStatus.BAD_REQUEST, MediaType.APPLICATION_JSON);
        } else if (ex instanceof TechnicalException) {
            BaseResponse response = BaseResponse.builder().code(ErrorCodeConstant.TECHNICAL_EXCEPTION)
                    .message(ex.toString())
                    .build();
            handleException(exchange, response, HttpStatus.CONFLICT, MediaType.APPLICATION_JSON);
        } else if (ex instanceof BusinessException) {
            BaseResponse response = BaseResponse.builder().code(ErrorCodeConstant.BUSINESS_EXCEPTION)
                    .message(ex.toString())
                    .build();
            handleException(exchange, response, HttpStatus.INTERNAL_SERVER_ERROR, MediaType.APPLICATION_JSON);
        } else if (ex instanceof Exception) {
            BaseResponse response = BaseResponse.builder().code(ErrorCodeConstant.TECHNICAL_EXCEPTION_GENERIC)
                    .message(ex.toString())
                    .build();
            return handleException(exchange, response, HttpStatus.INTERNAL_SERVER_ERROR, MediaType.APPLICATION_JSON);
        }

        BaseResponse response = BaseResponse.builder().code(ErrorCodeConstant.TECHNICAL_EXCEPTION_GENERIC)
                .message("Service is unavailable, please retry it later")
                .build();
        return handleException(exchange, response, HttpStatus.SERVICE_UNAVAILABLE, MediaType.APPLICATION_JSON);
    }

    @SneakyThrows
    public Mono<Void> handleException(ServerWebExchange serverWebExchange, BaseResponse body, HttpStatus status,
                                      MediaType contentType) {
        ServerHttpResponse response = serverWebExchange.getResponse();
        response.setStatusCode(status);
        response.getHeaders().setContentType(contentType);
        JsonMapper mapper = new JsonMapper();

        DataBuffer db = response.bufferFactory().wrap(mapper.writeValueAsString(body).getBytes(Charset.forName("UTF-8")));
        return response.writeWith(Mono.just(db));
    }
}
