package org.akj.springboot.rest.delegate.bean;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ServerInfo {
    String url;

    String host;

    int port;
}
