package org.akj.springboot.common.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.akj.springboot.common.constant.CustomHttpStatus;
import org.akj.springboot.common.constant.ErrorCodeConstant;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.util.WebUtils;

@ControllerAdvice
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<Object> handleException(Exception ex, HttpServletRequest httpServletRequest,
                                                  HttpServletResponse httpServletResponse) {
        log.error("method:{}, message:{}.", "handleException", ex);
        ServletWebRequest request = new ServletWebRequest(httpServletRequest);
        try {
            super.handleException(ex, request);
        } catch (TechnicalException e) {
            return handleTechnicalException(e, httpServletResponse);
        } catch (BusinessException e) {
            return handleBusinessException(e, httpServletResponse);
        } catch (Exception e) {
            return handleExceptionInternal(e, null, null, HttpStatus.INTERNAL_SERVER_ERROR, request);
        }

        return null;
    }

    private ResponseEntity<Object> handleTechnicalException(TechnicalException ex, HttpServletResponse httpServletResponse) {
        httpServletResponse.setStatus(HttpStatus.CONFLICT.value());

        BaseResponse response = BaseResponse.builder().code(ErrorCodeConstant.TECHNICAL_EXCEPTION)
                .message(null == ex.getMessage() || ex.getMessage().trim().isEmpty() ? ExceptionUtils.getStackTrace(ex) : ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    private ResponseEntity<Object> handleBusinessException(BusinessException ex, HttpServletResponse httpServletResponse) {
        httpServletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

        BaseResponse response = BaseResponse.builder().code(ErrorCodeConstant.BUSINESS_EXCEPTION)
                .message(null == ex.getMessage() || ex.getMessage().trim().isEmpty() ? ExceptionUtils.getStackTrace(ex) : ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status,
                                                             WebRequest request) {
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        }

        // construct base response object
        BaseResponse response = BaseResponse.builder()
                .body(body)
                .message(null == ex.getMessage() || ex.getMessage().trim().isEmpty() ? ExceptionUtils.getStackTrace(ex) : ex.getMessage())
                .code(ErrorCodeConstant.TECHNICAL_EXCEPTION_GENERIC).build();

        return new ResponseEntity<>(response, headers, status);
    }
}
