package com.yy.utils;


import com.mysql.jdbc.StringUtils;
import com.yy.excption.ServiceException;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import static com.baomidou.mybatisplus.core.toolkit.StringUtils.endsWith;
import static com.yy.common.RestResult.ERROR_MSG;
import static com.yy.common.RestResult.RESULT;


public class ResponseUtils {

    public static Map<String, Object> ofError(ServiceException exception) {
        Map<String, Object> object = new HashMap<>();
        int code = exception.getCode();
        object.put(RESULT, code);
        object.put(ERROR_MSG, exception.getMessage());
        return object;
    }

    public static void output(OutputStream response, String data) throws IOException {
        org.apache.commons.io.IOUtils.write(data, response, "UTF-8");
    }

    /**
     * 输出指定字符串
     */
    public static void output(HttpServletResponse response, String text) {
        try {
            //todo:StringUtils.startsWith(text.getBytes(), "{") 缺少start weith
            if (true && endsWith(text, "}")) {
                response.setContentType("application/json; charset=utf-8");
            } else {
                response.setContentType("text/html;charset=utf-8");
            }
            PrintWriter pw = response.getWriter();
            if (pw == null) {
                return;
            }
            pw.print(text);
            pw.flush();
            pw.close();
        } catch (Exception e) {
        }
    }

}
