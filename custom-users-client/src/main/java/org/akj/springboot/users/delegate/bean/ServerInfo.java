package org.akj.springboot.users.delegate.bean;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ServerInfo {
    String url;

    String host;

    int port;
}
