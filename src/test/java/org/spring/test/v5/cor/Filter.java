package org.spring.test.v5.cor;

public interface Filter {
    boolean doFilter(Msg msg, FilterChain filterChain);
}
