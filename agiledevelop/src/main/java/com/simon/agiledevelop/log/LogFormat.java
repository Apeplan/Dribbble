package com.simon.agiledevelop.log;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * describe:
 *
 * @author Simon Han
 * @date 2016.11.17
 * @email hanzx1024@gmail.com
 */

public class LogFormat {


    private static final char VERTICAL_BORDER_CHAR = '║';

    // Length: 100.
    private static final String TOP_HORIZONTAL_BORDER =
            "╔═════════════════════════════════════════════════" +
                    "══════════════════════════════════════════════════";

    // Length: 99.
    private static final String DIVIDER_HORIZONTAL_BORDER =
            "╟─────────────────────────────────────────────────" +
                    "──────────────────────────────────────────────────";

    // Length: 100.
    private static final String BOTTOM_HORIZONTAL_BORDER =
            "╚═════════════════════════════════════════════════" +
                    "══════════════════════════════════════════════════";

    private static final int JSON_INDENT = 4;
    private static final int XML_INDENT = 4;

    public static String formatThrowable(Throwable tr) {
        return StackTraceUtil.getStackTraceString(tr);
    }

    /**
     * 格式化现成信息
     *
     * @param data
     * @return
     */
    public static String formatThread(Thread data) {
        return "Thread: " + data.getName();
    }

    /**
     * 格式化堆栈信息
     *
     * @param stackTrace
     * @return
     */
    @Nullable
    public static String formatStackTrace(StackTraceElement[] stackTrace) {
        StringBuilder sb = new StringBuilder(256);
        if (stackTrace == null || stackTrace.length == 0) {
            return null;
        } else if (stackTrace.length == 1) {
            return "\t─ " + stackTrace[0].toString();
        } else {
            for (int i = 0, N = stackTrace.length; i < N; i++) {
                if (i != N - 1) {
                    sb.append("\t├ ");
                    sb.append(stackTrace[i].toString());
                    sb.append(lineSeparator());
                } else {
                    sb.append("\t└ ");
                    sb.append(stackTrace[i].toString());
                }
            }
            return sb.toString();
        }
    }

    /**
     * 格式化 JSON 数据
     *
     * @param json
     * @return
     */
    public static String formatJSON(String json) {
        String formattedString = null;
        if (json == null || json.trim().length() == 0) {
            throw new FormatException("JSON empty.");
        }
        try {
            if (json.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(json);
                formattedString = jsonObject.toString(JSON_INDENT);
            } else if (json.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(json);
                formattedString = jsonArray.toString(JSON_INDENT);
            } else {
                throw new FormatException("JSON should start with { or [, but found " + json);
            }
        } catch (Exception e) {
            throw new FormatException("Parse JSON error. JSON string:" + json, e);
        }
        return formattedString;
    }

    /**
     * 格式化 XML 数据
     *
     * @param xml
     * @return
     */
    public static String formatXML(String xml) {
        String formattedString;
        if (xml == null || xml.trim().length() == 0) {
            throw new FormatException("XML empty.");
        }
        try {
            Source xmlInput = new StreamSource(new StringReader(xml));
            StreamResult xmlOutput = new StreamResult(new StringWriter());
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount",
                    String.valueOf(XML_INDENT));
            transformer.transform(xmlInput, xmlOutput);
            formattedString = xmlOutput.getWriter().toString().replaceFirst(">", ">"
                    + lineSeparator());
        } catch (Exception e) {
            throw new FormatException("Parse XML error. XML string:" + xml, e);
        }
        return formattedString;
    }

    /**
     * 格式化 LogCat 信息的边框
     *
     * @param segments
     * @return
     */
    public static String formatBorder(String[] segments) {
        if (segments == null || segments.length == 0) {
            return "";
        }

        String[] nonNullSegments = new String[segments.length];
        int nonNullCount = 0;
        for (String segment : segments) {
            if (segment != null) {
                nonNullSegments[nonNullCount++] = segment;
            }
        }
        if (nonNullCount == 0) {
            return "";
        }

        StringBuilder msgBuilder = new StringBuilder();
        msgBuilder.append(TOP_HORIZONTAL_BORDER).append(lineSeparator());
        for (int i = 0; i < nonNullCount; i++) {
            msgBuilder.append(appendVerticalBorder(nonNullSegments[i]));
            if (i != nonNullCount - 1) {
                msgBuilder.append(lineSeparator()).append(DIVIDER_HORIZONTAL_BORDER)
                        .append(lineSeparator());
            } else {
                msgBuilder.append(lineSeparator()).append(BOTTOM_HORIZONTAL_BORDER);
            }
        }
        return msgBuilder.toString();
    }

    /**
     * 为每行信息，添加一个垂直的线
     *
     * @param msg the message to add border
     * @return the message with {@value #VERTICAL_BORDER_CHAR} in the start of each line
     */
    private static String appendVerticalBorder(String msg) {
        StringBuilder borderedMsgBuilder = new StringBuilder(msg.length() + 10);
        String[] lines = msg.split(lineSeparator());
        for (int i = 0, N = lines.length; i < N; i++) {
            if (i != 0) {
                borderedMsgBuilder.append(lineSeparator());
            }
            String line = lines[i];
            borderedMsgBuilder.append(VERTICAL_BORDER_CHAR).append(line);
        }
        return borderedMsgBuilder.toString();

    }

    /**
     * 行间距
     *
     * @return
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String lineSeparator() {
        try { // No need to detect whether current platform is android, just do it with catching.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                return System.lineSeparator();
            } else {
                return "\n";
            }
        } catch (Exception e) {
            return "\n";
        }
    }

}
