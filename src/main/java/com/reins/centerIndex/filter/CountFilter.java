package com.reins.centerIndex.filter;

import lombok.extern.log4j.Log4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@Component

public class CountFilter implements Filter {
    AtomicLong bitSizeRealTime = new AtomicLong();
    AtomicLong bitSizeLongTime = new AtomicLong();

    private static final Logger logger = LoggerFactory.getLogger(CountFilter.class);

        @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        long length = servletRequest.getContentLength();
        if(Objects.equals(request.getServletPath(), "/statistic/realtime"))
        {
            bitSizeRealTime.getAndAdd(length);
            logger.info("RealTime, " + bitSizeRealTime.get());
        }else if(Objects.equals(request.getServletPath(), "/statistic/longTimeStatistic")){
            bitSizeLongTime.getAndAdd(length);
            logger.info("LongTime, " + bitSizeLongTime.get());
        }


        filterChain.doFilter(servletRequest, servletResponse);
    }
}


