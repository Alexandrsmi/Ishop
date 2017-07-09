package ru.ishop.frontend.util;

import org.apache.commons.fileupload.FileItem;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Aleksandr Smirnov.
 */
public class Util {
    public static final int PRODUCTS_PAGE_COUNT = 10;

    public static int getIntParameter(HttpServletRequest request, String parameterName, int defaultValue) {
        String tmp = request.getParameter(parameterName);
        if (tmp != null) {
            return Integer.parseInt(tmp);
        }
        return defaultValue;
    }

    public static long getLongParameter(HttpServletRequest request, String parameterName, long defaultValue) {
        String tmp = request.getParameter(parameterName);
        if (tmp != null) {
            return Long.parseLong(tmp);
        }
        return defaultValue;
    }

    public static double getDoubleParameter(HttpServletRequest request, String parameterName, double defaultValue) {
        String tmp = request.getParameter(parameterName);
        if (tmp != null) {
            return Double.parseDouble(tmp);
        }
        return defaultValue;
    }

    public static double getDoubleParameter(FileItem fileItem, double defaultValue) {
        String tmp = fileItem != null ? fileItem.getString() : null;
        if (tmp != null) {
            return Double.parseDouble(tmp);
        }
        return defaultValue;
    }

    public static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
